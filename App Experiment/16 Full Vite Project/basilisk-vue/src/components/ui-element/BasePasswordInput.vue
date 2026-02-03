<template>
    <div :class="['base-password-input', {'not-valid': validation != null}]">
        <div class="validation-message">
            <i class="fas fa-exclamation-circle"></i>
            <span>{{validation}}</span>
        </div>
        <input :id="id" type="password" :value="modelValue" :disabled="isDisabled" @input="$emit('update:modelValue', $event.target.value)">
        <label :for="id">{{label}}</label>
    </div>
</template>

<script setup>
const props = defineProps({
    modelValue: {required: false},
    label: {required: true},
    id: {required: true},
    validation: {required: false},
    isDisabled: {default: false}
});
</script>

<style scoped>
.base-password-input{
    display: flex;
    flex-direction: column-reverse;
    align-items: flex-start;
    margin-bottom: 10px;
}
.base-password-input:last-child{
    margin-bottom:0;
}
.base-password-input > label{
    background-color: #778ca3;
    color:white;
    padding: 4px 8px;
    font-style: italic;
    text-transform: lowercase;
    border-top-left-radius: 4px;
    border-top-right-radius: 4px;
    cursor: text;
}
.base-password-input > input{
    outline: none;
    border: solid 1px #778ca3;
    height: 30px;
    box-sizing: border-box;
    padding: 0 6px;
    border-radius: 4px;
    border-top-left-radius: 0;
    width: 220px;
}
.base-password-input > input:focus{
    border: solid 1px #0984e3;  
}
.base-password-input > input:focus + label{
    background-color: #0984e3;
}
.validation-message{
    display: none;
    margin-top: 4px;
    color:#e74c3c;
    font-weight: bold;
}
.validation-message > i{
    margin-right: 3px;
}
div.not-valid .validation-message{
    display:block;
}
div.not-valid > input{
    border: solid 1px #e74c3c;
}
div.not-valid > label{
    background-color:#e74c3c;
}
input:disabled{
    background-color: #ced6e0;
    cursor:not-allowed;
}
</style>