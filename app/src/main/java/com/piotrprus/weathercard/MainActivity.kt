package com.piotrprus.weathercard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Water
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.piotrprus.weathercard.ui.theme.WeatherCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    WeatherCard(
                        WeatherItem(
                            date = "MON 8:32PM",
                            temperature = 21,
                            precipitation = 2,
                            windSpeed = 5
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewWeatherCard() {
    WeatherCard(
        WeatherItem(
            date = "MON, 8.32PM",
            temperature = 21,
            precipitation = 2,
            windSpeed = 5
        )
    )
}


@Composable
private fun WeatherCard(data: WeatherItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = "Hong Kong", style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = data.date, style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Text(text = data.temperature.toString(), style = MaterialTheme.typography.h1)
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = "°C",
                        style = MaterialTheme.typography.h3
                    )
                }
                Icon(
                    modifier = Modifier.size(60.dp),
                    imageVector = Icons.Default.WbSunny,
                    contentDescription = "Sun icon",
                    tint = MaterialTheme.colors.onSurface
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Water, contentDescription = "Water icon")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${data.precipitation}% Precipitation",
                        style = MaterialTheme.typography.subtitle2
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Air, contentDescription = "Air icon")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${data.windSpeed} km/h Winds",
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }
            ForecastSlider()
        }
    }
}

@Composable
fun ForecastSlider() {
    val (sliderValue, setSliderValue) = remember { mutableStateOf(6f) }
    val steps = 11
    val drawPadding = with(LocalDensity.current) { 10.dp.toPx() }
    val textSize = with(LocalDensity.current) { 10.dp.toPx() }
    val lineHeightDp = 10.dp
    val lineHeightPx = with(LocalDensity.current) { lineHeightDp.toPx() }
    val canvasHeight = 50.dp
    val textPaint = android.graphics.Paint().apply {
        color = if (isSystemInDarkTheme()) 0xffffffff.toInt() else 0xffff47586B.toInt()
        textAlign = android.graphics.Paint.Align.CENTER
        this.textSize = textSize
    }
    Box(contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .height(canvasHeight)
                .fillMaxWidth()
                .padding(
                    top = canvasHeight
                        .div(2)
                        .minus(lineHeightDp.div(2))
                )
        ) {
            val yStart = 0f
            val distance = (size.width.minus(2 * drawPadding)).div(steps + 1)
            (0..steps.plus(1)).forEach { step ->
                drawLine(
                    color = Color.DarkGray,
                    start = Offset(x = drawPadding + step.times(distance), y = yStart),
                    end = Offset(x = drawPadding + step.times(distance), y = lineHeightPx)
                )
                if (step.rem(3) == 0) {
                    this.drawContext.canvas.nativeCanvas.drawText(
                        "12.30",
                        drawPadding + step.times(distance),
                        size.height,
                        textPaint
                    )
                }
            }
        }
        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = sliderValue,
            valueRange = 0f..12f,
            steps = steps,
            colors = customSliderColors(),
            onValueChange = {
                setSliderValue(it)
            })
    }
}

@Composable
private fun customSliderColors(): SliderColors = SliderDefaults.colors(
    activeTickColor = Color.Transparent,
    inactiveTickColor = Color.Transparent
)
