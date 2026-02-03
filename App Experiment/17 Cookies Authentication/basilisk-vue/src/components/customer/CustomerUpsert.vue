<template>
    <base-dialog :title="'Customer Form'" :closeDialog="closeDialog">
        <input type="hidden" v-model="input.id">
        <base-text-input 
            :label="'company name *'" 
            :id="'company-name'" 
            v-model="input.companyName" 
            :validation="validationMessages.companyName">
        </base-text-input>
        <base-text-input 
            :label="'contact person *'" 
            :id="'contact-person'" 
            v-model="input.contactPerson" 
            :validation="validationMessages.contactPerson">
        </base-text-input>
        <base-text-area 
            :label="'address'" 
            id="'supplier-address'" 
            v-model="input.address"
            :validation="validationMessages.address">
        </base-text-area>
        <base-text-input 
            :label="'city'" 
            :id="'supplier-city'" 
            v-model="input.city" 
            :validation="validationMessages.city">
        </base-text-input>
        <base-text-input 
            :label="'phone'" 
            :id="'supplier-phone'" 
            v-model="input.phone" 
            :validation="validationMessages.phone">
        </base-text-input>
        <base-text-input 
            :label="'email'" 
            :id="'supplier-email'" 
            v-model="input.email" 
            :validation="validationMessages.email">
        </base-text-input>
        <base-button :event="submitForm">
            <i class="fas fa-save"></i>
            <span>save</span>
        </base-button> 
    </base-dialog>
</template>

<script setup>
import useStore from '../../store/customer/customer-store.js';
import useUpsertForm from '../../hooks/upsert-form.js';

const props = defineProps(['id']);
const store = useStore();
const id = props.id;
const {input, validationMessages, submitForm, closeDialog} = 
    useUpsertForm({id, store, closePath: '/customer'});
</script>