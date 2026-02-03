import { defineStore } from 'pinia';
import actions from './actions.js';

const useStore = defineStore('authStore', {
    state(){
        return{
            subject: "Vue Tryout",
            audience: "BasiliskWebUI",
            roleDropdown:[],
        }
    },
    actions
});

export default useStore;