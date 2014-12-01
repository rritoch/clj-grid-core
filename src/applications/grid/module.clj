(ns applications.grid.module
  (:require  [grid.core]
             [clojure.java.io :refer :all]
             [clojure.string :as string]
             [com.vnetpublishing.clj.grid.mvc.engine :refer :all]
             [com.vnetpublishing.clj.grid.mvc.base.module :as module]
             [com.vnetpublishing.clj.grid.mvc.base.view :as view]
             [com.vnetpublishing.clj.grid.lib.grid.kernel :refer :all]))

(module/make-module)

(def marker true)

(defn get-view
  [name]
  (let [v (module/get-view (this-ns) name)]
       (view/add-template-path v (str (:path (tglobal-get :current-theme)) *ds* "grid" *ds* name))
       v))

(defn --construct
  []
    (debug (str "applications.grid.module --construct"))
    (let [t-ns (this-ns)
          f (:file (meta #'marker))
          sf (if (and f
                      (not= f "NO_SOURCE_FILE"))
                 (as-file f))
          base-path (if sf 
                        (.toString (.getParent sf))
                        (str (string/join *ds* (butlast (string/split (.toString t-ns) #"\.")))
                             *ds*))]
      (ns-set t-ns "_basePath" base-path)))
