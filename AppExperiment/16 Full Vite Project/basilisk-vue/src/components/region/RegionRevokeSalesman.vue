<template>
    <base-dialog :title="'Revoke Salesman'" :closeDialog="closeDialog">
        <p>Are you sure you want to revoke this salesman from this region?</p>
        <base-warning-note>
            <p>
                Revoke salesman will not delete master data salesman.
            </p>
            <p>
                Revoking salesman is simply releasing this salesman from the duty of this region.
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
import useStore from '../../store/regionDetail/regionDetail-store.js';

const props = defineProps(['headerId','id']);
const router = useRouter();
const store = useStore();
const closeDialog = () => router.push(`/regionDetail/${props.headerId}`);
const deleteSelected = async() => {
    await store.delete({regionId: props.headerId, employeeNumber: props.id});
    closeDialog();
};
</script>