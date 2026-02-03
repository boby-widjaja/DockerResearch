<template>
    <div :class="['base-text-area', {'not-valid': validation != null}]">
        <div class="validation-message">
            <i class="fas fa-exclamation-circle"></i>
            <span>{{validation}}</span>
        </div>
        <textarea :id="id" :value="modelValue" @input="$emit('update:modelValue', $event.target.value)"></textarea>
        <label :for="id">{{label}}</label>
    </div>
</template>

<script setup>
const props = defineProps(['modelValue', 'label', 'id', 'validation']);
</script>

<style scoped>
.base-text-area{
    display: flex;
    flex-direction: column-reverse;
    align-items: flex-start;
    margin-bottom: 10px;
}
.base-text-area:last-child{
    margin-bottom: 0;
}
.base-text-area > label{
    background-color: #778ca3;
    color:white;
    padding: 4px 8px;
    font-style: italic;
    text-transform: lowercase;
    border-top-left-radius: 4px;
    border-top-right-radius: 4px;
    cursor: text;
}
.base-text-area > textarea{
    outline: none;
    border: solid 1px #778ca3;
    box-sizing: border-box;
    padding: 6px;
    border-radius: 4px;
    border-top-left-radius: 0;
    width: 300px;
    height: 80px;
    resize: none;
}
.base-text-area > textarea:focus{
    border: solid 1px #0984e3;  
}
.base-text-area > textarea:focus + label{
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
div.not-valid > textarea{
    border: solid 1px #e74c3c;
}
div.not-valid > label{
    background-color:#e74c3c;
}
</style>