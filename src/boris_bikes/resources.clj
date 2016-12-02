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
  [[lat lon]]
  {:methods {:get
             {:parameters {:query {(s/optional-key :lat) Double (s/optional-key :lon) Double}}
              :produces #{"application/json" "text/html"}
              :response (fn [ctx] (let [lat (or (get-in ctx [:parameters :query :lat]) lat)
                                        lon (or (get-in ctx [:parameters :query :lon]) lon)]
                                    (->> (boris-bikes lat lon 5000)
                                         (closest-n-bikepoints 5)
                                         (map ->bikepoint))))}}})
