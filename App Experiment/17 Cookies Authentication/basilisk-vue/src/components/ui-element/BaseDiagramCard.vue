<template>
    <div class="base-diagram-card">
        <h1>{{title}}</h1>
        <div>
            <slot></slot>
        </div>
    </div>
</template>

<script setup>
const props = defineProps(['title']);
</script>

<style scoped>
.base-diagram-card{
    background-color: white;
    box-sizing: border-box;
    border-radius: 6px;
    box-shadow: 0 4px 8px 0 #bdc3c7;
}
.base-diagram-card > div{
    padding: 16px;
}
h1{
    background-color: var(--electron);
    border-top-left-radius: 6px;
    border-top-right-radius: 6px;
    color:white;
    font-size: 16px;
    padding: 10px 16px;
    text-transform: capitalize;
}
:deep(select){
    display: inline-block;
    height: 30px;
    width: 250px;
    box-sizing: border-box;
    padding: 6px;
    border:solid 1px var(--silver);
    border-radius: 4px;
    vertical-align: middle;
}
:deep(.order-year){
    width: 75px;
}
:deep(.filter-container){
    margin-bottom: 10px;
}
:deep(.filter-container > div){
    display: inline-block;
    margin-right: 20px;
}
:deep(.two-side) {
    display: flex;
}
:deep(.two-side > .chart) {
    flex-grow: 1;
}
:deep(.two-side > .analysis) {
    width: 350px;
    border-left: var(--silver) solid 1px;
    padding: 10px 10px 10px 20px;
    margin-left: 20px;
}
:deep(.analysis p){
    margin: 18px 0;
    line-height: 16px;
}
:deep(.analysis .highlight) {
    color: #14acf8;
    font-weight: bold;
}
:deep(.analysis .note) {
    font-style: italic;
}
</style>