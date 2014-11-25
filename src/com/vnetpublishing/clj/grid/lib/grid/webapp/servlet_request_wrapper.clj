(ns com.vnetpublishing.clj.grid.lib.grid.webapp.servlet-request-wrapper
  (:gen-class :name com.vnetpublishing.clj.grid.lib.grid.webapp.ServletRequestWrapper
    
              :methods [[setContext [Object] void]
                        [getContext [] Object]
                        [postConstructHandler [javax.servlet.Servlet javax.servlet.ServletRequest] void]]
              :implements [javax.servlet.http.HttpServletRequest]
              :state state
              :init init)
  (:require [com.vnetpublishing.clj.grid.lib.grid.kernel :refer :all]))

(defn -getPathInfo
  [this]
  (.getAttribute this javax.servlet.RequestDispatcher/INCLUDE_PATH_INFO))

(defn -getServletPath
  [this]
    (.getServletPath (:request (deref (.state this)))))

(defn -getRequestURI
  [this]
    (.getRequestURI (:request (deref (.state this)))))

(defn -getContextPath
  [this]
    (.getContextPath (:request (deref (.state this)))))

(defn -getMethod
  [this]
    (.getMethod (:request (deref (.state this)))))

(defn -getQueryString
  [this]
    (.getQueryString (:request (deref (.state this)))))

(defn -getRequestDispatcher
  [this path]
  (let [c (.getServletConfig (:servlet (deref (.state this))))]
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
    (.setAttribute (:request (deref (.state this)))
                   name 
                   o))

(defn -getAttribute
  [this name]
    (.getAttribute (:request (deref (.state this)))
                   name))

(defn -getServletContext
  [this]
    #_(.getServletContext (:request (deref (.state this))))
    (.getServletContext (.getServletConfig (:servlet (deref (.state this))))))

; Wrap?
(defn -getSession
  [this]
  (.getSession (:request (deref (.state this)))))


(defn -getAsyncContext
  [this]
  (.getAsyncContext (:request (deref (.state this)))))

(defn -getAttributeNames
  [this]
  (.getAttributeNames (:request (deref (.state this)))))

(defn -getCharacterEncoding
  [this]
  (.getCharacterEncoding (:request (deref (.state this)))))

(defn -getContentLength
  [this]
  (.getContentLength (:request (deref (.state this)))))

(defn -getContentType
  [this]
  (.getContentType (:request (deref (.state this)))))

(defn -getDispatcherType
  [this]
  (.getDispatcherType (:request (deref (.state this)))))

(defn -getInputStream
  [this]
  (.getInputStream (:request (deref (.state this)))))

(defn -getLocalAddr
  [this]
  (.getLocalAddr (:request (deref (.state this)))))

(defn -getLocale
  [this]
  (.getLocale (:request (deref (.state this)))))

(defn -getLocales
  [this]
  (.getLocales (:request (deref (.state this)))))


(defn -getLocalName
  [this]
  (.getLocalName (:request (deref (.state this)))))

(defn -getLocalPort
  [this]
  (.getLocalPort (:request (deref (.state this)))))

(defn -getParameter
  [this name]
  (.getParameter (:request (deref (.state this)))
                 name))

(defn -getParameterMap
  [this]
  (.getParameterMap (:request (deref (.state this)))))

(defn -getParameterNames
  [this]
  (.getParameterNames (:request (deref (.state this)))))

(defn -getParameterValues
  [this name]
  (.getParameterValues (:request (deref (.state this)))
                       name))

(defn -getProtocol
  [this]
  (.getProtocol (:request (deref (.state this)))))

(defn -getReader
  [this]
  (.getReader (:request (deref (.state this)))))

(defn -getRealPath
  [this]
  (.getRealPath (:request (deref (.state this)))))

(defn -getRemoteAddr
  [this]
  (.getRemoteAddr (:request (deref (.state this)))))

(defn -getRemoteHost
  [this]
  (.getRemoteHost (:request (deref (.state this)))))

(defn -getRemotePort
  [this]
  (.getRemotePort (:request (deref (.state this)))))

(defn -getScheme
  [this]
  (.getScheme (:request (deref (.state this)))))


(defn -getServerName
  [this]
  (.getServerName (:request (deref (.state this)))))

(defn -getServerPort
  [this]
  (.getServerPort (:request (deref (.state this)))))

(defn -isAsyncStarted
  [this]
  (.isAsyncStarted (:request (deref (.state this)))))

(defn -isAsyncSupported
  [this]
  (.isAsyncSupported (:request (deref (.state this)))))

(defn -isSecure
  [this]
  (.isSecure (:request (deref (.state this)))))

(defn -removeAttribute
  [this name]
  (.removeAttribute (:request (deref (.state this)))
                    name))

(defn -setCharacterEncoding
  [this env]
  (.setCharacterEncoding (:request (deref (.state this)))
                    env))

(defn -startAsync
  ([this]
    (.startAsync (:request (deref (.state this)))))
  ([this request response]
    (.startAsync (:request (deref (.state this))) request response)))

(defn -setContext
   [this context]
     (swap! (.state this) assoc :context context))

(defn -getContext
  [this context]
    (:context (deref (.state this))))

(defn -postConstructHandler
  [this servlet request]
    (swap! (.state this) assoc :servlet servlet)
    (swap! (.state this) assoc :request request))

(defn -init
  []
    [[] (atom {})])
