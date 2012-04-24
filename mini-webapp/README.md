# mini-webapp

This is a very simple web app for demonstration purposes.

## Creating

We create a blank clojure project.

### project.clj
    lein new mini-webapp

We'll use the [ring](http://github.com/mmcgrana/ring) library, so we'll add that
to the project.clj `:dependencies`

    [ring "0.2.6"]

To create a war file we'll also use the leiningen-war plugin, so we add it to
`:dev-dependencies`

    [uk.org.alienscience/leiningen-war "0.0.7"]

We also need the servlet to be AOT compiled, so we'll add an :aot entry for
mini-webapp.servlet.

    :aot [mini-webapp.Servlet]

### servlet

We need three files - the servlet, the app and the web descriptor file.

First the servlet.


## War file

    lein deps
    lein compile
    lein uberwar


## Installation

See webapp-haproxy-nodes project.

## License

Copyright (C) 2010 Hugo Duncan

Distributed under the Eclipse Public License.
