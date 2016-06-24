(ns space-probe.core-test
  (:use clojure.test)
  (:require [space-probe.core :as sp]))

(deftest test-map-skeleton
  (testing "size of 0"
    (is (sp/map-skeleton 0) [[]]))
  (testing "small map"
    (is (sp/map-skeleton 2) [["." "."]
                             ["." "."]])))
