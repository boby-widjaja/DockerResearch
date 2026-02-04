import actions from './actions.js';
import {defineStore} from 'pinia';

const useStore = defineStore('deliveryStore', {
    state(){
        return{
            grid: [],
            page: 1,
            totalPages: null,
            company: ''
        }
    },
    actions
});

export default useStore;