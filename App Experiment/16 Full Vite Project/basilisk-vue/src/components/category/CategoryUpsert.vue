<template>
    <base-dialog :title="'Category Form'" :closeDialog="closeDialog">
        <input type="hidden" v-model="input.id">
        <base-text-input 
            :label="'name *'" 
            :id="'category-name'" 
            v-model="input.name" 
            :validation="validationMessages.name">
        </base-text-input>
        <base-text-area 
            :label="'description'" 
            id="'category-description'" 
            v-model="input.description"
            :validation="validationMessages.description">
        </base-text-area>
        <base-button :event="submitForm">
            <i class="fas fa-save"></i>
            <span>save</span>
        </base-button>
    </base-dialog>
</template>

<script setup>
import useStore from '../../store/category/category-store.js';
import useUpsertForm from '../../hooks/upsert-form.js';

const props = defineProps(['id']);
const store = useStore();
const id = props.id;
const {input, validationMessages, submitForm, closeDialog} = 
    useUpsertForm({id, store, closePath: '/category'});
</script>