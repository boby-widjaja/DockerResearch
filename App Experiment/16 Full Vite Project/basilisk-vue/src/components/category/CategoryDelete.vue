<template>
    <base-dialog :title="'Delete Category'" :closeDialog="closeDialog">
        <p>Are you sure you want to delete this category?</p>
        <base-warning-note>
            <p>
                Category that has so many product dependent on it will not be able to be deleted.
            </p>
            <p>
                Make sure that this category doesn't has any product(s) in it.
            </p>
        </base-warning-note>
        <base-button :event="deleteSelected">
            <i class="fas fa-trash-alt"></i>
            <span>delete</span>
        </base-button>
    </base-dialog>
</template>

<script setup>
import { useRouter } from 'vue-router';
import useStore from '../../store/category/category-store.js';

const props = defineProps(['id']);
const router = useRouter();
const store = useStore();
const closeDialog = () => router.push('/category');
const deleteSelected = async() => {
    const {status, data} = await store.delete(props.id);
    if(status === 409){
        router.push({
            name: 'categoryConflict', 
            params: {
                message: data,
                closeLink: '/category'
            }
        });
    } else {
        closeDialog();
    }
};
</script>