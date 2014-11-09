(ns com.vnetpublishing.clj.grid.lib.grid.jsp.servlet-response
  
  (:gen-class 
    :name com.vnetpublishing.clj.grid.lib.grid.jsp.ServletResponse
    :extends com.vnetpublishing.clj.grid.lib.mvc.base.Object
    :methods [[setWriter [java.io.PrintWriter] void]]
    :implements [javax.servlet.http.HttpServletResponse]
  )
  (:use [clojure.stacktrace]))

(defn -sendError
  ([this code]
    (println (str "ERROR " code)))
  ([this code err]
    (try (throw (Exception. (str code " " err)))
         (catch Exception e (print-stack-trace e)))))

(defn -isCommitted
  [this]
  (if (.get this "_committed") true false))

(defn -getWriter
  [this]
  (.get this "_writer"))

(defn -setWriter
   [this w]
   (.set this "_writer" w))

(defn -resetBuffer
  "Clears the content of the underlying buffer in the response without clearing headers or status code"
  [this]
)

(defn -setContentType
  [this content-type]
  nil
)


