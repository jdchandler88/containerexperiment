import Vue from 'vue'
import './plugins/vuetify'
import App from './App.vue'
import Login from './components/Login.vue'
import HelloWorld from './components/HelloWorld.vue'
import Vuetify from 'vuetify'
import Toasted from 'vue-toasted';
import VueRouter from 'vue-router'
import store from './store'

Vue.config.productionTip = false

Vue.use(Vuetify)
Vue.use(Toasted)
Vue.use(VueRouter)

var router = new VueRouter({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/home',
      name: 'Home',
      component: HelloWorld
    }
  ]
});

import 'vuetify/dist/vuetify.min.css'

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
