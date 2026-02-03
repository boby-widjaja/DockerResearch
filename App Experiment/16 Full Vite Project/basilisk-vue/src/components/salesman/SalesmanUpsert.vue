<template>
    <base-dialog :title="'Salesman Form'" :closeDialog="closeDialog">
        <base-text-input 
            :label="'employee number *'" 
            :id="'employee-number'" 
            v-model="input.employeeNumber" 
            :validation="validationMessages.employeeNumber"
            :isDisabled="props.id != 0">
        </base-text-input>
        <base-text-input 
            :label="'first name *'" 
            :id="'first-name'" 
            v-model="input.firstName" 
            :validation="validationMessages.firstName">
        </base-text-input>
        <base-text-input 
            :label="'last name'" 
            :id="'last-name'" 
            v-model="input.lastName" 
            :validation="validationMessages.lastName">
        </base-text-input>
        <base-dropdown-input
            :label="'employee level*'" 
            :id="'employee-level'" 
            v-model="input.level" 
            :validation="validationMessages.level"
            :placeholder="'No Employee Level'"
            :options="levelDropdown">
        </base-dropdown-input>
        <base-date-input 
            :label="'birth date *'" 
            :id="'birth-date'" 
            v-model="input.birthDate" 
            :validation="validationMessages.birthDate">
        </base-date-input>
        <base-date-input 
            :label="'hired date *'" 
            :id="'hired-date'" 
            v-model="input.hiredDate" 
            :validation="validationMessages.hiredDate">
        </base-date-input>
        <base-text-area
            :label="'address'" 
            id="'employee-address'" 
            v-model="input.address"
            :validation="validationMessages.address">
        </base-text-area>
        <base-text-input 
            :label="'city'" 
            :id="'employee-city'" 
            v-model="input.city" 
            :validation="validationMessages.city">
        </base-text-input>
        <base-dropdown-input
            :label="'superior'" 
            :id="'employee-superior'" 
            v-model="input.superiorEmployeeNumber" 
            :validation="validationMessages.superiorEmployeeNumber"
            :placeholder="'No Superior'"
            :options="superiorDropdown">
        </base-dropdown-input>
        <base-button :event="submitForm">
            <i class="fas fa-save"></i>
            <span>save</span>
        </base-button>
    </base-dialog>
</template>

<script setup>
import useStore from '../../store/salesman/salesman-store.js';
import useUpsertForm from '../../hooks/upsert-form.js';
import { ref } from 'vue';

const props = defineProps(['id']);
const store = useStore();
const id = props.id;
const levelDropdown = ref([]);
const superiorDropdown = ref([]);
const callback = async() => {
    await store.getEmployeeLevelDropdown();
    await store.getSuperiorDropdown();
    levelDropdown.value = store.employeeLevelDropdown;
    superiorDropdown.value = store.superiorDropdown;
}
const {input, validationMessages, submitForm, closeDialog} = 
    useUpsertForm({id, store, closePath: '/salesman', callback, nonGenerated: true});
</script>
