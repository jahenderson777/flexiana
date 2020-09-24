(ns flexiana.core
  (:require [uix.dom.alpha :as uix.dom]
            [uix.core.alpha :as uix]
            [xframe.core.alpha :as xf :refer [<sub]]
            [ajax.core :as ajax]
            ["react" :as react]
            
            [flexiana.fx]
            [flexiana.subs]))

(defn ! [& evt]
  (xf/dispatch evt))

(defn <- [k]
  (<sub [:get k]))

(defn input [{:keys [db-key size placeholder label]}]
  (let [val (<- db-key)
        ref (atom nil)]
    [:div {:style {:display "block"
                   :padding 8}}
     [:div label]
     [:input {:style {:font-size 20
                      :padding 8}
              
              :size (or size 50)
              :value (or val "")
              :placeholder placeholder
              :ref #(reset! ref %)
              :on-change #(! :set [db-key] (-> % .-target .-value))}]]))

(defn result []
  [:div {:style {:margin-top 30}}
   "result:"
   [:h1 
    (if (<- :scramble-result)
      "Yes! Your text was found in the scrambled!"
      "No :( Your text was not found in the scrambled")]])

(defn error []
  [:div {:style {:margin-top 30
                 :padding 20
                 :background "#f99"}}
   "error:"
   [:h1 (<- :scramble-error)]])

(defn main []
  (let [scramble-result (<- :scramble-result)
        scramble-error (<- :scramble-error)]
    [:div#content 
     [input {:db-key :scrambled
             :label "Scrambled text"
             :placeholder "scrambled text"}]
     [input {:db-key :to-find
             :label "Text to find"
             :placeholder "To find"}]
     [:button {:on-click #(! :scramble-request)}
      "find"]
     (if scramble-error
       [error]
       (when-not (nil? scramble-result)
         [result]))]))

(defn ^:dev/after-load start []
  (uix.dom/render [main] (.getElementById js/document "app")))

(defn init []
  (xf/dispatch [:db/init])
  (start))