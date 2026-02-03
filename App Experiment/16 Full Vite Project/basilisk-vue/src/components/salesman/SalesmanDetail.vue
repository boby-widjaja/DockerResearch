<template>
    <base-card>
        <base-header>
            <template #label>Number</template>
            <template #value>{{employeeNumber}}</template>
        </base-header>
        <base-header>
            <template #label>Name</template>
            <template #value>{{fullName}}</template>
        </base-header>
    </base-card>
    <base-card>
        <base-text-search 
            :placeholder="'Search By City...'" 
            @input="searchBy($event, 'city')" 
            :value="city">
        </base-text-search>
    </base-card>
    <base-button :isLink="true" :link="`/salesmanDetail/${headerId}/form`">
        <i class="fas fa-plus"></i>
        <span>Assign Region</span>
    </base-button>
    <base-table :headers="headers">
        <salesman-detail-row v-for="row in grid" 
            :key="row.id"
            :id="row.id"
            :city="row.city"
            :remark="row.remark"
            :headerId="headerId"
        ></salesman-detail-row>
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

import SalesmanDetailRow from './SalesmanDetailRow.vue';
import useStore from '../../store/salesmanDetail/salesmanDetail-store.js';
import usePagedTable from '../../hooks/paged-table.js';

const props = defineProps(['headerId']);
const store = useStore();
const headers = ref(['Action', 'City', 'Remark']);
const {grid, page, totalPages, city} = storeToRefs(store);
let employeeNumber = ref('');
let fullName = ref('');
const {selectPage, firstPage, lastPage, searchBy} = usePagedTable(store, async () => {
    store.headerId = props.headerId;
    const header = await store.findOne(props.headerId);
    employeeNumber.value = header.employeeNumber;
    fullName.value = `${header.firstName} ${header.lastName}`;
});
</script>