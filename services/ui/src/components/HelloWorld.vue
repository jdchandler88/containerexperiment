<template>
  <v-container>
    <v-flex xs12 sm6 md3>
      <v-text-field
        label="Message"
        v-model="message"
      ></v-text-field>
      <v-btn @click.native="publishMessage">Publish</v-btn>
    </v-flex>
  </v-container>
</template>

<script>
  import axios from 'axios'
  export default {
    data: () => ({
      message: "Hi there!"
    }),
    methods: {
      publishMessage() {
        console.log(this.message);
        axios.post(process.env.VUE_APP_SIMPLE_SERVICE_HOST + "/api/simple/", this.message) 
          .then(response => {
            console.log("RESPONSE: ", response);
          })
          .catch(error => {
            console.log("ERROR: ", error);
          })
      }
    }
  }
</script>

<style>

</style>
