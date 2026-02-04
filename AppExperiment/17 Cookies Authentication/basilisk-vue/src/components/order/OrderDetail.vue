<template>
    <base-card>
        <base-header>
            <template #label>Number</template>
            <template #value>{{header.invoiceNumber}}</template>
        </base-header>
        <base-header>
            <template #label>Customer</template>
            <template #value>{{header.customerCompany}}</template>
        </base-header>
        <base-header>
            <template #label>Salesman</template>
            <template #value>{{header.salesmanName}}</template>
        </base-header>
        <base-header>
            <template #label>Order Date</template>
            <template #value>{{header.orderDate}}</template>
        </base-header>
    </base-card>
    <base-button :isLink="true" :link="`/orderDetail/${headerId}/form/0`">
        <i class="fas fa-plus"></i>
        <span>Insert Detail</span>
    </base-button>
    <base-button @click="printInvoice(headerId)" >
        <i class="fas fa-download"></i>
        <span>Download Invoice</span>
    </base-button>
    <base-table :headers="headers">
        <order-detail-row v-for="row in grid" 
            :key="row.productId"
            :id="row.productId"
            :product="row.product"
            :price="row.price"
            :quantity="row.quantity"
            :discount="row.discount"
            :totalPrice="row.totalPrice"
            :headerId="headerId"
        ></order-detail-row>
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

import OrderDetailRow from './OrderDetailRow.vue';
import useStore from '../../store/orderDetail/orderDetail-store.js';
import usePagedTable from '../../hooks/paged-table.js';

const props = defineProps(['headerId']);
const store = useStore();
const headers = ref(['Action', 'Product', 'Price Per Unit', 'Quantity', 'Discount', 'Total']);
const {grid, page, totalPages} = storeToRefs(store);
let header = ref({});

const {selectPage, firstPage, lastPage} = usePagedTable(store, async () => {
    store.headerId = props.headerId;
    const response = await store.findHeader(props.headerId);
    header.value = response;
});

const printInvoice = invoiceNumber => {
    location.href = `http://localhost:7070/api/order/generateInvoice/${invoiceNumber}`;
}
</script>