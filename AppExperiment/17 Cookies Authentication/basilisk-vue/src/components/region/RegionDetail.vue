<template>
    <base-card>
        <base-header>
            <template #label>City</template>
            <template #value>{{city}}</template>
        </base-header>
        <base-header>
            <template #label>Remark</template>
            <template #value>{{remark}}</template>
        </base-header>
    </base-card>
    <base-card>
        <base-text-search 
            :placeholder="'Search By Number...'" 
            @input="searchBy($event,'employeeNumber')" 
            :value="employeeNumber">
        </base-text-search>
        <base-text-search 
            :placeholder="'Search By Name...'" 
            @input="searchBy($event,'name')" 
            :value="name">
        </base-text-search>
        <base-dropdown-search
            :placeholder="'Search By Level'"
            @input="searchBy($event,'employeeLevel')" 
            :value="employeeLevel"
            :options="employeeLevelDropdown">
        </base-dropdown-search>
        <base-text-search 
            :placeholder="'Search By Superior...'" 
            @input="searchBy($event,'superiorName')" 
            :value="superiorName">
        </base-text-search>
    </base-card>
    <base-button :isLink="true" :link="`/regionDetail/${headerId}/form`">
        <i class="fas fa-plus"></i>
        <span>Assign Salesman</span>
    </base-button>
    <base-table :headers="headers">
        <region-detail-row v-for="row in grid" 
            :key="row.employeeNumber"
            :id="row.employeeNumber"
            :fullName="row.fullName"
            :level="row.level"
            :superior="row.superior"
            :headerId="headerId"
        ></region-detail-row>
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

import RegionDetailRow from './RegionDetailRow.vue';
import useStore from '../../store/regionDetail/regionDetail-store.js';
import usePagedTable from '../../hooks/paged-table.js';

const props = defineProps(['headerId']);
const store = useStore();
const headers = ref(['Action', 'Employee Number', 'Full Name', 'Level', 'Superior']);
const {grid, page, totalPages, employeeNumber, name, employeeLevel, superiorName, employeeLevelDropdown} = storeToRefs(store);
let city = ref('');
let remark = ref('');
const {selectPage, firstPage, lastPage, searchBy} = usePagedTable(store, async () => {
    store.headerId = props.headerId;
    const header = await store.findOne(props.headerId);
    city.value = header.city;
    remark.value = header.remark;
});
</script>