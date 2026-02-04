<template>
    <div class="fraction">
        <h1 class="title">Sign In</h1>
        <input type="text" placeholder="Username" autocomplete="off" v-model="input.username">
        <div class="validation-message" v-if="validationMessages.username">
            <i class="fas fa-exclamation-circle"></i>
            <span>{{validationMessages.username}}</span>
        </div>
        <input type="password" placeholder="Password" v-model="input.password">
        <div class="validation-message" v-if="validationMessages.password">
            <i class="fas fa-exclamation-circle"></i>
            <span>{{validationMessages.password}}</span>
        </div>
        <div class="checkbox-container">
            <input type="checkbox" v-model="input.rememberMe">
            <label>Remember Me</label>
        </div>
        <button type="button" @click="signIn">sign in</button>
        <div class="link-container">
            <router-link :to="'/auth/register'">Click here to register</router-link>
        </div>
    </div>
</template>

<script setup>
import useStore from '../../store/auth/auth-store.js';
import { useRouter } from 'vue-router';
import { ref } from 'vue';

const store = useStore();
const router = useRouter();
const input = ref({
    username: '',
    password: '',
    rememberMe: false
});
const validationMessages = ref({});

const errorValidation = validations => {
    for(let validation of validations){
        validationMessages.value[validation.field] = validation.message;
    }
}

const signIn = async() => {
    const {status, data} = await store.authenticating({
        inputUsername: input.value.username,
        inputPassword: input.value.password,
        isRememberMe: input.value.rememberMe
    });
    if(status === 200){
        router.push('/');
    } else if(status === 422) {
        errorValidation(data);
    }
}
</script>
