(ns applications.grid.controllers.controller
  (:require [com.vnetpublishing.clj.grid.mvc.engine :refer :all]
            [com.vnetpublishing.clj.grid.lib.grid.kernel :refer :all]
            [com.vnetpublishing.clj.grid.mvc.base.view :as view]
            [com.vnetpublishing.clj.grid.mvc.base.module :as module]
            [com.vnetpublishing.clj.grid.mvc.base.controller :as controller]))

(controller/make-controller)

(defn method-display
  []
    (let [m (controller/get-module (this-ns))
          v (ns-call m 'get-view "grid")]
         (debug (str "view/display " v))
         (view/display v)
         (debug "view/render")
         (view/render v)))
  
(defn dispatch
   ([lock]
   (if (controller/dispatch? (this-ns) lock)
       (do (method-display)
           true)))
   ([] (dispatch true)))