<template>
    <div class="base-error-dialog">
        <div class="popup-dialog">
            <h1>
                <i class="fas fa-exclamation-circle"></i>
                <span>{{title}}</span>
            </h1>
            <div class="note">
                <slot></slot>
                <p class="server-message">Server Message: {{message}}</p>
            </div>
            <base-button :isLink="true" :link="closeLink">
                <i class="fas fa-times"></i>
                <span>Close</span>
            </base-button>
        </div>
    </div>
</template>

<script setup>
const props = defineProps(['title', 'message', 'closeLink']);
</script>

<style scoped>
.base-error-dialog {
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
.popup-dialog > h1{
    color:#c23616;
    text-align: center;
    font-size: 18px;;
}
.popup-dialog > h1 > i{
    margin-right: 6px;
}
.popup-dialog > .note{
    max-width: 450px;
    background-color: rgba(231, 76, 60, 0.4);
    border-left: solid 8px #e74c3c;
    padding: 15px;
    box-sizing: border-box;
    color:#4b4b4b;
    border-radius: 6px;
    margin:20px 0;
}
.popup-dialog > .note > p{
    margin-bottom: 18px;
    line-height: 18px;
}
.popup-dialog > .note > p:last-child{
    margin-bottom: 0;
}
.server-message{
    font-size: 14px;
    font-weight: bold;
    color:black;
}
</style>