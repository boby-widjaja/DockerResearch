<template>
    <base-dialog :title="'Salesman Detail Form'" :closeDialog="closeDialog">
        <input type="hidden" v-model="input.salesmanEmployeeNumber">
        <base-dropdown-input
            :label="'region *'"
            :id="'region'" 
            v-model="input.regionId" 
            :validation="validationMessages.regionId"
            :placeholder="'No Region'"
            :options="regionDropdown">
        </base-dropdown-input>
        <base-button :event="submitForm">
            <i class="fas fa-save"></i>
            <span>save</span>
        </base-button>
    </base-dialog>
</template>

<script setup>
import useStore from '../../store/salesmanDetail/salesmanDetail-store.js';
import useUpsertForm from '../../hooks/upsert-form.js';
import { ref } from 'vue';

const props = defineProps(['headerId']);
const store = useStore();
const regionDropdown = ref([]);

const callback = async() => {
    await store.getRegionDropdown(props.headerId);
    regionDropdown.value = store.regionDropdown;
}

const {input, validationMessages, submitForm, closeDialog} = 
    useUpsertForm({id: 0, store, closePath:`/salesmanDetail/${props.headerId}`, callback, insertValue: {salesmanEmployeeNumber: props.headerId}});
</script>