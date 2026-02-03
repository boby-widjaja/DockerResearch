<template>
    <base-diagram-card class="annual-income" :title="'Annual Income'">
        <div class="two-side">
            <div class="chart">
                <apexchart height="450" type="area" :options="optionsRef" :series="seriesRef"></apexchart>
            </div>
            <div class="analysis">
                <div>
                    <span>Order Year : </span>
                    <select class="order-year" @change="changeYear" v-model="selectedYear">
                        <option v-for="option in orderYearDropdown" :key="option.value" :value="option.value">{{option.text}}</option>
                    </select>
                </div>
                <p>
                    Data on the left-hand side shows our income in the year <span class="highlight">{{yearRef}}</span>.
                    The charts will show us the changes on every monthly period.
                </p>
                <p><span class="note">(Please hover your pointer to check the value on every month.)</span></p>
                <p>The total income in <span class="highlight">{{yearRef}}</span> is: <span class="highlight">{{totalIncomeRef}}</span>.</p>
                <p>
                    The fluctuation of our income compare to the previous year is <span class="highlight">{{fluctuationRef}}</span>.
                    <span class="note">(Positive value will show an increment, on the contrary the negative value will show us a decrement.)</span>
                </p>
                <p>The highest income lies on the period of <span class="highlight">{{highestPeriodRef}}</span>.</p>
                <p>The lowest income lies on the period of <span class="highlight">{{lowestPeriodRef}}</span>.</p>
            </div>
        </div>
    </base-diagram-card>
</template>

<script setup>
import {ref, onBeforeMount} from 'vue';
import {storeToRefs} from 'pinia';
import {useAreaOptions} from '../../hooks/diagram-options.js';
import useStore from '../../store/dashboard/dashboard-store.js';
const store = useStore();
let areaOptions = useAreaOptions({});
const optionsRef = ref(areaOptions.options);
const seriesRef = ref(areaOptions.series);
const yearRef = ref('');
const totalIncomeRef = ref('');
const fluctuationRef = ref('');
const highestPeriodRef = ref('');
const lowestPeriodRef = ref('');
const {orderYearDropdown} = storeToRefs(store);
const selectedYear = ref(null);

const updateFilter = () => {
    store.setAnnualIncomeFilter({year: selectedYear.value});
}

const renderGraph = async() => {
    await store.refreshAnnualIncome();
    const {incomes, year, totalIncome, fluctuation, highestPeriod, lowestPeriod} = store.annualIncome;
    const {options, series} = useAreaOptions({
        labels: ["January","February","March","April","May","June","July","August","September","October","November","December"],
        name: 'Incomes',
        data: incomes
    });
    optionsRef.value = options;
    seriesRef.value = series;
    yearRef.value = year;
    totalIncomeRef.value = totalIncome;
    fluctuationRef.value = fluctuation;
    highestPeriodRef.value = highestPeriod;
    lowestPeriodRef.value = lowestPeriod;
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
.annual-income{
    grid-column: 1 / 3;
    grid-row: 1 / 2;
}
</style>