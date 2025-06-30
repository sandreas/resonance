package com.pilabor.resonance.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.unit.dp

/*
import androidx.compose.ui.graphics.vector.ImageVector
import com.pilabor.resonance.icons.createIcon

val {NAME}: ImageVector
    get() {
        if ({NAME}_ != null) return {NAME}_!!
        {NAME}_ = createIcon(name="{NAME}", path="{PATH}")
        return {NAME}_!!
    }

private var {NAME}_: ImageVector? = null
 */


fun createIcon(name:String, path: String): ImageVector? {
    return ImageVector.Builder(
        name = name,
        defaultWidth = 16.dp,
        defaultHeight = 16.dp,
        viewportWidth = 16f,
        viewportHeight = 16f
    ).run {
        val pathStr = path
        addPath(
            pathData = addPathNodes(pathStr),
            name = "",
            fill = SolidColor(Color.Black),
            pathFillType = PathFillType.EvenOdd,
            )
        build()
    }
}