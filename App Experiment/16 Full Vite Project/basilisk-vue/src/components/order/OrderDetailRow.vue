<template>
    <tr>
        <td>
            <base-update-delete
                :updateLink="`/orderDetail/${headerId}/form/${id}`"
                :deleteLink="`/orderDetail/${headerId}/delete/${id}`"
            ></base-update-delete>
        </td>
        <td>{{product}}</td>
        <td>{{formattedPrice}}</td>
        <td>{{quantity}}</td>
        <td>{{formattedDiscount}}</td>
        <td>{{formattedTotalPrice}}</td>
    </tr>
</template>

<script setup>
import { computed } from 'vue';
const props = defineProps(['id', 'product', 'price', 'quantity', 'discount', 'totalPrice', 'headerId']);
const formattedDiscount = computed(() => {
    return new Intl.NumberFormat('id-ID', {
        style:'percent'
    }).format(props.discount / 100);
});
const formattedPrice = computed(() => {
    return new Intl.NumberFormat('id-ID', {
        style: 'currency',
        currency: 'IDR'
    }).format(props.price);
});
const formattedTotalPrice = computed(() => {
    return new Intl.NumberFormat('id-ID', {
        style: 'currency',
        currency: 'IDR'
    }).format(props.totalPrice);
});
</script>