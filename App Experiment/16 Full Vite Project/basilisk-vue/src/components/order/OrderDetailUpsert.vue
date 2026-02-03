<template>
    <base-dialog :title="'Order Detail Form'" :closeDialog="closeDialog">
        <input type="hidden" v-model="input.invoiceNumber">
        <base-dropdown-input v-if="!isUpdate"
            :label="'product *'" 
            :id="'order-detail-product'" 
            v-model="input.productId" 
            :validation="validationMessages.productId"
            :placeholder="'No Product'"
            :options="productDropdown">
        </base-dropdown-input>
        <div v-else>
            <base-text-input 
                :label="'product *'" 
                :id="'order-product-name'" 
                v-model="input.productName" 
                :isDisabled="true">
            </base-text-input>
            <input type="hidden" v-model="input.productId">
        </div>
        <base-number-input 
            :label="'quantity *'" 
            :id="'order-detail-quantity'" 
            v-model="input.quantity"
            :validation="validationMessages.quantity">
        </base-number-input>
        <base-decimal-input 
            :label="'discount *'" 
            :id="'order-detail-discount'" 
            v-model="input.discount"
            :validation="validationMessages.discount">
        </base-decimal-input>
        <base-button :event="submitForm">
            <i class="fas fa-save"></i>
            <span>save</span>
        </base-button>
    </base-dialog>
</template>

<script setup>
import useStore from '../../store/orderDetail/orderDetail-store.js';
import useUpsertForm from '../../hooks/upsert-form.js';
import { ref } from 'vue';

const props = defineProps(['headerId', 'id']);
const store = useStore();
const productId = props.id;
const invoiceNumber = props.headerId;
const isUpdate = ref(productId != 0);
const id = isUpdate.value ? {invoiceNumber, productId} : 0;
const productDropdown = ref([]);

const callback = async({invoiceNumber}) => {
    await store.getProductDropdown(invoiceNumber);
    productDropdown.value = store.productDropdown;
}

const {input, validationMessages, submitForm, closeDialog} = 
    useUpsertForm({id, store, closePath: `/orderDetail/${invoiceNumber}`, callback, insertValue: {invoiceNumber}});
</script>

