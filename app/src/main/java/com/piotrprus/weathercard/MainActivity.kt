package com.piotrprus.weathercard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Water
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.piotrprus.weathercard.ui.theme.WeatherCardTheme

class MainActivity : ComponentActivity() {
    private val forecastMockState = mutableStateListOf(
        WeatherItem(date = "Mon, 8:00 AM, Cloudy", temperature = 12, 30, 2, R.drawable.cloudy),
        WeatherItem(date = "Mon, 9:00 AM, Cloudy", temperature = 14, 60, 3, R.drawable.cloudy),
        WeatherItem(date = "Mon, 10:00 AM, Mostly cloudy", temperature = 15, 80, 5, R.drawable.rain),
        WeatherItem(date = "Mon, 11:00 AM, Mostly sunny", temperature = 15, 30, 2, R.drawable.sunny),
        WeatherItem(date = "Mon, 12:00 PM, Mostly sunny", temperature = 18, 20, 1, R.drawable.sunny),
        WeatherItem(date = "Mon, 1:00 PM, Sunny", temperature = 20, 0, 1, R.drawable.sunny),
        WeatherItem(date = "Mon, 2:00 PM, Sunny", temperature = 20, 0, 2, R.drawable.sunny),
        WeatherItem(date = "Mon, 3:00 PM, Sunny", temperature = 19, 0, 2, R.drawable.sunny),
        WeatherItem(date = "Mon, 4:00 PM, Sunny", temperature = 19, 0, 2, R.drawable.sunny),
        WeatherItem(date = "Mon, 5:00 PM, Sunny", temperature = 17, 0, 3, R.drawable.sunny),
        WeatherItem(date = "Mon, 6:00 PM, Heavy showers", temperature = 16, 80, 7, R.drawable.rain),
        WeatherItem(date = "Mon, 7:00 PM, Heavy showers", temperature = 15, 90, 9, R.drawable.rain),
    )

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val selectedValue: Int by viewModel.selected.observeAsState(initial = 0)
                    WeatherCard(forecastMockState, selectedValue) {
                        viewModel.onValueChanged(it)
                    }
                }
            }
        }
    }
}

@Composable
private fun WeatherCard(list: List<WeatherItem>, selectedValue: Int, onValueChange: (Int) -> Unit) {
    val item by remember(selectedValue, list) {
        derivedStateOf { list[selectedValue] }
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            MeasurementView(item)
            ForecastSlider(
                list.map { it.date.split(",")[1] },
                onValueChange = { onValueChange(it) },
                value = selectedValue.toFloat()
            )
        }
    }
}

@Composable
private fun MeasurementView(data: WeatherItem) {
    Text(text = "Hong Kong", style = MaterialTheme.typography.h5)
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = data.date, style = MaterialTheme.typography.body2.copy(color = Color.Gray))
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
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = data.icon),
            contentDescription = "Weather icon"
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
                style = MaterialTheme.typography.body2.copy(color = Color.Gray)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Air, contentDescription = "Air icon")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${data.windSpeed} km/h Winds",
                style = MaterialTheme.typography.body2.copy(color = Color.Gray)
            )
        }
    }
}

@Composable
fun ForecastSlider(dates: List<String>, value: Float, onValueChange: (Int) -> Unit) {
    val (sliderValue, setSliderValue) = remember { mutableStateOf(value) }
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
            val distance = (size.width.minus(2 * drawPadding)).div(dates.size.minus(1))
            dates.forEachIndexed { index, date ->
                drawLine(
                    color = Color.DarkGray,
                    start = Offset(x = drawPadding + index.times(distance), y = yStart),
                    end = Offset(x = drawPadding + index.times(distance), y = lineHeightPx)
                )
                if (index.rem(2) == 1) {
                    this.drawContext.canvas.nativeCanvas.drawText(
                        date,
                        drawPadding + index.times(distance),
                        size.height,
                        textPaint
                    )
                }
            }
        }
        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = sliderValue,
            valueRange = 0f..dates.size.minus(1).toFloat(),
            steps = dates.size.minus(2),
            colors = customSliderColors(),
            onValueChange = {
                setSliderValue(it)
                onValueChange(it.toInt())
            })
    }
}

@Composable
private fun customSliderColors(): SliderColors = SliderDefaults.colors(
    activeTickColor = Color.Transparent,
    inactiveTickColor = Color.Transparent
)