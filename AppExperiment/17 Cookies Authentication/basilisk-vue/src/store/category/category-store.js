import actions from './actions.js';
import {defineStore} from 'pinia';

const useStore = defineStore('categoryStore', {
    state(){
        return{
            grid: [],
            page: 1,
            totalPages: null,
            name: ''
        }
    },
    actions
});

export default useStore;