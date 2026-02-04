<template>
    <base-dialog :title="'Order Form'" :closeDialog="closeDialog">
        <base-dropdown-input
            :label="'customer *'" 
            :id="'order-customer'" 
            v-model="input.customerId" 
            :validation="validationMessages.customerId"
            :placeholder="'No Customer'"
            :options="customerDropdown"
            @input="autoFillDestination(input.customerId)">
        </base-dropdown-input>
        <base-dropdown-input
            :label="'salesman *'" 
            :id="'order-salesman'" 
            v-model="input.salesEmployeeNumber" 
            :validation="validationMessages.salesEmployeeNumber"
            :placeholder="'No Salesman'"
            :options="salesmanDropdown">
        </base-dropdown-input>
        <base-date-input 
            :label="'order date *'" 
            :id="'order-date'" 
            v-model="input.orderDate" 
            :validation="validationMessages.orderDate">
        </base-date-input>
        <base-date-input 
            :label="'shipped date'" 
            :id="'shipped-date'" 
            v-model="input.shippedDate" 
            :validation="validationMessages.shippedDate">
        </base-date-input>
        <base-date-input 
            :label="'due date'" 
            :id="'due-date'" 
            v-model="input.dueDate" 
            :validation="validationMessages.dueDate">
        </base-date-input>
        <base-dropdown-input
            :label="'delivery *'" 
            :id="'order-delivery'" 
            v-model="input.deliveryId" 
            :validation="validationMessages.deliveryId"
            :placeholder="'No Delivery'"
            :options="deliveryDropdown">
        </base-dropdown-input>
        <base-text-area
            :label="'destination address *'" 
            id="'destination-address'" 
            v-model="input.destinationAddress"
            :validation="validationMessages.destinationAddress">
        </base-text-area>
        <base-text-input 
            :label="'destination city *'" 
            :id="'destination-city'" 
            v-model="input.destinationCity" 
            :validation="validationMessages.destinationCity">
        </base-text-input>
        <base-text-input 
            :label="'postal code *'" 
            :id="'destination-postal-code'" 
            v-model="input.destinationPostalCode" 
            :validation="validationMessages.destinationPostalCode">
        </base-text-input>
        <base-button :event="submitForm">
            <i class="fas fa-save"></i>
            <span>save</span>
        </base-button> 
    </base-dialog>
</template>

<script setup>
import useStore from '../../store/order/order-store.js';
import useUpsertForm from '../../hooks/upsert-form.js';
import { ref } from 'vue';

const props = defineProps(['id']);
const store = useStore();
const id = props.id;
const customerDropdown = ref([]);
const salesmanDropdown = ref([]);
const deliveryDropdown = ref([]);

const callback = async() => {
    await store.getCustomerDropdown();
    await store.getSalesmanDropdown();
    await store.getDeliveryDropdown();
    customerDropdown.value = store.customerDropdown;
    salesmanDropdown.value = store.salesmanDropdown;
    deliveryDropdown.value = store.deliveryDropdown;
}
const {input, validationMessages, submitForm, closeDialog} = 
    useUpsertForm({id, store, closePath: '/order', callback, nonGenerated: true});

const autoFillDestination = async(customerId) => {
    if(customerId !== "null"){
        const customer = await store.findCustomer(customerId);
        input.value.destinationAddress = customer.address;
        input.value.destinationCity = customer.city;
    }
}
</script>