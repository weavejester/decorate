;; Copyright (c) James Reeves. All rights reserved.
;; The use and distribution terms for this software are covered by the Eclipse
;; Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) which
;; can be found in the file epl-v10.html at the root of this distribution. By
;; using this software in any fashion, you are agreeing to be bound by the
;; terms of this license. You must not remove this notice, or any other, from
;; this software.

(ns decorate
  "Macros for redefining functions with decorators.")

(defmacro redef
  "Redefine an existing var, keeping the metadata intact."
  [name value]
  `(let [m# (meta #'~name)
         v# (def ~name ~value)]
     (alter-meta! v# merge m#)
     v#))

(defmacro decorate
  "Wrap a function in one or more decorators."
  [func & decorators]
  `(redef ~func (-> ~func ~@decorators)))

(defmacro decorate-with
  "Wrap multiple functions in a single decorator."
  [decorator & funcs]
  `(do ~@(for [f funcs]
          `(redef ~f (~decorator ~f)))))

(defmacro decorate-bind
  "Wrap named functions in a decorator for a bounded scope."
  [decorator funcs & body]
  `(binding
     [~@(mapcat (fn [f] [f (list decorator f)]) funcs)]
     ~@body))
