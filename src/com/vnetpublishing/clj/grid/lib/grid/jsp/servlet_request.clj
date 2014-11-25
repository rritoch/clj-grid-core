(ns com.vnetpublishing.clj.grid.lib.grid.jsp.servlet-request
  (:gen-class :name com.vnetpublishing.clj.grid.lib.grid.jsp.ServletRequest
              :methods [[setContext [Object] void]
                        [getContext [] Object]
                        [postConstructHandler [] void]]
              :state state
              :init init
              :implements [javax.servlet.http.HttpServletRequest])
  (:import [java.util HashMap]))

(defn -getPathInfo
  [this]
  (.getAttribute this javax.servlet.RequestDispatcher/INCLUDE_PATH_INFO))

(defn -getServletPath
  [this]
  "/")

(defn -getContextPath
  [this]
    "/")

(defn -getRequestURI
  [this]
  (.getAttribute this javax.servlet.RequestDispatcher/INCLUDE_SERVLET_PATH))

(defn -getMethod
  [this]
  "GET")

(defn -getQueryString
  [this]
  "")

(defn -getRequestDispatcher
  [this path]
  (let [c (:context (deref (.state this)))]
       (if (and c
                path)
            (if (.startsWith path "/")
                (.getRequestDispatcher (.getServletContext c)
                                       path)
                (let [servlet-path (if (.getAttribute this 
                                                      javax.servlet.RequestDispatcher/INCLUDE_SERVLET_PATH)
                                       (.getAttribute this 
                                                      javax.servlet.RequestDispatcher/INCLUDE_SERVLET_PATH)
                                       (.getServletPath this))
                      path-info (.getPathInfo this)
                      
                      request-path (if path-info
                                       (str servlet-path path-info)
                                       (str servlet-path))
                      psp (.lastIndexOf request-path "/")
                      relative (if (> 0 psp)
                                   (str request-path path)
                                   (str (.substring request-path 0 (+ psp 1)) path))]
                     (.getRequestDispatcher (.getServletContext c) relative))))))

(defn -setAttribute
  [this name o]
    (.put (:attributes (deref (.state this))) name o))

(defn -getAttribute
  [this name]
  (.get (:attributes (deref (.state this))) name))

(defn -postConstructHandler
  [this]
    (swap! (.state this) assoc :attributes (HashMap.)))

(defn -setContext
   [this context]
     (swap! (.state this) assoc :context context))

(defn -getContext
  [this]
    (:context (deref (.state this))))

(defn -getServletContext
  [this]
    (.getServletContext (:context (deref (.state this)))))

(defn -getSession
  [this]
    (new com.vnetpublishing.clj.grid.lib.grid.jsp.Session))

(defn -getParameter
  [this name]
    nil)

(defn -init
  []
    [[] (atom {})])
