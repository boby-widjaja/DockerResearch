<template>
    <div class="fraction">
        <h1 class="title">Sign Up</h1>
        <input type="text" placeholder="Username" v-model="input.username">
        <div class="validation-message" v-if="validationMessages.username">
            <i class="fas fa-exclamation-circle"></i>
            <span>{{validationMessages.username}}</span>
        </div>
        <input type="password" placeholder="Password" v-model="input.password">
        <div class="validation-message" v-if="validationMessages.password">
            <i class="fas fa-exclamation-circle"></i>
            <span>{{validationMessages.password}}</span>
        </div>
        <input type="password" placeholder="Confirm Password" v-model="input.passwordConfirmation">
        <div class="validation-message" v-if="validationMessages.passwordConfirmation">
            <i class="fas fa-exclamation-circle"></i>
            <span>{{validationMessages.passwordConfirmation}}</span>
        </div>
        <div class="validation-message" v-if="validationMessages.object">
            <i class="fas fa-exclamation-circle"></i>
            <span>{{validationMessages.object}}</span>
        </div>
        <select v-model="input.role">
            <option value="null">Choose Role</option>
            <option v-for="option in roleDropdown" :value="option.value">{{option.text}}</option>
        </select>
        <div class="validation-message" v-if="validationMessages.role">
            <i class="fas fa-exclamation-circle"></i>
            <span>{{validationMessages.role}}</span>
        </div>
        <button type="button" @click="signUp">sign up</button>
        <div class="link-container">
            <router-link :to="'/auth/login'">Click here to login</router-link>
        </div>
    </div>
</template>

<script setup>
import useStore from '../../store/auth/auth-store.js';
import {useRouter} from 'vue-router';
import {ref, onBeforeMount} from 'vue';

const store = useStore();
const router = useRouter();

const input = ref({
    username: '',
    password: '',
    passwordConfirmation: '',
    role: null
})
const roleDropdown = ref([]);
const validationMessages = ref({});

onBeforeMount(async() => {
    await store.getRoleDropdown();
    roleDropdown.value = store.roleDropdown;
});

const errorValidation = validations => {
    for(let validation of validations){
        validationMessages.value[validation.field] = validation.message;
    }
}

const signUp = async() => {
    const {status, data} = await store.register({
        username:input.value.username,
        password:input.value.password,
        passwordConfirmation:input.value.passwordConfirmation,
        role:input.value.role
    });
    if(status === 201){
        router.push('/auth/login');
    } else if(status === 422){
        errorValidation(data);
    };
}
</script>