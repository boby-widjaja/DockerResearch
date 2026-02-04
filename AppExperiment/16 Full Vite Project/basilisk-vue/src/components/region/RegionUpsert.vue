<template>
    <base-dialog :title="'Region Form'" :closeDialog="closeDialog">
        <input type="hidden" v-model="input.id">
        <base-text-input 
            :label="'city *'" 
            :id="'region-city'" 
            v-model="input.city" 
            :validation="validationMessages.city">
        </base-text-input>
        <base-text-area 
            :label="'remark'" 
            id="'region-remark'" 
            v-model="input.remark"
            :validation="validationMessages.remark">
        </base-text-area>
        <base-button :event="submitForm">
            <i class="fas fa-save"></i>
            <span>save</span>
        </base-button>
    </base-dialog>
</template>

<script setup>
import useStore from '../../store/region/region-store.js';
import useUpsertForm from '../../hooks/upsert-form.js';

const props = defineProps(['id']);
const store = useStore();
const id = props.id;
const {input, validationMessages, submitForm, closeDialog} = 
    useUpsertForm({id, store, closePath: '/region'});
</script>