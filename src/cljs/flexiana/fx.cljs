(ns flexiana.fx
  (:require [xframe.core.alpha :as xf]
            [ajax.core :as ajax]
            ))

(xf/reg-fx :http-get
           (fn [_ [_ {:keys [url on-ok on-failed params]}]]
             (ajax/GET url {:handler #(xf/dispatch (conj on-ok %))
                            :error-handler (if on-failed
                                             #(xf/dispatch (conj on-failed %))
                                             println)
                            :keywords? true
                            :params params
                            :response-format :json})))

(xf/reg-fx :http-post
           (fn [_ [_ {:keys [url params]}]]
             (ajax/POST url {:handler println
                             :error-handler println
                             :params params
                             :format :raw})))

(xf/reg-event-fx
 :db/init
 (fn [_ _]
   {:db {}}))

(xf/reg-event-db
 :set
 (fn [db [_ ks v]]
   (assoc-in db ks v)))

(xf/reg-event-db
 :scramble-request-result
 (fn [db [_ result]]
   (assoc db :scramble-result (:result result))))

(xf/reg-event-db
 :scramble-request-error
 (fn [db [_ result]]
   (assoc db :scramble-error "something is up with the api :(")))

(xf/reg-event-fx
 :scramble-request
 (fn [{:keys [scrambled to-find] :as db} _]
   {:db (dissoc db :scramble-result :scramble-error)
    :http-get {:url "/api/scramble"
               :on-ok [:scramble-request-result]
               :params {:s scrambled :find-s to-find}
               :on-failed [:scramble-request-error]}}))

