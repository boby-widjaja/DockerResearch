<template>
    <base-card>
        <base-text-search 
            :placeholder="'Search By Company...'" 
            @input="searchBy($event, 'company')" 
            :value="company">
        </base-text-search>
    </base-card>
    <base-button :isLink="true" :link="'/delivery/form/0'">
        <i class="fas fa-plus"></i>
        <span>Insert</span>
    </base-button>
    <base-table :headers="headers">
        <delivery-row v-for="row in grid" 
            :key="row.id"
            :id="row.id"
            :company="row.companyName"
            :phone="row.phone"
            :cost="row.cost"
        ></delivery-row>
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

import DeliveryRow from './DeliveryRow.vue';
import useStore from '../../store/delivery/delivery-store.js';
import usePagedTable from '../../hooks/paged-table.js';

const store = useStore();
const headers = ref(['Action', 'Company', 'Phone', 'Cost']);
const {grid, page, totalPages, company} = storeToRefs(store);
const {selectPage, firstPage, lastPage, searchBy} = usePagedTable(store);
</script>