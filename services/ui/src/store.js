import Vue from 'vue'
import Vuex from 'vuex'
import jwt from 'jsonwebtoken'
Vue.use(Vuex);
export default new Vuex.Store({
    state: {
        accessToken: null,
        refreshToken: null,
        roles: []
    },
    mutations: {
        setAccessToken(state, payload) {
            state.accessToken = payload.accessToken;
        },
        setRefreshToken(state, payload) {
            state.refreshToken = payload.refreshToken;
        }
    },
    getters: {
        roles: state => {
            var decoded = jwt.decode(state.accessToken);
            return decoded.roles;
        }
    }
});