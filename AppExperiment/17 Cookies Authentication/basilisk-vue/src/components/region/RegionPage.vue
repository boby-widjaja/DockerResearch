<template>
    <base-card>
        <base-text-search 
            :placeholder="'Search By City...'" 
            @input="searchBy($event, 'city')" 
            :value="city">
        </base-text-search>
    </base-card>
    <base-button :isLink="true" :link="'/region/form/0'">
        <i class="fas fa-plus"></i>
        <span>Insert</span>
    </base-button>
    <base-table :headers="headers">
        <region-row v-for="row in grid" 
            :key="row.id"
            :id="row.id"
            :city="row.city"
            :remark="row.remark"
        ></region-row>
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

import RegionRow from './RegionRow.vue';
import useStore from '../../store/region/region-store.js';
import usePagedTable from '../../hooks/paged-table.js';

const store = useStore();
const headers = ref(['Action', 'City', 'Remark']);
const {grid, page, totalPages, city} = storeToRefs(store);
const {selectPage, firstPage, lastPage, searchBy} = usePagedTable(store);
</script>