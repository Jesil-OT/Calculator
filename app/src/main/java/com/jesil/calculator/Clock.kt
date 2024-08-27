package com.jesil.calculator

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ClockWidget(
    modifier: Modifier = Modifier,
) {
    var formatter: DateTimeFormatter
    val ukDigitalTime = remember { mutableStateOf("") }
    val amsDigitalTime = remember { mutableStateOf("") }
    val uaeDigitalTime = remember { mutableStateOf("") }
    val colDigitalTime = remember { mutableStateOf("") }

    val ukTime = remember { mutableStateOf(Time(seconds = 0f, minute = 0f, hour = 0f)) }
    val amsTime = remember { mutableStateOf(Time(seconds = 0f, minute = 0f, hour = 0f)) }
    val uaeTime = remember { mutableStateOf(Time(seconds = 0f, minute = 0f, hour = 0f))   }
    val colTime = remember { mutableStateOf(Time(seconds = 0f, minute = 0f, hour = 0f))   }

    DisposableEffect(key1 = 0) {
        var ukTimeZone: ZonedDateTime
        var amsTimeZone: ZonedDateTime
        var uaeTimeZone: ZonedDateTime
        var colTimeZone: ZonedDateTime

        val observable = Observable.interval(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .subscribe {
                formatter = DateTimeFormatter.ofPattern("HH:mm")

                ukTimeZone = ZonedDateTime.now(ZoneId.of("Europe/London"))
                ukDigitalTime.value = formatter.format(ukTimeZone)
                ukTime.value = Time(
                    hour = ukTimeZone.hour.toFloat(),
                    seconds = ukTimeZone.second.toFloat(),
                    minute = ukTimeZone.minute.toFloat(),
                )

                amsTimeZone = ZonedDateTime.now(ZoneId.of("Europe/Amsterdam"))
                amsDigitalTime.value = formatter.format(amsTimeZone)
                amsTime.value = Time(
                    seconds = amsTimeZone.second.toFloat(),
                    minute = amsTimeZone.minute.toFloat(),
                    hour = amsTimeZone.hour.toFloat()
                )

                uaeTimeZone = ZonedDateTime.now(ZoneId.of("Asia/Dubai"))
                uaeDigitalTime.value = formatter.format(uaeTimeZone)
                uaeTime.value = Time(
                    seconds = uaeTimeZone.second.toFloat(),
                    minute = uaeTimeZone.minute.toFloat(),
                    hour = uaeTimeZone.hour.toFloat()
                )

                colTimeZone = ZonedDateTime.now(ZoneId.of("America/Bogota"))
                colDigitalTime.value = formatter.format(colTimeZone)
                colTime.value = Time(
                    seconds = colTimeZone.second.toFloat(),
                    minute = colTimeZone.minute.toFloat(),
                    hour = colTimeZone.hour.toFloat()
                )
            }
        onDispose {
            observable.dispose()
        }
    }


    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(Color(0xFF262626))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = modifier.padding(10.dp)
        ) {
            ClockItem(
                timeInDigital = ukDigitalTime.value,
                place = "UK",
                time = ukTime.value,
            )
            ClockItem(
                timeInDigital = amsDigitalTime.value,
                place = "AMS",
                time = amsTime.value,
                clockBackgroundColor = Color.Black,
                clockHandColor = Color.White
            )
            ClockItem(
                timeInDigital = uaeDigitalTime.value,
                place = "UAE",
                time = uaeTime.value,
            )
            ClockItem(
                timeInDigital = colDigitalTime.value,
                place = "COL",
                time = colTime.value,
                clockBackgroundColor = Color.Black,
                clockHandColor = Color.White
            )
        }
    }
}


@Composable
fun ClockItem(
    modifier: Modifier = Modifier,
    timeInDigital: String,
    place: String,
    clockBackgroundColor: Color = Color.White,
    clockHandColor: Color = Color.Black,
    time: Time
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .background(color = Color(0xFF313131))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(start = 1.dp, end = 1.dp, top = 5.dp, bottom = 10.dp)
        ) {
            with(time) {
                Clock(
                    circleRadius = 90f,
                    time = Time(seconds, minute, hour),
                    backgroundColor = clockBackgroundColor,
                    clockHandColor = clockHandColor
                )

                Text(
                    text = timeInDigital,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 5.dp)
                )
                Text(
                    text = place,
                    fontSize = 12.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }
        }
    }
}

@Composable
fun Clock(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Black,
    clockHandColor: Color = Color.White,
    circleRadius: Float,
    time: Time,
) {
    val circleCenter = remember { mutableStateOf(Offset.Zero) }

    Box(
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(70.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            circleCenter.value = Offset(x = canvasWidth / 2f, y = canvasHeight / 2f)

            drawCircle(
                color = backgroundColor,
                radius = circleRadius,
                center = circleCenter.value
            )

            drawCircle(
                color = clockHandColor,
                radius = 10f,
                center = circleCenter.value
            )

            val clockHands = listOf(ClockHand.MINUTES, ClockHand.HOURS, ClockHand.SECONDS)
            with(time) {
                clockHands.forEach { clockHand ->
                    val angleInDegrees = when (clockHand) {
                        ClockHand.SECONDS -> seconds * 360f / 60f
                        ClockHand.MINUTES -> (minute + seconds / 60f) * 360f / 60f
                        ClockHand.HOURS -> (((hour % 12) / 12f * 60f) + minute / 12f) * 360f / 60f
                    }
                    val lineLength = when (clockHand) {
                        ClockHand.SECONDS -> circleRadius * 0.9f
                        ClockHand.MINUTES -> circleRadius * 0.9f
                        ClockHand.HOURS -> circleRadius * 0.6f
                    }
                    val lineThickness = when (clockHand) {
                        ClockHand.SECONDS -> 0.5f
                        ClockHand.MINUTES -> 1.5f
                        ClockHand.HOURS -> 1.5f
                    }

                    val start = Offset(
                        x = circleCenter.value.x,
                        y = circleCenter.value.y
                    )
                    val end = Offset(
                        x = circleCenter.value.x,
                        y = circleCenter.value.y + lineLength,
                    )
                    val secondHandStart = when(clockHand){
                        ClockHand.SECONDS -> Offset(x = circleCenter.value.x, y = circleCenter.value.y / 2 + 40)
                        ClockHand.MINUTES -> start
                        ClockHand.HOURS -> start
                    }
                    val lineColor = when (clockHand) {
                        ClockHand.SECONDS -> Color(0xFFFF8000)
                        ClockHand.MINUTES -> clockHandColor
                        ClockHand.HOURS -> clockHandColor
                    }

                    rotate(
                        degrees = angleInDegrees - 180,
                        pivot = start
                    ) {
                        drawLine(
                            color = lineColor,
                            start = secondHandStart,
                            end = end,
                            strokeWidth = lineThickness.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }
            }
            drawCircle(
                color = Color(0xFFFF8000),
                radius = 5f,
                center = circleCenter.value
            )
        }
    }
}

enum class ClockHand {
    SECONDS, MINUTES, HOURS
}

data class Time(
    var seconds: Float,
    var minute: Float,
    var hour: Float
)


@Preview
@Composable
fun ClockItemPreview() {
    ClockItem(
        timeInDigital = "12:00",
        place = "UK",
        time = Time(hour = 2f, minute = 50f, seconds = 20f),
    )
}

@Preview
@Composable
fun ClockPreview() {
    Clock(
        circleRadius = 90f,
        time = Time(seconds = 10f, minute = 0f, hour = 3f),
        backgroundColor = Color.White,
        clockHandColor = Color.Black
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ClockWidgetPreview() {
    ClockWidget()
}