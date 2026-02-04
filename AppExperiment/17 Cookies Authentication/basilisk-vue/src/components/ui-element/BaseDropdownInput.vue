<template>
    <div :class="['base-dropdown-input', {'not-valid': validation != null}]">
        <div class="validation-message">
            <i class="fas fa-exclamation-circle"></i>
            <span>{{validation}}</span>
        </div>
        <select :id="id" :value="(modelValue === undefined) ? 'null' : modelValue" @input="$emit('update:modelValue', $event.target.value)">
            <option value="null">{{placeholder}}</option>
            <option v-for="option in options" :key="option.value" :value="option.value">{{option.text}}</option>
        </select>
        <label :for="id">{{label}}</label>
    </div>
</template>

<script setup>
const props = defineProps(['modelValue', 'label', 'id', 'validation', 'placeholder', 'options']);
</script>

<style scoped>
.base-dropdown-input{
    display: flex;
    flex-direction: column-reverse;
    align-items: flex-start;
    margin-bottom: 10px;
}
.base-dropdown-input:last-child{
    margin-bottom:0;
}
.base-dropdown-input > label{
    background-color: #778ca3;
    color:white;
    padding: 4px 8px;
    font-style: italic;
    text-transform: lowercase;
    border-top-left-radius: 4px;
    border-top-right-radius: 4px;
    cursor: text;
}
.base-dropdown-input > select{
    outline: none;
    border: solid 1px #778ca3;
    height: 30px;
    box-sizing: border-box;
    padding: 0 6px;
    border-radius: 4px;
    border-top-left-radius: 0;
    width: 220px;
}
.base-dropdown-input > select:focus{
    border: solid 1px #0984e3;  
}
.base-dropdown-input > select:focus + label{
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
div.not-valid > select{
    border: solid 1px #e74c3c;
}
div.not-valid > label{
    background-color:#e74c3c;
}
</style>