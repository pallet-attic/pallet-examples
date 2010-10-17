(ns hudson.ci
  (:require
   [pallet.core :as core]
   [pallet.crate.automated-admin-user :as automated-admin-user]
   [pallet.crate.git :as git]
   [pallet.crate.hudson :as hudson]
   [pallet.crate.tomcat :as tomcat]
   [pallet.maven :as maven]
   [pallet.parameter :as parameter]
   [pallet.resource :as resource]
   [pallet.resource.directory :as directory]
   [pallet.resource.package :as package]
   [pallet.resource.remote-file :as remote-file]
   [pallet.resource.service :as service]
   [pallet.resource.user :as user]
   [pallet.stevedore :as stevedore]))

(defn setup-users
  "Setup the hudson user"
  [request]
  (let [user (parameter/get-for-target request [:hudson :user])]
    (->
     request
     (user/user user :comment "\"hudson,,,\""))))

(defn ci-config
  [request]
  (let [properties (maven/properties)]
    (->
     request
     (package/package "maven2")
     (git/git)
     (tomcat/tomcat)
     (user/user "testuser" :create-home true :shell :bash)
     (service/with-restart "tomcat6"
       (tomcat/server-configuration (tomcat/server))
       (hudson/tomcat-deploy)
       (hudson/config
        :use-security true
        :security-realm :hudson
        :authorization-strategy :global-matrix
        :permissions [{:user "me" :permissions hudson/all-permissions}
                      {:user "maintainer"
                       :permissions #{:hudson-read
                                      :item-build :item-read
                                      :item-workspace :run-delete :run-update
                                      :scm-tag}}
                      {:user "anonymous" :permissions [:item-read]}]
        :admin-user "admin"
        :admin-password "password")
       (setup-users)
       (hudson/plugin :git)
       (hudson/plugin :github)
       (hudson/plugin :instant-messaging)
       (hudson/plugin
        :ircbot
        :enabled true :hostname "irc.freenode.net" :port 6667
        :nick "jcloudsci" :nick-serv-password "jcloudsci"
        :hudson-login "ircbot"
        :hudson-password "ircpwd"
        :default-targets [{:name "#jclouds"}]
        :command-prefix "hudson:")
       (hudson/user
        "me"
        {:full-name "My Name"
         :password-hash "password"
         :email "my.email@somewhere.com"})
       (hudson/user
        "ircbot"
        {:full-name "IRC bot"
         :password-hash "ircpwd"
         :email "bot.email@somewhere.com"})
       (hudson/maven "default maven" "2.2.1")
       (hudson/job :maven2 "jclouds"
                   :maven-name "default maven"
                   :goals "-P clean deploy"
                   :group-id "org.jclouds"
                   :artifact-id "jclouds"
                   :branches ["origin/*"]
                   :merge-target "master"
                   :github {:projectUrl "http://github.com/hugoduncan/pallet/"}
                   :aggregator-style-build true
                   :maven-opts ""
                   :scm ["git://github.com/jclouds/jclouds.git"]
                   :publishers {:ircbot
                                {:targets [{:name "#jclouds"}]
                                 :strategy :all}})))))

(core/defnode hudson
  "Hudson node"
  {}
  :bootstrap (resource/phase
              (automated-admin-user/automated-admin-user))
  :configure (resource/phase
              (ci-config))
  :restart-tomcat (resource/phase
                   (service/service "tomcat6" :action :restart))
  :reload-configuration (resource/phase
                         (hudson/reload-configuration))
  :build-pallet (resource/phase (hudson/build "jclouds")))
