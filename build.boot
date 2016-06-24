; vim: set ft=clojure:

(set-env! :dependencies
          '[[boot/core "2.0.0-rc8"]
            [org.clojure/clojure "1.7.0"]
            [adzerk/boot-test  "1.1.1" :scope "test"]]
          :source-paths #{"src/" "test/"})

(require '[adzerk.boot-test :refer :all])
(require '[space-probe.core])

(deftask do-thing
  "Does the thing"
  []
  (println "Doing the thing"))

; (deftask do-thing-from-src
;   "Does the things from src"
;   []
;   (space-probe.core/do-src-thing))

(deftask space-probe
  "Let's probe into space!"
  []
  (let [space-map (space-probe.core/generate-map 20)
        accessability-map (space-probe.core/generate-accessability-map space-map)]
    (do
      (space-probe.core/print-map space-map)
      (println "")
      (space-probe.core/print-map accessability-map))))

; (defn -main  [& args]
;   "Main function to do the thing"
;   []
;   (space-probe.core/do-src-thing))
