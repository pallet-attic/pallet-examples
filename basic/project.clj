(defproject basic "0.4.0"
  :description "Basic Pallet Usage"
  :dependencies [[org.cloudhoist/pallet "0.4.3"]
                 [org.cloudhoist/pallet-crates-standalone "0.4.0"]
                 [org.jclouds/jclouds-all "1.0-beta-8"]
                 [org.jclouds/jclouds-jsch "1.0-beta-8"]
                 [org.jclouds/jclouds-log4j "1.0-beta-8"]
                 [log4j/log4j "1.2.14"]]
  :dev-dependencies [[swank-clojure/swank-clojure "1.2.1"] ; swank
                     [vmfest "0.2.2"]                      ; virtualbox
                     [org.cloudhoist/pallet-lein "0.4.0"]  ; lein
                     [cake-pallet "0.4.0"]]                ; cake
  :repositories
  {"sonatype" "http://oss.sonatype.org/content/repositories/releases"
   "nexus" "https://nexus.sourcesense.com/nexus/content/groups/public"}
  :tasks [cake-pallet.tasks])
