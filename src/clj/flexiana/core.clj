(ns flexiana.core
  (:gen-class)
  (:require [compojure.api.sweet :as compojure-api]
            [compojure.route :as route]
            [ring.util.http-response :refer [ok]]
            [clojure.java.io :as io]
            [schema.core :as s]
            [flexiana.scramble :as scramble]
            [ring.adapter.jetty :refer [run-jetty]]))

(def handler
  (compojure-api/routes
   (compojure-api/api
    {:swagger
     {:ui "/swagger"
      :spec "/swagger.json"
      :data {:info {:title "Scramblies-api"
                    :description "Flexiana technical challenge"}
             :tags [{:name "api", :description "Scramblies API"}]}}}

    (compojure-api/context "/api" []
      :tags ["api"]

      (compojure-api/GET "/scramble" []
        :return {:result Boolean}
        :query-params [s :- String, find-s :- String]
        :summary "Takes two strings. Returns true if 'find-s' can be made from the characters in 's'"
        (ok {:result (scramble/scramble? s find-s)}))))
   (route/resources "/")
   (route/not-found "404 Not Found")))


(defn -main [& args]
  (def stop-server
    (run-jetty handler {:port  3000
                        :join? false})))