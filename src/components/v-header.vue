<template>
    <div class="v-header">
        <div class="logo">
            <img src="../assets/logo-hor.png" alt="">
        </div>
        <div class="search">
            <input class="search__input" type="text" placeholder="Поиск" v-model="search" />
        </div>
        <div class="contacts">
            <a href="https://github.com/kirilla111/my_weather">https://github.com/kirilla111/my_weather</a>
        </div>

    </div>
</template>

<script>
    export default {
        name: "v-header",
        data() {
            //это персональные данные
            return {
                search: '',
            };
        },
    };
</script>

<style>
    .v-header {
        width: 100%;
        background-color: rgb(0, 157, 236);
        min-height: 4.5em;

        display: grid;
        grid-template-columns: 1.5fr 3fr 1.5fr;
        grid-template-rows: 1fr;
        gap: 0px 0px;
        grid-template-areas:
            "logo search contacts";
        text-align: center;
        align-content: center;
        

        border-bottom: 2px solid #2D3DB3;
        -webkit-box-shadow: 0px 17px 13px 8px rgba(79, 176, 224, 0.73);
        box-shadow: 0px 17px 13px 8px rgba(79, 176, 224, 0.73);
    }
    input{
        outline: none;
    }
    .search__input {
        width: 50%;
        height: 2em;
        white-space: nowrap;
        padding-left: 20px;
        overflow: hidden;
        text-overflow: ellipsis;
        background: #6EC9F6;
        border: 2px solid #2D3DB3;
        border-radius: 26.5px;
        font-size: 16px;
    }
    .search{
        padding-top: 2em;
        padding-bottom: 2em;
    }
    .logo{
       
    }
    .contacts{
        vertical-align: middle;
    }
    .contacts a{
        color: rgb(1, 33, 49);
        
    }
   img{
       height: 6em;
   }
</style>