# tree-encoded-numbers

Representing positive integers as binary trees in Clojure, based on
the work of David Haraburda.

See http://students.cse.unt.edu/~dh0142/ for thesis and associated
Java implementation.

## Try it
1. Clone repo
2. Run lein repl

```clojure
user=> (use 'tree-encoded-numbers.viz)
nil
user=> (in-ns 'tree-encoded-numbers.viz)
#<Namespace tree-encoded-numbers.viz>
tree-encoded-numbers.viz=> (show-tree-for-num 82)
```
A window will open displaying the structure of the tree.

<img src="https://github.com/downloads/adamhoward/tree-encoded-numbers/tree-82.png" alt="82 as a tree" title="82 as a tree" />

