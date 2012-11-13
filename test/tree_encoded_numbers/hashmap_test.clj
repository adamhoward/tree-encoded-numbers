(ns tree-encoded-numbers.hashmap-test
  (:use clojure.test
        tree-encoded-numbers.hashmap))

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
    (is (= {:left nil :right nil} (num-to-tree 1)))
    (is (= {:left {:left nil :right nil} :right nil} (num-to-tree 2)))
    (is (= {:left nil :right {:left nil :right nil}} (num-to-tree 3)))
    (is (= {:left nil :right {:left nil :right {:left nil :right nil}}} (num-to-tree 7)))
    (is (= {:left {:left nil :right {:left nil :right nil}} :right nil} (num-to-tree 8)))
    (is (= {:left nil :right nil} (succ nil)))
    (is (= {:left {:left nil :right nil} :right nil} (succ (succ nil))))
    (is (= {:left nil :right nil} (pred (succ (succ nil)))))
    (is (= nil (pred (pred (succ (succ nil))))))
    (is (= nil (pred nil)))
    (is (= nil (add-trees nil nil)))
    (is (= {:left nil :right nil} (add-trees {:left nil :right nil} nil)))
    (is (= {:left nil :right nil} (add-trees nil {:left nil :right nil})))
    (is (= {:left {:left nil :right nil} :right nil} (add-trees {:left nil :right nil} {:left nil :right nil})))))