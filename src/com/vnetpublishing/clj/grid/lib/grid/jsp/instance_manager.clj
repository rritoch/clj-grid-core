(ns com.vnetpublishing.clj.grid.lib.grid.jsp.instance-manager
  (:gen-class 
     :name com.vnetpublishing.clj.grid.lib.grid.jsp.InstanceManager
     :extends com.vnetpublishing.clj.grid.lib.mvc.base.Object
     :implements [org.apache.tomcat.InstanceManager]
     :methods [[initStandAlone [] void]])
  (:use [com.vnetpublishing.clj.grid.lib.grid.kernel]))


(defn -destroyInstance
  [this obj]
)

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
