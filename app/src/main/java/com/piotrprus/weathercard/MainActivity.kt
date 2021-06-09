package com.piotrprus.weathercard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
                    tint = MaterialTheme.colors.onPrimary
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
    val (sliderValue, setSliderValue) = remember { mutableStateOf(1f) }
    Slider(
        modifier = Modifier.fillMaxWidth(),
        value = sliderValue,
        valueRange = 1f..12f,
        steps = 12,
        onValueChange = {
            setSliderValue(it)
        })
}
