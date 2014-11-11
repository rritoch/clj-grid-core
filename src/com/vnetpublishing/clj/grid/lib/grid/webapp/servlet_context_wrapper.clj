(ns com.vnetpublishing.clj.grid.lib.grid.webapp.servlet-context-wrapper
  (:gen-class 
    :name com.vnetpublishing.clj.grid.lib.grid.webapp.ServletContextWrapper
    :extends com.vnetpublishing.clj.grid.lib.mvc.base.Object
    :methods [[postConstructHandler [javax.servlet.ServletConfig javax.servlet.ServletContext] void]]
    :implements [javax.servlet.ServletContext])
  (:import [javax.servlet Filter])
  (:use [com.vnetpublishing.clj.grid.lib.grid.kernel]
        [com.vnetpublishing.clj.grid.lib.mvc.engine]))

(defmulti -addFilter)

(defmethod -addFilter [String Class] 
  [this filter-name clz] 
    (let [filters (:filters (deref (.state this)))]
         (if (not (get @filters filter-name))
             (do 
                 (swap! filters assoc filter-name (.createFilter this clz))
                 (create-instance com.vnetpublishing.clj.grid.lib.grid.webapp.FilterRegistration$Dynamic
                                  []
                                  [this (get @filters filter-name)])))))
      
(defmethod -addFilter [String Filter] 
  [this filter-name filter] 
  (let [filters (:filters (deref (.state this)))]
         (if (not (get @filters filter-name))
             (do 
                 (swap! filters assoc filter-name filter)
                 (create-instance com.vnetpublishing.clj.grid.lib.grid.webapp.FilterRegistration$Dynamic
                                  []
                                  [this (get @filters filter-name)])))))

(defmethod -addFilter [String String] [this filter-name clz-name] 
  (let [filters (:filters (deref (.state this)))
        clz (resolve clz-name)]
         (if (not (get @filters filter-name))
             (do 
                 (swap! filters assoc filter-name (.createFilter this clz))
                 (create-instance com.vnetpublishing.clj.grid.lib.grid.webapp.FilterRegistration$Dynamic
                                  []
                                  [this (get @filters filter-name)])))))

(defn -createFilter
  [this clz]
    (create-instance clz []))

(defn -getAttribute
  [this name]
    (.getAttribute (.get this "_servletcontext")
                   name))

(defn -getAttributeNames
  [this]
    (.getAttributeNames (.get this "_servletcontext")))

          
; Note: Shouldn't we be wrapping this?
(defn -getContext
  [this uripath]
    (.getContext (.get this "_servletcontext")
                   uripath))

(defn -getContextPath
  [this]
    (.getContextPath (.get this "_servletcontext")))

(defn -getFilterRegistration
  [this filter-name]
    (let [filters (:filters (deref (.state this)))]
         (if (get @filters filter-name)
             (create-instance com.vnetpublishing.clj.grid.lib.grid.webapp.FilterRegistration$Dynamic
                         []
                         [this (get @filters filter-name)]))))

(defn -getFilterRegistrations
  [this]
    (let [filters (deref (:filters (deref (.state this))))]
         (zipmap (keys filters) (map (partial (fn [ob f] 
                                                  (.getFilterRegistration ob f))
                                              this)
                                     (keys filters)))))

(defn -getInitParameter
  [this name]
    (.getInitParameter (.get this "_servletcontext")
                       name))

(defn -getInitParameterNames
  [this]
    (.getInitParameterNames (.get this "_servletcontext")))

(defn -getMajorVersion
  [this]
    (.getMajorVersion (.get this "_servletcontext")))

(defn -getMimeType
  [this file]
    (.getInitParameter (.get this "_servletcontext")
                       file))


(defn -getMinorVersion
  [this]
    (.getMinorVersion (.get this "_servletcontext")))

; Shouldn't we be wrapping this?
(defn -getNamedDispatcher
  [this name]
    (.getNamedDispatcher (.get this "_servletcontext")
                         name))

(defn -getRealPath
  [this path]
    (.getRealPath (.get this "_servletcontext")
                        path))

(defn -getRequestDispatcher
  [this path]
  (create-instance com.vnetpublishing.clj.grid.lib.grid.jsp.RequestDispatcher [] path))

(defn -getResource
  [this path]
    (get-resource path))

(defn -getResourceAsStream [this path]
  (some-> (get-resource path) .openStream))

; Shouldn't we be implementing this?
(defn -getResourcePaths
  [this path]
    (.getResourcePaths (.get this "_servletcontext")
                        path))

(defn -getClassLoader
  [this]
  (java.lang.ClassLoader/getSystemClassLoader))

(defn -getServerInfo
  [this]
     (.getServerInfo (.get this "_servletcontext")))

#_(defn -getServlet
  [this name]
    (.getServlet (.get this "_servletcontext")
                   name))

(defn -createServlet
  [this c]
  (let [servlet (.newInstance c)]
     (.init servlet (:config (deref (.state this))))
     servlet))

(defn -addServlet
   [this servlet-name servlet-ref]
   (let [servlet (cond (instance? String servlet-ref)
                       (.createServlet this
                                       (resolve (symbol servlet-ref)))
                       (instance? Class servlet-ref)
                       (.createServlet this
                                       servlet-ref)
                       :else
                       servlet-ref)]
     (swap! (:servlets (deref (.state this))) assoc servlet-name servlet))
   
   nil)

(defn -getServlet
  [this name]
    (get (deref (:servlets (deref (.state this)))) name))

(defn -getServletContextName
  [this]
     (.getServletContextName (.get this "_servletcontext")))

(defn -getServletNames
  [this]
     (.getServletNames (.get this "_servletcontext")))

(defn -getServlets
  [this]
     (.getServlets (.get this "_servletcontext")))

(defn -log
  ([this arg0]
     (.log (.get this "_servletcontext")
           arg0))
  ([this arg0 arg1]
     (.log (.get this "_servletcontext")
           arg0
           arg1)))

(defn -removeAttribute
  [this name]
    (.removeAttribute (.get this "_servletcontext")
                      name))

(defn -setAttribute
  [this name obj]
    (.setAttribute (.get this "_servletcontext")
                      name
                      obj))

(defn -postConstructHandler
  [this servletconfig servletcontext]
    (swap! (.state this) assoc :config servletconfig)
    (swap! (.state this) assoc :servlets (atom {}))
    (swap! (.state this) assoc :filters (atom {}))
    (assign this ["_servletcontext" servletcontext]))
