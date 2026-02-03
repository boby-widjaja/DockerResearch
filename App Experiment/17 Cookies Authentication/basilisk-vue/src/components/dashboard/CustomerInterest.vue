<template>
    <base-diagram-card class="customer-interest" :title="'Customer Interest'">
        <div class="filter-container">
            <div>
                <span>Order Year : </span>
                <select class="order-year" @change="changeYear" v-model="selectedYear">
                    <option v-for="option in orderYearDropdown" :key="option.value" :value="option.value">{{option.text}}</option>
                </select>
            </div>
            <div>
                <span>Customer : </span>
                <select class="customer-id" @change="changeCustomer" v-model="selectedCustomer">
                    <option v-for="option in customerDropdown" :key="option.value" :value="option.value">{{option.text}}</option>
                </select>
            </div>
        </div>
        <div class="chart">
            <apexchart height="430" type="radar" :options="optionsRef" :series="seriesRef"></apexchart>
        </div>
    </base-diagram-card>
</template>

<script setup>
import {ref, onBeforeMount} from 'vue';
import {storeToRefs} from 'pinia';
import {useRadarOptions} from '../../hooks/diagram-options.js';
import useStore from '../../store/dashboard/dashboard-store.js';
const store = useStore();
let radarOptions = useRadarOptions({});
const optionsRef = ref(radarOptions.options);
const seriesRef = ref(radarOptions.series);
const {orderYearDropdown, customerDropdown} = storeToRefs(store);
const selectedYear = ref(null);
const selectedCustomer = ref(null);

const updateFilter = () => {
    store.setCustomerInterestFilter({
        year: selectedYear.value,
        customerId: selectedCustomer.value
    });
}

const renderGraph = async() => {
    await store.refreshCustomerInterest();
    const {categoryNames, totalQuantity} = store.customerInterest;
    const {options, series} = useRadarOptions({
        categories: categoryNames,
        name: 'Total Quantity',
        data: totalQuantity
    });
    optionsRef.value = options;
    seriesRef.value = series;
}

onBeforeMount(async() => {
    selectedYear.value = await store.getDefaultOrderYear();
    selectedCustomer.value = await store.getDefaultCustomer();
    updateFilter();
    renderGraph();
});

const changeYear = () => {
    updateFilter();
    renderGraph();
}

const changeCustomer = () => {
    updateFilter();
    renderGraph();
}
</script>

<style scoped>
.customer-interest {
    grid-column: 2 / 3;
    grid-row: 4 / 5;
}
</style>