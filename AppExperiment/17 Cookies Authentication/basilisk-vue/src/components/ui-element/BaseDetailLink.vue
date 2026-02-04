<template>
    <router-link :to="link" class="base-detail-link">
        <i class="fas fa-search"></i>
        <slot></slot>
    </router-link>
</template>

<script setup>
const props = defineProps(['link'])
</script>

<style scoped>
.base-detail-link{
    color:var(--electron);
}
i{
    margin-right: 5px;
}
</style>