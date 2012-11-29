(ns tree-encoded-numbers.viz
  (:require [clojure.zip :as zip]
            [dorothy.core :as d]
            [tree-encoded-numbers.vector :as tree]))

(defn num-tree-to-dorothy
  "Takes a vector of the form [2 [1 0 0] 0] representing a tree
   and returns a vector suitable for the digraph function in the
   dorothy library"
  [original]
  (loop [loc (zip/vector-zip original)
         acc []]
    (if (zip/end? loc)
      acc
      (recur (zip/next loc)
             (if-not (or (vector? (zip/node loc))
                         (nil? (zip/prev (zip/prev loc))))
               (if (= (first (zip/node (zip/up loc))) (zip/node loc))
                 (conj acc
                       [(d/gen-id (zip/next (zip/up (zip/up loc)))) (d/gen-id loc)]
                       [(d/gen-id loc) {:label (str (zip/node loc))}]
                       [(d/gen-id (zip/next (zip/up (zip/up loc)))) {:label (str (zip/node (zip/next (zip/up (zip/up loc)))))}])
                 (conj acc
                       [(d/gen-id (zip/next (zip/up loc))) (d/gen-id loc)]
                       [(d/gen-id loc) {:label (str (zip/node loc))}]
                       [(d/gen-id (zip/next (zip/up loc))) {:label (str (zip/node (zip/next (zip/up loc))))}]))
               acc)))))

(defn show-tree-for-num [n]
  "Shows the tree in a window"
  (->
   (d/digraph (num-tree-to-dorothy (tree/tree-to-num-tree (tree/num-to-tree n))))
   d/dot
   d/show!))

(defn save-tree-for-num [n]
  "Saves the tree as a png"
  (->
   (d/digraph (num-tree-to-dorothy (tree/tree-to-num-tree (tree/num-to-tree n))))
   d/dot
   (d/save! "out.png" {:format :png})))
