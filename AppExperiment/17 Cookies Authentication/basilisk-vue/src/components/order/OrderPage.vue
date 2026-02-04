<template>
    <base-card>
        <base-text-search 
            :placeholder="'Search By Number...'" 
            @input="searchBy($event, 'invoiceNumber')" 
            :value="invoiceNumber">
        </base-text-search>
        <base-dropdown-search
            :placeholder="'Search By Customer'"
            @input="searchBy($event, 'customerId')"
            :value="customerId"
            :options="customerDropdown">
        </base-dropdown-search>
        <base-dropdown-search
            :placeholder="'Search By Salesman'"
            @input="searchBy($event, 'employeeNumber')"
            :value="employeeNumber"
            :options="salesmanDropdown">
        </base-dropdown-search>
        <base-dropdown-search
            :placeholder="'Search By Delivery'"
            @input="searchBy($event, 'deliveryId')"
            :value="deliveryId"
            :options="deliveryDropdown">
        </base-dropdown-search>
        <base-date-search
            @input="searchBy($event, 'orderDate')"
            :value="orderDate">
        </base-date-search>
    </base-card>
    <base-button :isLink="true" :link="'/order/form/0'">
        <i class="fas fa-plus"></i>
        <span>Insert</span>
    </base-button>
    <base-table :headers="headers">
        <order-row v-for="row in grid" 
            :key="row.invoiceNumber"
            :id="row.invoiceNumber"
            :customer="row.customer"
            :salesman="row.salesman"
            :orderDate="row.orderDate"
            :delivery="row.delivery"
        ></order-row>
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

import OrderRow from './OrderRow.vue';
import useStore from '../../store/order/order-store.js';
import usePagedTable from '../../hooks/paged-table.js';

const store = useStore();
const headers = ref(['Action', 'Invoice Number', 'Customer', 'Salesman', 'Order Date', 'Delivery']);
const {grid, page, totalPages, invoiceNumber, customerId, employeeNumber, deliveryId, orderDate, customerDropdown, salesmanDropdown, deliveryDropdown} = storeToRefs(store);
const {selectPage, firstPage, lastPage, searchBy} = usePagedTable(store);
</script>