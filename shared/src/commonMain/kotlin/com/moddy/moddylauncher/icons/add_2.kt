package com.moddy.moddylauncher.icons

import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
val add_2: ImageVector
    get() {
        if (_add_2 != null) {
            return _add_2!!
        }
        _add_2 =
            ImageVector.Builder(
                name = "add_2",
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
                        moveTo(11f, 21f)
                        verticalLineTo(13f)
                        horizontalLineTo(3f)
                        verticalLineTo(11f)
                        horizontalLineToRelative(8f)
                        verticalLineTo(3f)
                        horizontalLineToRelative(2f)
                        verticalLineToRelative(8f)
                        horizontalLineToRelative(8f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(13f)
                        verticalLineToRelative(8f)
                        horizontalLineTo(11f)
                        close()
                    }
                }
                .build()
        return _add_2!!
    }

private var _add_2: ImageVector? = null