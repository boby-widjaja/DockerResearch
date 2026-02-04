<template>
    <table class="base-table">
        <thead>
            <tr>
                <th v-for="header in headers" :key="header">{{header}}</th>
            </tr>
        </thead>
        <tbody>
            <slot></slot>
        </tbody>
    </table>
</template>

<script setup>
const props = defineProps(['headers'])
</script>

<style scoped>
.base-table{
    width: 100%;
    margin-top: 12px;
    text-align: left;
    border-collapse: collapse;
}
th{
    padding: 8px;
}
:deep(td){
    padding:8px;
    background-color: white;
    border-bottom: var(--twinkle) solid 6px;
}
:deep(tr:first-child td:first-child){
    border-top-left-radius: 6px;
}
:deep(tr:first-child td:last-child){
    border-top-right-radius: 6px;
}
:deep(tr:last-child td){
    border-bottom: none;
}
:deep(tr:last-child td:first-child){
    border-bottom-left-radius: 6px;
}
:deep(tr:last-child td:last-child){
    border-bottom-right-radius: 6px;
}
</style>