(ns com.vnetpublishing.clj.grid.lib.grid.servlet.clj-servlet
    (:gen-class
        :name com.vnetpublishing.clj.grid.lib.grid.servlet.CljServlet
        :init cl-init
        :state state
        :extends javax.servlet.http.HttpServlet)
    (:require [clojure.string :as string]
              [clojure.java.io :as io]
              [com.vnetpublishing.clj.grid.lib.grid.servlet.core :as core]
              [com.vnetpublishing.clj.grid.lib.grid.kernel :refer :all])
    (:import [com.vnetpublishing.clj.grid.lib.grid.webapp ServletRequestWrapper]
             [javax.servlet ServletContext]
             [javax.servlet.http HttpServletResponse]))


(defn -init
  [this servletconfig-in]
    (swap! (.state this) 
                assoc 
                :servlet-config
                servletconfig-in)
    (debug "-init complete!"))

(defn -getServletConfig
  [this]
    (:servlet-config (deref (.state this))))

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
                 *dispatch* true
                 *transaction* (gen-transaction-state)
                 *out* (.getWriter response)]
         (if *osgi-context*
             (debug "-doPost Have OSGi Context")
             (debug "-doPost Missing OSGi Context"))
         (let [path (servlet-request-resource-path)
               path-1 (clojure.string/replace (subs path 1) "/" *ds*)
               serve-content true
               resource (get-resource path)
               content-type "text/html;charset=utf-8"]
              (if resource
                  (do (.setContentType *servlet-response* 
                          content-type)
                      ;; Write output!
                      (if serve-content
                          (frequire path-1 (path-to-ns path))))
                  (.sendError response HttpServletResponse/SC_NOT_FOUND))))))

(defn -doGet
  [this request response]
  (.doPost this request response))

(defn -cl-init
  []
  [[] (atom {})])