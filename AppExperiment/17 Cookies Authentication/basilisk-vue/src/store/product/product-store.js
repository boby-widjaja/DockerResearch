import actions from './actions.js';
import {defineStore} from 'pinia';

const useStore = defineStore('productStore', {
    state(){
        return{
            grid: [],
            page: 1,
            totalPages: null,
            name:'',
            categoryId:"",
            supplierId:"",
            categoryDropdown:[],
            supplierDropdown:[]
        }
    },
    actions
});

export default useStore;