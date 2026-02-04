<template>
    <base-dialog :title="'Revoke Region'" :closeDialog="closeDialog">
        <p>Are you sure you want to revoke this region from this salesman?</p>
        <base-warning-note>
            <p>
                Revoke region will not delete master data region.
            </p>
            <p>
                Revoking region is simply releasing this region from salesman's territory.
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
import useStore from '../../store/salesmanDetail/salesmanDetail-store.js';

const props = defineProps(['headerId','id']);
const router = useRouter();
const store = useStore();
const closeDialog = () => router.push(`/salesmanDetail/${props.headerId}`);
const deleteSelected = async() => {
    await store.delete({employeeNumber: props.headerId, regionId: props.id});
    closeDialog();
};
</script>