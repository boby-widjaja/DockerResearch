<template>
    <div :class="['base-file-input', {'not-valid': validation != null}]">
        <div class="validation-message">
            <i class="fas fa-exclamation-circle"></i>
            <span>{{validation}}</span>
        </div>
        <input :id="id" type="file" @input="uploadingImage">
        <label :for="id">{{label}}</label>
    </div>
</template>

<script setup>
const props = defineProps(['label', 'id', 'validation', 'uploadingImage']);
</script>

<style scoped>
.base-file-input{
    display: flex;
    flex-direction: column-reverse;
    align-items: flex-start;
    margin-bottom: 10px;
}
.base-file-input:last-child{
    margin-bottom:0;
}
.base-file-input > label{
    background-color: #778ca3;
    color:white;
    padding: 4px 8px;
    font-style: italic;
    text-transform: lowercase;
    border-top-left-radius: 4px;
    border-top-right-radius: 4px;
    cursor: text;
}
.base-file-input > input{
    border: solid 1px #778ca3;
    box-sizing: border-box;
    padding: 8px;
    border-radius: 4px;
    border-top-left-radius: 0;
    width: 300px;
    overflow: hidden;
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