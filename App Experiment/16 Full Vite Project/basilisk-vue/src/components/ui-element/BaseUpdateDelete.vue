<template>
    <div class="base-update-delete">
        <router-link :to="updateLink">
            <i class="fas fa-edit"></i>
        </router-link>
        <router-link :to="deleteLink">
            <i class="fas fa-trash-alt"></i>
        </router-link>
    </div>
</template>

<script setup>
const props = defineProps(['updateLink', 'deleteLink']);
</script>

<style scoped>
.base-update-delete{
    display: flex;
}
.base-update-delete a{
    color:white;
    flex-shrink: 0;
    height: 30px;
    width: 30px;
    line-height: 30px;
    text-align: center;
    font-size: 14px;
}
.base-update-delete a:first-child{
    background-color: #16a085;
    border-top-left-radius: 4px;
    border-bottom-left-radius: 4px;
}
.base-update-delete a:last-child{
    background-color: #e74c3c;
    border-top-right-radius: 4px;
    border-bottom-right-radius: 4px;
}
</style>