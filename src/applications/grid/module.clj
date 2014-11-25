(ns applications.grid.module
  (:require  [grid.core]
             [clojure.java.io :refer :all]
             [com.vnetpublishing.clj.grid.mvc.engine :refer :all]
             [com.vnetpublishing.clj.grid.mvc.base.module :as module]
             [com.vnetpublishing.clj.grid.mvc.base.view :as view]
             [com.vnetpublishing.clj.grid.lib.grid.kernel :refer :all]))

(module/make-module)

(def marker true)

(defn get-view
  [name]
  (let [v (module/get-view (:ns (meta #'get-view)) name)]
       (view/add-template-path v (str (:path (tglobal-get :current-theme)) *ds* "grid" *ds* name))
       v))

(defn --construct
  []
    (let [base-path (.toString (.getParent (as-file (:file (meta #'marker)))))]
      (ns-set (:ns (meta #'--construct)) "_basePath" base-path)))
