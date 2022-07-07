(defproject com.taoensso/timbre "5.2.1"
  :author "Peter Taoussanis <https://www.taoensso.com>"
  :description "Pure Clojure/Script logging library"
  :url "https://github.com/ptaoussanis/timbre"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments "Same as Clojure"}
  :min-lein-version "2.3.3"
  :global-vars {*warn-on-reflection* true
                *assert*             true}

  :dependencies
  [[com.taoensso/encore "3.23.0"]
   [io.aviso/pretty     "1.1.1"]]

  :plugins
  [[lein-pprint    "1.3.2"]
   [lein-ancient   "0.7.0"]
   [lein-codox     "0.10.8"]
   [lein-cljsbuild "1.1.8"]]

  :profiles
  {;; :default [:base :system :user :provided :dev]
   :server-jvm {:jvm-opts ^:replace ["-server"]}
   :provided {:dependencies [[org.clojure/clojure       "1.7.0"]
                             [org.clojure/clojurescript "1.11.60"]]}
   :1.7      {:dependencies [[org.clojure/clojure "1.7.0"]]}
   :1.8      {:dependencies [[org.clojure/clojure "1.8.0"]]}
   :1.9      {:dependencies [[org.clojure/clojure "1.9.0"]]}
   :1.10     {:dependencies [[org.clojure/clojure "1.10.3"]]}
   :1.11     {:dependencies [[org.clojure/clojure "1.11.1"]]}
   :depr     {:jvm-opts ["-Dtaoensso.elide-deprecated=true"]}
   :dev      [:1.10 :test :server-jvm :depr :3pa]

   :3pa ; 3rd-party appenders
   {:dependencies
    [[irclj                   "0.5.0-alpha4"]
     [org.graylog2/gelfclient "1.5.1"
      :exclusions [com.fasterxml.jackson.core/jackson-core]]
     [org.julienxx/clj-slack  "0.6.3"]
     [org.clojure/java.jdbc   "0.7.12"]
     [com.mchange/c3p0        "0.9.5.5"]
     [cheshire                "5.11.0"]
     [ymilky/franzy           "0.0.1"]
     [com.newrelic.agent.java/newrelic-agent "3.30.0"]
     [net.java.dev.jna/jna    "5.12.1"]
     [raven-clj               "1.6.0"]
     [congomongo              "2.5.1"]
     [server-socket           "1.0.0"]
     [org.zeromq/cljzmq       "0.1.4"]
     [cljs-node-io            "1.1.2"] ; Node spit appender
     ]}

   :test
   {:dependencies
    [[org.clojure/test.check    "1.1.1"]
     [org.clojure/tools.logging "1.2.4"]
     [com.taoensso/nippy        "3.1.3"]
     [com.taoensso/carmine      "3.1.0"
      :exclusions [com.taoensso/timbre]]
     [com.draines/postal        "2.0.5"]]}}

  :cljsbuild
  {:test-commands
   {"node"    ["node" :node-runner "target/main.js"]
    "phantom" ["phantomjs" :runner "target/main.js"]}

   :builds
   [{:id :main
     :source-paths ["src" "test"]
     :compiler
     {:output-to "target/main.js"
      :optimizations :advanced
      :pretty-print false}}]}

  :aliases
  {"build-once" ["cljsbuild" "once"]
   "deploy-lib" ["do" "build-once," "deploy" "clojars," "install"]
   "start-dev"  ["with-profile" "+dev" "repl" ":headless"]
   "test-all"   ["do" "clean,"
                 "with-profile" "+1.10:+1.9:+1.8:+1.7" "test,"
                 "with-profile" "+test" "cljsbuild" "test"]}

  :repositories
  {"sonatype-oss-public"
   "https://oss.sonatype.org/content/groups/public/"})
