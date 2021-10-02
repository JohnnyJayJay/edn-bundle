(ns edn-bundle.format
  (:import (java.text MessageFormat))
  (:refer-clojure :exclude [format]))

(defn format
  "Format a list of arguments using a java.text.MessageFormat pattern."
  [^String pattern & args]
  (.format (MessageFormat. pattern) (into-array Object args)))
