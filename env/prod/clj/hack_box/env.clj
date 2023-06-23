(ns hack-box.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[hack-box started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[hack-box has shut down successfully]=-"))
   :middleware identity})
