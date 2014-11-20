(ns com.vnetpublishing.clj.grid.lib.grid.webapp.servlet-request-wrapper
  (:gen-class 
    :name com.vnetpublishing.clj.grid.lib.grid.webapp.ServletRequestWrapper
    :extends com.vnetpublishing.clj.grid.lib.mvc.base.Object
    ;:implements [javax.servlet.ServletRequest]
    :methods [[setContext [Object] void]
              [getContext [] Object]
              [postConstructHandler [javax.servlet.Servlet javax.servlet.ServletRequest] void]]
    :implements [javax.servlet.http.HttpServletRequest])
  (:use [com.vnetpublishing.clj.grid.lib.grid.kernel]
        [com.vnetpublishing.clj.grid.lib.mvc.engine]))

(defn -getPathInfo
  [this]
  (.getAttribute this javax.servlet.RequestDispatcher/INCLUDE_PATH_INFO))

(defn -getServletPath
  [this]
    (.getServletPath (.get this "_request")))

(defn -getRequestURI
  [this]
    (.getRequestURI (.get this "_request")))

(defn -getContextPath
  [this]
    (.getContextPath (.get this "_request")))

(defn -getMethod
  [this]
    (.getMethod (.get this "_request")))

(defn -getQueryString
  [this]
    (.getQueryString (.get this "_request")))

(defn -getRequestDispatcher
  [this path]
  (let [c (.getServletConfig (.get this "_servlet"))]
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
    (.setAttribute (.get this "_request")
                   name 
                   o))

(defn -getAttribute
  [this name]
    (.getAttribute (.get this "_request")
                   name))

(defn -getServletContext
  [this]
    #_(.getServletContext (.get this "_request"))
    (.getServletContext (.getServletConfig (.get this "_servlet"))))

; Wrap?
(defn -getSession
  [this]
  (.getSession (.get this "_request")))


(defn -getAsyncContext
  [this]
  (.getAsyncContext (.get this "_request")))

(defn -getAttributeNames
  [this]
  (.getAttributeNames (.get this "_request")))

(defn -getCharacterEncoding
  [this]
  (.getCharacterEncoding (.get this "_request")))

(defn -getContentLength
  [this]
  (.getContentLength (.get this "_request")))

(defn -getContentType
  [this]
  (.getContentType (.get this "_request")))

(defn -getDispatcherType
  [this]
  (.getDispatcherType (.get this "_request")))

(defn -getInputStream
  [this]
  (.getInputStream (.get this "_request")))

(defn -getLocalAddr
  [this]
  (.getLocalAddr (.get this "_request")))

(defn -getLocale
  [this]
  (.getLocale (.get this "_request")))

(defn -getLocales
  [this]
  (.getLocales (.get this "_request")))


(defn -getLocalName
  [this]
  (.getLocalName (.get this "_request")))

(defn -getLocalPort
  [this]
  (.getLocalPort (.get this "_request")))

(defn -getParameter
  [this name]
  (.getParameter (.get this "_request")
                 name))

(defn -getParameterMap
  [this]
  (.getParameterMap (.get this "_request")))

(defn -getParameterNames
  [this]
  (.getParameterNames (.get this "_request")))

(defn -getParameterValues
  [this name]
  (.getParameterValues (.get this "_request")
                       name))

(defn -getProtocol
  [this]
  (.getProtocol (.get this "_request")))

(defn -getReader
  [this]
  (.getReader (.get this "_request")))

(defn -getRealPath
  [this]
  (.getRealPath (.get this "_request")))

(defn -getRemoteAddr
  [this]
  (.getRemoteAddr (.get this "_request")))

(defn -getRemoteHost
  [this]
  (.getRemoteHost (.get this "_request")))

(defn -getRemotePort
  [this]
  (.getRemotePort (.get this "_request")))

(defn -getScheme
  [this]
  (.getScheme (.get this "_request")))


(defn -getServerName
  [this]
  (.getServerName (.get this "_request")))

(defn -getServerPort
  [this]
  (.getServerPort (.get this "_request")))

(defn -isAsyncStarted
  [this]
  (.isAsyncStarted (.get this "_request")))

(defn -isAsyncSupported
  [this]
  (.isAsyncSupported (.get this "_request")))

(defn -isSecure
  [this]
  (.isSecure (.get this "_request")))

(defn -removeAttribute
  [this name]
  (.removeAttribute (.get this "_request")
                    name))

(defn -setCharacterEncoding
  [this env]
  (.setCharacterEncoding (.get this "_request")
                    env))

(defn -startAsync
  ([this]
    (.startAsync (.get this "_request")))
  ([this request response]
    (.startAsync (.get this "_request") request response)))

(defn -setContext
   [this context]
   (.set this "_context" context))

(defn -getContext
  [this context]
  (.get this "_context"))

(defn -postConstructHandler
  [this servlet request]
  (assign this ["_servlet" servlet
                "_request" request]))


