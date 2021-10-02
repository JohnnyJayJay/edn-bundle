(defproject com.github.johnnyjayjay/edn-bundle "0.1.0-SNAPSHOT"
  :description "EDN format for Java's ResourceBundle API"
  :url "https://github.com/JohnnyJayJay/edn-bundle"
  :license {:name "MIT License"
            :url "https://mit-license.org/"}
  :dependencies [[org.clojure/clojure "1.10.3"]]
  :source-paths ["src/clojure"]
  :java-source-paths ["src/java"]
  :repl-options {:init-ns edn-bundle.core})
