<template>
    <tr>
        <td>
            <base-update-delete
                :updateLink="`/supplier/form/${id}`"
                :deleteLink="`/supplier/delete/${id}`"
            ></base-update-delete>
        </td>
        <td>{{company}}</td>
        <td>{{contact}}</td>
        <td>{{jobTitle}}</td>
    </tr>
</template>

<script setup>
defineProps(['id', 'company', 'contact', 'jobTitle']);
</script>