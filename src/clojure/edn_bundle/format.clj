(ns edn-bundle.format
  (:import (java.text MessageFormat))
  (:refer-clojure :exclude [format]))

(defn format [^String pattern & args]
  (.format (MessageFormat. pattern) (into-array Object args)))
