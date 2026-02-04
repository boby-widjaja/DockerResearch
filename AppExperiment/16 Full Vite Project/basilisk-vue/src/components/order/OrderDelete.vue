<template>
    <base-dialog :title="'Delete Order'" :closeDialog="closeDialog">
        <p>Are you sure you want to delete this order?</p>
        <base-warning-note>
            <p>
                Removing order will also remove all its details permanently.
            </p>
            <p>
                The invoice number of the deleted order might be used again in the new order.
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
import useStore from '../../store/order/order-store.js';

const props = defineProps(['id']);
const router = useRouter();
const store = useStore();
const closeDialog = () => router.push('/order');
const deleteSelected = async() => {
    await store.delete(props.id);
    closeDialog();
};
</script>