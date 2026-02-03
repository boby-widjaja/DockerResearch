<template>
    <base-dialog :title="'Delete Supplier'" :closeDialog="closeDialog">
        <p>Are you sure you want to delete this supplier?</p>
        <base-warning-note>
            <p>
                Deleting supplier will be proceed as soft delete.
            </p>
            <p>
                This mean supplier will not be shown in the application but will be remain in the database.
            </p>
            <p>
                This will not cause any conflict between data integrity.
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
import useStore from '../../store/supplier/supplier-store.js';

const props = defineProps(['id']);
const router = useRouter();
const store = useStore();
const closeDialog = () => router.push('/supplier');
const deleteSelected = async() => {
    await store.delete(props.id);
    closeDialog();
};
</script>