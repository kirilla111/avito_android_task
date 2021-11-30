<template>
    <div class="v-header">
        <div class="header__inner">
            <div class="logo">
                <img src="../assets/logo-hor2.png" alt="">
            </div>
            <div class="search">
                <input class="search__input" type="text" placeholder="🔎 Search.." v-model="search" />
            </div>
            <div class="contacts">
                <div class="container">
                    <a href="https://github.com/kirilla111/my_weather">Разработчики</a>
                </div>


            </div>
        </div>


    </div>
</template>

<script>
 import {eventBus} from "../main.js";
    export default {
        name: "v-header",
        data() {
            //это персональные данные
            return {
                search: '',
            };
        },
        watch: {
            search: function () {
                var keyword = this.search.toLowerCase();
                this.$store.commit("changeRequest", keyword);
                eventBus.$emit('GetRequest');
                console.log(this.$store.getters.request);
            }
        }
    };
</script>

<style>
    .v-header {
        width: 100%;
        background-color: rgb(0, 157, 236);
        min-height: 4.5em;

        display: flex;
        align-items: center;
        text-align: center;

        border-bottom: 2px solid #2D3DB3;
        -webkit-box-shadow: 0px 17px 13px 8px rgba(79, 176, 224, 0.73);
        box-shadow: 0px 17px 13px 8px rgba(79, 176, 224, 0.73);
    }

    .header__inner {
        display: flex;
        flex-direction: row;
        width: 100%;
        justify-content: space-between;
        margin: auto;
        padding: 0;
    }

    input {
        outline: none;
    }

    .search__input {
        width: 70%;
        height: 2em;
        white-space: nowrap;
        padding-left: 20px;
        overflow: hidden;
        text-overflow: ellipsis;
        background: #6EC9F6;
        border: 2px solid #2D3DB3;
        border-radius: 26.5px;
        font-size: 16px;
        margin-right: 1em;
    }

    .search {
        width: 100%;
        display: flex;
        flex-direction: column;
        margin: auto;
        padding: 0;
        padding-top: 2em;
        padding-bottom: 2em;
        align-items: center;
        justify-content: center;
    }

    .logo {
        margin: auto;
        padding: 0;
        display: flex;
        justify-content: center;
        padding-left: 20px;
        padding-right: 20px;
    }

    .logo img {
        height: 6em;
    }

    .contacts {
        display: flex;
        justify-content: center;
        padding: 20px;
        font-family: 'Source Sans Pro', sans-serif;
    }

    .contacts a {
        text-decoration: none;
        color: rgb(0, 0, 0);
        font-size: 1.2em;
        text-align: center;
        align-items: center;
    }

    .contacts a:hover {
        color: rgb(194, 194, 194);
        cursor: pointer;

    }

    .contacts a {
        color: rgb(1, 33, 49);

    }

    .contacts .container {
        display: flex;
        text-align: center;
        align-items: center;
    }



    @media screen and (max-width: 700px) {
        .header__inner {
            flex-direction: column;
            justify-content: center;
            margin: auto;
            padding: 0;
        }
    }
</style>