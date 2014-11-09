(ns com.vnetpublishing.clj.grid.lib.grid.jsp.servlet-context
  (:gen-class 
    :name com.vnetpublishing.clj.grid.lib.grid.jsp.ServletContext
    :extends com.vnetpublishing.clj.grid.lib.mvc.base.Object
    :implements [javax.servlet.ServletContext]
    :methods [[postConstructHandler [javax.servlet.ServletConfig] void]]
  )
  (:use [com.vnetpublishing.clj.grid.lib.grid.kernel])
  (:require [clojure.java.io :as io]))

;see https://svn.apache.org/repos/asf/tomcat/tc8.0.x/tags/TOMCAT_8_0_9/java/org/apache/catalina/core/ApplicationContext.java

(def ORDERED_LIBS nil)

(defn -addFilter
  [this filter-name filter-ref]
  
)

(defn -addListener
  [this class-ref]
  nil)

(defn -addServlet
   [this servlet-name servlet-ref]
   (let [servlet (cond (instance? String servlet-ref)
                       (.createServlet this
                                       (resolve (symbol servlet-ref)))
                       (instance? Class servlet-ref)
                       (.createServlet this
                                       servlet-ref)
                       :else
                       servlet-ref)]
     (swap! (:servlets (deref (.state this))) assoc servlet-name servlet))
   
   nil)

(defn -createFilter
  [this c]
  
)

(defn -createListener
  [this c]
)

(defn -createServlet
  [this c]
  (let [servlet (.newInstance c)]
     (.init servlet (:config (deref (.state this))))
     servlet))

(defn -declareRoles
  [& role-names]
)

(defn -getAttribute
  [this n]
  (let [ret (get (deref (:attrs (deref (.state this)))) n)]
       ret))

#_(defn -getAttributeNames
  [this]
)

(defn -getServerInfo
  [this]
  "VWP 3.0")

(defn -getClassLoader
  [this]
  (java.lang.ClassLoader/getSystemClassLoader))

#_(defn -getContext
   [this uripath]
)

#_(defn -getContextPath
   [this]
)

(defn -getDefaultSessionTrackingModes
  [this]
)

(defn -getEffectiveMinorVersion
  [this]
)

(defn -getEffectiveSessionTrackingModes
 [this]
)

(defn -getFilterRegistration
  [this name]
)

(defn -getFilterRegistrations
  [this]
)


 (defn -getInitParameter
   [this name]
 )

(defn -getInitParameterNames
  [this]
)

(defn -getJspConfigDescriptor
  [this]
)

(defn -getMajorVersion
   [this]
)
(defn -getMimeType
  [this f]
)

(defn -getMinorVersion
  [this]
)

(defn -getNamedDispatcher
   [this name]
)

(defn -getRealPath
  [this path]
  (let [rpath (str (.getCanonicalFile (java.io.File. "src")) 
                   (clojure.string/replace path "/" *ds*))]
  rpath))

(defn -getRequestDispatcher
  [this path]
  (create-instance com.vnetpublishing.clj.grid.lib.grid.jsp.RequestDispatcher [] path))


(defn -getResource
  [this path]
  (get-resource path))
 
(defn -getResourceAsStream
  [this path]
  (let [r (.getResource this path)]
    (if r (.openStream r))))

#_(defn -getResourcePaths
   [this path]
)

(defn -getServletInfo
  [this]
)

(defn -getServlet
  [this name]
  (get (deref (:servlets (deref (.state this)))) name))


(defn -getServletContextName
  [this]
)

(defn -getServletNames
  [this]
)

(defn -getServletRegistration
  [this servlet-name]
)

(defn -getServletRegistrations
  [this]
)

(defn -getServlets
  [this]
)

(defn -getSessionCookieConfig
  [this]
)

(defn -log
  ([this e msg] )
  ([this msg])
)

(defn -removeAttribute
  [this name]
  (swap! (:attrs (deref (.state this))) dissoc name))

(defn -setAttribute
  [this name obj]
  (swap! (:attrs (deref (.state this))) assoc name obj))

(defn -setInitParameter
  [this name value]
)

(defn -setSessionTrackingModes
  [this session-tracking-mode]
)

(defn -postConstructHandler
  [this config]
  (let [webtmp (java.io.File. *compile-path*)]
      (.mkdirs webtmp)
      (swap! (.state this) assoc  :attrs (atom {javax.servlet.ServletContext/TEMPDIR (.getCanonicalFile webtmp)
                                                org.apache.jasper.Constants/SERVLET_CLASSPATH (clojure.string/join java.io.File/pathSeparator (map #(.getFile %) 
                                                                                                                                                   (seq (.getURLs (java.lang.ClassLoader/getSystemClassLoader)))))
                                                "org.apache.tomcat.InstanceManager" (new com.vnetpublishing.clj.grid.lib.grid.jsp.InstanceManager)
       }))
      (swap! (.state this) assoc  :config config)
      (swap! (.state this) assoc :servlets (atom {}))))
