<template>
    <base-card>
        <base-text-search 
            :placeholder="'Search By Name...'" 
            @input="searchBy($event, 'name')" 
            :value="name">
        </base-text-search>
        <base-dropdown-search
            :placeholder="'Search By Category...'"
            @input="searchBy($event, 'categoryId')"
            :value="categoryId"
            :options="categoryDropdown">
        </base-dropdown-search>
        <base-dropdown-search
            :placeholder="'Search By Supplier...'"
            @input="searchBy($event, 'supplierId')"
            :value="supplierId"
            :options="supplierDropdown">
        </base-dropdown-search>
    </base-card>
    <base-button :isLink="true" :link="'/product/form/0'">
        <i class="fas fa-plus"></i>
        <span>Insert</span>
    </base-button>
    <base-tile-container>
        <product-tile v-for="tile in grid"
            :key="tile.id"
            :id="tile.id"
            :name="tile.name"
            :supplier="tile.supplier"
            :category="tile.category"
            :price="tile.price"
            :imagePath="tile.imagePath"
        ></product-tile>
    </base-tile-container>
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
import { storeToRefs } from 'pinia';
import ProductTile from './ProductTile.vue';
import useStore from '../../store/product/product-store.js';
import usePagedTable from '../../hooks/paged-table.js';

const store = useStore();
const {grid, page, totalPages, name, categoryId, supplierId, categoryDropdown, supplierDropdown} = storeToRefs(store);
const {selectPage, firstPage, lastPage, searchBy} = usePagedTable(store);
</script>