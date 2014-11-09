(ns com.vnetpublishing.clj.grid.lib.grid.common.osgi.logger
  (:gen-class 
     :name com.vnetpublishing.clj.grid.lib.grid.common.osgi.Logger
     :extends org.apache.felix.framework.Logger)
  (:require [com.vnetpublishing.clj.grid.lib.grid.kernel :refer :all]
            [clojure.repl :refer [pst]])
  (:import [org.apache.felix.framework Logger]
           [org.osgi.framework BundleException]))


(defn -doLog [this bundle sr level msg throwable]
  (let [b-str (if bundle (.toString bundle) "none")
        sr-str (if sr (str sr) "none")
        t-str (if throwable (str "(" throwable ")") "") 
        s (str "Bundle: "
               b-str 
               "SvcRef: "
               sr-str
               " Message:"
               msg
               t-str)
        t-deep (or (if (instance? BundleException throwable)
                       (.getNestedException throwable))
                       throwable)]
       (binding [*out* *err*]
                (cond (= level Logger/LOG_DEBUG)
                        (println (str "OSGI-DEBUG: " s))
                      (= level Logger/LOG_ERROR)
                        (do (println (str "OSGI-ERROR: " s))
                            (pst t-deep))
                      (= level Logger/LOG_INFO)
                        (println (str "OSGI-INFO: " s))
                      (= level Logger/LOG_WARNING)
                         (println (str "OSGI-WARNING: " s))
                      :else
                         (println (str "OSGI-UNKNOWN["
                                       level
                                       "]: " 
                                       s))))))
