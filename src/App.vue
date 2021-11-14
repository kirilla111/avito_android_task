<template>
  <body>
    <v-header id="v-header"/>
    <router-view/>
    <v-footer id="v-footer"/>
  </body>
</template>

<script>
import vHeader from '@/components/v-header.vue'
import vFooter from '@/components/v-footer.vue'

export default {
  name: "myweather",
  components: {
    vHeader,
    vFooter
  }
}
</script>



<style>
body{
    padding: 0;
    margin: 0;
    box-sizing: border-box;
    
}
</style>
