<template>
    <tr>
        <td>
            <base-update-delete
                :updateLink="`/region/form/${id}`"
                :deleteLink="`/region/delete/${id}`"
            ></base-update-delete>
        </td>
        <td>
            <base-detail-link :link="`/regionDetail/${id}`">{{city}}</base-detail-link>
        </td>
        <td>{{remark}}</td>
    </tr>
</template>

<script setup>
defineProps(['id', 'city', 'remark']);
</script>