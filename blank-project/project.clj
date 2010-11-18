(defproject blank-project "0.4.0-SNAPSHOT"
  :description "blank-project for pallet"
  :dependencies [[org.cloudhoist/pallet "0.4.0-SNAPSHOT"]
                 [org.cloudhoist/pallet-crates-all "0.4.0-SNAPSHOT"]
                 [org.jclouds/jclouds-all "1.0-beta-8"]
                 [org.jclouds/jclouds-jsch "1.0-beta-8"]
                 [org.jclouds/jclouds-log4j "1.0-beta-8"]
                 [log4j/log4j "1.2.14"]]

  :dev-dependencies [;;[swank-clojure/swank-clojure "1.2.1"]
                     [org.cloudhoist/pallet-lein "0.2.0"]
                     [cake-pallet "0.1.0"]]
  :tasks [cake-pallet.tasks]
  :repositories {"sonatype" "https://oss.sonatype.org/content/repositories/releases/"
                 "sonatype-snapshots" "https://oss.sonatype.org/content/repositories/snapshots/"})
