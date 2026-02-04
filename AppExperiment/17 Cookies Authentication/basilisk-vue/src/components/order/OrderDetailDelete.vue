<template>
    <base-dialog :title="'Delete Order Detail'" :closeDialog="closeDialog">
        <p>Are you sure you want to delete this order detail?</p>
        <base-warning-note>
            <p>
                Changing the detail will update its invoice.
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
import useStore from '../../store/orderDetail/orderDetail-store.js';

const props = defineProps(['id', 'headerId']);
const router = useRouter();
const store = useStore();
const closeDialog = () => router.push(`/orderDetail/${props.headerId}`);
const deleteSelected = async() => {
    await store.delete({invoiceNumber:props.headerId, productId:props.id});
    closeDialog();
};
</script>
