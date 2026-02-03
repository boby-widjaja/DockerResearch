import actions from './actions.js';
import {defineStore} from 'pinia';

const useStore = defineStore('orderDetailStore', {
    state(){
        return{
            grid: [],
            page: 1,
            totalPages:null,
            headerId:null,
            productDropdown:[]
        }
    },
    actions
});

export default useStore;