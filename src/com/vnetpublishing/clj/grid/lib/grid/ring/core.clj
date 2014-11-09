(ns com.vnetpublishing.clj.grid.lib.grid.ring.core
  (:require [com.vnetpublishing.clj.grid.lib.grid.servlet.core :as servlet-core]
            [grid.dispatcher])
  (:use [com.vnetpublishing.clj.grid.lib.grid.kernel]))

(def ^:dynamic *http-response* nil)
(def ^:dynamic *http-request* nil)

(defn hello-handler [req]
  (let [sw (new java.io.StringWriter)]
       (binding [*out* sw
                 *err* sw
                 *http-response* (atom {:body "" :headers {} :status 200})
                 *http-request* (atom req)
                 *transaction* (gen-transaction-state)
                 *inc* (atom [""])]
         (servlet-core/dispatch grid.dispatcher/dispatch)
         (swap! *http-response* assoc :body (.toString sw))
         @*http-response*)))