(ns com.vnetpublishing.clj.grid.lib.grid.jsp.servlet-request
  
  (:gen-class 
    :name com.vnetpublishing.clj.grid.lib.grid.jsp.ServletRequest
    :extends com.vnetpublishing.clj.grid.lib.mvc.base.Object
    :methods [[setContext [Object] void]
              [getContext [] Object]]
    ;:implements [javax.servlet.ServletRequest]
    :implements [javax.servlet.http.HttpServletRequest]
  ))

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
  (let [c (.get this "_context")]
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
  (.set (.get this "_attributes") name o))

(defn -getAttribute
  [this name]
  (.get (.get this "_attributes") name))

(defn -postConstructHandler
  [this]
  (.set this "_attributes" (new com.vnetpublishing.clj.grid.lib.mvc.base.Object)))

(defn -setContext
   [this context]
   (.set this "_context" context))

(defn -getContext
  [this context]
  (.get this "_context"))

(defn -getServletContext
  [this]
  (.getServletContext (.get this "_context")))

(defn -getSession
  [this]
  (new com.vnetpublishing.clj.grid.lib.grid.jsp.Session)
)

(defn -getParameter
  [this name]
  nil)
