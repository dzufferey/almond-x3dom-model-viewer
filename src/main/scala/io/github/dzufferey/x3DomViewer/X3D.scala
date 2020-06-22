package io.github.dzufferey.x3DomViewer

import scalatags.Text.all._

object X3D {

    //tags
    lazy val x3d = tag("x3d")
    lazy val scene = tag("scene")
    lazy val viewpoint = tag("viewpoint")
    lazy val orthoviewpoint = tag("orthoviewpoint")
    lazy val navigationInfo = tag("navigationinfo")
    lazy val transform = tag("transform")
    lazy val group = tag("group")
    lazy val billboard = tag("billboard")
    lazy val shape = tag("shape")
    lazy val lineSet = tag("lineset")
    lazy val indexedLineSet = tag("indexedlineset")
    lazy val indexedFaceSet = tag("indexedfaceset")
    lazy val plane = tag("plane")
    lazy val sphere = tag("sphere")
    lazy val cone = tag("cone")
    lazy val text = tag("text")
    lazy val fontStyle = tag("fontstyle")
    lazy val coordinate = tag("coordinate")
    lazy val appearance = tag("appearance")
    lazy val material = tag("material")
    lazy val depthMode = tag("depthMode")
    lazy val lineProperties = tag("lineproperties")

    //attributes
    lazy val use = attr("use")
    lazy val defn = attr("def")
    lazy val orientation = attr("orientation")
    lazy val position = attr("position")
    lazy val typeParams = attr("typeparams")
    lazy val rotation = attr("rotation")
    lazy val translation = attr("translation")
    lazy val radius = attr("radius")
    lazy val primType = attr("primtype")
    lazy val subdivision = attr("subdivision")
    lazy val vertexCount = attr("vertexcount")
    lazy val coordIndex = attr("coordindex")
    lazy val index = attr("index")
    lazy val point = attr("point")
    lazy val solid = attr("solid")
    lazy val string = attr("string")
    lazy val colorPerVertex = attr("colorpervertex")
    lazy val lit = attr("lit")
    //lazy val color = attr("color")
    lazy val emissiveColor = attr("emissivecolor")
    lazy val diffuseColor = attr("diffusecolor")
    lazy val specularColor = attr("specularcolor")
    lazy val ambientIntensity = attr("ambientintensity")
    lazy val lineWidthScaleFactor = attr("linewidthscalefactor")
    lazy val readOnly = attr("readonly")


    def lineAppearance(color: String, scale: Double = 1.0) = {
        appearance(
            material( diffuseColor := "0 0 0", specularColor := "0 0 0", emissiveColor := color ),
            lineProperties( lineWidthScaleFactor := scale.toString )
        )
    }

}
