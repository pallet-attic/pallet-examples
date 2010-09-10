(ns mini-webapp.Servlet
  "Servlet stub that delegates to our application."
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:require ring.util.servlet))

;; Reference to our application code
(defonce the-app (atom nil))

(defn start-swank
  "Function to start swank server.  This can be run when the server is
   started to give a remote repl.  swank-clojure will need to be added
   as a dependency tot he war file."
  []
  (require 'swank.swank)
  (let [start-repl (var-get (ns-resolve (the-ns 'swank.swank) 'start-repl))]
    (start-repl)))

(defn resolve-and-start-app
  "This ia a workaround for the recursive AOT compilation in closure. By
   dynamically requiring and resolving, the rest of the application is not
   AOT compiled."
  []
  (require 'mini-webapp.app)
  (do
    ;; Uncomment the following line, and add swank-clojure as a dependency,
    ;; to run a remote repl inside your web app.
    ;; (start-swank)

    ;; there is a potential race condition here, but given that we shall
    ;; not be invoking this from multiple threads, I'm not worried
    (reset! the-app (var-get (ns-resolve (the-ns 'mini-webapp.app) 'app)))))

;; delay app, so that resolve-and-start-app is called once
(def app (delay (resolve-and-start-app)))


;; set up a server that serves heynote-app
(ring.util.servlet/defservice @app)
