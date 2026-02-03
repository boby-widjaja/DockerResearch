<template>
    <tr>
        <td>
            <base-button :isLink="true" :link="`/regionDetail/${headerId}/delete/${id}`">revoke</base-button>
        </td>
        <td>{{id}}</td>
        <td>{{fullName}}</td>
        <td>{{level}}</td>
        <td>{{superior}}</td>
    </tr>
</template>

<script setup>
const props = defineProps(['id', 'fullName', 'level', 'superior', 'headerId']);
</script>