<template>
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
    <base-button :isLink="true" :link="'/salesman/form/0'">
        <i class="fas fa-plus"></i>
        <span>Insert</span>
    </base-button>
    <base-table :headers="headers">
        <salesman-row v-for="row in grid" 
            :key="row.employeeNumber"
            :id="row.employeeNumber"
            :fullName="row.fullName"
            :level="row.level"
            :superior="row.superior"
        ></salesman-row>
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

import SalesmanRow from './SalesmanRow.vue';
import useStore from '../../store/salesman/salesman-store.js';
import usePagedTable from '../../hooks/paged-table.js';

const store = useStore();
const headers = ref(['Action', 'Employee Number', 'Full Name', 'Level', 'Superior']);
const {grid, page, totalPages, employeeNumber, name, employeeLevel, superiorName, employeeLevelDropdown} = storeToRefs(store);
const {selectPage, firstPage, lastPage, searchBy} = usePagedTable(store);
</script>