import Vue from 'vue'
import './plugins/vuetify'
import App from './App.vue'
import Vuetify from 'vuetify'
import Toasted from 'vue-toasted';


Vue.config.productionTip = false

Vue.use(Vuetify)
Vue.use(Toasted)

import 'vuetify/dist/vuetify.min.css'

new Vue({
  render: h => h(App),
}).$mount('#app')
