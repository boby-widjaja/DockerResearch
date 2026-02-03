<template>
    <base-diagram-card class="category-popularity" :title="'Category Popularity'">
        <div class="body">
            <div class="filter-container">
                <span>Order Year : </span>
                <select class="order-year" @change="changeYear" v-model="selectedYear">
                    <option v-for="option in orderYearDropdown" :key="option.value" :value="option.value">{{option.text}}</option>
                </select>
            </div>
            <div class="chart">
                <apexchart width="650" type="pie" :options="optionsRef" :series="seriesRef"></apexchart>
            </div>
        </div>
    </base-diagram-card>
</template>

<script setup>
import {ref, onBeforeMount} from 'vue';
import {storeToRefs} from 'pinia';
import {usePieOptions} from '../../hooks/diagram-options.js';
import useStore from '../../store/dashboard/dashboard-store.js';
const store = useStore();
let pieOptions = usePieOptions();
const optionsRef = ref(pieOptions);
const seriesRef = ref([]);
const {orderYearDropdown} = storeToRefs(store);
const selectedYear = ref(null);

const updateFilter = () => {
    store.setCategoryPopularityFilter({year: selectedYear.value});
}

const renderGraph = async() => {
    await store.refreshCategoryPopularity();
    const {categoryNames, totalQuantity} = store.categoryPopularity;
    optionsRef.value = usePieOptions(categoryNames);
    seriesRef.value = totalQuantity;
}

onBeforeMount(async() => {
    selectedYear.value = await store.getDefaultOrderYear();
    updateFilter();
    renderGraph();
});

const changeYear = (event) => {
    updateFilter();
    renderGraph();
}
</script>

<style scoped>
.category-popularity {
    grid-column: 1 / 2;
    grid-row: 4 / 5;
}
</style>