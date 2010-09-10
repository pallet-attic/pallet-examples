(ns mini-webapp.Servlet
  "Servlet stub that delegates to our application."
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:require ring.util.servlet))

;; Reference to our application code
(defonce the-app (atom nil))

(defn resolve-and-start-app
  "This ia a workaround for the recursive AOT compilation in closure. By
   dynamically requiring and resolving, the rest of the application is not
   AOT compiled."
  []
  (require 'com.cloudhoist.app)
  (require 'swank.swank)
  (let [start-repl (var-get (ns-resolve (the-ns 'swank.swank) 'start-repl))]
    (start-repl))
  (reset!                             ; there is a potential race condition here
   the-app (var-get (ns-resolve (the-ns 'mini-webapp.app) 'app))))

(defn get-app
  "Return the application object"
  []
  (if @the-app
    @the-app
    (resolve-and-start-app)))

;; set up a server that serves heynote-app
(ring.util.servlet/defservice (get-app))
