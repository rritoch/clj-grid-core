(ns applications.grid.models.grid
  (:require [clojure.string :as string]
            [com.vnetpublishing.clj.grid.mvc.base.model :as model]
            [com.vnetpublishing.clj.grid.mvc.base.version :as version]))

(model/make-model)

(defn get-version
  []
    (string/join "." (version/get-version)))