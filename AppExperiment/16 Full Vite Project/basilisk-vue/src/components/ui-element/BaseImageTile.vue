<template>
    <div class="base-image-tile">
        <div class="title">{{title}}</div>
        <div class="image-container">
            <img :src="imageSource" alt="photo">
        </div>
        <slot></slot>
    </div>
</template>

<script setup>
const props = defineProps(['title', 'imageSource'])
</script>

<style scoped>
.base-image-tile{
    background-color: white;
    padding: 15px;
    box-sizing: border-box;
    border-radius: 6px;
    box-shadow: 0 4px 8px 0 #bdc3c7;
}
.title{
    font-size: 15px;
    margin-bottom: 10px;
    text-align: center;
}
.image-container {
    background-color:var(--dracula);
    height: 140px;
    text-align: center;
    overflow: hidden;
    border-radius: 4px;
    margin-bottom: 8px;
}
.image-container > img {
    height: 140px;
}
:deep(.button-container){
    display:flex;
    margin-bottom:15px;
}
:deep(.button-container > .base-button){
    flex-grow: 1;
}
:deep(.row){
    display: flex;
    justify-content: center;
    column-gap: 6px;
    margin:8px 0;
    font-size:14px;
}
:deep(.label){
    font-weight: bold;
    color:var(--dracula);
}
:deep(.highlight){
    color:var(--electron);
    font-weight: bold;
    text-align: center;
    margin:8px 0;
    font-size:14px;
}
</style>