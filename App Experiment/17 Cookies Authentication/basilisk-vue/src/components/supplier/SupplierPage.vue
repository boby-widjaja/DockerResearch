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
        <base-text-search 
            :placeholder="'Search By Job Title...'" 
            @input="searchBy($event, 'jobTitle')" 
            :value="jobTitle">
        </base-text-search>
    </base-card>
    <base-button :isLink="true" :link="'/supplier/form/0'">
        <i class="fas fa-plus"></i>
        <span>Insert</span>
    </base-button>
    <base-table :headers="headers">
        <supplier-row v-for="row in grid" 
            :key="row.id"
            :id="row.id"
            :company="row.companyName"
            :contact="row.contactPerson"
            :jobTitle="row.jobTitle"
        ></supplier-row>
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

import SupplierRow from './SupplierRow.vue';
import useStore from '../../store/supplier/supplier-store.js';
import usePagedTable from '../../hooks/paged-table.js';

const store = useStore();
const headers = ref(['Action', 'Company', 'Contact', 'Job Title']);
const {grid, page, totalPages, company, contact, jobTitle} = storeToRefs(store);
const {selectPage, firstPage, lastPage, searchBy} = usePagedTable(store);
</script>