(ns com.vnetpublishing.clj.grid.lib.grid.jsp.request-dispatcher
  
  (:gen-class :name com.vnetpublishing.clj.grid.lib.grid.jsp.RequestDispatcher
              :implements [javax.servlet.RequestDispatcher]
              :state state
              :init init
              :methods [[postConstructHandler [String] void]])
  (:require [com.vnetpublishing.clj.grid.lib.grid.http-mapper :as http-mapper]
            [com.vnetpublishing.clj.grid.lib.grid.kernel :refer :all]))


;see http://tomcat.apache.org/tomcat-8.0-doc/api/index.html?org/apache/jasper/JspC.html
;https://svn.apache.org/repos/asf/tomcat/tc8.0.x/tags/TOMCAT_8_0_9/java/org/apache/jasper/

(def ERROR_EXCEPTION "")
 
(def ERROR_EXCEPTION_TYPE "")

(def ERROR_MESSAGE "")
(def ERROR_REQUEST_URI "")
(def ERROR_SERVLET_NAME "")
(def ERROR_STATUS_CODE "")
(def FORWARD_CONTEXT_PATH "")
(def FORWARD_PATH_INFO "")
(def FORWARD_QUERY_STRING "")
(def FORWARD_REQUEST_URI "")
(def FORWARD_SERVLET_PATH "")
(def INCLUDE_CONTEXT_PATH "")
(def INCLUDE_PATH_INFO "")
(def INCLUDE_QUERY_STRING "")
(def INCLUDE_REQUEST_URI "")


; Tomcat provides wrapper that is a structure provided by Tomcat when dispatcher is created
; Tomcat provides support object that is used to provide event channel

; Tomcat wrapper provides what? 

; Tomcat filter factory creates filter chain
; Tomcat filter chain provides ApplicationFilterConfig
; Tomcat ApplicationFilterConfig provides filter 

; Tomcat provides org.apache.catalina.deploy.FilterDef which provides class name

; Tomcat StandardWrapper.allocate provides servlet via loadServlet?
; Tomcat StandardWrapper provides servlet via findChild?


; Java provides filterconfig
; Java provides filter

(defn ^:private invoke
  [dispatcher request response state]
  
  (let [servletcontext (.getServletContext request)
        servlet (.createServlet servletcontext (http-mapper/map-url (:path (deref (.state dispatcher)))))]
     (.setAttribute request javax.servlet.RequestDispatcher/INCLUDE_SERVLET_PATH (:path (deref (.state dispatcher))))
         (binding [*out* *err*]
           (println (str "invoke to: " 
                         (:path (deref (.state dispatcher)))
                         " servletcontext: "
                         (class servletcontext)
                         " servlet: "
                         (class servlet)
                         
                         )))
     ; set RequestDispatcher.INCLUDE_PATH_INFO
    (.service servlet request response)))

(defn -forward
  [this request response]
  (if (.isCommitted response)
      (throw (java.lang.IllegalStateException. "Response committed")))
  
  (.resetBuffer response)
  
  (let [servlet-path nil
        path-info nil
        r-state (atom {:outter-request request
                       :outter-response response
                       :including false
                       :wrap-request nil
                       :wrap-response nil
                       :hrequest nil
                       :hresponse nil})]
     ; Check compliance???
     ; Wrap Response???
     (if (not (or servlet-path path-info))
       (do
         ; Wrap request??
         ; Apply Request Attributes???
         (invoke this request response r-state))
       (do
         ; ???
       ))))

(defn -include
  [this request response]
    nil)

(defn -postConstructHandler
  [this path]
    (swap! (.state this) assoc :path path))

(defn -init
  []
    [[] (atom {})])
