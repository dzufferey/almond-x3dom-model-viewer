# almond-x3dom-model-viewer

![Version 0.2.3](https://img.shields.io/badge/version-0.2.3-green.svg)

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

## Usage

First, you need to install Jupyter and Almond.
This project currently uses Scala 2.13 but should also work with Scala 2.12.

To use it there is two options.
1. _Local version_
  * clone this repository
  * run `sbt publishLocal`
  * add the following import in your notebook:
    ```
    import $ivy.`com.github.dzufferey::almond-x3dom-model-viewer:0.2.3`
    ```
2. _Remote version_
  * add the address of the maven repo for the artifact:
    ```
    import coursierapi._
    interp.repositories() ++= Seq(MavenRepository.of("https://jitpack.io"))
    ```
  * in a following cell you can add the import:
    ```
    import $ivy.`com.github.dzufferey::almond-x3dom-model-viewer:0.2.3`
    ```

Then you can look in the [example notebook](example.ipynb) to learn how to call the viewer.

## ToDo

* orthographic and normal projection
  - navigation/zoom with orthographic projection
  - preserve position when switching between the style of projections
  - shortcuts to pick front/back, left/right, top/bottom views
* view
  - pick the initial zoom level/grid size according to object displayed
  - adapt the size of grid/axis according to the current view
  - different granularity of ticks for axis/grid, e.g., small every unit and bigger every 10 units
* light
  - directional/head light as option
