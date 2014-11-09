(ns grid.core
  (:require [clojure.reflect]
            [com.vnetpublishing.clj.grid.lib.grid.osgi.framework-container]
            [com.vnetpublishing.clj.grid.lib.grid.osgi.exec-system-activator]
            [com.vnetpublishing.clj.grid.lib.grid.servlet.core :as servlet-core]
            [clojure.java.io :as io]
            [com.vnetpublishing.clj.grid.lib.mvc.base.version :as version]
            [com.vnetpublishing.clj.grid.lib.grid.common.constants :as constants]
            [clojure.string :as string]
            [grid.dispatcher :refer :all]
            [com.vnetpublishing.clj.grid.lib.grid.osgi.functions :refer :all]
            ;[com.vnetpublishing.clj.nativedep :refer :all]
            [com.vnetpublishing.clj.grid.lib.grid.kernel :refer :all])
  (:import [org.apache.felix.framework.util FelixConstants]
           [com.vnetpublishing.clj.grid.lib.grid.common.osgi Logger]))

(defn bt-item-filename
  [item]
  (str (string/join *ds* (butlast (string/split (string/replace (.getClassName item) 
                                                                                      #"-" 
                                                                                      "_")
                                                              #"\.")))
       *ds*
       (.getFileName item)))

(defn bt-item-path
  [item]
  (string/join *ds* (butlast (string/split (string/replace (.getClassName item) 
                                                                                      #"-" 
                                                                                      "_")
                                                              #"\."))))

(defn encode-header
  [name value]
  (str name ":" value))

(defn osgi-handler
  [framework]
  (Thread/sleep 10000)
  (framework-stop framework))

(defn -main
  [& args]
  
  (let [ver (version/getVersion)]
       (debug (str "Starting Grid version "
                   (nth ver 0)
                   "."
                   (nth ver 1)
                   "."
                   (nth ver 2))))
  (binding [*debug-kernel* true]
    (let [fwc (new com.vnetpublishing.clj.grid.lib.grid.osgi.FrameworkContainer)
          activator (create-instance com.vnetpublishing.clj.grid.lib.grid.osgi.ExecSystemActivator [])
          log (Logger.)
          cache-dir (io/file "WEB-INF/cache")]
          
         (.setLogLevel log (int 4))
         
         (.setConfigVar fwc
                        "felix.cache.rootdir"
                        (.getCanonicalPath cache-dir))
         (.setConfigVar fwc
                        FelixConstants/LOG_LEVEL_PROP
                        "4")
         (.setConfigVar fwc
                        FelixConstants/LOG_LOGGER_PROP
                        log)
         
         (.setConfigVar fwc
                        "org.osgi.framework.system.packages.extra"
                        (string/join "," constants/default-osgi-exports))
         (.setConfigVar fwc
                        "org.osgi.framework.storage.clean"
                        "onFirstInit")
         
      (.setDispatchHandler activator #(servlet-core/dispatch dispatch))
      (.launch fwc activator)
      (debug "Clean exit!")
      (System/exit 0))))

  