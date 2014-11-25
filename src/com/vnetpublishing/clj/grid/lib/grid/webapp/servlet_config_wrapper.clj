(ns com.vnetpublishing.clj.grid.lib.grid.webapp.servlet-config-wrapper
  (:gen-class :name com.vnetpublishing.clj.grid.lib.grid.webapp.ServletConfigWrapper
              :methods [[postConstructHandler [javax.servlet.ServletConfig] void]]
              :implements [javax.servlet.ServletConfig]
              :state state
              :init init)
  (:require [com.vnetpublishing.clj.grid.lib.grid.webapp.servlet-context-wrapper]
            [com.vnetpublishing.clj.grid.lib.grid.http-mapper :as http-mapper]
            [com.vnetpublishing.clj.grid.lib.grid.kernel :refer :all]))


(defn -getInitParameter
  [this name]
    (.getInitParameter (:servletconfig (deref (.state this))) name))

(defn -getInitParameterNames
  [this]
    (.getInitParameterNames (:servletconfig (deref (.state this)))))

(defn -getServletName
  [this]
    (.getServletName (:servletconfig (deref (.state this)))))

(defn -getServletContext
  [this]
    (:servletcontext (deref (.state this))))

(defn -postConstructHandler
  [this servletconfig]
  (let [servletcontext (create-instance com.vnetpublishing.clj.grid.lib.grid.webapp.ServletContextWrapper [] this (.getServletContext servletconfig))]
    
    (swap! (.state this) assoc :servletconfig servletconfig)
    (swap! (.state this) assoc :servletcontext servletcontext)

    (http-mapper/add-suffix-mapping ".jsp" 
                                    (resolve (symbol "org.apache.jasper.servlet.JspServlet")))
    (http-mapper/add-suffix-mapping ".clj" 
                                    (resolve (symbol"com.vnetpublishing.clj.grid.lib.grid.servlet.CljServlet")))
    (http-mapper/set-default-mapping (resolve (symbol "com.vnetpublishing.clj.grid.lib.grid.servlet.DefaultServlet")))
    
    (.addServlet (.getServletContext this)
               "jsp"
               "org.apache.jasper.servlet.JspServlet")
    (.addServlet (.getServletContext this)
               "clj"
               "com.vnetpublishing.clj.grid.lib.grid.servlet.CljServlet")
    (.addServlet (.getServletContext this)
               "default"
               "com.vnetpublishing.clj.grid.lib.grid.servlet.DefaultServlet")))

(defn -init
  []
    [[] (atom {})])

