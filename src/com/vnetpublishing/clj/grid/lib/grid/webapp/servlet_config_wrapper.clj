(ns com.vnetpublishing.clj.grid.lib.grid.webapp.servlet-config-wrapper
  (:gen-class 
    :name com.vnetpublishing.clj.grid.lib.grid.webapp.ServletConfigWrapper
    :extends com.vnetpublishing.clj.grid.lib.mvc.base.Object
    ;:implements [javax.servlet.ServletRequest]
    :methods [[postConstructHandler [javax.servlet.ServletConfig] void]]
    :implements [javax.servlet.ServletConfig])
  (:require [com.vnetpublishing.clj.grid.lib.grid.webapp.servlet-context-wrapper])
  (:use [com.vnetpublishing.clj.grid.lib.grid.kernel]
        [com.vnetpublishing.clj.grid.lib.mvc.engine]))


(defn -getInitParameter
  [this name]
    (.getInitParameter (.get this "_servletconfig") name))

(defn -getInitParameterNames
  [this]
    (.getInitParameterNames (.get this "_servletconfig")))

(defn -getServletName
  [this]
    (.getServletName (.get this "_servletconfig")))

(defn -getServletContext
  [this]
    (.get this "_servletcontext"))

(defn -postConstructHandler
  [this servletconfig]
  (let [servletcontext (create-instance com.vnetpublishing.clj.grid.lib.grid.webapp.ServletContextWrapper [] this (.getServletContext servletconfig))]
    
    (assign this ["_servletconfig" servletconfig
                  "_servletcontext" servletcontext])
    (.addServlet (.getServletContext this)
               "jsp"
               "org.apache.jasper.servlet.JspServlet")))

