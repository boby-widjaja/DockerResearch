<template>
    <div class="the-layout">
        <the-navigation></the-navigation>
        <the-header></the-header>
        <main>
            <router-view></router-view>
        </main>
    </div>
</template>

<script setup>
import TheNavigation from './TheNavigation.vue';
import TheHeader from './TheHeader.vue';
</script>

<style scoped>
.the-layout{
    display: grid;
    min-height: 100vh;
    min-width: 1000px;
    grid-template-columns: 180px 1fr;
    grid-template-rows: 60px 1fr;
    grid-template-areas:
        "navigation top-header"
        "navigation main-content";
}
main{
    background-color: var(--twinkle);
    grid-area: main-content;
    padding: 18px;
    box-sizing: border-box;
}
</style>