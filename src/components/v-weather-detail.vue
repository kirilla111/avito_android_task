<template>
    <div class="v-weather-detail">
        <div class="weather-detail__container">
            <div class="weather-detail__container__center">
                <div class="weather-detail__data">
                    <p>{{ SetDate(dt*1000) }}</p>
                </div>
                <div class="weather-detail__image">
                    <img :src="getPath(weather_image)" alt="">
                </div>
                <div class="weather-detail__degrees">
                    {{ degrees.toFixed(0) }}°С {{ degrees_like.toFixed(0) }}°С
                </div>

            </div>


            <div class="weather-detail__description">
                <img src="../assets/icons8-барометр-30.png" alt="">
                <p>{{ pressure }}</p>
            </div>
            <div class="weather-detail__description">
                <img src="../assets/icons8-влажный-30.png" alt="">
                <p>{{ humidity }}</p>
            </div>
            <div class="weather-detail__description">
                <img src="../assets/icons8-скорость-ветра-13-17-лет-30.png" alt="">
                <p>{{ speed }}</p>
            </div>
            <div class="weather-detail__description">
                <img src="../assets/icons8-зонтик-30.png" alt="">
                <p>{{ all }}</p>
            </div>


        </div>


    </div>
</template>

<script>
    export default {
        name: "v-weather-detail",
        props: {
            dt: Number,
            degrees: Number,
            degrees_like: Number,
            weather_image: String,
            pressure: {
                type: Number,
                default: 970
            },
            humidity: {
                type: Number,
                default: 85
            },
            speed: {
                type: Number,
                default: 5.85
            },
            all: {
                type: Number,
                default: 100
            }
        },
        methods: {
            SetDate(dt) {
                var date = new Date(dt);
                var d = date.getDate();
                var m = date.getMonth()+1;
                var h = date.getHours();
                return d+"."+m+" "+h+":00";
            },
            getPath(img){
                console.log(img);
                return "https://openweathermap.org/img/wn/"+img+"@2x.png";;
            }
        }
    }
</script>

<style scoped>
    p {
        padding: 0px;
        margin: 0px;
        font-family: 'Source Sans Pro', sans-serif;
    }

    .v-weather-detail {
        max-width: 100%;
        padding: 10px;
        height: auto;
        background: #6ec9f695;
        justify-content: center;
        align-items: center;
        box-shadow: 0px 0px 10px 10px rgba(0, 0, 0, 0.25);
        border-radius: 39px;
    }

    .weather-detail__data {
        font-size: 1.2em;
        font-weight: bold;
    }

    .weather-detail__degrees {
        font-size: 1em;
    }

    .weather-detail__container {
        display: flex;
        flex-direction: column;

    }

    .weather-detail__container__center {
        text-align: center;
        padding-bottom: 10px;
    }

    .weather-detail__image img {
        width: 60px;
        height: 60px;
    }

    .weather-detail__description {
        display: flex;
        flex-direction: row;
        text-align: center;
    }

    .weather-detail__description img {
        width: 30px;
        height: 30px;
        padding-right: 10px;
    }

    .weather-detail__description p {
        padding: 5px 0px 5px;
    }
</style>