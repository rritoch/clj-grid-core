(ns ^{:doc "Paths configuration" } config.paths
  (:use [clojure.java.io]
        [com.vnetpublishing.clj.grid.lib.grid.kernel]))

(script "Configuring paths.."
        
        (if (not (resolve 'config.core/stdconfig))
            (require 'config.core))

        (let [stdconfig-ref (resolve 'config.core/stdconfig)]
             (if stdconfig-ref 
                 (do (debug (str "stdconfig meta: " (meta stdconfig-ref)))
                     (swap! (deref stdconfig-ref) 
                            assoc 
                            :base-path
                            (->  (as-file (:file (meta stdconfig-ref)))
                            (.getCanonicalFile)
                            (.getParentFile)
                            (.getParentFile)
                            (.getCanonicalPath)))
                     (debug (str "base-path = " (:base-path (deref (deref stdconfig-ref)))))
                     (swap! (deref stdconfig-ref) 
                        assoc
                        :src-path
                         (str (:base-path (deref (deref stdconfig-ref)))
                              java.io.File/separator
                              "src"))
                     (swap! (deref stdconfig-ref) 
                            assoc 
                            :config-path
                            (str (:src-path (deref (deref stdconfig-ref)))
                                 java.io.File/separator
                                 "config")))
                  (println "WARNING: config.core not found"))
             #_(doseq [[k v] (deref (deref stdconfig-ref))]
                     (println (str "    " (name k) "=" v)))
             ;(require 'config.domain)
             (ginc (str "config" java.io.File/separator "domain.clj"))
             (debug "POST DOMAIN")

        (if (and stdconfig-ref
                 (.exists (as-file (str (:config-path (deref (deref stdconfig-ref))) 
                                   java.io.File/separator 
                                   "paths_local.clj"))))
          (load-file (str (:config-path (deref (deref stdconfig-ref)))
                     java.io.File/separator 
                     "paths_local.clj"))
          (do
             (add-include-path (str "config" *ds* "domains" *ds* (or (tglobal-get :domain) "any")))))))

