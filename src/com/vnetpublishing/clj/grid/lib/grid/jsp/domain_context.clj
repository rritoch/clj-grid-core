(ns com.vnetpublishing.clj.grid.lib.grid.jsp.domain-context
  (:gen-class 
     :name com.vnetpublishing.clj.grid.lib.grid.jsp.DomainContext
     :extends com.vnetpublishing.clj.grid.lib.mvc.base.Object
     :implements [javax.servlet.ServletConfig]
     :methods [[initStandAlone [] void]])
  (:use [com.vnetpublishing.clj.grid.lib.grid.kernel]
        [com.vnetpublishing.clj.grid.lib.mvc.engine]))

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
  (.addServlet (.getServletContext this)
               "jsp"
               "org.apache.jasper.servlet.JspServlet"))

(defn -postConstructHandler
  [this]
  (assign this ["_init_params" {}]))
