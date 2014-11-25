(ns com.vnetpublishing.clj.grid.lib.grid.jsp.servlet-response
  (:gen-class :name com.vnetpublishing.clj.grid.lib.grid.jsp.ServletResponse
              :methods [[setWriter [java.io.PrintWriter] void]
                        [postConstructHandler [] void]]
              :state state
              :init init
              :implements [javax.servlet.http.HttpServletResponse])
  (:use [clojure.stacktrace]))

(defn -sendError
  ([this code]
    (println (str "ERROR " code)))
  ([this code err]
    (try (throw (Exception. (str code " " err)))
         (catch Exception e (print-stack-trace e)))))

(defn -isCommitted
  [this]
    (if (:committed (deref (.state this)))
        true 
        false))

(defn -getWriter
  [this]
    (:writer (deref (.state this))))

(defn -setWriter
   [this w]
     (swap! (.state this) assoc :writer w))

(defn -resetBuffer
  "Clears the content of the underlying buffer in the response without clearing headers or status code"
  [this]
    nil)

(defn -setContentType
  [this content-type]
    nil)

(defn -postConstructHandler
  [this]
    nil)

(defn -init
  []
    [[] (atom {})])
