(defproject blank-project "0.6.0"
  :description "blank-project for pallet"
  :dependencies [[org.cloudhoist/pallet "0.6.4"]
                 [org.cloudhoist/pallet-crates-all "0.5.0"]
                 [org.jclouds/jclouds-all "1.0.0"]
                 [org.jclouds.driver/jclouds-jsch "1.0.0"]
                 [org.jclouds.driver/jclouds-log4j "1.0.0"]
                 [log4j/log4j "1.2.14"]]
  :dev-dependencies [[swank-clojure/swank-clojure "1.3.2"] ; swank
                     [vmfest "0.2.2"]                      ; virtualbox
                     [org.cloudhoist/pallet-lein "0.4.1"]  ; lein
                     [cake-pallet "0.4.0"]]                ; cake
  :repositories
  {"sonatype" "https://oss.sonatype.org/content/repositories/releases/"}
  :tasks [cake-pallet.tasks])
