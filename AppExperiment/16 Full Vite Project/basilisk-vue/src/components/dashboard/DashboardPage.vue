<template>
    <div class="container">
        <annual-income></annual-income>
        <salesmen-comparison></salesmen-comparison>
        <salesmen-performance></salesmen-performance>
        <income-by-region></income-by-region>
        <customer-activity></customer-activity>
        <category-popularity></category-popularity>
        <customer-interest></customer-interest>
    </div>
</template>

<script setup>
import AnnualIncome from './AnnualIncome.vue';
import SalesmenComparison from './SalesmenComparison.vue';
import SalesmenPerformance from './SalesmenPerformance.vue';
import IncomeByRegion from './IncomeByRegion.vue';
import CustomerActivity from './CustomerActivity.vue';
import CategoryPopularity from './CategoryPopularity.vue';
import CustomerInterest from './CustomerInterest.vue';

import {onBeforeMount} from 'vue';
import useStore from '../../store/dashboard/dashboard-store.js';
const store = useStore();

onBeforeMount(async() => {
    await store.getOrderYearDropdown();
    await store.getSalesmanDropdown();
    await store.getCustomerDropdown();
});

store.getOrderYearDropdown();
</script>

<style scoped>
.container{
    display: grid;
    grid-gap: 15px;
    grid-template-columns: 700px 1fr;
    grid-template-rows: repeat(4, 1fr);
}
</style>