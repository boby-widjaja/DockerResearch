import actions from './actions.js';
import {defineStore} from 'pinia';

const useStore = defineStore('regionStore', {
    state(){
        return{
            grid: [],
            page: 1,
            totalPages: null,
            city: ''
        }
    },
    actions
});

export default useStore;