package com.github.dzufferey.x3DomViewer

import scalatags.Text.all._
import almond.display.{Html, Text}

class Viewer(conf: Config,
             uid: Long = scala.util.Random.nextLong().abs) {
    //the uid prevents interferences between multiple viewers in the same notebook

    import X3D.{position => xpos, _}

    val defaultRotation = "1 0 0 -1.570796326795" // z axis up

    val mainViewer = "mainViewer" + uid.toString
    val sidePanelId = "sidePanel" + uid.toString
    val gridAndAxes = "gridAndAxes" + uid.toString
    val gridCheckbox = "gridCheckbox" + uid.toString
    val gridSize = "gridSize" + uid.toString
    val gridInner = "gridInner" + uid.toString
    val gridBorder = "gridBorder" + uid.toString

    def toggleVisibility(id: String) = {
        s"""{var shape = document.getElementById('$id');
            |if (this.checked) shape.setAttribute('render', true);
            |else shape.setAttribute('render', false);}""".stripMargin
    }

    def updateGrid(size: String, tick: String) = {
        s"""{var plane = document.getElementById('$gridInner');
            |var s = Math.ceil($size/$tick) * $tick;
            |plane.size=(2*s) + " " + (2*s);
            |plane.subdivision=(2*s/$tick) + " " + (2*s/$tick);
            |var border = document.getElementById('$gridBorder');
            |border.point = (-s) + ' ' + (-s) + ' 0.0 , ' +
            |               (-s) + ' ' + ( s) + ' 0.0 , ' +
            |               ( s) + ' ' + ( s) + ' 0.0 , ' +
            |               ( s) + ' ' + (-s) + ' 0.0';}""".stripMargin
    }

    //TODO togglePerspective: center of rotation/position does not work and adapt field of view
    def togglePerspective = {
        s"""{var runtime = document.getElementById('$mainViewer').runtime;
            |var old_vp = runtime.viewpoint();
            |runtime.nextView();
            |var new_vp = runtime.viewpoint();
            |new_vp.centerOfRotation = old_vp.centerOfRotation; 
            |new_vp.orientation = old_vp.orientation;
            |new_vp.position = old_vp.position;}""".stripMargin
    }
    
    def sidePanel = {
        div(id := sidePanelId,
            top := "0",
            right := "-10px",
            zIndex := "2",
            backgroundColor := "Black",
            color := "White",
            transition := "0.5s ease-in-out",
            height := "25%",
            width := "15%",
            position := "absolute",
            margin := "10px",
            css("transform") := "translateX(100%)"
        )(
            div(margin := "5px", float := "right")(
                button(onclick := s"document.getElementById('$sidePanelId').style.transform = 'translateX(100%)';",
                       fontSize := "200%", border := "2px solid Gray", backgroundColor := "Black", color := "White")("×")
            ),
            div(margin := "5px")(
                input(id := gridCheckbox, tpe := "checkbox", checked := "checked", onchange := toggleVisibility(gridAndAxes)),
                label( `for` := gridCheckbox)("Show grid")
            ),
            div(margin := "5px")(
                input(width := "50px")(id := gridSize, tpe := "number", value := "10",  min := "0", onchange := updateGrid("this.value", "1")),
                label( `for` := gridSize)("Grid Size")
            ),
            div(margin := "5px")(
                button(onclick := togglePerspective)("Toggle ortho. persp."),
                span("(experimental)")
            ),
        )
    }


    def grid(_size: Int, step: Int) = {
        val grey = appearance( material( diffuseColor := "0.0 0.0 0.0", specularColor := "0.0 0.0 0.0", emissiveColor := "0.3 0.3 0.3" ) )
        val sSize = s"${2*_size} ${2*_size}"
        val sStep = s"${2*_size/step} ${2*_size/step}"
        val sBorder = s"-${_size} -${_size} 0.0 , -${_size} ${_size} 0.0 , ${_size} ${_size} 0.0, ${_size} -${_size} 0.0"
        group(
            shape(
                plane(id := gridInner, solid := "false", size := sSize, primType := "LINES", subdivision := sStep ),
                grey
            ),
            shape(
                indexedLineSet(coordIndex := "0 1 2 3 0", colorPerVertex := "false", lit := "false")(
                    coordinate(id := gridBorder, point := sBorder)
                ),
                grey
            )
        )
    }

    def axes(length: Int, step: Int) = {
        group(
            shape( // X
                lineSet( vertexCount := "2" ) (
                    coordinate( point := s"0 0 0.001, $length 0 0.001")
                ),
                lineAppearance("1 0 0")
            ),
            shape( // Y
                lineSet( vertexCount := "2" ) (
                    coordinate( point := s"0 0 0.001, 0 $length 0.001")
                ),
                lineAppearance("0 1 0")
            ),
            shape( // Z
                lineSet( vertexCount := "2" ) (
                    coordinate( point := s"0 0 0, 0 0 $length")
                ),
                lineAppearance("0 0 1")
            ),
            for (z <- 0 to length by step) yield {
                shape(
                    lineSet( vertexCount := "2" ) (
                        coordinate( point := s"0 0 $z, $step 0 $z")
                    ),
                    lineAppearance("0 0 1")
                )
            }
        )
    }

    def viewer(content: Modifier) = {
        div(backgroundColor := conf.backgroundColor,
            borderStyle := "solid",
            overflowX := "hidden",
            script( tpe := "text/javascript", src := "https://www.x3dom.org/download/x3dom.js" ),
            link( rel := "stylesheet", tpe := "text/css", href := "https://www.x3dom.org/download/x3dom.css" ),
            x3d(id := mainViewer)(
                scene(
                    viewpoint( id:= "viewPointPersp", xpos := "5.53912 7.69774 6.54642" , orientation := "-0.69862 0.66817 0.25590 1.00294" ),
                    navigationInfo( id := "navi", tpe := "\"TURNTABLE\" \"ANY\"", typeParams := "0, 0, 0.0, 3.14" ),
                    orthoviewpoint( id:= "viewPointOrtho", xpos := "5.53912 7.69774 6.54642" , orientation := "-0.69862 0.66817 0.25590 1.00294" ),
                    transform(rotation := defaultRotation)(
                        group(id := gridAndAxes)(
                            grid(conf.gridSize, 1),
                            axes(conf.axisSize, 1),
                        ),
                        content
                    )
                )
            ),
            button( fontSize := "20px",
                    cursor := "pointer",
                    backgroundColor := "Black",
                    color := "White",
                    padding := "5px 10px",
                    border := "none",
                    position := "absolute",
                    top := "5px",
                    right := "25px",
                    onclick := s"document.getElementById('$sidePanelId').style.transform = 'translateX(0%)';")("Options"),
            sidePanel
        ).render
    }

    //some helpers to create elements
    def display(content: Modifier) =  Html(viewer(content))

    def debugDisplay(content: Modifier) =  Text(viewer(content))

}

object Viewer {

    def viewer(content: Modifier, conf: Config = Config) = {
        val v = new Viewer(conf)
        v.viewer(content)
    }

    def display(content: Modifier, conf: Config = Config) =  Html(viewer(content, conf))

    def debugDisplay(content: Modifier, conf: Config = Config) =  Text(viewer(content, conf))

}
