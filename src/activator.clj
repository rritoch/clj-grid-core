(do
  
  (clojure.core/ns activator (:require [com.vnetpublishing.clj.grid.lib.grid.osgi.activator]) 
    (:gen-class :implements org.osgi.framework.BundleActivator)) 
  (do 
    (def activator/grid-mods nil) 
    (clojure.core/defn activator/-start [context__1000__auto__] 
      (clojure.core/loop [m__1001__auto__ (clojure.core/or activator/grid-mods (quote ()))] (if (clojure.core/empty? m__1001__auto__) nil (recur (do (.start (com.vnetpublishing.clj.grid.lib.grid.osgi.activator/get-grid-module context__1000__auto__ (clojure.core/first m__1001__auto__)) context__1000__auto__) (clojure.core/rest m__1001__auto__)))))) 
    (clojure.core/defn activator/-stop [context__1000__auto__] 
      (clojure.core/loop [m__1001__auto__ activator/grid-mods] (if (clojure.core/empty? m__1001__auto__) nil (recur (do (.stop (com.vnetpublishing.clj.grid.lib.grid.osgi.activator/get-grid-module context__1000__auto__ (clojure.core/first m__1001__auto__)) context__1000__auto__) (clojure.core/rest m__1001__auto__))))))))