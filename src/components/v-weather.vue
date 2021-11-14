<template>
    <div class="v-weather">
        <div class="container__row__main">
            <div>
                <div class="container__row">
                    <div class="city_name">{{city_name}}</div>

                </div>
                <div class="container__row">
                    <img class="v-weather__image" :src="weather_image" alt="" />
                    <p class="weather__degrees">{{ degrees.toFixed(0) }} °С</p>


                </div>
                <div class="container__row">
                    <p>Feels like {{ degrees_like.toFixed(0) }} °С |</p>
                    <p>| {{ weather_description }}</p>
                </div>
            </div>
            <div class="container">
                <p class="font20">More information</p>
                <p class="more__information">Pressure {{pressure}}</p>
                <p class="more__information">Humidity {{humidity}}</p>
                <p class="more__information">Wind speed {{speed}}</p>
                <p class="more__information">Probability of precipitation {{all}}</p>

            </div>
        </div>


    </div>
</template>

<script>
    import Weather from "./v-weather.vue";

    export default {
        name: "v-weather",
        components: {
            Weather,
        },
        data() {
            return {};
        },
        props: {
            city_name: String,
            degrees: Number,
            degrees_like: Number,
            weather_image: String,
            weather_description: String,
            pressure: String,
            humidity: String,
            speed: String,
            all: String
        },
        methods: {

        }
    };
</script>

<style scoped>
    @import url('https://fonts.googleapis.com/css2?family=Source+Sans+Pro&family=Yuji+Syuku&display=swap');

    p {
        padding: 0px;
        margin: 0px;
        font-family: 'Source Sans Pro', sans-serif;
    }

    .v-weather {
        max-width: 100%;
        padding: 20px;
        height: auto;
        background: #6ec9f695;
        box-shadow: 0px 0px 10px 10px rgba(0, 0, 0, 0.25);
        border-radius: 39px;
    }

    .weather__discription {
        text-align: left;
    }

    .container__row {
        display: flex;
        flex-direction: row;
        height: auto;
        justify-content: space-around;
    }
     .container__row__main {
        display: flex;
        flex-direction: row;
        height: auto;
        justify-content: space-around;
    }

    .weather__degrees {
        font-size: 4em;
    }

    .city_name {
        grid-area: city_name;
        padding-bottom: 20px;
        text-align: left;
        font-family: 'Yuji Syuku', serif;
        font-size: 2em;
    }

    .font20 {
        padding-top: 15px;
        grid-area: city_name;
        padding-bottom: 20px;
        font-family: 'Yuji Syuku', serif;
        font-size: 1em;
    }

    .more__information {
        padding: 5px 0px 5px;
    }

    @media screen and (max-width: 500px) {
        .v-weather {
            
        }
        .container__row__main{
            flex-direction: column;
        }
        .container{
            text-align: center;
        }
    }
</style>