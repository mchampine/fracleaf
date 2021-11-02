;; # Fractal Leaf Visualization in Vega Lite
(ns notebooks.fractalleaf.vega
  (:require [nextjournal.clerk.viewer :as v]
            [fracleaf.core :as fraclc]))

(defn fracleafspec [points]
  {:height 800 :width 500
  :data {:values (fraclc/aftxpoints points)}
  :mark "point"
  :encoding {:x {:field "x" :type "quantitative"}
             :y {:field "y" :type "quantitative"}}
  })

;; ## 2,000 points
(v/vl (fracleafspec 2000))

;; ## 20,000 points
(v/vl (fracleafspec 20000))
