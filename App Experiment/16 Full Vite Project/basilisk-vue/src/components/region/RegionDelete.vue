<template>
    <base-dialog :title="'Delete Region'" :closeDialog="closeDialog">
        <p>Are you sure you want to delete this region?</p>
        <base-warning-note>
            <p>
                Deleting this region will remove all relation of this region to all of it salesmen.
            </p>
            <p>
                Salesmen data will be remain in the application, only the relation with the region is removed.
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
import useStore from '../../store/region/region-store.js';

const props = defineProps(['id']);
const router = useRouter();
const store = useStore();
const closeDialog = () => router.push('/region');
const deleteSelected = async() => {
    await store.delete(props.id);
    closeDialog();
};
</script>