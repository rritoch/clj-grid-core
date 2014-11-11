(ns com.vnetpublishing.clj.grid.lib.grid.webapp.filter-registration
  (:gen-class :name com.vnetpublishing.clj.grid.lib.grid.webapp.FilterRegistration
              :extends com.vnetpublishing.clj.grid.lib.mvc.base.Object
              :methods [[postConstructHandler [javax.servlet.ServletContext java.util.Map] void]]
              :implements [javax.servlet.FilterRegistration]
              :prefix -
              :main false)
  (:use [com.vnetpublishing.clj.grid.lib.grid.kernel]
        [com.vnetpublishing.clj.grid.lib.mvc.engine]))

(gen-class :name com.vnetpublishing.clj.grid.lib.grid.webapp.FilterRegistration$Dynamic
           :extends  com.vnetpublishing.clj.grid.lib.grid.webapp.FilterRegistration
           :implements [javax.servlet.FilterRegistration$Dynamic]
           :prefix dyn-
           :main false)

(defn -AddMappingForServletNames
  [this dispatcher-types is-match-after & servlet-names]
    (let [ctx (.get this "_context")
          filter-def (.get this "_filter_def")]
      (.addFilterMap ctx 
                     {:filter-name (:filter-name filter-def)
                      :dispatcher-types dispatcher-types 
                      :servlet-names servlet-names}
                     is-match-after)))

(defn -AddMappingForUrlPatterns
  [this dispatcher-types is-match-after & url-patterns]
    (let [ctx (.get this "_context")
          filter-def (.get this "_filter_def")]
         (.addFilterMap ctx
                        {:filter-name (:filter-name filter-def)
                         :dispatcher-types dispatcher-types
                         :url-patterns url-patterns}
                        is-match-after)))

(defn -getServletNameMappings
  [this]
    (let [ctx (.get this "_context")
          filter-def (.get this "_filter_def")
          filter-maps (.getFilterMaps ctx)]
         (flatten (keep (partial #(if (= (:filter-name %2) %1)
                                      (:servlet-names %2))
                                 (:filter-name filter-def))))))

(defn -getUrlPatternMappings
  [this]
    (let [ctx (.get this "_context")
          filter-def (.get this "_filter_def")
          filter-maps (.getFilterMaps ctx)]
         (flatten (keep (partial #(if (= (:filter-name %2) %1)
                                      (:url-patterns %2))
                                 (:filter-name filter-def))))))

(defn -postConstructHandler
  [this ctx filter-def]
   (.set this "_context" ctx)
   (.set this "_filter_def" filter-def))

(defn dyn-setAsyncSupported 
  [this is-async-supported]
    (.set this "_async_supported" is-async-supported))
