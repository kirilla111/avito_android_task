<template>
    <div class="v-footer">
        <div class="footer__logo">MyWeather</div>

        <div class="footer__link">
            ©MyWeather, 2021
        </div>
    </div>
</template>

<script>
    export default {
        name: "v-footer"
    };
</script>

<style scoped>
    .v-footer {
        display: flex;
        flex-direction: column;
        margin-top: 100px;
        position: -webkit-fixed;
        position: fixed;
        text-align: center;
        left: 0;
        bottom: 0;
        width: 100%;
        min-height: 40px;
        background: rgb(0, 157, 236);
        padding: 15px 0;
        z-index: 100;
        border-top: 2px solid #2D3DB3;
    }

    .footer__logo {
        font-size: 2em;
        color: black;
    }

    .footer__link {
        margin-top: 15px;
        color: rgb(49, 49, 49);
        font-family: 'Dosis', sans-serif;
    }

    @media screen and (max-width: 400px) {
        .v-footer {
            flex-direction: column;
            justify-content: center;
            margin: auto;
            padding: 0;
        }
    }

    @media screen and (max-width: 700px) {
        .v-footer {
            flex-direction: column;
            justify-content: center;
            margin: auto;
            padding: 0;
        }

        .footer__logo {
            margin-bottom: 0.5em;
        }
    }
</style>