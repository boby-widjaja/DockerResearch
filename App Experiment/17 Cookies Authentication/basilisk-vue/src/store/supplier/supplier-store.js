import actions from './actions.js';
import {defineStore} from 'pinia';

const useStore = defineStore('supplierStore', {
    state(){
        return{
            grid: [],
            page: 1,
            totalPages: null,
            company: '',
            contact: '',
            jobTitle: ''
        }
    },
    actions
});

export default useStore;