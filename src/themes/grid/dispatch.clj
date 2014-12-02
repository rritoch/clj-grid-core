(ns themes.grid.dispatch
    (:require [grid.core]
              [clojure.java.io :refer :all]
              [com.vnetpublishing.clj.grid.mvc.base.module :as module]
              [com.vnetpublishing.clj.grid.mvc.base.controller :as controller]
              [com.vnetpublishing.clj.grid.lib.grid.kernel :refer :all])
    (:import [java.util HashMap]))

(fscript "Grid Theme dispatcher"
    (let [req-app-id (.getParameter *servlet-request* "app")
          app-id (if (empty? req-app-id)
                     "grid"
                     req-app-id)
          req-controller-id (.getParameter *servlet-request* "controller")
          controller-id (if (empty? req-controller-id)
                            "controller"
                            req-controller-id)
          autoload-apps (vec (set [app-id]))
          applications (HashMap.)]
         
         (tglobal-set :current-theme
                      (assoc (tglobal-get :current-theme) 
                             :path 
                             (clojure.string/join *ds*
                                                  ["themes" 
                                                   "grid"])))
         (loop [ids autoload-apps]
               (if (empty? ids)
                   nil
                   (recur (let [cur-app-id (first ids)
                                cur-app (get-module (symbol (str "applications." cur-app-id ".module")))]
                               (module/ns-init cur-app)
                               (.put applications
                                     cur-app-id
                                     cur-app)
                               (rest ids)))))
         (binding [*current-module* (.get applications app-id)] 
                  (let [m-sym 'com.vnetpublishing.clj.grid.mvc.base.module
                        controller-ns (call-other (find-other-ns *current-module* m-sym) 
                                                  'get-controller 
                                                  *current-module* controller-id)]
                  (controller/dispatch controller-ns true)))))
