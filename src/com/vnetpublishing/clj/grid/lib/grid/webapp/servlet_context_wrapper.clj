(ns com.vnetpublishing.clj.grid.lib.grid.webapp.servlet-context-wrapper
  (:gen-class :name com.vnetpublishing.clj.grid.lib.grid.webapp.ServletContextWrapper
              :methods [[postConstructHandler [javax.servlet.ServletConfig javax.servlet.ServletContext] void]
                        [addFilterMap [java.util.Map Boolean] void]
                        [getFilterMaps [] java.util.List]]
              :implements [javax.servlet.ServletContext]
              :state state
              :init init)
  (:import [javax.servlet Filter]
           [org.apache.tika Tika])
  (:use [com.vnetpublishing.clj.grid.lib.grid.kernel]))


(def mime-detector (Tika.))

(defmulti -addFilter)

(defmethod -addFilter [String Class] 
  [this filter-name clz] 
    (let [filters (:filters (deref (.state this)))]
         (if (not (get @filters filter-name))
             (do 
                 (swap! filters assoc filter-name (.createFilter this clz))
                 (create-instance com.vnetpublishing.clj.grid.lib.grid.webapp.FilterRegistration$Dynamic
                                  []
                                  [this {:filter (get @filters filter-name)
                                         :filter-name filter-name}])))))
      
(defmethod -addFilter [String Filter] 
  [this filter-name filter] 
  (let [filters (:filters (deref (.state this)))]
         (if (not (get @filters filter-name))
             (do 
                 (swap! filters assoc filter-name filter)
                 (create-instance com.vnetpublishing.clj.grid.lib.grid.webapp.FilterRegistration$Dynamic
                                  []
                                  [this {:filter (get @filters filter-name)
                                         :filter-name filter-name}])))))

(defmethod -addFilter [String String] 
  [this filter-name clz-name] 
  (let [filters (:filters (deref (.state this)))
        clz (resolve clz-name)]
         (if (not (get @filters filter-name))
             (do 
                 (swap! filters assoc filter-name (.createFilter this clz))
                 (create-instance com.vnetpublishing.clj.grid.lib.grid.webapp.FilterRegistration$Dynamic
                                  []
                                  [this {:filter (get @filters filter-name)
                                         :filter-name filter-name}])))))

(defn -createFilter
  [this clz]
    (create-instance clz []))

(defn -getAttribute
  [this name]
    (.getAttribute (:servletcontext (deref (.state this)))
                   name))

(defn -getAttributeNames
  [this]
    (.getAttributeNames (:servletcontext (deref (.state this)))))

          
; Note: Shouldn't we be wrapping this?
(defn -getContext
  [this uripath]
    (.getContext (:servletcontext (deref (.state this)))
                   uripath))

(defn -getContextPath
  [this]
    (.getContextPath (:servletcontext (deref (.state this)))))

(defn -getFilterRegistration
  [this filter-name]
    (let [filters (:filters (deref (.state this)))]
         (if (get @filters filter-name)
             (create-instance com.vnetpublishing.clj.grid.lib.grid.webapp.FilterRegistration$Dynamic
                         []
                         [this {:filter (get @filters filter-name)}]))))

(defn -getFilterRegistrations
  [this]
    (let [filters (deref (:filters (deref (.state this))))]
         (zipmap (keys filters) (map (partial (fn [ob f] 
                                                  (.getFilterRegistration ob f))
                                              this)
                                     (keys filters)))))

(defn -getInitParameter
  [this name]
    (.getInitParameter (:servletcontext (deref (.state this)))
                       name))

(defn -getInitParameterNames
  [this]
    (.getInitParameterNames (:servletcontext (deref (.state this)))))

(defn -getMajorVersion
  [this]
    (.getMajorVersion (:servletcontext (deref (.state this)))))

(defn -getMimeType
  [this file]
    (.detect mime-detector file))

(defn -getMinorVersion
  [this]
    (.getMinorVersion (:servletcontext (deref (.state this)))))

; Shouldn't we be wrapping this?
(defn -getNamedDispatcher
  [this name]
    (.getNamedDispatcher (:servletcontext (deref (.state this)))
                         name))

(defn -getRealPath
  [this path]
    (.getRealPath (:servletcontext (deref (.state this)))
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
    (.getResourcePaths (:servletcontext (deref (.state this)))
                        path))

(defn -getClassLoader
  [this]
  (java.lang.ClassLoader/getSystemClassLoader))

(defn -getServerInfo
  [this]
     (.getServerInfo (:servletcontext (deref (.state this)))))

#_(defn -getServlet
  [this name]
    (.getServlet (:servletcontext (deref (.state this)))
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
     (.getServletContextName (:servletcontext (deref (.state this)))))

(defn -getServletNames
  [this]
     (.getServletNames (:servletcontext (deref (.state this)))))

(defn -getServlets
  [this]
     (.getServlets (:servletcontext (deref (.state this)))))

(defn -log
  ([this arg0]
     (.log (:servletcontext (deref (.state this)))
           arg0))
  ([this arg0 arg1]
     (.log (:servletcontext (deref (.state this)))
           arg0
           arg1)))

(defn -removeAttribute
  [this name]
    (.removeAttribute (:servletcontext (deref (.state this)))
                      name))

(defn -setAttribute
  [this name obj]
    (.setAttribute (:servletcontext (deref (.state this)))
                      name
                      obj))

(defn -addFilterMap
  [this m after]
    (if after
        (swap! (:filter-maps (deref (.state this))) conj m)
        (let [vins (fn [items n-item]
                       (vec (conj (seq items) n-item)))]
             (swap! (:filter-maps (deref (.state this))) vins m))))

(defn -getFilterMaps
  [this]
    (seq (deref (:filter-maps (deref (.state this))))))

(defn -postConstructHandler
  [this servletconfig servletcontext]
    (swap! (.state this) assoc :config servletconfig)
    (swap! (.state this) assoc :servlets (atom {}))
    (swap! (.state this) assoc :filters (atom {}))
    (swap! (.state this) assoc :filter-maps (atom []))
    (swap! (.state this) assoc :servletcontext servletcontext))


(defn -init
  []
    [[] (atom {})])

