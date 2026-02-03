<template>
    <div class="base-error-container">
        <div>
            <base-logo></base-logo>
        </div>
        <div class="base-card">
            <h1 class="title">{{title}}</h1>
            <slot></slot>
            <div class="link-container">
                <router-link :to="'/'">Click here to back to the landing page</router-link>
            </div>
        </div>
    </div>
</template>

<script setup>
const props = defineProps(['title']);
</script>

<style scoped>
.base-error-container{
    background-color: var(--twinkle);
    width: 100%;
    height:100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}
.base-card{
    background-color: white;
    width: 400px;
    padding: 25px;
    text-align: center;
    box-sizing: border-box;
    border-radius: 6px;
    box-shadow: 0 4px 8px 0 #bdc3c7;
    margin-top: 12px;
    margin-bottom: 12px;
}
.base-card:first-child{
    margin-top: 0;
}
.base-card:last-child{
    margin-bottom: 0;
}
.title{
    text-align: center;
    margin: 0 0 25px 0;
    text-transform: capitalize;
    font-weight: bold;
    font-size: 18px;
    color:var(--electron);
}
:deep(p){
    font-size: 14px;
    line-height: 20px;
}
.link-container{
    text-align: center;
    margin-top: 30px;
}
.link-container > a{
    color:var(--electron);
    text-decoration: underline;
    font-size: 14px;
}
</style>