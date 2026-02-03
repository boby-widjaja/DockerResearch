import actions from './actions.js';
import {defineStore} from 'pinia';

const useStore = defineStore('salesmanStore', {
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
            superiorDropdown:[]
        }
    },
    actions
});

export default useStore;