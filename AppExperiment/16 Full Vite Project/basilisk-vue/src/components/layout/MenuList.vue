<template>
    <router-link class="menu-list" :to="link" v-if="isAuth()">
        <i :class="icon"></i>
        <span>{{name}}</span>
    </router-link>
</template>

<script setup>
import {ref} from 'vue';
import useStore from '../../store/auth/auth-store.js';
const props = defineProps(['name', 'icon', 'link', 'auth']);

const store = useStore();
const role = ref(store.getRole());

const isAuth = () => {
    return props.auth.includes(role.value);
}
</script>

<style scoped>
.menu-list{
    display: flex;
    align-items: center;
    margin: 15px 10px;
    color:var(--electron);
    border-radius: 4px;
    padding: 8px;
    font-size: 14px;
}
.router-link-active,
.menu-list:hover{
    background-color: var(--electron);
    color:white;
}
i{
    margin-right: 10px;
}
.menu-list:last-child{
    margin-bottom: 0;
}
</style>