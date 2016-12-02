(ns boris-bikes.resources
  (require [boris-bikes.bikepoint :refer [boris-bikes closest-n-bikepoints ->bikepoint]]
           [schema.core :as s]))

(def access-control
  {:access-control {:realm "Bikes"
                    :scheme "Basic"
                    :authorization {:methods {:get "bikepoints/view"}}
                    :verify (fn [[user password]]
                              (if (and (= user "mark")
                                       (= password "changeme"))
                                {:user user :roles #{"bikepoints/view"}}))}})

(defn bikepoints
  [[lat lon radius number]]
  {:methods {:get
             {:parameters
              {:query {(s/optional-key :lat) Double
                       (s/optional-key :lon) Double
                       (s/optional-key :radius) Double
                       (s/optional-key :number) Double}}
              :produces #{"application/json" "text/html"}
              :response (fn [ctx] (let [lat (or (get-in ctx [:parameters :query :lat]) lat)
                                        lon (or (get-in ctx [:parameters :query :lon]) lon)
                                        radius (or (get-in ctx [:parameters :query :radius]) radius)
                                        number (or (get-in ctx [:parameters :query :number]) number)]
                                    (->> (boris-bikes lat lon radius)
                                         (closest-n-bikepoints number)
                                         (map ->bikepoint))))}}})
