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
      message: "Hi there!",
      socket: null
    }),
    mounted() {
      console.log("mounted");
      this.socket = new WebSocket("ws://localhost:8083/api/notifications/notifications");
      this.socket.onmessage = (message) => {
        this.receiveMessage(message);
      };
      this.socket.onerror = (message) => {
        console.log("ERROR WITH WEBSOCKET", message);
      };
    },
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
      },
      receiveMessage(message) {
        console.log("MESSAGE RECEIVED!", message.data);
        let toast = this.$toasted.show(message.data, { 
          theme: "toasted-primary", 
          position: "bottom-right", 
          duration : 5000
        });
      }
    }
  }
</script>

<style>

</style>
