# edn-bundle

A library implementing the [EDN format](https://github.com/edn-format/edn) for the Java [ResourceBundle](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/ResourceBundle.html) API as well as some utilities to use ResourceBundles from Clojure. 

[![Clojars Project](https://img.shields.io/clojars/v/com.github.johnnyjayjay/edn-bundle.svg)](https://clojars.org/com.github.johnnyjayjay/edn-bundle)

## Usage

Write your resource bundles in EDN:

``` clojure
;; messages.edn / messages_en.edn
{:actions/submit "Submit"
 :actions/cancel "Cancel"
 :notifications.messages/new "You received {1, choice, 0#no new messages|1#one new message|1<#{1, number, integer} new messages} from {0}."}

;; messages_de.edn
{:actions/submit "Bestätigen"
 :actions/cancel "Abbrechen"
 :notifications.messages/new "Du hast {1, choice, 0#keine neuen Nachrichten|1#eine neue Nachricht|1<#{1, number, integer} neue Nachrichten} von {0} erhalten."}

;; ... etc.
```

Load them in Clojure:

``` clojure
(require '[edn-bundle.core :as bnd])

;; get-bundle calls ResourceBundle.getBundle(...) with the given (optional) parameters.
;; Setting :control to bnd/edn-control is required if you want to use the EDN format. 
;; However you can use get-bundle in any other context (e.g. with the default control and properties files) as well.
(def bundle
  (bnd/get-bundle
    "messages"
    :locale Locale/DE
    :control bnd/edn-control))

;; Retrieving objects from a bundle is simple
(bnd/get-object bundle :actions/submit) ; => "Bestätigen"
```

Use the formatting utils to format string messages:

``` clojure
(require '[edn-bundle.format :as fmt])

;; This function is just a thin wrapper for java.text.MessageFormat#format.
(fmt/format "Hello, {0}!" "World") ; => Hello, World!

(fmt/format (bnd/get-object bundle :notifications.messages/new) "Alice" 1) ; => Du hast eine neue Nachricht von Alice erhalten.
```

## License

Copyright © 2021 JohnnyJayJay

This program and the accompanying materials are made available under the
terms of the MIT License which is available at 
https://mit-license.org/.
