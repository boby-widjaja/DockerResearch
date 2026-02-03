<template>
    <base-dialog :title="'Order Detail Form'" :closeDialog="closeDialog">
        <input type="hidden" v-model="input.regionId">
        <base-dropdown-input
            :label="'salesman *'" 
            :id="'salesman'" 
            v-model="input.salesmanEmployeeNumber" 
            :validation="validationMessages.salesmanEmployeeNumber"
            :placeholder="'No Salesman'"
            :options="salesmanDropdown">
        </base-dropdown-input>
        <base-button :event="submitForm">
            <i class="fas fa-save"></i>
            <span>save</span>
        </base-button>
    </base-dialog>
</template>

<script setup>
import useStore from '../../store/regionDetail/regionDetail-store.js';
import useUpsertForm from '../../hooks/upsert-form.js';
import { ref } from 'vue';

const props = defineProps(['headerId']);
const store = useStore();
const salesmanDropdown = ref([]);

const callback = async() => {
    await store.getSalesmanDropdown(props.headerId);
    salesmanDropdown.value = store.salesmanDropdown;
}

const {input, validationMessages, submitForm, closeDialog} = 
    useUpsertForm({id: 0, store, closePath:`/regionDetail/${props.headerId}`, callback, insertValue: {regionId: props.headerId}});
</script>