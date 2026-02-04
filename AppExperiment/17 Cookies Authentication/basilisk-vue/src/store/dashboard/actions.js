import axios from "axios";

export default{
    setAnnualIncome(payload){
        this.annualIncome = (payload === undefined) ? {} : payload;
    },
    setAnnualIncomeFilter(payload){
        this.annualIncomeFilter = (payload === undefined) ? {} : payload;
    },
    setSalesmenComparison(payload){
        this.salesmenComparison = (payload === undefined) ? [] : payload;
    },
    setSalesmenComparisonFilter(payload){
        this.salesmenComparisonFilter = (payload === undefined) ? [] : payload;
    },
    setSalesmenPerformance(payload){
        this.salesmenPerformance = (payload === undefined) ? [] : payload;
    },
    setSalesmenPerformanceFilter(payload){
        this.salesmenPerformanceFilter = (payload === undefined) ? [] : payload;
    },
    setCategoryPopularity(payload){
        this.categoryPopularity = (payload === undefined) ? [] : payload;
    },
    setCategoryPopularityFilter(payload){
        this.categoryPopularityFilter = (payload === undefined) ? [] : payload;
    },
    setCustomerActivity(payload){
        this.customerActivity = (payload === undefined) ? [] : payload;
    },
    setCustomerActivityFilter(payload){
        this.customerActivityFilter = (payload === undefined) ? [] : payload;
    },
    setIncomeByRegion(payload){
        this.incomeByRegion = (payload === undefined) ? [] : payload;
    },
    setIncomeByRegionFilter(payload){
        this.incomeByRegionFilter = (payload === undefined) ? [] : payload;
    },
    setCustomerInterest(payload){
        this.customerInterest = (payload === undefined) ? [] : payload;
    },
    setCustomerInterestFilter(payload){
        this.customerInterestFilter = (payload === undefined) ? [] : payload;
    },
    setOrderYearDropdown(payload){
        this.orderYearDropdown = (payload === undefined) ? [] : payload;
    },
    setSalesmanDropdown(payload){
        this.salesmanDropdown = (payload === undefined) ? [] : payload;
    },
    setCustomerDropdown(payload){
        this.customerDropdown = (payload === undefined) ? [] : payload;
    },
    async getDefaultOrderYear(){
        const response = await axios.get('/dashboard/orderYearDropdown');
        return response.data[0].value;
    },
    async getDefaultSalesman(){
        const response = await axios.get('/dashboard/salesmanDropdown');
        return response.data[0].value;
    },
    async getDefaultCustomer(){
        const response = await axios.get('/dashboard/customerDropdown');
        return response.data[0].value;
    },
    async getOrderYearDropdown(){
        const response = await axios.get('/dashboard/orderYearDropdown');
        this.setOrderYearDropdown(response.data);
    },
    async getSalesmanDropdown(){
        const response = await axios.get('/dashboard/salesmanDropdown');
        this.setSalesmanDropdown(response.data);
    },
    async getCustomerDropdown(){
        const response = await axios.get('/dashboard/customerDropdown');
        this.setCustomerDropdown(response.data);
    },
    async refreshAnnualIncome(){
        const {annualIncomeFilter} = this;
        if(annualIncomeFilter !== null){
            const {year} = annualIncomeFilter;
            const response = await axios.get(`/dashboard/annualIncome/${year}`);
            this.setAnnualIncome(response.data);
        }
    },
    async refreshSalesmenComparison(){
        const {salesmenComparisonFilter} = this;
        if(salesmenComparisonFilter !== null){
            const {year} = salesmenComparisonFilter;
            const response = await axios.get(`/dashboard/salesmenComparison/${year}`);
            this.setSalesmenComparison(response.data);
        }
    },
    async refreshIncomeByRegion(){
        const {incomeByRegionFilter} = this;
        if(incomeByRegionFilter !== null){
            const {year} = incomeByRegionFilter;
            const response = await axios.get(`/dashboard/incomeByRegion/${year}`);
            this.setIncomeByRegion(response.data);
        }
    },
    async refreshCategoryPopularity(){
        const {categoryPopularityFilter} = this;
        if(categoryPopularityFilter !== null){
            const {year} = categoryPopularityFilter;
            const response = await axios.get(`/dashboard/categoryPopularity/${year}`);
            this.setCategoryPopularity(response.data);
        }
    },
    async refreshSalesmenPerformance(){
        const {salesmenPerformanceFilter} = this;
        if(salesmenPerformanceFilter !== null){
            const {year, employeeNumber} = salesmenPerformanceFilter;
            const response = await axios.get(`/dashboard/salesmanPerformance?employeeNumber=${employeeNumber}&year=${year}`);
            this.setSalesmenPerformance(response.data);
        }
    },
    async refreshCustomerActivity(){
        const {customerActivityFilter} = this;
        if(customerActivityFilter !== null){
            const {year, customerId} = customerActivityFilter;
            const response = await axios.get(`/dashboard/customerActivity?customerId=${customerId}&year=${year}`);
            this.setCustomerActivity(response.data);
        }
    },
    async refreshCustomerInterest(){
        const {customerInterestFilter} = this;
        if(customerInterestFilter !== null){
            const {year, customerId} = customerInterestFilter;
            const response = await axios.get(`/dashboard/customerInterest?customerId=${customerId}&year=${year}`);
            this.setCustomerInterest(response.data);
        }
    },
}