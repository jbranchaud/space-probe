(ns space-probe.core-test
  (:use clojure.test)
  (:require [space-probe.core :as sp]))

(deftest test-map-skeleton
  (testing "size of 0"
    (is (sp/map-skeleton 0) [[]]))
  (testing "small map"
    (is (sp/map-skeleton 2) [["." "."]
                             ["." "."]])))
(deftest test-generate-accessibility-row-in-isolation
  (testing "fully accessible"
    (is (sp/generate-accessibility-row-in-isolation ["." "." "." "." "."])
        ["." "." "." "." "."]))

  (testing "As to Xs"
    (is (sp/generate-accessibility-row-in-isolation ["." "A" "."])
        ["." "X" "."]))

  (testing "Gs to adjacent Xs"
    (is (sp/generate-accessibility-row-in-isolation ["." "G" "."])
        ["X" "G" "X"]))

  (testing "adjacent Gs"
    (is (sp/generate-accessibility-row-in-isolation ["." "." "G" "G" "."])
        ["." "X" "G" "G" "X"]))

  (testing "a mix of things"
    (is (sp/generate-accessibility-row-in-isolation ["." "A" "G" "." "A"])
        ["." "X" "G" "X" "X"])))
