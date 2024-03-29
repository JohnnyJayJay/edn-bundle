(ns edn-bundle.core
  (:import
   (java.util Locale ResourceBundle ResourceBundle$Control)
   (com.github.johnnyjayjay.ednbundle EdnResourceBundle$Control)))

(def edn-control (EdnResourceBundle$Control.))

(defn ^ResourceBundle get-bundle
  "Get a java.util.ResourceBundle using the given parameters."
  [^String base-name & {:keys [^Locale locale ^ClassLoader loader ^ResourceBundle$Control control]}]
  (cond
    (and locale loader control) (ResourceBundle/getBundle base-name locale loader control)
    (and locale loader) (ResourceBundle/getBundle base-name locale loader)
    (and locale control) (ResourceBundle/getBundle base-name locale control)
    control (ResourceBundle/getBundle base-name control)
    locale (ResourceBundle/getBundle base-name locale)
    :else (ResourceBundle/getBundle base-name)))

(defn get-object
  "Retrieve an object from a resource bundle."
  [^ResourceBundle bundle key]
  (.getObject bundle (str key)))
