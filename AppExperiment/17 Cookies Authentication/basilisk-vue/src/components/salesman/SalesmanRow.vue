<template>
    <tr>
        <td>
            <base-update-delete
                :updateLink="`/salesman/form/${id}`"
                :deleteLink="`/salesman/delete/${id}`"
            ></base-update-delete>
        </td>
        <td>
            <base-detail-link :link="`/salesmanDetail/${id}`">{{id}}</base-detail-link>
        </td>
        <td>{{fullName}}</td>
        <td>{{level}}</td>
        <td>{{superior}}</td>
    </tr>
</template>

<script setup>
defineProps(['id', 'fullName', 'level', 'superior']);
</script>