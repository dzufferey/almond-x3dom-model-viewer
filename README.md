# almond-x3dom-model-viewer

A simple 3D viewer that can be used with [Almond](https://almond.sh/) ([Scala](https://www.scala-lang.org/) [Jupyter notebook](https://jupyter.org/)).

I have a bunch of projects around 3D modeling, printing, and milling for which I like to use Jupyter notebooks.
I wanted to have a 3D viewer for these projects with the following requirements:
* only scala
* can be used though a Maven artifact: just add an extra library dependency to your build file, no extra installation requires.

[X3DOM](https://www.x3dom.org/) is used for the rendering.
This is not the best but it is quite simple to use.

The projects for which I made this viewer:
* [scadla](https://github.com/dzufferey/scadla/): a scala library for constructive solid geometry 
* [libgcode](https://github.com/dzufferey/libgcode): a scala library to create and manipulate [G-code](https://en.wikipedia.org/wiki/G-code)
