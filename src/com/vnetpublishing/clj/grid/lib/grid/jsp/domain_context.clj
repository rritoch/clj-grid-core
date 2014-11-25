(ns com.vnetpublishing.clj.grid.lib.grid.jsp.domain-context
  (:gen-class :name com.vnetpublishing.clj.grid.lib.grid.jsp.DomainContext
              :implements [javax.servlet.ServletConfig]
              :state state
              :init init
              :methods [[initStandAlone [] void]
                        [postConstructHandler [] void]])
  (:require [com.vnetpublishing.clj.grid.lib.grid.http-mapper :as http-mapper]
            [com.vnetpublishing.clj.grid.lib.grid.kernel :refer :all]))

(defn -getInitParameter
  [this name]
    (get (:init-params (deref (.state this))) name))

(defn -getInitParameterNames
  [this]
    (java.util.Collections/enumeration (.keySet (:init-params (deref (.state this))))))

(defn -getServletName 
  []
    "Grid-Stand-Alone")

; This is our application "container"
; Ex: https://svn.apache.org/repos/asf/tomcat/tc8.0.x/tags/TOMCAT_8_0_9/java/org/apache/catalina/core/StandardContext.java

(defn -getServletContext
  [this]
  (if (:context (deref (.state this)))
      (:context (deref (.state this)))
      (let [c (create-instance com.vnetpublishing.clj.grid.lib.grid.jsp.ServletContext [] this)]
        (swap! (.state this) assoc :context c)
        c)))

(defn -initStandAlone
  [this]
    (http-mapper/add-suffix-mapping ".jsp" 
                                    (resolve (symbol "org.apache.jasper.servlet.JspServlet")))
    (http-mapper/add-suffix-mapping ".clj" 
                                    (resolve (symbol "com.vnetpublishing.clj.grid.lib.grid.servlet.CljServlet")))
    (http-mapper/set-default-mapping (resolve (symbol "com.vnetpublishing.clj.grid.lib.grid.servlet.DefaultServlet")))
    
    (.addServlet (.getServletContext this)
                 "jsp"
                 "org.apache.jasper.servlet.JspServlet")
    (.addServlet (.getServletContext this)
                 "clj"
                 "com.vnetpublishing.clj.grid.lib.grid.servlet.CljServlet")
    
    (.addServlet (.getServletContext this)
                 "default"
                 "com.vnetpublishing.clj.grid.lib.grid.servlet.DefaultServlet"))

(defn -postConstructHandler
  [this]
    (swap! (.state this) assoc :init-params {}))
 
(defn -init
  []
    [[] (atom {})])
