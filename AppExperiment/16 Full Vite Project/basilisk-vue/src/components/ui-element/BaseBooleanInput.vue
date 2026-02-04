<template>
    <div :class="['base-boolean-input', {'not-valid': validation != null}]">
        <div class="element">
            <input :id="id" type="checkbox" :checked="modelValue" @input="$emit('update:modelValue', $event.target.checked)">
            <label :for="id">{{label}}</label>
        </div>
        <div class="validation-message">
            <i class="fas fa-exclamation-circle"></i>
            <span>{{validation}}</span>
        </div>
    </div>
</template>

<script setup>
const props = defineProps(['modelValue', 'label', 'id', 'validation']);
</script>

<style scoped>
.base-boolean-input{
    margin:15px 0;
}
.element{
    display: flex;
    align-items: center;
    column-gap: 4px;
}
input{
    width: 22px;
    height: 22px;
}
label{
    background-color: #778ca3;
    color:white;
    padding: 4px 8px;
    border-radius: 4px;
}
input:focus + label{
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