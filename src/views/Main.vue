<template>
  <div class="main">

  <div class="weather_card">
       <v-weather
        :city_name="city_name"
        :degrees="degrees"
        :degrees_like ="degrees_like"
        :weather_image ="weather_image"
        :weather_description ="weather_description"
        :pressure ="pressure"
        :humidity ="humidity"
        :speed ="speed"
        :all ="all"
       ></v-weather>
  </div>

  <div class="weather_description">
    <div class="weather_description__container">
 <v-weather-detail
        v-for="(day, index) in weather"
        :key="index"
        :data="day.data"
        :degrees="day.degrees"
        :image="day.image"
        :degrees_like ="day.degrees_like"
        :weather_description ="day.weather_description"
        :pressure ="day.pressure"
        :humidity ="day.humidity"
        :speed ="day.speed"
        :all ="day.all"
    >

    </v-weather-detail>
    </div>
   


    
  </div>

    
  </div>
</template>

<script>
import vWeather from '../components/v-weather.vue'
import vWeatherDetail from '../components/v-weather-detail.vue'
import axios from "axios";

export default {
  name: 'Main',
  components: {
    vWeather,
    vWeatherDetail
  },
  data() {
    return {
      city_name: '',
      degrees: 0,
      degrees_like: 1,
      weather_image: '',
      weather_description: '',
      pressure: '',
      humididty: '',
      speed: '',
      all: '',
      weather: [
        {
          data: '15.11',
          image: '',
          degrees: 0,
          degrees_like: 1,
          weather_description: '42',
          pressure: '324',
          humidity: '423',
          speed: '423',
          all: '23',
        },
        {
          data: '15.11',
          image: '',
          degrees: 0,
          degrees_like: 1,
          weather_description: '42',
          pressure: '324',
          humidity: '423',
          speed: '423',
          all: '23',
        },
        {
          data: '15.11',
          image: '',
          degrees: 0,
          degrees_like: 1,
          weather_description: '42',
          pressure: '324',
          humidity: '423',
          speed: '423',
          all: '23',
        },
        {
          data: '15.11',
          image: '',
          degrees: 0,
          degrees_like: 1,
          weather_description: '42',
          pressure: '324',
          humidity: '423',
          speed: '423',
          all: '23',
        },
        {
          data: '15.11',
          image: '',
          degrees: 0,
          degrees_like: 1,
          weather_description: '42',
          pressure: '324',
          humidity: '423',
          speed: '423',
          all: '23',
        }
      ]
    }
  },
  props:{

    
  },
  created() {
    var vm = this;
    axios.get("https://api.openweathermap.org/data/2.5/weather?q=Moscow&APPID=1c395069d4f1ce617f9bb295f114c6cc").then((res) => {
      vm.city_name = res.data.name;
      vm.degrees = res.data.main.temp-273,15;
      vm.degrees_like = res.data.main.feels_like-273,15;
      vm.weather_image = "https://openweathermap.org/img/wn/"+res.data.weather[0]['icon']+"@2x.png";
      vm.weather_description = res.data.weather[0]['description'];
      vm.pressure = res.data.main.pressure;
      vm.humidity = res.data.main.humidity;
      vm.speed = res.data.wind.speed;
      vm.all = res.data.clouds.all;
    });

    axios.get("https://api.openweathermap.org/data/2.5/forecast?q=Moscow&APPID=1c395069d4f1ce617f9bb295f114c6cc").then((res) => {
      vm.city_name = res.data.name;
      vm.degrees = res.data.main.temp-273,15;
      vm.degrees_like = res.data.main.feels_like-273,15;
      vm.weather_image = "https://openweathermap.org/img/wn/"+res.data.weather[0]['icon']+"@2x.png";
      vm.weather_description = res.data.weather[0]['description'];
      vm.pressure = res.data.main.pressure;
      vm.humidity = res.data.main.humidity;
      vm.speed = res.data.wind.speed;
      vm.all = res.data.clouds.all;
    });
  }
};
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Yuji+Syuku&display=swap');
  .main{
    width: 100%;
    height: 100%;
    /*background-image: url(../assets/pattern-background.jpg) ;*/
    background-color: rgba(162, 216, 243, 0.73);
    background-repeat: no-repeat;
    background-position: center;
    background-size: cover;

    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: auto;
    gap: 0px 0px;
    grid-auto-flow: row dense;
    grid-template-areas:
      "city_name"
      "weather_card "
      "weather_description";

    padding-bottom: 20px;
  }

.weather_card {  grid-area: weather_card;  margin: 20px; }

.weather_description { grid-area: weather_description; margin: 20px; }

.weather_description__container {
  display: grid;
  max-width: 100%;
  grid-column-gap: 20px;
  grid-row-gap: 20px;
  grid-template-columns: repeat(auto-fill, 120px);
  flex-wrap: wrap;
  justify-content: space-around;
  align-items: center;
}

@media screen and (max-width: 300px) {
  .weather_description__container {
  grid-template-columns: repeat(auto-fill, 100%);
}
}

</style>
