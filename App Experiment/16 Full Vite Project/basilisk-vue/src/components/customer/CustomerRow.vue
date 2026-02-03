<template>
    <tr>
        <td>
            <base-update-delete
                :updateLink="`/customer/form/${id}`"
                :deleteLink="`/customer/delete/${id}`"
            ></base-update-delete>
        </td>
        <td>{{company}}</td>
        <td>{{contact}}</td>
        <td>{{address}}</td>
        <td>{{city}}</td>
    </tr>
</template>

<script setup>
defineProps(['id', 'company', 'contact', 'address', 'city']);
</script>