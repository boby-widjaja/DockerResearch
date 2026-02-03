<template>
    <button v-if="!isLink" @click="event" class="base-button">
        <slot></slot>
    </button>
    <router-link v-else :to="link" class="base-button">
        <slot></slot>
    </router-link>
</template>

<script setup>
const props = defineProps({
    isLink: {default: false},
    link: {default: "javascript:;"},
    event: {required: false}
})
</script>

<style scoped>
.base-button{
    display: inline-block;
    height: 30px;
    line-height: 30px;
    padding: 0 10px;
    text-transform: uppercase;
    box-sizing: border-box;
    cursor: pointer;
    border-radius: 6px;
    background-color:var(--electron);
    color:white;
    text-align: center;
    margin-right: 8px;
}
.base-button:last-child{
    margin-right: 0;
}
:deep(i){
    margin-right: 6px;
}
</style>