package com.codewithfk.musify_android.ui.feature.widgets

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle


@Composable
fun HighlightedText(
    textResourceID: Int,
    spanStyle: SpanStyle,
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    textAlign: androidx.compose.ui.text.style.TextAlign? = null,
) {
    val rawText = stringResource(textResourceID)
    val annotatedString = buildAnnotatedString {
        val regex = "<highlight>(.*?)</highlight>".toRegex()
        var lastIndex = 0
        Log.d("HighlightedText", "Match searching in: $rawText")
        for (match in regex.findAll(rawText)) {
            Log.d("HighlightedText", "Match found: ${match.value}")
            val start = match.range.first
            val end = match.range.last + 1
            val beforeText = rawText.substring(lastIndex, start)
            val highlightedText = match.groupValues[1] ?: ""
            append(beforeText)
            withStyle(style = spanStyle) {
                append(highlightedText)
            }
            lastIndex = end
        }
        if (lastIndex < rawText.length) {
            append(rawText.substring(lastIndex))
        }
    }
    Text(annotatedString, modifier = modifier, style = textStyle, textAlign = textAlign)
}