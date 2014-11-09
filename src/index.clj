(ns index
  (:require [grid.core])
  (:use [com.vnetpublishing.clj.grid.lib.grid.kernel]))

(script "[index.clj] started..." 
  
  ; Load path configuration
  (ginc-once (str "config" *ds* "paths.clj"))
  (debug "[index.clj] After config/paths.clj")

  ; Load theme configuration
  (ginc-once (str "config" *ds* "theme_config.clj"))

  ; Launch Theme
  (ginc (str "themes" 
             *ds* 
             ;(:id (:current-theme (deref (globals)))) 
             (:id (tglobal-get :current-theme))
             *ds* 
             "dispatch.clj")))