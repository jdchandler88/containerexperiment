<template>
    <div>
        <!-- list of users and a user detail view-->
        <v-layout>
            <v-flex>
                <v-toolbar color="indigo" dark>
                        <v-toolbar-side-icon></v-toolbar-side-icon>
                        <v-toolbar-title>Users</v-toolbar-title>
                        <v-spacer></v-spacer>
                </v-toolbar>
            </v-flex>
        </v-layout>
        <v-layout>
            <v-flex xs12 sm6>
                <v-card>
                    <v-list>
                        <v-list-tile
                            v-for="user in users"
                            :key="user.username"
                            @click="userSelected(user)"
                        >
                            <v-list-tile-action>
                                <v-icon v-if="user.icon" color="pink">star</v-icon>
                            </v-list-tile-action>

                            <v-list-tile-content>
                                <v-list-tile-title v-text="user.username"></v-list-tile-title>
                            </v-list-tile-content>
                        </v-list-tile>
                    </v-list>
                </v-card>
            </v-flex>
            <v-flex> 
                <v-card>
                    <ul v-if="selectedUser">
                        <li>Username: {{selectedUser.username}}</li>
                        <li>Name: {{selectedUser.firstName}} {{selectedUser.lastName}} </li>
                        <li>Avatar: <img :src="selectedUser.avatarUrl"/></li>
                        <li>Phone: {{selectedUser.phone}}</li>
                        <li>Bio: {{selectedUser.bio}}</li>
                    </ul>
                </v-card>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>
    import axios from 'axios';
    export default {
        data() {
            return {
                users: [],
                selectedUser: null
            };
        },
        methods: {
            userSelected(user) {
                this.selectedUser = user;
                console.log(user);
            },
            getUsers() {
                axios.get(process.env.VUE_APP_USERS_HOST + process.env.VUE_APP_USERS_PATH)
                    .then(res => {
                        this.users = res.data;
                        var firstUser = res.data[0];
                        if (firstUser) {
                            this.userSelected(res.data[0]);
                        }
                    })
                    .catch(err => {
                        console.log("error retrieving users.", err);
                    });
            }
        },
        mounted() {
            this.getUsers();
        }
    }
</script>