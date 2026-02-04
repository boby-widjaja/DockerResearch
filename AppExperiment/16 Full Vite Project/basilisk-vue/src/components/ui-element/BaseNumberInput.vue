<template>
    <div :class="['base-number-input', {'not-valid': validation != null}]">
        <div class="validation-message">
            <i class="fas fa-exclamation-circle"></i>
            <span>{{validation}}</span>
        </div>
        <input :id="id" type="number" min="0" max="999" step="1" :value="modelValue" @input="$emit('update:modelValue', $event.target.value)">
        <label :for="id">{{label}}</label>
    </div>
</template>

<script setup>
const props = defineProps(['modelValue', 'label', 'id', 'validation']);
</script>

<style scoped>
.base-number-input{
    display: flex;
    flex-direction: column-reverse;
    align-items: flex-start;
    margin-bottom: 10px;
}
.base-number-input:last-child{
    margin-bottom:0;
}
.base-number-input > label{
    background-color: #778ca3;
    color:white;
    padding: 4px 8px;
    font-style: italic;
    text-transform: lowercase;
    border-top-left-radius: 4px;
    border-top-right-radius: 4px;
    cursor: text;
}
.base-number-input > input{
    outline: none;
    border: solid 1px #778ca3;
    height: 30px;
    box-sizing: border-box;
    padding: 0 6px;
    border-radius: 4px;
    border-top-left-radius: 0;
    width: 100px;
}
.base-number-input > input:focus{
    border: solid 1px #0984e3;  
}
.base-number-input > input:focus + label{
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
</style>