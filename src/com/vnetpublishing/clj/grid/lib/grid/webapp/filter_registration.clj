(ns com.vnetpublishing.clj.grid.lib.grid.webapp.filter-registration
  (:gen-class :name com.vnetpublishing.clj.grid.lib.grid.webapp.FilterRegistration
              :methods [[postConstructHandler [javax.servlet.ServletContext java.util.Map] void]]
              :implements [javax.servlet.FilterRegistration]
              :prefix -
              :state state
              :init init
              :main false)
  (:use [com.vnetpublishing.clj.grid.lib.grid.kernel]))

(gen-class :name com.vnetpublishing.clj.grid.lib.grid.webapp.FilterRegistration$Dynamic
           :extends  com.vnetpublishing.clj.grid.lib.grid.webapp.FilterRegistration
           :implements [javax.servlet.FilterRegistration$Dynamic]
           :prefix dyn-
           :main false)

(defn -AddMappingForServletNames
  [this dispatcher-types is-match-after & servlet-names]
    (let [ctx (:context (deref (.state this)))
          filter-def (:filter-def (deref (.state this)))]
      (.addFilterMap ctx 
                     {:filter-name (:filter-name filter-def)
                      :dispatcher-types dispatcher-types 
                      :servlet-names servlet-names}
                     is-match-after)))

(defn -AddMappingForUrlPatterns
  [this dispatcher-types is-match-after & url-patterns]
    (let [ctx (:context (deref (.state this)))
          filter-def (:filter-def (deref (.state this)))]
         (.addFilterMap ctx
                        {:filter-name (:filter-name filter-def)
                         :dispatcher-types dispatcher-types
                         :url-patterns url-patterns}
                        is-match-after)))

(defn -getServletNameMappings
  [this]
    (let [ctx (:context (deref (.state this)))
          filter-def (:filter-def (deref (.state this)))
          filter-maps (.getFilterMaps ctx)]
         (flatten (keep (partial #(if (= (:filter-name %2) %1)
                                      (:servlet-names %2))
                                 (:filter-name filter-def))
                        filter-maps))))

(defn -getUrlPatternMappings
  [this]
    (let [ctx (:context (deref (.state this)))
          filter-def (:filter-def (deref (.state this)))
          filter-maps (.getFilterMaps ctx)]
         (flatten (keep (partial #(if (= (:filter-name %2) %1)
                                      (:url-patterns %2))
                                 (:filter-name filter-def))
                        filter-maps))))

(defn -postConstructHandler
  [this ctx filter-def]
    (swap! (.state this) :context ctx)
    (swap! (.state this) :filter-def filter-def))

(defn dyn-setAsyncSupported 
  [this is-async-supported]
    (swap! (.state this) :async-supported is-async-supported))

(defn -init
  []
    [[] (atom {})])
