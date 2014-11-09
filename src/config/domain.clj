(ns ^{:doc "Domain Configuration"} config.domain
    (:use [clojure.java.io]
          [com.vnetpublishing.clj.grid.lib.grid.kernel]))

(script "Configuring domains"
  (tglobal-set :domain "any")
  (if (.exists (as-file (str "config" 
                             java.io.File/separator 
                             "domain_local.clj")))
      (load (str "config" 
                        java.io.File/pathSeparator 
                        "domain_local.clj"))))
