import actions from './actions.js';
import {defineStore} from 'pinia';

const useStore = defineStore('dashboardStore', {
    state(){
        return{
            annualIncome:{},
            annualIncomeFilter:null,
            salesmenComparison:{},
            salesmenComparisonFilter:null,
            salesmenPerformance:{},
            salesmenPerformanceFilter:null,
            categoryPopularity:{},
            categoryPopularityFilter:null,
            customerActivity:{},
            customerActivityFilter:null,
            incomeByRegion:{},
            incomeByRegionFilter:null,
            customerInterest:{},
            customerInterestFilter:null,
            orderYearDropdown:[],
            salesmanDropdown:[],
            customerDropdown:[],
        }
    },
    actions
});

export default useStore;