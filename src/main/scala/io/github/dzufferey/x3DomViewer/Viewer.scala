package io.github.dzufferey.x3DomViewer

import scalatags.Text.all._
import almond.display.{Html, Text}

class Viewer(uid: Long = scala.util.Random.nextLong(Long.MaxValue)) {
    //the uid prevents interferences between multiple viewers in the same notebook
    
    import X3D.{position => xpos, _}
    
    val defaultRotation = "1 0 0 -1.570796326795" // z axis up
    
    val mainViewer = "mainViewer" + uid.toString
    val sidePanelId = "sidePanel" + uid.toString
    val gridAndAxes = "gridAndAxes" + uid.toString
    val gridCheckbox = "gridCheckbox" + uid.toString
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
            transition := "0.5s",
            height := "15%",
            width := "0",
            position := "absolute",
            overflowX := "hidden",
            margin := "10px",
        )(
            div(margin := "5px")(
                button(onclick := s"document.getElementById('$sidePanelId').style.width = '0%';")("close")
            ),
            div(margin := "5px")(
                input(id := gridCheckbox, tpe := "checkbox", checked := "checked", onchange := toggleVisibility(gridAndAxes)),
                label( `for` := gridCheckbox)("Grid")
            ),
            div(margin := "5px")(
                button(onclick := togglePerspective)("Perspective")
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
    
    //TODO orthographic and normal projection, some script to swtich between the two
    //TODO shortcuts to pick front/back, left/right, top/bottom views
    //TODO pick initial zoom level according to content bounded box
    
    def viewer(content: Modifier) = {
        div(backgroundColor := "rgba(128, 128, 196, 0.4)",
            borderStyle := "solid",
            script( tpe := "text/javascript", src := "https://www.x3dom.org/download/x3dom.js" ),
            link( rel := "stylesheet", tpe := "text/css", href := "https://www.x3dom.org/download/x3dom.css" ),
            x3d(id := mainViewer)(
                scene(
                    viewpoint( id:= "viewPointPersp", xpos := "5.53912 7.69774 6.54642" , orientation := "-0.69862 0.66817 0.25590 1.00294" ),
                    navigationInfo( id := "navi", tpe := "\"TURNTABLE\" \"ANY\"", typeParams := "0, 0, 0.0, 3.14" ),
                    orthoviewpoint( id:= "viewPointOrtho", xpos := "5.53912 7.69774 6.54642" , orientation := "-0.69862 0.66817 0.25590 1.00294" ),
                    transform(rotation := defaultRotation)(
                        group(id := gridAndAxes)(
                            grid(10, 1),
                            axes(100, 1),
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
                    zIndex := "1",
                    onclick := s"document.getElementById('$sidePanelId').style.width = '15%';")("Options"),
            sidePanel
        ).render
    }
    
    //some helpers to create elements
    def display(content: Modifier) =  Html(viewer(content))
    
    def debugDisplay(content: Modifier) =  Text(viewer(content))
    
}

object Viewer {
    
    def viewer(content: Modifier) = {
        val v = new Viewer()
        v.viewer(content)
    }
    
    def display(content: Modifier) =  Html(viewer(content))
    
    def debugDisplay(content: Modifier) =  Text(viewer(content))
    
}
