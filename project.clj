(defproject fracleaf "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [uncomplicate/neanderthal "0.43.1"]
                 ;;Optional. If bytedeco is not present, a system-wide MKL is used.
                 [org.bytedeco/mkl-platform-redist "2021.3-1.5.6"]
                 [techascent/tech.viz "6.00-beta-16-2"]    ;; vega-lite viz
                 [io.github.nextjournal/clerk "0.1.164"]]  ;; notebooks
  ;; Nvidia doesn't ship CUDA for macOS; you have to add this to your project
  :exclusions [[org.jcuda/jcuda-natives :classifier "apple-x86_64"]
               [org.jcuda/jcublas-natives :classifier "apple-x86_64"]]
  :jvm-opts ["--add-opens=java.base/jdk.internal.ref=ALL-UNNAMED"
             "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED"]
  :repl-options {:init-ns fracleaf.core})
