package com.example.microservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForecastResponse {
    private String cod;
    private int message;
    private int cnt;
    private List<ForecastItem> list;
    private City city;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ForecastItem {
        private long dt;
        private Main main;
        private List<WeatherResponse.Weather> weather;
        private Clouds clouds;
        private Wind wind;
        private int visibility;
        private double pop;
        private Rain rain;
        private Sys sys;
        private String dt_txt;
        
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Main {
            private double temp;
            private double feels_like;
            private double temp_min;
            private double temp_max;
            private int pressure;
            private int sea_level;
            private int grnd_level;
            private int humidity;
            private double temp_kf;
        }
        
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Clouds {
            private int all;
        }
        
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Wind {
            private double speed;
            private int deg;
            private double gust;
        }
        
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Rain {
            private double threeHour;
        }
        
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Sys {
            private String pod;
        }
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class City {
        private long id;
        private String name;
        private Coord coord;
        private String country;
        private long population;
        private int timezone;
        private long sunrise;
        private long sunset;
        
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Coord {
            private double lat;
            private double lon;
        }
    }
} 