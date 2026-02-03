<template>
    <tr>
        <td>
            <base-button :isLink="true" :link="`/salesmanDetail/${headerId}/delete/${id}`">revoke</base-button>
        </td>
        <td>{{city}}</td>
        <td>{{remark}}</td>
    </tr>
</template>

<script setup>
const props = defineProps(['id', 'city', 'remark', 'headerId']);
</script>