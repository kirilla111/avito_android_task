import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    request: 'moscow',
  },
  mutations: {
    changeRequest(state,req){
      state.request=req;
    },
  },
  actions: {
  },
  modules: {
  },
  getters: {
    request: state => {return state.request;},
  }
})
