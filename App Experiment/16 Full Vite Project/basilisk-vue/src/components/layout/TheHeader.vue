<template>
    <header>
        <h1>{{title}}</h1>
        <div class="wrapper">
            <div class="user">
                <div class="username">{{username}}</div>
                <div class="role">{{role}}</div>
            </div>
            <base-button type="button" @click="changePassword">change password</base-button>
            <base-button type="button" @click="signout">
                <i class="fas fa-sign-out-alt"></i>
                <span>sign out</span>
            </base-button>
        </div>
    </header>
</template>

<script setup>
import useStore from '../../store/auth/auth-store.js';
import {ref, computed } from 'vue';
import {useRouter, useRoute} from 'vue-router';

const store = useStore();
const router = useRouter();
const route = useRoute();
const title = computed(() => route.meta.title);
const username = ref(store.getUsername());
const role = ref(store.getRole());
const signout = () => {
    store.logout();
    router.push('/auth/login');
}
const changePassword = () => {
    router.push('/changePassword');
}
</script>

<style scoped>
header{
    grid-area: top-header;
    background-color: white;
    display:flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 15px;
    border-left:var(--twinkle) 1px solid;
}
.username{
    color:var(--electron);
    font-weight: bold;
}
h1{
    font-size: 18px;
    text-transform: capitalize;
}
.wrapper{
    display: flex;
}
.user{
    margin-right: 8px;
}
</style>