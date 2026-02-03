<template>
    <div class="base-dialog">
        <div class="popup-dialog">
            <header>
                <h1>{{title}}</h1>
                <button @click="closeDialog"><i class="fas fa-times"></i></button>
            </header>
            <slot></slot>
        </div>
    </div>
</template>

<script setup>
const props = defineProps(['title', 'closeDialog']);
</script>

<style scoped>
.base-dialog {
    display:flex;
    align-items:center;
    justify-content:center;
    background-color: rgba(0, 0, 0, 0.5);
    position:fixed;
    width:100%;
    height:100vh;
    top:0;
    left:0;
    overflow: auto;
}
.popup-dialog {
    background-color: white;
    padding:18px;
    border-radius:6px;
    min-width:300px;
    box-sizing: border-box;
}
.popup-dialog header {
    display:flex;
    justify-content:space-between;
    align-items: center;
    margin-bottom:20px;
}
.popup-dialog header > h1 {
    margin:0;
    font-size:16px;
}
.popup-dialog header > button{
    background-color: #e74c3c;
    color:white;
    cursor: pointer;
    display: inline-block;
    width: 25px;
    height: 25px;
    border-radius:50%;
    line-height: 25px;
    padding: 0;
    text-align: center;
}
</style>