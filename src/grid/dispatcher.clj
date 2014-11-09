(ns grid.dispatcher
   (:use [com.vnetpublishing.clj.grid.lib.grid.kernel]))

(defn dispatch
  []
  (if (not *dispatch*)
      (binding [*dispatch* true
                *transaction* (gen-transaction-state)]
               (if (not (resolve 'config.core/stdconfig))
                   (require 'config.core))
               (if (resolve 'config.core/stdconfig)
                   (do (debug "###DISPATCH###")
                       (grequire (str ".." *ds* "index.clj")))
                   (throw (Exception. "config.core/stdconfig not defined!"))
               ))))