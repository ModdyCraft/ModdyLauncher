package com.moddy.moddylauncher.icons

import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
val play_arrow: ImageVector
    get() {
        if (_play_arrow != null) {
            return _play_arrow!!
        }
        _play_arrow =
            Builder(
                name = "play_arrow",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(8f, 19f)
                        verticalLineTo(5f)
                        lineToRelative(11f, 7f)
                        lineTo(8f, 19f)
                        close()
                    }
                }
                .build()
        return _play_arrow!!
    }

private var _play_arrow: ImageVector? = null