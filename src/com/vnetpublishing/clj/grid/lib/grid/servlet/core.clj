(ns com.vnetpublishing.clj.grid.lib.grid.servlet.core
  (:import [java.io PrintWriter])
  (:use [com.vnetpublishing.clj.grid.lib.grid.kernel]))

(def ^:dynamic *servlet* nil)

#_(defn- dispatch-low
     [f args]
     (let [w (.getWriter *servlet-response*)]
       (binding [*out* w]
         (apply f args))))


(defn- dispatch-low
     [f args]
     (apply f args))

(defn dispatch
      [f & args]
      (if (or *servlet-request*
              *servlet-response*)
          (dispatch-low f args)
          (let [request (new com.vnetpublishing.clj.grid.lib.grid.jsp.ServletRequest)
                response (new com.vnetpublishing.clj.grid.lib.grid.jsp.ServletResponse)
                context (create-instance com.vnetpublishing.clj.grid.lib.grid.jsp.DomainContext [])]
             (.postConstructHandler request)
             (.postConstructHandler response)
             (.initStandAlone context)
             (.setContext request context)
          (binding [*servlet-request* request
                    *servlet-response* response]
                   (.setWriter *servlet-response* (new PrintWriter *out*))
                   (dispatch-low f args)))))