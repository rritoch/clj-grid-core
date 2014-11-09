(ns applications.grid.controllers.controller
    (:gen-class
      :name applications.grid.controllers.controller
      :exposes-methods {dispatch parentDispatch}
      :methods [[methodDisplay [] void]]
      :extends com.vnetpublishing.clj.grid.lib.mvc.base.Controller))


(defn -methodDisplay
  [this]
  (let [view (.getView (.getModule this) "grid")]
       (.display view)
       (.render view)))
  
(defn -dispatch
   ([this lock]
   (if (.parentDispatch this lock)
       (do (.methodDisplay this)
         true
       )))
   ([this] (.dispatch this true)))