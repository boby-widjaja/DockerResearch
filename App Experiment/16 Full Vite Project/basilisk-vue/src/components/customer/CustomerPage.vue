<template>
    <base-card>
        <base-text-search 
            :placeholder="'Search By Company...'" 
            @input="searchBy($event, 'company')" 
            :value="company">
        </base-text-search>
        <base-text-search 
            :placeholder="'Search By Contact...'" 
            @input="searchBy($event, 'contact')" 
            :value="contact">
        </base-text-search>
    </base-card>
    <base-button :isLink="true" :link="'/customer/form/0'">
        <i class="fas fa-plus"></i>
        <span>Insert</span>
    </base-button>
    <base-table :headers="headers">
        <customer-row v-for="row in grid" 
            :key="row.id"
            :id="row.id"
            :company="row.companyName"
            :contact="row.contactPerson"
            :address="row.address"
            :city="row.city"
        ></customer-row>
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

import CustomerRow from './CustomerRow.vue';
import useStore from '../../store/customer/customer-store.js';
import usePagedTable from '../../hooks/paged-table.js';

const store = useStore();
const headers = ref(['Action', 'Company', 'Contact', 'Address', 'City']);
const {grid, page, totalPages, company, contact} = storeToRefs(store);
const {selectPage, firstPage, lastPage, searchBy} = usePagedTable(store);
</script>