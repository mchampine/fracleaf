# fracleaf

A Clojure project to visualize a fractal shape created by iterating an affine transform.

Uses [Neaderthal](https://neanderthal.uncomplicate.org/) for the affine transform, nextjournal/clerk for notebook functionality, and [tech.viz](https://github.com/techascent/tech.viz) for visualization.

## Usage

Clone the repo and jack-in.
Go to src/fracleaf/core.clj and evaluate (clerk/show! "src/fracleaf/core.clj") to display a notebook page showing the code and output of the leaf visualization. Evaluate (clerk/show! "notebooks/fractalleaf.clj") for a notebook of just the visualization. See src/fracleaf/gensvg.clj to generate svgs.

## License

Copyright © 2021 Mark Champine

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
