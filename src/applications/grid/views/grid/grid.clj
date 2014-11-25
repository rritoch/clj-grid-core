(ns applications.grid.views.grid.grid
  (:require [clojure.string :as string]
            [com.vnetpublishing.clj.grid.mvc.engine :refer :all]
            [com.vnetpublishing.clj.grid.mvc.base.view :as view]
            [com.vnetpublishing.clj.grid.mvc.base.module :as module]))

(view/make-view)

(defn display
  []
    (let [t-ns (:ns (meta #'display))
          module (view/get-module t-ns)
          vwp-model (module/get-model module "grid")]
         (assign t-ns ["version" (ns-call vwp-model 'get-version)])))
