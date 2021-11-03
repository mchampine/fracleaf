(ns fracleaf.core
  (:require [clojure.java.browse :as browse]
            [nextjournal.clerk.webserver :as webserver]
            [nextjournal.clerk :as clerk]
            [nextjournal.clerk.viewer :as v]
            [nextjournal.beholder :as beholder])
  (:use     [uncomplicate.neanderthal core native vect-math]
            [clojure.repl]))

;; # Fractal Leaf Visualization
;; ## Based on Gareth Williams 9th Edition - Linear Algebra with Applications (2017)
;; ### matrix operations via neanderthal, notebook functionality via clerk

;; ## pg. 133 Fractal Pictures of Nature
;; "A fractal is a convenient label for irregular and fragmented
;; self-similar shapes"

;; This exercise creates a leaf shape by repeatedly applying affine
;; transformations (matrix xform + translation) to an initial
;; point. The chosen tranformations are applied with varying
;; probabilities and in a random order.

;; pg. 124: Affine Transformation T(u) = A(u) + v
(defn affinexf
  "2D Affine transform on point p by matrix m and translation t"
  [m t p]
  (xpy
   (mv (dge 2 2 m) (dv p))
   (dv t)))

;; p. 135:
;; "The affine transformations and corresponding probabilities that
;; generate a fractal are written as rows of a matrix, called an
;; iterated function system (IFS)."

;; The IFS for the fern is implemented as a map of affinexform -> frequency as follows:

;; ### Iterated Function System (IFS)
(def myaffines
  {(partial affinexf [0.86 -0.03 0.03 0.86] [0 1.5]) 83
   (partial affinexf [0.2 0.21 -0.25 0.23] [0 1.5]) 8
   (partial affinexf [-0.15 0.25 0.27 0.26] [0 0.45]) 8
   (partial affinexf [0 0 0 0.17] [0 0]) 1})

;; ## Generate points by iterating the affine transforms

;; ### First, express the probabilities as function counts

(defn frequencies-inv
  "Inverse of 'frequencies'. Provide a map of counts and values
  and get back the dataset that would produce them."
  [fm]
  (mapcat (fn [[k v]] (repeat v k)) fm))

;; ##### The IFS is expressed below as 100 affine transformation function instances with counts as specified in myaffines:
(def ifs-affines (frequencies-inv myaffines)) 

;; ### Apply a random affine transformation
(defn rnd-aftx
  "Apply a random affine transform from ifs-affines to the supplied coordinate"
  [coord]
  (let [r (int (* 100 (rand)))
        atxfn (nth ifs-affines r)] ;; choose one randomly
    (atxfn coord)))

;; # Visualization

;; ### generate x,y points by iterating the affine transforms
(defn aftx->data
  "Iterate affine transform aftx n times from [0 0] and return a map of :x :y coordinates"
  [aftx n]
  (->> (iterate aftx [0 0])
       (take n)
       (mapv (fn [v] (let [[vl vr] (into [] v)] {:x vl :y vr})))))

(defn aftxpoints
  "Generate a map of n points using the rnd-aftx function"
  [n]
  (aftx->data rnd-aftx n))


;; ### Use Clerk for notebook visualization

;; start Clerk's webserver on port 7777, opening the browser when done
(clerk/serve! {:browse? true})

;; #### Evaluate this form to show the file.
(clerk/show! "src/fracleaf/core.clj")

;; Optionally start a file-watcher to automatically refresh notebooks when saved
(comment
  (clerk/serve! {:watch-paths ["notebooks" "src"]}))

;; Note: to show _only_ the visualization instead of this whole file:
;; (clerk/show! "notebooks/fractalleaf.clj")

;; ### Draw a Fractal Leaf with 20k points (vega-lite via Clerk)
(v/vl
 {:height 800 :width 500
  :data {:values (aftxpoints 20000)}
  :mark "point"
  :encoding {:x {:field "x" :type "quantitative"}
             :y {:field "y" :type "quantitative"}}})
