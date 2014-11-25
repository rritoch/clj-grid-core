(ns index
  (:require [grid.core]
            [com.vnetpublishing.clj.grid.lib.grid.kernel :refer :all]))

(fscript "[index.clj] started..." 
  
  ; Load path configuration
  (ginc-once (str "config" *ds* "paths.clj"))
  (debug "[index.clj] After config/paths.clj")

  ; Load theme configuration
  (ginc-once (str "config" *ds* "theme_config.clj"))

  ; Launch Theme
  (finc (str "themes" 
             *ds* 
             ;(:id (:current-theme (deref (globals)))) 
             (:id (tglobal-get :current-theme))
             *ds* 
             "dispatch.clj")
        (symbol (str "themes." (:id (tglobal-get :current-theme)) ".dispatch"))))