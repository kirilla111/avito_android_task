<template>
    <div class="v-weather">
        <div class="weather">

            <div class="weather__main">

                <div class="weather__container">
                    <div class="city_name">{{city_name}}</div>
                </div>
                <div class="weather__container">
                    <div>
                        <img class="v-weather__image" :src="weather_image" alt="" />
                    </div>
                    <div class="weather__degrees">
                        {{ degrees.toFixed(0) }} °С
                    </div>
                </div>
                <div class="weather__container">
                    <p>Feels like {{ degrees_like.toFixed(0) }} °С | {{ weather_description }}</p>
                </div>

            </div>

            <div class="weather__additional">

                <p class="weather__additional__name">More information</p>

                <div class="weather__additional__description">
                    <img src="../assets/icons8-барометр-30.png" alt="">
                    <p>Pressure {{pressure}}</p>
                </div>
                <div class="weather__additional__description">
                    <img src="../assets/icons8-влажный-30.png" alt="">
                    <p>Humidity {{humidity}}</p>
                </div>
                <div class="weather__additional__description">
                    <img src="../assets/icons8-скорость-ветра-13-17-лет-30.png" alt="">
                    <p>Wind speed {{speed}}</p>
                </div>
                <div class="weather__additional__description">
                    <img src="../assets/icons8-зонтик-30.png" alt="">
                    <p>Probability of precipitation {{all}}</p>
                </div>

            </div>
        </div>


    </div>
</template>

<script>

    export default {
        name: "v-weather",
        data() {
            return {};
        },
        props: {
            city_name: String,
            degrees: Number,
            degrees_like: Number,
            weather_image: String,
            weather_description: String,
            pressure: Number,
            humidity: Number,
            speed: Number,
            all: Number
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

    .weather__container {
        display: flex;
        flex-direction: row;
        height: auto;
        align-items: center;
        justify-content: space-around;
    }

    .weather {
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
        font-weight: bold;
    }
    .weather__main{

    }
    .weather__additional{
        display: flex;
        flex-direction: column;
    }
    .weather__additional__description{
        display: flex;
        flex-direction: row;
        text-align: center;
        align-items: center;
    }
    .weather__additional__description img{
        width: 30px;
        height: 30px;
        padding-right: 10px;
    }

    .weather__additional__name {
        padding-top: 15px;
        grid-area: city_name;
        padding-bottom: 20px;
        font-family: 'Yuji Syuku', serif;
        font-size: 1.2em;
        font-weight: bold;
    }

    .weather__additional__description p {
        padding: 5px 0px 5px;
    }

    @media screen and (max-width: 500px) {
        .v-weather {}

        .weather {
            flex-direction: column;
        }

        .container {
            text-align: center;
        }
    }
</style>