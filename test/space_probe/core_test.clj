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

(deftest test-generate-accessibility-row
  (testing "fully accessible"
    (is (sp/generate-accessibility-row
          ["." "." "."]
          ["." "." "."]
          ["." "." "."])
        ["." "." "."]))

  (testing "basic gravity well"
    (is (sp/generate-accessibility-row
          ["." "." "."]
          ["." "G" "."]
          ["." "." "."])
        ["X" "G" "X"]))

  (testing "gravity well in above row"
    (is (sp/generate-accessibility-row
          ["." "G" "."]
          ["." "." "."]
          ["." "." "."])
        ["X" "X" "X"]))

  (testing "gravity well in below row"
    (is (sp/generate-accessibility-row
          ["." "." "."]
          ["." "." "."]
          ["." "G" "."])
        ["X" "X" "X"]))

  (testing "gravity well in corner"
    (is (sp/generate-accessibility-row
          ["G" "." "."]
          ["." "." "."]
          ["." "." "."])
        ["X" "X" "."]))

  (testing "gravity well in other corner"
    (is (sp/generate-accessibility-row
          ["." "." "."]
          ["." "." "."]
          ["." "." "G"])
        ["." "X" "X"]))

  (testing "no row above"
    (is (sp/generate-accessibility-row
          nil
          ["G" "." "."]
          ["." "." "."])
        ["G" "X" "."]))

  (testing "no row below"
    (is (sp/generate-accessibility-row
          ["." "." "."]
          ["." "." "G"]
          nil)
        ["." "X" "G"])))
