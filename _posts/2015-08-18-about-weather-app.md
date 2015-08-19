---
layout: post
title: About Minesweeper
---

This is an Android app that displays the weather forecast of a location obtained using Android Location Services. As long as there is data connectivity, the app can detect the location and display the current weather and a 4-day weather forecast.

![Weather app screenshot](https://raw.githubusercontent.com/ss37/Weather/gh-pages/public/images/screenshot_1.JPG)

You can click on a list of cities in the menu to display the current weather and a 4-day weather forecast for another city.

![Weather app screenshot](https://raw.githubusercontent.com/ss37/Weather/gh-pages/public/images/screenshot_2.JPG)

![Weather app screenshot](https://raw.githubusercontent.com/ss37/Weather/gh-pages/public/images/screenshot_3.JPG)

For more details, you can view about the weather in the Android browser.

![Weather app screenshot](https://raw.githubusercontent.com/ss37/Weather/gh-pages/public/images/screenshot_4.JPG)

I used [Open Weather Map API](http://openweathermap.org/api) for displaying the current weather and a 4-day weather forecast for any city. I receive data in JSON, which I then parse and display as an Android activity.