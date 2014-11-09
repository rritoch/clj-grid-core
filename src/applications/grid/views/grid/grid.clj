(ns applications.grid.views.grid.grid
    (:gen-class
      :name applications.grid.views.grid
      :extends com.vnetpublishing.clj.grid.lib.mvc.base.View)
    (:use [com.vnetpublishing.clj.grid.lib.grid.kernel]
          [com.vnetpublishing.clj.grid.lib.mvc.engine]))

(defn -display
     [this]
     (let [module (.getModule this)
           vwp-model (.getModel module "grid")]
          (assign this ["version" (.getVersion vwp-model)])))
