(defproject basic "0.6.0"
  :description "Basic Pallet Usage"
  :dependencies [[org.cloudhoist/pallet "0.6.6"]
                 [org.cloudhoist/pallet-crates-all "0.5.0"]
                 ;; jclouds imports
                 [org.jclouds/jclouds-all "1.2.1"]
                 [org.jclouds/jclouds-compute "1.2.1"]
                 [org.jclouds/jclouds-blobstore "1.2.1"]
                 [org.jclouds.driver/jclouds-jsch "1.2.1"]
                 [org.jclouds.driver/jclouds-slf4j "1.2.1"]
                 ;; logging
                 [ch.qos.logback/logback-core "1.0.0"]
                 [ch.qos.logback/logback-classic "1.0.0"]]
  :dev-dependencies [[swank-clojure/swank-clojure "1.3.2"] ; swank
                     [org.cloudhoist/pallet-lein "0.4.1"]] ; lein
  :repositories
  {"sonatype" "http://oss.sonatype.org/content/repositories/releases"}
  :tasks [cake-pallet.tasks])
