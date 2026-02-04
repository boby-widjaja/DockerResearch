<template>
    <div class="base-card">
        <slot></slot>
    </div>
</template>

<style scoped>
.base-card{
    background-color: white;
    padding: 16px;
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
</style>