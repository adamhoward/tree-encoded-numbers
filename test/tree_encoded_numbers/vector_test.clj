(ns tree-encoded-numbers.vector-test
  (:use clojure.test
        tree-encoded-numbers.vector))

(deftest numbers
  (testing "Numbers"
    (is (= 0 (-> 0 num-to-tree tree-to-num)))
    (is (= 1 (-> 1 num-to-tree tree-to-num)))
    (is (= 2 (-> 2 num-to-tree tree-to-num)))
    (is (= 3 (-> 3 num-to-tree tree-to-num)))
    (is (= 0 (tree-to-num (pred (pred (succ (succ nil)))))))
    (is (= 1 (tree-to-num (pred (succ (succ nil))))))
    (is (= 2 (tree-to-num (succ (succ nil)))))
    (is (= 1 (tree-to-num (succ nil))))))

(deftest tree-structure
  (testing "Representation of tree"
    (is (= nil (num-to-tree 0)))
    (is (= [nil nil] (num-to-tree 1)))
    (is (= [[nil nil] nil] (num-to-tree 2)))
    (is (= [nil [nil nil]] (num-to-tree 3)))
    (is (= [nil [nil [nil nil]]] (num-to-tree 7)))
    (is (= [[nil [nil nil]] nil] (num-to-tree 8)))
    (is (= [nil nil] (succ nil)))
    (is (= [[nil nil] nil] (succ (succ nil))))
    (is (= [nil nil] (pred (succ (succ nil)))))
    (is (= nil (pred (pred (succ (succ nil))))))
    (is (= nil (pred nil)))
    (is (= nil (add-trees nil nil)))
    (is (= [nil nil] (add-trees [nil nil] nil)))
    (is (= [nil nil] (add-trees nil [nil nil])))
    (is (= [[nil nil] nil] (add-trees [nil nil] [nil nil])))))