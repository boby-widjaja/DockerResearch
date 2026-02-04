<template>
    <base-card>
        <base-text-search 
            :placeholder="'Search By Name...'" 
            @input="searchBy($event, 'name')" 
            :value="name">
        </base-text-search>
    </base-card>
    <base-button :isLink="true" :link="'/category/form/0'">
        <i class="fas fa-plus"></i>
        <span>Insert</span>
    </base-button>
    <base-table :headers="headers">
        <category-row v-for="row in grid" 
            :key="row.id"
            :id="row.id"
            :name="row.name"
            :description="row.description"
        ></category-row>
    </base-table>
    <base-pagination 
        :page="page" 
        :totalPages="totalPages" 
        :selectPage="selectPage" 
        :firstPage="firstPage" 
        :lastPage="lastPage">
    </base-pagination>
    <teleport to="body">
        <router-view></router-view>
    </teleport>
</template>

<script setup>
import { ref } from 'vue';
import { storeToRefs } from 'pinia';

import CategoryRow from './CategoryRow.vue';
import useStore from '../../store/category/category-store.js';
import usePagedTable from '../../hooks/paged-table.js';

const store = useStore();
const headers = ref(['Action', 'Name', 'Description']);
const {grid, page, totalPages, name} = storeToRefs(store);
const {selectPage, firstPage, lastPage, searchBy} = usePagedTable(store);
</script>