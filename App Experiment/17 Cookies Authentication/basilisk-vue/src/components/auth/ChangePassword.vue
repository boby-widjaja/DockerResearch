<template>
    <base-card>
        <input type="hidden" v-model="input.username">
        <base-validation-message v-if="validationMessages.object">
            {{validationMessages.object}}
        </base-validation-message>
        <base-password-input 
            :label="'old password *'" 
            :id="'old-password'" 
            v-model="input.oldPassword" 
            :validation="validationMessages.oldPassword">
        </base-password-input>
        <base-password-input 
            :label="'new password *'" 
            :id="'new-password'" 
            v-model="input.newPassword" 
            :validation="validationMessages.newPassword">
        </base-password-input>
        <base-password-input 
            :label="'new password confirmation*'" 
            :id="'new-password-confirmation'" 
            v-model="input.newPasswordConfirmation" 
            :validation="validationMessages.newPasswordConfirmation">
        </base-password-input>
        <base-button :event="submitForm">
            <i class="fas fa-save"></i>
            <span>save</span>
        </base-button>
    </base-card>
</template>

<script setup>
import useStore from '../../store/auth/auth-store.js';
import {useRouter} from 'vue-router';
import {ref} from 'vue';
const store = useStore();
const router = useRouter();
const input = ref({
    username: store.getUsername(),
    oldPassword: '',
    newPassword: '',
    newPasswordConfirmation: ''
});
const validationMessages = ref({});

const errorValidation = validations => {
    for(let validation of validations){
        validationMessages.value[validation.field] = validation.message;
    }
}

const submitForm = async() => {
    validationMessages.value = {};
    const {status, data} = await store.changePassword(input.value);
    if(status === 422){
        errorValidation(data);
    } else {
        store.logout();
        router.push('/auth/login');
    }
}
</script>
