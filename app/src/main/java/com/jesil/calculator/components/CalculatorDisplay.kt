package com.jesil.calculator.components

import android.R.attr.text
import android.annotation.SuppressLint
//import androidx.compose.animation.animateColor
//import androidx.compose.animation.animateContentSize
//import androidx.compose.animation.core.RepeatMode
//import androidx.compose.animation.core.infiniteRepeatable
//import androidx.compose.animation.core.rememberInfiniteTransition
//import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.TextDelegate
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.calculator.ui.theme.CalculatorTheme

@Composable
fun CalculatorDisplay(
    modifier: Modifier = Modifier,
    expression: String,
    answer: String
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd,
        content = {
            Column {
                AnswerTextField(answer = answer)
                Spacer(modifier = Modifier.height(16.dp))
                ExpressionTextField(expression = expression,)
            }
        }
    )
}

@Composable
fun AnswerTextField(
    modifier: Modifier = Modifier,
    answer: String
) {
   Box(
       modifier = modifier.fillMaxWidth(),
       contentAlignment = Alignment.BottomEnd,
       content = {
           Text(
               text = answer,
               style = MaterialTheme.typography.bodyMedium.copy(
                   color = MaterialTheme.colorScheme.primary,
                   fontSize = 30.sp
               ),
               maxLines = 1,
               modifier = Modifier
                   .padding(end = 5.dp)
               ,
               textAlign = TextAlign.End
           )
       }
   )
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ExpressionTextField(
    modifier: Modifier = Modifier,
    expression: String,
    maxFontSize: TextUnit = 70.sp,
    minFontSize: TextUnit = 16.sp,
    style: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
) {
    // Step 1: Track the font size that fits
    var fittedFontSize by remember(expression) { mutableStateOf(maxFontSize) }

    // Step 2: measure text every time constraints change
        BoxWithConstraints(modifier = modifier) {
            // Remember a TextMeasurer (recommended API)
            val textMeasurer = rememberTextMeasurer()

            // Capture density in composition (do NOT call LocalDensity.current inside coroutine)
            val density = LocalDensity.current

            // Track the fitted font size
            var fittedFontSize by remember(expression) { mutableStateOf(maxFontSize) }

            // We need the pixel width available to the Text
            val availableWidthPx = with(density) { this@BoxWithConstraints.maxWidth.toPx().toInt() }

            // Run binary search whenever text or width changes
            LaunchedEffect(text, availableWidthPx, minFontSize, maxFontSize, style) {
                // Work in density block if you need conversions (we already used density above)
                with(density) {
                    var low = minFontSize.value
                    var high = maxFontSize.value
                    var best = low

                    // If there's no available width (e.g. unmeasured), exit early
                    if (availableWidthPx <= 0) return@LaunchedEffect

                    // Binary search (step 0.5 to avoid infinite loops on floats)
                    while (low <= high) {
                        val mid = (low + high) / 2f

                        val layoutResult = textMeasurer.measure(
                            text = AnnotatedString(expression),
                            style = style.copy(fontSize = mid.sp),
                            constraints = Constraints(maxWidth = availableWidthPx),
                            maxLines = 1
                        )

                        // TextLayoutResult exposes whether it overflowed in width
                        if (!layoutResult.didOverflowWidth) {
                            best = mid
                            low = mid + 0.5f // try larger
                        } else {
                            high = mid - 0.5f // too big, try smaller
                        }
                    }

                    // publish the best found size (convert back to sp)
                    fittedFontSize = best.sp
                }
            }

            // Display the measured text
            Text(
                text = expression,
                style = style.copy(fontSize = fittedFontSize),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
//                modifier = Modifier.animateContentSize()
            )
        }

//    Row (
//        modifier = modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.End,
//        verticalAlignment = Alignment.CenterVertically,
//        content = {
////            Text(
////                text = expression,
////                style = MaterialTheme.typography.bodyMedium.copy(
////                    color = MaterialTheme.colorScheme.onBackground,
////                    fontWeight = FontWeight.Bold,
////                    fontSize = 70.sp
////                ),
////                maxLines = 1,
////                modifier = Modifier
////                    .padding(end = 5.dp)
////                    .animateContentSize()
////            )
//            var scaledFontSize by remember { mutableStateOf(maxFontSize) }
//
//            Text(
//                text = expression,
//                style = MaterialTheme.typography.bodyMedium.copy(
//                    fontWeight = FontWeight.Bold,
//                    color = MaterialTheme.colorScheme.onBackground,
//                    fontSize = scaledFontSize
//                ),
//                maxLines = 1,
//                onTextLayout = { layout ->
////                    if (layout.didOverflowWidth && scaledFontSize > minFontSize) {
////                        scaledFontSize *= 0.9f   // shrink gradually until it fits
////                    }
//                },
//                modifier = Modifier.animateContentSize()
//            )
//        }
//    )

}

@PreviewLightDark
@Composable
fun CalculatorDisplayPreview() {
    CalculatorTheme {
        CalculatorDisplay(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            expression = "1+2928", answer = "3"
        )
    }
}