<template>
    <select class="base-dropdown-search">
        <option value="">{{placeholder}}</option>
        <option v-for="option in options" :key="option.value" :value="option.value">{{option.text}}</option>
    </select>
</template>

<script setup>
const props = defineProps(['placeholder', 'options']);
</script>

<style scoped>
.base-dropdown-search{
    outline: none;
    border:solid 1px var(--silver);
    border-radius:4px;
    height: 30px;
    width: 200px;
    box-sizing: border-box;
    padding: 4px;
    margin-right: 8px;
}
.base-dropdown-search:last-child{
    margin-right: 0;
}
</style>