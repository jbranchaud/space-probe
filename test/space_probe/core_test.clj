(ns space-probe.core-test
  (:use clojure.test)
  (:require [space-probe.core :as sp]))

(deftest test-add-things
  (is (= 2 (sp/add-things 1 1))))

(deftest add-1-to-1
  (is (= 2 (+ 1 1))))
