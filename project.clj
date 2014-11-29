(defproject clj-grid-core "0.1.0-SNAPSHOT"
  :description "Grid Platform - Core"
  :url "http://home.vnetpublishing.com"
  
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
               ["compile" "com.vnetpublishing.clj.grid.lib.grid.webapp.filter-registration"]
               ["compile" "com.vnetpublishing.clj.grid.lib.grid.webapp.servlet-config-wrapper"]
               ["compile" "com.vnetpublishing.clj.grid.lib.grid.webapp.servlet-request-wrapper"]
               ["compile" "com.vnetpublishing.clj.grid.lib.grid.common.osgi.logger"]
               "javac" "compile"]
  :dependencies [[clj-grid-kernel "0.1.0-SNAPSHOT"]
                 [clj-grid-mvc "0.1.0-SNAPSHOT"]
                 [clj-nativedep "0.1.0"]
                 [log4j/log4j "1.2.16" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jdmk/jmxtools
                                                    com.sun.jmx/jmxri]]
                 [org.clojure/tools.logging "0.3.0"]
                 [org.apache.tomcat/tomcat-jasper "7.0.52"]
                 [com.cemerick/pomegranate "0.3.0"]
                 [org.apache.tika/tika-core "1.5"]]
  :main grid.core
  :resource-paths ["resources"]
  ;:target-path "WEB-INF/%s/"
  ;:compile-path "WEB-INF/classes"
  :aot :all)
