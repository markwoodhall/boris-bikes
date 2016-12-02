(ns boris-bikes.bikepoint
  (require [yada.yada :refer [listener resource as-resource]]
           [cheshire.core :refer [parse-string]]))

(def bikepoint-api-url "https://api.tfl.gov.uk/BikePoint")

(defn boris-bikes
  [lat lon radius-metres]
  (let [url (str bikepoint-api-url "?lat=" lat "&lon=" lon "&radius=" radius-metres "&app_id=&app_key=")]
    (:places (parse-string (slurp url) true))))

(defn closest-n-bikepoints
  [n col]
  (take n (sort-by :distance col)))

(defn ->bikepoint
  [{:keys [commonName additionalProperties distance]}]
  {:name commonName
   :bikes (:value (first (filter #(= (:key %) "NbBikes") additionalProperties)))
   :distance distance})
