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

(defn next-to-gravity-sink-in-isolation
  [idx row]
  (or (= (get row (- idx 1)) "G")
      (= (get row (+ idx 1)) "G")))

(defn mark-gravity-sinks
  "mark each occurrence of G as X"
  [row]
  (map
    #(if (= % "G")
       "X" %)
    row))

(defn generate-accessibility-row-in-isolation
  "Generates a row of dots, Xs, and Gs to represent accessibility"
  [row]
  (mark-gravity-sinks
    (map-indexed
      (fn [idx position]
        (if (= position "G")
          "G"
          (if (= position "A")
            "X"
            (if (next-to-gravity-sink-in-isolation idx row)
              "X"
              "."))))
      row)))

(defn gravity-sink-in-adjacent-row
  [idx row]
  (or (= (get row (- idx 1)) "G")
      (= (get row idx) "G")
      (= (get row (+ idx 1)) "G")))

(defn next-to-gravity-sink
  [idx above-row row below-row]
  (or
    (next-to-gravity-sink-in-isolation idx row)
    (gravity-sink-in-adjacent-row idx above-row)
    (gravity-sink-in-adjacent-row idx below-row)))

(defn generate-accessibility-row
  "Generates an accessibility row of dots, Xs, and Gs in context of above/below rows"
  [above-row row below-row]
  (mark-gravity-sinks
    (map-indexed
      (fn [idx position]
        (if (= position "G")
          "G"
          (if (= position "A")
            "X"
            (if (next-to-gravity-sink idx above-row row below-row)
              "X"
              "."))))
      row)))

(defn generate-map
  "Generates a map of size NxN"
  [N]
  (vec 
    (take N (repeatedly #(vec-of-position-types N)))))

(defn generate-accessability-map
  "Generates a map of Xs and dots representing accessible spaces"
  [space-map]
  (map-indexed
    (fn [row-num row]
      (generate-accessibility-row
        (get space-map (- row-num 1))
        row
        (get space-map (+ row-num 1))))
    space-map))
