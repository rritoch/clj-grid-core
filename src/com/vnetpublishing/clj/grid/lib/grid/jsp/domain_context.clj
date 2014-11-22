(ns com.vnetpublishing.clj.grid.lib.grid.jsp.domain-context
  (:gen-class 
     :name com.vnetpublishing.clj.grid.lib.grid.jsp.DomainContext
     :extends com.vnetpublishing.clj.grid.lib.mvc.base.Object
     :implements [javax.servlet.ServletConfig]
     :methods [[initStandAlone [] void]])
  (:require [com.vnetpublishing.clj.grid.lib.grid.http-mapper :as http-mapper]
        [com.vnetpublishing.clj.grid.lib.grid.kernel :refer :all]
        [com.vnetpublishing.clj.grid.lib.mvc.engine :refer :all]))

(defn -getInitParameter
  [this name]
  (get (.get this "_init_params") name))

(defn -getInitParameterNames
  [this]
  (java.util.Collections/enumeration (.keySet (.get this "_init_params"))))

(defn -getServletName 
  []
  "Grid-Stand-Alone")

; This is our application "container"
; Ex: https://svn.apache.org/repos/asf/tomcat/tc8.0.x/tags/TOMCAT_8_0_9/java/org/apache/catalina/core/StandardContext.java

(defn -getServletContext
  [this]
  (if (not (.get this "_context"))
      (.set this "_context" 
        (create-instance com.vnetpublishing.clj.grid.lib.grid.jsp.ServletContext [] this)))
  (.get this "_context"))

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
  (assign this ["_init_params" {}]))
