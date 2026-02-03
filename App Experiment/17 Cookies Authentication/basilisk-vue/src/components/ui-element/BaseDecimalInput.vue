<template>
    <div :class="['base-decimal-input', {'not-valid': validation != null}, {'decimal-not-valid': decimalValidation != null}]">
        <div class="validation-message">
            <i class="fas fa-exclamation-circle"></i>
            <span>{{validation}}</span>
        </div>
        <div class="decimal-validation-message">
            <i class="fas fa-exclamation-circle"></i>
            <span>{{decimalValidation}}</span>
        </div>
        <input :id="id" type="text" :value="modelValue" @input="validateInput" @blur="correctingFormat">
        <label :for="id">{{label}}</label>
    </div>
</template>

<script setup>
import { ref } from 'vue';
const props = defineProps(['modelValue', 'label', 'id', 'validation']);
const emit = defineEmits(['update:modelValue']);
const decimalValidation = ref(null);

const correctingFormat = event => {
    let convertedValue = Number(event.target.value).toFixed(2);
    event.target.value = convertedValue;
}

const validateInput = event => {
    let convertedValue = Number(event.target.value).toFixed(2);
    if(isNaN(convertedValue)){
        decimalValidation.value = 'Please insert decimal value';
        event.target.value = Number(0).toFixed(2);
    } else {
        decimalValidation.value = null;
    }
    emit('update:modelValue', event.target.value);
}
</script>

<style scoped>
.base-decimal-input{
    display: flex;
    flex-direction: column-reverse;
    align-items: flex-start;
    margin-bottom: 10px;
}
.base-decimal-input:last-child{
    margin-bottom:0;
}
.base-decimal-input > label{
    background-color: #778ca3;
    color:white;
    padding: 4px 8px;
    font-style: italic;
    text-transform: lowercase;
    border-top-left-radius: 4px;
    border-top-right-radius: 4px;
    cursor: text;
}
.base-decimal-input > input{
    outline: none;
    border: solid 1px #778ca3;
    height: 30px;
    box-sizing: border-box;
    padding: 0 6px;
    border-radius: 4px;
    border-top-left-radius: 0;
    width: 220px;
}
.base-decimal-input > input:focus{
    border: solid 1px #0984e3;  
}
.base-decimal-input > input:focus + label{
    background-color: #0984e3;
}
.validation-message, .decimal-validation-message{
    display: none;
    margin-top: 4px;
    color:#e74c3c;
    font-weight: bold;
}
.validation-message > i,
.decimal-validation-message > i{
    margin-right: 3px;
}
div.not-valid .validation-message{
    display:block;
}
div.decimal-not-valid .decimal-validation-message{
    display: block;
}
div.not-valid > input{
    border: solid 1px #e74c3c;
}
div.not-valid > label{
    background-color:#e74c3c;
}
</style>