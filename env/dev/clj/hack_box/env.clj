(ns hack-box.env
  (:require
   [selmer.parser :as parser]
   [clojure.tools.logging :as log]
   [hack-box.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[hack-box started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[hack-box has shut down successfully]=-"))
   :middleware wrap-dev})
