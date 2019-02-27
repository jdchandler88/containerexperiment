<template>
  <div id="app">
    <input v-model="message"/>
    <button @click="publishMessage">Publish</button>
    <button id="btn" @click="getResponse">Get Response</button>
    <label for="btn">{{response}}</label>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'app',
  data: function() {
    return {
      message: "Default message",
      response: ""
    }
  },
  methods: {
    publishMessage() {
      console.log("URL: " + this.simpleServiceUrl);
      axios.post(this.simpleServiceUrl, this.message)
      .then(result => {
        console.log("SUCCESS: ", result);
      }).catch(error => {
        console.log("ERROR: ", error);
      });
    },
    getResponse() {
      axios.get(this.simpleServiceUrl)
        .then(result=> {
          this.response = result.data
        }).catch(error=> {
          console.log("ERROR: ", error);
        })
    }
  },
  computed: {
    simpleServiceUrl() {
      return process.env.VUE_APP_SIMPLE_SERVICE_HOST  + process.env.VUE_APP_SIMPLE_SERVICE_PATH;
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
