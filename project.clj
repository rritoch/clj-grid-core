(defproject clj-grid-core "0.1.0-SNAPSHOT"
  :description "Grid Platform - Core"
  :url "http://home.vnetpublishing.com"
  
  ;:ring {:handler vwp.core/hello-handler}
  ;:plugins [[lein-ring "0.8.11"]]
  :plugins [[grid "0.1.0-SNAPSHOT"]]
  
  :grid {:default-channel "devel"
         :deploy-channels [["devel" {:app-root "public_html"}]]
         :modules [applications.grid]
         :osgi {:import-package [org.osgi.framework]}}
  
  :prep-tasks [["compile" "com.vnetpublishing.clj.grid.lib.grid.jsp.session"]
               ["compile" "com.vnetpublishing.clj.grid.lib.grid.jsp.servlet-request"]
               ["compile" "com.vnetpublishing.clj.grid.lib.grid.jsp.servlet-response"]
               ["compile" "com.vnetpublishing.clj.grid.lib.grid.jsp.instance-manager"]
               ["compile" "com.vnetpublishing.clj.grid.lib.grid.jsp.request-dispatcher"]
               ["compile" "com.vnetpublishing.clj.grid.lib.grid.jsp.servlet-context"]
               ["compile" "com.vnetpublishing.clj.grid.lib.grid.jsp.domain-context"]
               ["compile" "com.vnetpublishing.clj.grid.lib.grid.webapp.servlet-config-wrapper"]
               ["compile" "com.vnetpublishing.clj.grid.lib.grid.common.osgi.logger"]
               "javac" "compile"]
  :repositories [["releases" {:url "http://home.vnetpublishing.com/artifactory/libs-release-local"
                              :creds :gpg}]
                 ["snapshots" {:url "http://home.vnetpublishing.com/artifactory/libs-snapshot-local"
                               :creds :gpg}]]  
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-nativedep "0.1.0"]
                 [log4j/log4j "1.2.16" :exclusions [javax.mail/mail
                                              javax.jms/jms
                                              com.sun.jdmk/jmxtools
                                              com.sun.jmx/jmxri]]
                 [org.clojure/tools.logging "0.3.0"]
                 ;[javax/javaee-api "7.0"]
                 [org.apache.tomcat/tomcat-jasper "7.0.52"]
                 [clj-grid-mvc "0.1.0-SNAPSHOT"]]
  :main grid.core
  :resource-paths ["resources"]
  ;:target-path "WEB-INF/%s/"
  ;:compile-path "WEB-INF/classes"
  :aot :all)
