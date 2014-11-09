(ns themes.grid.dispatch
    (:require [grid.core])
    (:use [clojure.java.io]
          [com.vnetpublishing.clj.grid.lib.mvc.engine :as engine]
          [com.vnetpublishing.clj.grid.lib.grid.kernel]))

(script "Grid Theme dispatcher"
    (let [req-app-id (.getParameter *servlet-request* "app")
          app-id (if (empty? req-app-id)
                     "grid"
                     req-app-id)
          req-controller-id (.getParameter *servlet-request* "controller")
          controller-id (if (empty? req-controller-id)
                            "controller"
                            req-controller-id)
          autoload-apps (vec (set [app-id]))
          applications (com.vnetpublishing.clj.grid.lib.mvc.base.Object.)]
         (engine/start)
         (engine/set-default-template-engine "jsp")
         (engine/add-template-engine "jsp" "com.vnetpublishing.clj.grid.lib.grid.jsp.TemplateEngine")
         
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
                                cur-app (get-module (symbol (str "applications." cur-app-id)))]
                               (.set applications 
                                     cur-app-id 
                                     cur-app)
                               (rest ids)))))
         (let [app (.get applications app-id)
               controller (call-other app 'getController controller-id)]
              (call-other controller 'dispatch))))
