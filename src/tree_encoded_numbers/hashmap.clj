(ns tree-encoded-numbers.hashmap
  (:require [clojure.math.numeric-tower :as math]))

;;; Tree encoded as a hashmap with two keys {:left :right}
(defn left-node [tree]
  (:left tree))

(defn right-node [tree]
  (:right tree))

(defn num-to-tree [n]
  (if (or (zero? n) (nil? n))
    nil
    (loop [left 0
           num n]
      (if (= (mod num 2) 0)
        (recur (inc left) (/ num 2))
        (let [right (/ (dec num) 2)
              left-tree (if (= left 0) nil (num-to-tree left))
              right-tree (if (= right 0) nil (num-to-tree right))]
          {:left left-tree :right right-tree})))))

(def mem (atom {}))
(defn my-memoize [f]
  (fn [& args]
    (if-let [e (find @mem args)]
      (val e)
      (let [ret (apply f args)]
        (swap! mem assoc args ret)
        ret))))

(def num-to-tree-memo
  (my-memoize
   (fn [n]
     (if (or (zero? n) (nil? n))
       nil
       (loop [left 0
              num n]
         (if (= (mod num 2) 0)
           (recur (inc left) (/ num 2))
           (let [right (/ (dec num) 2)
                 left-tree (if (= left 0) nil (num-to-tree-memo left))
                 right-tree (if (= right 0) nil (num-to-tree-memo right))]
             {:left left-tree :right right-tree})))))))

(declare pred)

(defn succ [tree]
;;  (prn "succ" tree)
  (if (nil? tree)
    {:left nil :right nil}
    (if (nil? (left-node tree))
      {:left (succ (left-node (succ (right-node tree)))) :right (right-node (succ (right-node tree)))}
      {:left nil :right {:left (pred (left-node tree)) :right (right-node tree)}})))

(def succ (memoize succ))

(defn pred [tree]
;;  (prn "pred" tree)
  (if (nil? (left-node tree))
    (if (nil? (right-node tree))
      nil
      {:left (succ (left-node (right-node tree))) :right (right-node (right-node tree))})
    {:left nil :right (pred {:left (pred (left-node tree)) :right (right-node tree)})}))

(def pred (memoize pred))

;;; Generic?

(defn tree-to-num [tree]
  (let [lval (if (= (left-node tree) nil) 0 (tree-to-num (left-node tree)))
        rval (if (= (right-node tree) nil) 0 (tree-to-num (right-node tree)))]
    (* (math/expt 2 lval) (+ (* 2 rval) 1))))

(defn tree-to-num [tree]
  (if (nil? tree)
    0
    (let [lval (tree-to-num (left-node tree))
          rval (tree-to-num (right-node tree))]
      (* (math/expt 2 lval) (+ (* 2 rval) 1)))))

(defn tree-to-num [tree]
  (if (nil? tree)
    0
    (* (math/expt 2 (tree-to-num (left-node tree))) (+ (* 2 (tree-to-num (right-node tree))) 1))))

(defn add-trees [t1 t2]
  (loop [result t1
         z t2]
    (if (nil? z)
      result
      (recur (succ result) (pred z)))))

(defn add-trees [t1 t2]
  (if (nil? t2)
    t1
    (add-trees (succ t1) (pred t2))))
