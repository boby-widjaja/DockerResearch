<template>
    <base-dialog :title="'Delete Delivery'" :closeDialog="closeDialog">
        <p>Are you sure you want to delete this delivery?</p>
        <base-warning-note>
            <p>
                Delivery that has so many order dependent on it will not be able to be deleted.
            </p>
            <p>
                Make sure that this delivery doesn't has any order(s) in it.
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
import useStore from '../../store/delivery/delivery-store.js';

const props = defineProps(['id']);
const router = useRouter();
const store = useStore();
const closeDialog = () => router.push('/delivery');
const deleteSelected = async() => {
    const {status, data} = await store.delete(props.id);
    if(status === 409){
        router.push({
            name: 'deliveryConflict', 
            params: {
                message: data,
                closeLink: '/delivery'
            }
        });
    } else {
        closeDialog();
    }
};
</script>