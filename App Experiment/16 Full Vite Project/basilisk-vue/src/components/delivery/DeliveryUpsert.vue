<template>
    <base-dialog :title="'Delivery Form'" :closeDialog="closeDialog">
        <input type="hidden" v-model="input.id">
        <base-text-input 
            :label="'company *'" 
            :id="'delivery-company'" 
            v-model="input.companyName" 
            :validation="validationMessages.companyName">
        </base-text-input>
        <base-text-input 
            :label="'phone'" 
            :id="'delivery-phone'" 
            v-model="input.phone" 
            :validation="validationMessages.phone">
        </base-text-input>
        <base-decimal-input 
            :label="'cost *'" 
            :id="'delivery-cost'" 
            v-model="input.cost"
            :validation="validationMessages.cost">
        </base-decimal-input>
        <base-button :event="submitForm">
            <i class="fas fa-save"></i>
            <span>save</span>
        </base-button>
    </base-dialog>
</template>

<script setup>
import useStore from '../../store/delivery/delivery-store.js';
import useUpsertForm from '../../hooks/upsert-form.js';

const props = defineProps(['id']);
const store = useStore();
const id = props.id;
const {input, validationMessages, submitForm, closeDialog} = 
    useUpsertForm({id, store, closePath: '/delivery'});
</script>