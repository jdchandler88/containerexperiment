<!-- 

first page needs to be login. is this a view?

routes: 

/ -> login
/home -> helloworld

-->

<template>
  <v-app>
    <v-container>
      <v-layout v-if="isAuthenticated">
        <v-flex>
          <v-card>
            <v-btn to="/home">Home</v-btn>
            <v-btn v-for="route in customRoutes" :key="route.name" :to="route.path">{{route.name}}</v-btn>
          </v-card>
        </v-flex>
      </v-layout>
      <router-view @authenticated="authenticated"></router-view>
    </v-container>
    
    
  </v-app>
</template>

<script>
import HelloWorld from './components/HelloWorld'
import Login from './components/Login'
import ManageUsers from './components/ManageUsers';
import ManageRoles from './components/ManageRoles';
export default {
  name: 'App',
  components: {
    HelloWorld,
    Login
  },
  data () {
    return {
      isAuthenticated: false,
      customRoutes: []
    }
  },
  methods: {
    authenticated(result) {
      console.log("login result: ", result);
      if (result.success) {
        //store token information
        this.$store.commit('setAccessToken', {
          accessToken: result.data.accessToken
        });
        this.$store.commit('setRefreshToken', {
          refreshToken: result.data.refreshToken
        });
        var roles = this.$store.getters.roles;
        this.isAuthenticated = true;
        //configure application for noral user vs. admin
        console.log("roles", roles);
        console.log("prerouter", this.$router);
        if (roles.includes("Administrators")) {
          this.customRoutes = [
            {
              path: '/manageUsers',
              name: 'ManageUsers',
              component: ManageUsers
            },
            {
              path: '/manageRoles',
              name: 'ManageRoles', 
              component: ManageRoles
            }
          ];
          this.$router.addRoutes(this.customRoutes);
          console.log("postrouter", this.$router);
        }
        //go home
        this.$router.push("/home");
      } else {
        alert("Invalid credentials entered.", result.data);
      }
    }
  },
  computed: {
    oidApiPath() {
      return process.env.VUE_APP_AUTH_HOST + process.env.VUE_APP_AUTHENTICATION_PATH;
    }
  }
}
</script>
