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
         'oidApiPath',
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
             console.log("login clicked! loginPath=" + this.oidApiPath, ", usernamParameter:",  this.usernameParameterName, ", passwordParameterName: ", this.passwordParameterName, ", other params: ", this.otherParams);
            var data = new FormData();
            data[this.usernameParameterName] = this.username;
            data[this.passwordParameterName] = this.password;
            for (var key in this.otherParams) {
                console.log("key", key, "value", this.otherParams[key]);
                data[key] = this.otherParams[key];
            }
            console.log("post data: ", this.encodeForm(data), this.otherParams);
            axios.post(this.oidApiPath, this.encodeForm(data), 
                { 
                    headers: {'Content-Type': 'application/x-www-form-urlencoded' }
                })
                .then(res => {
                    console.log("respone!: ", res);
                })
                .catch(err => {
                    console.log("error!: ", err);
                });
         },
         encodeForm(data) {
            return Object.keys(data)
            .map(key => encodeURIComponent(key) + '=' + encodeURIComponent(data[key]))
            .join('&');
         }
     }
 }
</script>

<style>

</style>