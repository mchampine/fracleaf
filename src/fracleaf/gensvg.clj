(ns fracleaf.gensvg
  (:require [tech.viz.vega :as vega]
            [fracleaf.core :as flc]))

;;; GENERATE AN SVG of the FRACTAL LEAF ;;;

;; plot them
(defn myscat
  "Make a scatterplot from x,y coordinate data"
  [data title filenam]
  (-> (vega/scatterplot data :x :y
                      {:title title :height 800 :width 500})
    (vega/vega->svg-file filenam)))

;; 1,000 points
(myscat (flc/aftxpoints 2000) "Fractal Leaf - 1,000 points" "svgplots/leaf1000.svg")

;; 5,000 points
(myscat (flc/aftxpoints 5000) "Fractal Leaf - 5,000 points" "svgplots/leaf5000.svg")

;; ;; 20,000 points
(myscat (flc/aftxpoints 20000) "Fractal Leaf - 20,000 points" "svgplots/leaf20000.svg")

