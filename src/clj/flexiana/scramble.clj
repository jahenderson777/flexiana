(ns flexiana.scramble
  (:require [clojure.string :as str]))

(defn scramble? 
  "takes two strings. Returns true if 'find-s' can be made from the characters in 's'.
   e.g.
   (scramble \"abbc\" \"abc\") => true
   (scramble \"abc\" \"abbbbbc\") => nil"
  [s find-s]
  (cond
    (empty? find-s) true
    (empty? s) false
    :else
    (if-let [idx (str/index-of s (first find-s))]      
      (recur (str (subs s 0 idx)
                  (subs s (inc idx)))
             (subs find-s 1))
      false)))

(comment

  ;; I had a go at making a fast version, but it was only 3 times faster, and the original
  ;; version seemed pretty fast, so it didn't seem worth the unreadability
  
  (defn count-chars
    "returns 26 element int-array representing character counts for a given string.
   e.g. (count-chars \"abbc\") => [1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]"
    [s]
    (let [counts (int-array 26)]
      (dotimes [i (count s)]
        (let [c-idx (- (int (nth s i)) 97)]
          (aset-int counts c-idx (inc (aget counts c-idx)))))
      counts))

  (defn scramble? [s find-s]
    (let [s-counts (count-chars s)
          find-s-counts (count-chars find-s)]
      (loop [i 25]
        (or (neg? i)
            (let [x (- (aget s-counts i)
                       (aget find-s-counts i))]
              (when (>= x 0)
                (recur (dec i))))))))

  (time 
   (do
     (scramble? "abcd" "bcvvvv")
     (scramble? "rekqodlw" "world")
     (scramble? "cedewaraaossoqqyt" "codewars")
     (scramble? "katas"  "steak")))

  (defn random-string [length]
    (apply str (take length (repeatedly #(char (+ 97 (rand-int 25)))))))

  (time (scramble? (random-string 3000) (random-string 2200)))

  (time
   (let [n 1000
         long-strs (take n (repeatedly #(random-string 3000)))
         short-strs (take n (repeatedly #(random-string 2200)))]
     (doall (for [i (range n)]
              (scramble? (nth long-strs i) (nth short-strs i))))
     nil)))

