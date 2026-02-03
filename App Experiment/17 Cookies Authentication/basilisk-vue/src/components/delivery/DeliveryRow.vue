<template>
    <tr>
        <td>
            <base-update-delete
                :updateLink="`/delivery/form/${id}`"
                :deleteLink="`/delivery/delete/${id}`"
            ></base-update-delete>
        </td>
        <td>{{company}}</td>
        <td>{{phone}}</td>
        <td>{{formattedCost}}</td>
    </tr>
</template>

<script setup>
import { computed } from 'vue';
const props = defineProps(['id', 'company', 'phone', 'cost']);
const formattedCost = computed(() => {
    return new Intl.NumberFormat('id-ID', {
        style: 'currency',
        currency: 'IDR'
    }).format(props.cost);
})
</script>