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
                        >
                            <v-list-tile-action>
                                <v-icon v-if="user.icon" color="pink">star</v-icon>
                            </v-list-tile-action>

                            <v-list-tile-content>
                                <v-list-tile-title v-text="user.username"></v-list-tile-title>
                            </v-list-tile-content>

                            <v-list-tile-avatar>
                                <img :src="user.avatar">
                            </v-list-tile-avatar>
                        </v-list-tile>
                    </v-list>
                </v-card>
            </v-flex>
            <v-flex> 
                <v-card>
                    USER DETAIL VIEW
                </v-card>
            </v-flex>
        </v-layout>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                users: [
                    {
                        username: "jdchand"
                    },
                    {
                        username: "anotheruser"
                    }
                ]
            };
        }
    }
</script>