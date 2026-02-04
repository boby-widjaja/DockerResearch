import actions from './actions.js';
import {defineStore} from 'pinia';

const useStore = defineStore('salesmanDetailStore', {
    state(){
        return{
            grid: [],
            page: 1,
            totalPages: null,
            city: '',
            headerId:null,
            regionDropdown:[]
        }
    },
    actions
});

export default useStore;