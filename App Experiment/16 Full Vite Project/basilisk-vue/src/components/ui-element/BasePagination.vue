<template>
    <div class="base-pagination">
        <div>Page {{page}} of {{totalPages}}</div>
        <div>
            <button v-if="page > 1" type="button" @click="firstPage()">
                <i class="fas fa-angle-double-left"></i>
            </button>
            <button v-if="page - 2 >= 1" type="button" @click="selectPage(page - 2)">
                {{page - 2}}
            </button>
            <button v-if="page - 1 >= 1" type="button" @click="selectPage(page - 1)">
                {{page - 1}}
            </button>
            <button class="selected" type="button" @click="selectPage(page)">
                {{page}}
            </button>
            <button v-if="page + 1 <= totalPages" type="button" @click="selectPage(page + 1)">
                {{page + 1}}
            </button>
            <button v-if="page + 2 <= totalPages" type="button" @click="selectPage(page + 2)">
                {{page + 2}}
            </button>
            <button v-if="page < totalPages" type="button" @click="lastPage()">
                <i class="fas fa-angle-double-right"></i>
            </button>
        </div>
    </div>
</template>

<script setup>
const props = defineProps(['page', 'totalPages', 'selectPage', 'firstPage', 'lastPage']);
</script>

<style scoped>
.base-pagination{
    margin-top: 12px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-weight: bold;
}
.base-pagination button,
.base-pagination a{
    margin-left: 5px;
    cursor: pointer;
    display: inline-block;
    width: 32px;
    height: 32px;
    text-align: center;
    padding: 0;
    background-color: white;
    border-radius: 6px;
}
.base-pagination button:hover,
.base-pagination a:hover,
.base-pagination .selected{
    color:white;
    background-color: var(--electron);
}
@media only screen and (max-width:480px) {
    .base-pagination{
        flex-direction: column-reverse;
        row-gap: 12px;
    }
}
</style>