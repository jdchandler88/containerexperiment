<template>
    <!-- provide form for logging in --> 
    <v-content>
      <v-container fluid fill-height>
        <v-layout align-center justify-center>
          <v-flex xs12 sm8 md4>
            <v-card class="elevation-12">
              <v-toolbar dark color="primary">
                <v-toolbar-title>Login form</v-toolbar-title>
                <v-spacer></v-spacer>

              </v-toolbar>
              <v-card-text>
                <v-form>
                  <v-text-field prepend-icon="person" name="login" label="Login" type="text" v-model="username"></v-text-field>
                  <v-text-field prepend-icon="lock" name="password" label="Password" id="password" type="password" v-model="password"></v-text-field>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" @click.native="loginClicked">Login</v-btn>
              </v-card-actions>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-content>
</template>

<script>
import axios from 'axios'
 export default {
     props: [
         'usernameParameterName',
         'passwordParameterName',
         'otherParams'
     ],
     data() {
         return {
             username: "",
             password: ""
         };
     },
     methods: {
         loginClicked() {
            //login call
            var data = {};
            data["username"] = this.username;
            data["password"] = this.password;
            axios.post(this.oidApiPath, data)
                .then(res => {
                  this.$store.commit('setAccessToken', {
                    accessToken: res.data.accessToken
                  });
                  this.$store.commit('setRefreshToken', {
                    refreshToken: res.data.refreshToken
                  });
                  var roles = this.$store.getters.roles;
                  console.log("roles", roles);
                  this.$router.push("/home");
                })
                .catch(err => {
                  console.log("login failure.");
                });
         }
     },
    computed: {
      oidApiPath() {
        return process.env.VUE_APP_AUTH_HOST + process.env.VUE_APP_AUTHENTICATION_PATH;
      }
    }
 }
</script>

<style>

</style>