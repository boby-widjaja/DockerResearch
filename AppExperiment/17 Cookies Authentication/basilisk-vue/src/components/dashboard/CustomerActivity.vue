<template>
    <base-diagram-card class="customer-activity" :title="'Customer Activity'">
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
            <apexchart height="400" type="bar" :options="optionsRef" :series="seriesRef"></apexchart>
        </div>
    </base-diagram-card>
</template>

<script setup>
import {ref, onBeforeMount} from 'vue';
import {storeToRefs} from 'pinia';
import {useBarOptions} from '../../hooks/diagram-options.js';
import useStore from '../../store/dashboard/dashboard-store.js';
const store = useStore();
let barOptions = useBarOptions({});
const optionsRef = ref(barOptions.options);
const seriesRef = ref(barOptions.series);
const {orderYearDropdown, customerDropdown} = storeToRefs(store);
const selectedYear = ref(null);
const selectedCustomer = ref(null);

const updateFilter = () => {
    store.setCustomerActivityFilter({
        year: selectedYear.value,
        customerId: selectedCustomer.value
    });
}

const renderGraph = async() => {
    await store.refreshCustomerActivity();
    const data = store.customerActivity;
    const {options, series} = useBarOptions({
        categories: ["January","February","March","April","May","June","July","August","September","October","November","December"],
        data
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
.customer-activity {
    grid-column: 2 / 3;
    grid-row: 3 / 4;
}
</style>