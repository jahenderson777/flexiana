(ns flexiana.scramble-test
  (:require [clojure.test :refer :all]
            [flexiana.scramble :refer [scramble?]]
            [clojure.string :as str]
            ;[clojure.spec.alpha :as s]
            ;[clojure.spec.gen.alpha :as gen]
            ;[clojure.spec.test.alpha :as stest]
            ))


(deftest scramble-test-simple
  (testing "given examples"
    (is (= true (scramble? "rekqodlw" "world")))
    (is (= true (scramble? "cedewaraaossoqqyt" "codewars")))
    (is (= false (scramble? "katas" "steak"))))
  (testing "edge cases"
    (is (= true (scramble? "" "")))))