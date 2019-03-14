<template>
  <v-app>
    <Login 
      @loginSuccess="loginSuccess" 
      @loginFailure="loginFailure"
      :oidApiPath="this.oidApiPath"
      usernameParameterName="username"
      passwordParameterName="password"
      :otherParams="{}"
    ></Login>
    <v-toolbar app>
      <v-toolbar-title class="headline text-uppercase">
        <span>Vuetify</span>
        <span class="font-weight-light">MATERIAL DESIGN</span>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn
        flat
        href="https://github.com/vuetifyjs/vuetify/releases/latest"
        target="_blank"
      >
        <span class="mr-2">Latest Release</span>
      </v-btn>
    </v-toolbar>

    <v-content>
      <HelloWorld/>
    </v-content>
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
