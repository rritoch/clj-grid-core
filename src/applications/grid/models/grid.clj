(ns applications.grid.models.grid
    (:gen-class
      :name applications.grid.models.grid
      :methods [[getVersion [] String]]
      :extends com.vnetpublishing.clj.grid.lib.mvc.base.Model)
    (:require [com.vnetpublishing.clj.grid.lib.mvc.base.version :as version]))

(defn -getVersion
      [this]
      (clojure.string/join "." (version/getVersion) ))