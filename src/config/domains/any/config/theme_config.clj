(ns config.domains.any.config.theme-config
  (:require [grid.core])
  (:use [com.vnetpublishing.clj.grid.lib.grid.kernel]))

(script "Configure theme..."
   (tglobal-set :current-theme {:id "grid"}))