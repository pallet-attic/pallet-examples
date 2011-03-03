(defproject webapp-nodes "0.4.0"
  :description "Webapp on tomcat"
  :dependencies [[org.cloudhoist/pallet "0.4.6"
                  :exclusions [org.jclouds/jclouds-compute
                               org.jclouds/jclouds-blobstore
                               org.jclouds/jclouds-scriptbuilder
                               org.jclouds/jclouds-aws
                               org.jclouds/jclouds-bluelock
                               org.jclouds/jclouds-gogrid
                               org.jclouds/jclouds-rackspace
                               org.jclouds/jclouds-rimuhosting
                               org.jclouds/jclouds-slicehost
                               org.jclouds/jclouds-terremark
                               org.jclouds/jclouds-jsch
                               org.jclouds/jclouds-log4j
                               org.jclouds/jclouds-enterprise]]
                 [org.cloudhoist/pallet-crates-all "0.4.0"]
                 [org.jclouds/jclouds-all "1.0-beta-9b"]
                 [org.jclouds.driver/jclouds-jsch "1.0-beta-9b"]
                 [org.jclouds.driver/jclouds-log4j "1.0-beta-9b"]
                 [org.jclouds.driver/jclouds-enterprise "1.0-beta-9b"]
                 [com.jcraft/jsch "0.1.42"]
                 [log4j/log4j "1.2.14"]]
  :dev-dependencies [[swank-clojure/swank-clojure "1.2.1"] ; swank
                     [vmfest "0.2.2"]                      ; virtualbox
                     [org.cloudhoist/pallet-lein "0.4.0"]  ; lein
                     [cake-pallet "0.4.0"]]                ; cake
  :repositories
  {"sonatype" "https://oss.sonatype.org/content/repositories/releases"}
  :tasks [cake-pallet.tasks])
