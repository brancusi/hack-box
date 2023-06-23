(ns hack-box.apis.internal.routes
  (:require [hack-box.middleware.auth :as auth-middleware]
            [hack-box.middleware.exception :as exception]
            [hack-box.middleware.formats :as formats]
            [hack-box.middleware.logger :refer [wrap-logger]]
            [reitit.coercion.spec :as spec-coercion]
            [reitit.ring.coercion :as coercion]
            [reitit.ring.middleware.multipart :as multipart]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.parameters :as parameters]
            [reitit.swagger :as swagger]
            [reitit.swagger-ui :as swagger-ui]))

(defn temp-get-handler
  [{:keys [body-params]}]
  (tap> body-params)
  {:status 200
   :body
   {:result {:msg "Hello Son"}}})

(defn temp-post-handler
  [{:keys [body-params]}]
  (tap> body-params)
  {:status 200
   :body
   {:result {:msg "Hello Son"}}})

(defn routes []
  [""
   {:coercion spec-coercion/coercion
    :muuntaja formats/instance
    :swagger {:id ::api}
    :middleware [;; query-params & form-params
                 parameters/parameters-middleware
                 ;; content-negotiation
                 muuntaja/format-negotiate-middleware
                 ;; encoding response body
                 muuntaja/format-response-middleware
                 ;; exception handling
                 exception/exception-middleware
                 ;; decoding request body
                 muuntaja/format-request-middleware
                 ;; coercing request parameters
                 coercion/coerce-request-middleware
                 ;; coercing response bodys
                 coercion/coerce-response-middleware
                 ;; multipart
                 multipart/multipart-middleware
                 ;; logger middleware
                 wrap-logger]}

   ["/api" {:no-doc true
            :swagger {:info {:title "hack-box"
                             :description "https://cljdoc.org/d/metosin/reitit"}}}

    ["/swagger.json"
     {:get (swagger/create-swagger-handler)}]

    ["/api-docs/*"
     {:get (swagger-ui/create-swagger-ui-handler
            {:url "/swagger.json"
             :config {:validator-url nil}})}]
    ["/authed"
     {:parameters {:header {:x-api-key string?}}
      :middleware [auth-middleware/wrap-api-key]}

     ["/my-endpoint"
      {:swagger {:tags ["my-endpoint"]}}

      [""
       {:get {:summary "Get"
              :responses {200 {:body {:result map?}}
                          400 {:body {:error string?}}}
              :handler temp-get-handler}
        :post {:responses {200 {:body {:result map?}}
                           400 {:body {:error string?}}}
               :handler temp-post-handler}}]]]]])
