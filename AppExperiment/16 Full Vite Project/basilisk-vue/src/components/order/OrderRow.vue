<template>
    <tr>
        <td>
            <base-update-delete
                :updateLink="`/order/form/${id}`"
                :deleteLink="`/order/delete/${id}`"
            ></base-update-delete>
        </td>
        <td>
            <base-detail-link :link="`/orderDetail/${id}`">{{id}}</base-detail-link>
        </td>
        <td>{{customer}}</td>
        <td>{{salesman}}</td>
        <td>{{orderDate}}</td>
        <td>{{delivery}}</td>
    </tr>
</template>

<script setup>
defineProps(['id', 'customer', 'salesman', 'orderDate', 'delivery']);
</script>