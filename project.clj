(defproject boris-bikes "0.1.0-SNAPSHOT"
  :description "FIXME: write description"

  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [yada "1.1.33"]
                 [aleph "0.4.1"]
                 [bidi "2.0.8"]
                 [com.stuartsierra/component "0.3.1"]
                 [cheshire "5.6.3"]]
  :target-path "target/%s"
  :main boris-bikes.app
  :min-lein-version "2.0.0"
  :uberjar-name "boris-bikes.jar"
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[reloaded.repl "0.2.3"]
                                  [org.clojure/tools.namespace "0.2.11"]]
                   :source-paths ["dev"]}})
