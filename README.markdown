This is a small library for Compojure that allows defined functions to
be enhanced by decorator functions.

For example, here is a logging decorator function:

    (defn wrap-logging [func desc]
      (fn [& args]
        (prn "Entering" desc)
        (apply func args)
        (prn "Leaving" desc)))

You can apply it to an existing function with the `decorate` macro:

    (decorate handle-request
      (wrap-logging "request handler"))

This will redefine the handle-request function, keeping the metadata
intact.

The decorate library provides four macros:

`(redef name value)` - redefine a var without losing its metadata.

`(decorate func & decorators)` - wrap a single function in one or
more decorators.

`(decorate-with decorator & funcs)` - wrap many functions in the
same decorator.

`(decorate-bind decorator funcs & body)` - wrap many functions in
same decorator for a bounded scope.
