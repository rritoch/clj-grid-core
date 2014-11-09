(ns com.vnetpublishing.clj.grid.lib.grid.jsp.template-engine
    (:gen-class
      :name com.vnetpublishing.clj.grid.lib.grid.jsp.TemplateEngine
      :extends com.vnetpublishing.clj.grid.lib.mvc.base.Object
      :implements [com.vnetpublishing.clj.grid.lib.mvc.types.TemplateEngine])
    (:require [com.vnetpublishing.clj.grid.lib.grid.servlet.core :as servlet-core])
    (:use [com.vnetpublishing.clj.grid.lib.grid.kernel]))

(defn -getDefaultFileExt
  [this]
  ".jsp")

(defn -render
  [this source view]
  (let [s (str "/"
               (clojure.string/replace source *ds* "/"))]
       (debug (str "template: " s))
       (.setAttribute *servlet-request* "viewdata" view)
       (.forward (.getRequestDispatcher *servlet-request* s) 
                 *servlet-request* 
                 *servlet-response*)))
