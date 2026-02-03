<template>
    <base-error-dialog :title="'409 Conflict'" :message="message" :closeLink="closeLink">
        <p>
            Trying to change data that conflict with data integrity.
        </p>
    </base-error-dialog>
</template>

<script setup>
const props = defineProps(['message', 'closeLink']);
</script>