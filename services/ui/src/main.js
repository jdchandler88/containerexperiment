import Vue from 'vue'
import './plugins/vuetify'
import App from './App.vue'
import Vuetify from 'vuetify'
import Toasted from 'vue-toasted';
import VueRouter from 'vue-router'



Vue.config.productionTip = false

Vue.use(Vuetify)
Vue.use(Toasted)
Vue.use(VueRouter)

import 'vuetify/dist/vuetify.min.css'

new Vue({
  render: h => h(App),
}).$mount('#app')
