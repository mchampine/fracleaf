(ns fracleaf.core
  (:require [uncomplicate.neanderthal.linalg :as linalg]
            [tech.viz.vega :as vega])
  (:use     [uncomplicate.neanderthal core native vect-math]
            [clojure.repl]))

;; Based on Gareth Williams 9th Edition - Linear Algebra with
;; Applications 2017 p. 133 Fractal Pictures of Nature

;; "A fractal is a convenient label for irregular and fragmented
;; self-similar shapes"

;; This exercise creates a leaf shape by repeatedly applying affine
;; transformations (matrix xform + translation) to an initial point.

;; The chosen tranformations are applied with varying probabilities
;; and in a random order.

;; p. 124: Affine Transformation T(u) = A(u) + v
(defn affinexform
  "Perform an affine transform of the vector av by matrix am and scalar value sv"
  [am av sv]
  (xpy
   (mv (dge 2 2 am) (dv sv))
   (dv av)))

;; p. 135:
;; "The affine transformations and corresponding probabilities that
;; generate a fractal are written as rows of a matrix, called an
;; iterated function system (IFS). The IFS for the fern is as follows."
(def myaffine1 (partial affinexform [0.86 -0.03 0.03 0.86] [0 1.5])) ;; 83%
(def myaffine2 (partial affinexform [0.2 0.21 -0.25 0.23] [0 1.5]))  ;; 8%
(def myaffine3 (partial affinexform [-0.15 0.25 0.27 0.26] [0 0.45])) ;; 8%
(def myaffine4 (partial affinexform [0 0 0 0.17] [0 0])) ;; 1%

;; The first challenge is to call the affine transformations with
;; the specified probabilities. Start by generating a sequence with
;; functions represented in the right frequencies (according to probabilities)

(defn frequencies-inv
  "Inverse of 'frequencies'. Provide a map of counts and values
  and get back the dataset that would produce them."
  [fm]
  (mapcat (fn [[k v]] (repeat v k)) fm))

(frequencies-inv {0 2, 1 1, 2 1, 3 3, 4 1, 5 1, 6 3})
;; => (0 0 1 2 3 3 3 4 5 6 6 6)

;; ifs-affines is the IFS expressed as 100 affine 
;; transformation function instances with the specified counts
(def ifs-affines (frequencies-inv {myaffine1 83
                                   myaffine2 8
                                   myaffine3 8
                                   myaffine4 1}))

(defn rnd-aftx
  "Apply a random affine transform from ifs-affines to the supplied coordinate"
  [coord]
  (let [r (int (* 100 (rand)))
        atxfn (nth ifs-affines r)] ;; choose one randomly
    (atxfn coord)))

;;; Visualization

;; generate points
(defn aftx->data
  "Iterate affine transform aftx n times from [0 0]
  and return a map of :x :y coordinates"
  [aftx n]
  (->> (iterate aftx [0 0])
       (take n)
       (mapv (fn [v] (let [[vl vr] (into [] v)] {:x vl :y vr})))))

;; plot them
(defn myscat
  "Make a scatterplot from x,y coordinate data"
  [data title filenam]
  (-> (vega/scatterplot data :x :y
                      {:title title :height 800 :width 500})
    (vega/vega->svg-file filenam)))

;; 1,000 points
(myscat (aftx->data rnd-aftx 1000) "Fractal Leaf - 1,00 points" "svgplots/leaf1000.svg")

;; 5,000 points
(myscat (aftx->data rnd-aftx 5000) "Fractal Leaf - 5,000 points" "svgplots/leaf5000.svg")

;; 20,000 points
(myscat (aftx->data rnd-aftx 20000) "Fractal Leaf - 20,000 points" "svgplots/leaf20000.svg")

