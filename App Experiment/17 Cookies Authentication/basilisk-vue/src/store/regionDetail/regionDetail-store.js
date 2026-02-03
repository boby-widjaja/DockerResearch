import actions from './actions.js';
import {defineStore} from 'pinia';

const useStore = defineStore('regionDetailStore', {
    state(){
        return{
            grid: [],
            page: 1,
            totalPages: null,
            employeeNumber: '',
            name:'',
            employeeLevel:'',
            superiorName:'',
            employeeLevelDropdown:[],
            headerId:null,
            salesmanDropdown:[]
        }
    },
    actions
});

export default useStore;