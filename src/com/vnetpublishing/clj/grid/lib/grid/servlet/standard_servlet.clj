(ns com.vnetpublishing.clj.grid.lib.grid.servlet.standard-servlet
    (:gen-class
        :name com.vnetpublishing.clj.grid.lib.grid.servlet.StandardServlet
        :init cl-init
        :state state
        :extends javax.servlet.http.HttpServlet
        :methods [[osgiInit [Object] void]])
    (:require [grid.dispatcher]
              [com.vnetpublishing.clj.grid.lib.grid.osgi.webapp-system-activator :as activator]
              [com.vnetpublishing.clj.grid.lib.grid.servlet.core :as core]
              [clojure.string :as string]
              [com.vnetpublishing.clj.grid.lib.grid.common.constants :as constants]
              [com.vnetpublishing.clj.grid.lib.grid.webapp.servlet-request-wrapper :as servlet-request-wrapper]
              [com.vnetpublishing.clj.grid.lib.grid.kernel :refer :all]
              [com.vnetpublishing.clj.grid.lib.grid.osgi.functions :refer :all])
    (:import [com.vnetpublishing.clj.grid.lib.grid.webapp ServletRequestWrapper]
             [com.vnetpublishing.clj.grid.lib.grid.common.osgi Logger]
             [org.apache.felix.framework.util FelixConstants]
             [javax.servlet ServletContext]))

(def OSGI_CONTEXT_ATTRIBUTE "com.vnetpublishing.clj.grid.lib.grid.servlet.standard-servlet.osgi-context")

(defn handle-osgi-start
  [servlet ctx]
  (.osgiInit servlet ctx))

(defn -init
  [this servletconfig-in]
  (binding [*debug-kernel* true]
    (let [servletconfig (create-instance com.vnetpublishing.clj.grid.lib.grid.webapp.ServletConfigWrapper [] servletconfig-in) 
          fwc (new com.vnetpublishing.clj.grid.lib.grid.osgi.FrameworkContainer)
          activator (create-instance com.vnetpublishing.clj.grid.lib.grid.osgi.WebappSystemActivator [])
          log (Logger.)]
         (.setLogLevel log (int 4))
         (swap! (.state this) 
                assoc 
                :servlet-config
                servletconfig)
         (.setStartHandler activator (partial handle-osgi-start this))
         (.setConfigVar fwc
                        "felix.cache.rootdir" 
                        (.getRealPath (.getServletContext servletconfig)
                                      "WEB-INF/cache"))
         (.setConfigVar fwc
                        FelixConstants/LOG_LEVEL_PROP
                        "4")
         (.setConfigVar fwc
                        FelixConstants/LOG_LOGGER_PROP
                        log)
         (.setConfigVar fwc
                        "org.osgi.framework.system.packages.extra"
                        (string/join "," constants/default-osgi-exports))
         (.setConfigVar fwc
                        "org.osgi.framework.storage.clean"
                        "onFirstInit")
         (future (binding [*local-web-root* (str (.getRealPath (.getServletContext (.getServletConfig this)) "/")
             *ds*)]
         (.launch fwc activator)))
         (debug "-init complete!"))))

(defn -getServletConfig
  [this]
    (:servlet-config (deref (.state this))))

(defn -osgiInit
  [this ctx]
    (.setAttribute (.getServletContext (.getServletConfig this))
                   OSGI_CONTEXT_ATTRIBUTE
                   ctx)
    (debug "-osgiInit Complete"))

(defn -doPost
  [this request-in response]
  (let [request (create-instance ServletRequestWrapper [] this request-in)
        ctx (.getServletContext (.getServletConfig this))]
       (binding [*compile-path* (.getAttribute ctx ServletContext/TEMPDIR)
                 *local-web-root* (str (.getRealPath ctx "/")
                                       *ds*)
                 *debug-kernel* true
                 *inc* (atom [""]) ;alt done by exec activator
                 *servlet-request* request 
                 *servlet-response* response
                 core/*servlet* this
                 *osgi-context* (.getAttribute ctx
                                               OSGI_CONTEXT_ATTRIBUTE)]
         (if *osgi-context*
             (debug "-doPost Have OSGi Context")
             (debug "-doPost Missing OSGi Context"))
         (let [path (servlet-request-resource-path)
               r (get-resource path)]
           (if (and r
                    ; and not directory?
                    (not (.endsWith (.toString r) "/")))
               ; Deliver static resource
               (do  (debug (str "Found resource: " r))
                    (.forward (.getRequestDispatcher *servlet-request* path) 
                         *servlet-request* 
                         *servlet-response*))
               ; Dispatch web-application
               (do (.setContentType *servlet-response* "text/html;charset=utf-8")
                   (core/dispatch grid.dispatcher/dispatch)))))))

(defn -doGet
  [this request response]
  (.doPost this request response))

(defn -cl-init
  []
  [[] (atom {})])

(defn -destroy
  [this]
  (let [servletconfig (.getServletConfig this)
        ctx (.getAttribute (.getServletContext servletconfig)
                           OSGI_CONTEXT_ATTRIBUTE)]
    (if ctx
        (framework-stop (.getBundle ctx)))))
  
