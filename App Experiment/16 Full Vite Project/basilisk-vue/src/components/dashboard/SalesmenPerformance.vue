<template>
    <base-diagram-card class="salesmen-performance" :title="'Salesmen Performance'">
        <div class="filter-container">
            <div>
                <span>Order Year : </span>
                <select class="order-year" @change="changeYear" v-model="selectedYear">
                    <option v-for="option in orderYearDropdown" :key="option.value" :value="option.value">{{option.text}}</option>
                </select>
            </div>
            <div>
                <span>Salesman : </span>
                <select class="salesman-employee-number" @change="changeSalesman" v-model="selectedSalesmen">
                    <option v-for="option in salesmanDropdown" :key="option.value" :value="option.value">{{option.text}}</option>
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
const {orderYearDropdown, salesmanDropdown} = storeToRefs(store);
const selectedYear = ref(null);
const selectedSalesmen = ref(null);

const updateFilter = () => {
    store.setSalesmenPerformanceFilter({
        year: selectedYear.value,
        employeeNumber: selectedSalesmen.value
    });
}

const renderGraph = async() => {
    await store.refreshSalesmenPerformance();
    const data = store.salesmenPerformance;
    const {options, series} = useBarOptions({
        categories: ["January","February","March","April","May","June","July","August","September","October","November","December"],
        data
    });
    optionsRef.value = options;
    seriesRef.value = series;
}

onBeforeMount(async() => {
    selectedYear.value = await store.getDefaultOrderYear();
    selectedSalesmen.value = await store.getDefaultSalesman();
    updateFilter();
    renderGraph();
});

const changeYear = () => {
    updateFilter();
    renderGraph();
}

const changeSalesman = () => {
    updateFilter();
    renderGraph();
}

</script>

<style scoped>
.salesmen-performance {
    grid-column: 2 / 3;
    grid-row: 2 / 3;
}
</style>