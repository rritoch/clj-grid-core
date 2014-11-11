(ns com.vnetpublishing.clj.grid.lib.grid.webapp.filter-registration
  (:gen-class :name com.vnetpublishing.clj.grid.lib.grid.webapp.FilterRegistration
              :extends com.vnetpublishing.clj.grid.lib.mvc.base.Object
              :methods [[postConstructHandler [javax.servlet.ServletContext] void]]
              :implements [javax.servlet.FilterRegistration]
              :prefix -
              :main false)
  (:require [com.vnetpublishing.clj.grid.lib.grid.webapp.servlet-context-wrapper])
  (:use [com.vnetpublishing.clj.grid.lib.grid.kernel]
        [com.vnetpublishing.clj.grid.lib.mvc.engine]))

(gen-class :name com.vnetpublishing.clj.grid.lib.grid.webapp.FilterRegistration$Dynamic
           :extends  com.vnetpublishing.clj.grid.lib.grid.webapp.FilterRegistration
           :implements [javax.servlet.FilterRegistration$Dynamic]
           :prefix dyn-
           :main false)

(defn -AddMappingForServletNames
  [this dispatcher-types is-match-after & servlet-names]
    nil)

(defn -AddMappingForUrlPatterns
  [this dispatcher-types is-match-after url-patterns]
    nil)

(defn -getServletNameMappings
  [this]
    nil)

(defn -getUrlPatternMappings
  [this]
    nil)

(defn dyn-setAsyncSupported 
  [this is-async-supported]
    (.set this "_async_supported" is-async-supported))
