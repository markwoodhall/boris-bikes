(ns boris-bikes.app
  (require [boris-bikes.resources :refer [access-control bikepoints]]
           [yada.yada :refer [listener resource as-resource]]
           [com.stuartsierra.component :as component])
  (:gen-class))

(defrecord Server [port]
  component/Lifecycle

  (start [{:keys [port] :as this}]
    (println "Starting server on port" port)
    (let [srv (listener
                ["/"
                 [
                  ["bikepoints" (-> [51.5649218 -0.0233642]
                               bikepoints
                               (merge access-control)
                               resource)]
                  ]]
                {:port port})]
      (assoc this :server srv)))

  (stop [{:keys [port server] :as this}]
    (println "Stopping server on port" port)
    (if-let [close (:close server)]
      (close))
    (assoc this :server nil)))

(defn new-system
  [config-options]
  (let [{:keys [port]} config-options]
    (component/system-map
      :server (map->Server {:port port}))))

(defn -main
  [& args]
  (let [system (new-system {:port (Integer/parseInt (System/getenv "PORT"))})]
    (component/start system)
    (loop [input (read-line)]
      (if (= input "Q")
        (component/stop-system system)
        (recur (read-line))))))

