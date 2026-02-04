<template>
    <tr>
        <td>
            <base-update-delete
                :updateLink="`/category/form/${id}`"
                :deleteLink="`/category/delete/${id}`"
            ></base-update-delete>
        </td>
        <td>{{name}}</td>
        <td>{{description}}</td>
    </tr>
</template>

<script setup>
defineProps(['id', 'name', 'description']);
</script>