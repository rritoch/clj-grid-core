(ns com.vnetpublishing.clj.grid.lib.grid.jsp.instance-manager
  (:gen-class :name com.vnetpublishing.clj.grid.lib.grid.jsp.InstanceManager
              :implements [org.apache.tomcat.InstanceManager])
  (:require [com.vnetpublishing.clj.grid.lib.grid.kernel :refer :all]))

(defn -destroyInstance
  [this obj]
    nil)

(defn -newInstance
   ([this t]
   (cond (= Class (type t))
         (create-instance t [])
         (instance? String t)
         (create-instance (symbol t) [])
         :else
         (create-instance (type t))))
   ([this fqcn cl]
         (.newInstance (.loadClass cl fqcn))))
