import actions from './actions.js';
import {defineStore} from 'pinia';

const useStore = defineStore('orderStore', {
    state(){
        return{
            grid: [],
            page: 1,
            totalPages: null,
            invoiceNumber: '',
            customerId:'',
            employeeNumber:'',
            deliveryId:'',
            orderDate:'',
            customerDropdown:[],
            salesmanDropdown:[],
            deliveryDropdown:[]
        }
    },
    actions
});

export default useStore;