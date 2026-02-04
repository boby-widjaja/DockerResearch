import actions from './actions.js';
import {defineStore} from 'pinia';

const useStore = defineStore('customerStore', {
    state(){
        return{
            grid: [],
            page: 1,
            totalPages: null,
            company: '',
            contact: ''
        }
    },
    actions
});

export default useStore;