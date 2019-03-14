<!-- 

first page needs to be login. is this a view?

routes: 

/ -> login
/home -> helloworld

-->

<template>
  <v-app>
    <router-view></router-view>
  </v-app>
</template>

<script>
import HelloWorld from './components/HelloWorld'
import Login from './components/Login'
export default {
  name: 'App',
  components: {
    HelloWorld,
    Login
  },
  data () {
    return {
    }
  },
  methods: {
    loginSuccess(data) {
      console.log("oooooh", data);
      this.$router.push("/home");
    },
    loginFailure() {
      console.log("you fucked up");
    }
  },
  computed: {
    oidApiPath() {
      return process.env.VUE_APP_AUTH_HOST + process.env.VUE_APP_AUTHENTICATION_PATH;
    }
  }
}
</script>
