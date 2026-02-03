<template>
    <input class="base-text-search" type="text" :placeholder="placeholder">
</template>

<script setup>
const props = defineProps(['placeholder']);
</script>

<style scoped>
.base-text-search{
    outline: none;
    border:solid 1px var(--silver);
    border-radius:4px;
    height: 30px;
    width: 200px;
    box-sizing: border-box;
    padding: 4px;
    margin-right: 8px;
}
.base-text-search:last-child{
    margin-right: 0;
}
</style>