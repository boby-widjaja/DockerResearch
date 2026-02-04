<template>
    <nav>
        <div class="logo-container">
            <base-logo></base-logo>
        </div>
        <div class="menu-container">
            <menu-list v-for="menu in menus" 
                :key="menu.name"
                :name="menu.name"
                :icon="menu.icon"
                :link="menu.link"
                :auth="menu.auth"
            ></menu-list>
        </div>
    </nav>
</template>

<script setup>
import MenuList from './MenuList.vue';
import {ref} from 'vue';

const menus = ref([
    {
        name: 'Dashboard', 
        icon: 'fas fa-chart-pie', 
        link: '/dashboard',
        auth: ['Administrator', 'Salesman', 'Finance']
    },
    {
        name: 'Category', 
        icon: 'fas fa-th', 
        link: '/category',
        auth: ['Administrator', 'Salesman', 'Finance']
    },
    {
        name: 'Supplier', 
        icon: 'fab fa-amazon', 
        link: '/supplier',
        auth: ['Administrator']
    },
    {
        name: 'Product', 
        icon: 'fas fa-box-open', 
        link: '/product',
        auth: ['Administrator', 'Salesman', 'Finance']
    },
    {
        name: 'Region', 
        icon: 'fas fa-map-marked-alt', 
        link: '/region',
        auth: ['Administrator', 'Salesman', 'Finance']
    },
    {
        name: 'Customer', 
        icon: 'fas fa-users', 
        link: '/customer',
        auth: ['Administrator', 'Salesman']
    },
    {
        name: 'Delivery', 
        icon: 'fas fa-truck-moving', 
        link: '/delivery',
        auth: ['Administrator']
    },
    {
        name: 'Salesman', 
        icon: 'fas fa-user-tie', 
        link: '/salesman',
        auth: ['Administrator', 'Salesman', 'Finance']
    },
    {
        name: 'Order', 
        icon: 'fas fa-file-invoice-dollar', 
        link: '/order',
        auth: ['Administrator', 'Finance']
    },
])
</script>

<style scoped>
nav{
    grid-area: navigation;
    background-color: white;
}
.logo-container{
    display: flex;
    align-items:center;
    justify-content: center;
    height: 60px;
}
</style>