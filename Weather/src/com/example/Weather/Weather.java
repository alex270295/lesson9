package com.example.Weather;

public class Weather {
    String city;
    int tempMin;
    int tempMax;
    String windDirect;
    int id;
    int windSpeed;
    boolean ready = false;
    String date;

    Weather(int id, String city, int tempMin, int tempMax, int windSpeed, String windDirect, String date) {
        this.id = id;
        this.city = city;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.windDirect = windDirect;
        this.windSpeed = windSpeed;
        this.date = date;
    }

    Weather(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return city;
    }
}
