<template>
    <base-dialog :title="'Delete Salesman'" :closeDialog="closeDialog">
        <p>Are you sure you want to delete this salesman?</p>
        <base-warning-note>
            <p>
                Salesman who has subordinate(s) or who has handle order(s) cannot be deleted.
            </p>
            <p>
                Make sure that this salesman has no subordinate or never handle any order.
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
import useStore from '../../store/salesman/salesman-store.js';

const props = defineProps(['id']);
const router = useRouter();
const store = useStore();
const closeDialog = () => router.push('/salesman');
const deleteSelected = async() => {
    const {status, data} = await store.delete(props.id);
    if(status === 409){
        router.push({
            name: 'salesmanConflict', 
            params: {
                message: data,
                closeLink: '/salesman'
            }
        });
    } else {
        closeDialog();
    }
};
</script>