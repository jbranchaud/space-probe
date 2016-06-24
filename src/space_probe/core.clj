(ns space-probe.core)

(def position-types
  {:asteroid { :character "A" :percentage 30 }
   :gravity-well { :character "G" :percentage 10 }
   :empty-space { :character "." :percentage 60 }
   })

(def position-type-distribution
  (vec
    (flatten
      (map
        (fn [[_ {c :character p :percentage}]]
          (vec (repeat p c)))
        position-types))))

(defn map-skeleton
  [N]
  (vec (repeat N
      (vec (repeat N ".")))))

(defn print-map
  "Prints a map represented by a 2D vector"
  [map]
  (doseq [row map]
    (doseq [position row]
      (print position))
    (print "\n")))

(defn vec-of-position-types
  "Create a vector of length N with distributed position types"
  [N]
  (let [rand-nums (take N (repeatedly #(rand-int 100)))]
    (map
      (fn [rand-num] (get position-type-distribution rand-num))
      rand-nums)))

(defn generate-map
  "Generates a map of size NxN"
  [N]
  (vec 
    (take N (repeatedly #(vec-of-position-types N)))))

(defn adjacent-cell-match
  [index a-row b-row c-row match-char]
  (let [a-adjacent [(- index 1) index (+ index 1)]
        b-adjacent [(- index 1) (+ index 1)]
        c-adjacent [(- index 1) index (+ index 1)]]
    (some true?
          (flatten
            (map
              (fn [[row indexes]] (map
                                  (fn [index] (= match-char (get row index)))
                                  indexes))
              {a-row a-adjacent b-row b-adjacent c-row c-adjacent})))))

(defn generate-accessability-row
  "Generates a row of Xs and dots representing accessible spaces"
  [a-row b-row c-row match-char]
  (map-indexed
    (fn [idx _] (if (adjacent-cell-match
                         idx
                         a-row
                         b-row
                         c-row
                         match-char)
                     "X" "."))
    b-row))

(defn generate-accessability-map
  "Generates a map of Xs and dots representing accessible spaces"
  [space-map]
  (map-indexed
    (fn [row-num row]
      (generate-accessability-row
        (get space-map (- row-num 1))
        row
        (get space-map (+ row-num 1))
        "G"))
    space-map))
