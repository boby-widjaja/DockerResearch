import axios from "axios";
import Swal from 'sweetalert2';

export default{
    setGrid(payload){
        this.grid = (payload === undefined) ? [] : payload;
    },
    setPage(payload){
        this.page = (payload === undefined) ? 1 : payload;
    },
    setTotalPages(payload){
        this.totalPages = (payload === undefined) ? 0 : payload;
    },
    setCustomerDropdown(payload){
        this.customerDropdown = (payload === undefined) ? [] : payload;
    },
    setSalesmanDropdown(payload){
        this.salesmanDropdown = (payload === undefined) ? [] : payload;
    },
    setDeliveryDropdown(payload){
        this.deliveryDropdown = (payload === undefined) ? [] : payload;
    },
    async getCustomerDropdown(){
        const response = await axios.get('/order/customerDropdown');
        this.setCustomerDropdown(response.data);
    },
    async getSalesmanDropdown(){
        const response = await axios.get('/order/salesmanDropdown');
        this.setSalesmanDropdown(response.data);
    },
    async getDeliveryDropdown(){
        const response = await axios.get('/order/deliveryDropdown');
        this.setDeliveryDropdown(response.data);
    },
    async refreshGrid(){
        const {page, invoiceNumber, customerId, employeeNumber, deliveryId, orderDate} = this;
        Swal.fire({
            title: 'Loading...',
            text: 'Please wait while we process the data.',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });
        const response = await axios.get(`/order?page=${page}&invoiceNumber=${invoiceNumber}&customerId=${customerId}&employeeNumber=${employeeNumber}&deliveryId=${deliveryId}&orderDate=${orderDate}`);
        await this.getCustomerDropdown();
        await this.getSalesmanDropdown();
        await this.getDeliveryDropdown();
        Swal.close();
        const {grid, totalPages} = response.data;
        this.setGrid(grid);
        this.setTotalPages(totalPages);
    },
    async upsert(parameters){
        const {method, payload} = parameters;
        Swal.fire({
            title: 'Loading...',
            text: 'Please wait while we update the data.',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });
        const response = await axios[method](`/order`, payload).then(response => {
            Swal.close();
            this.refreshGrid();
            return response;
        });
        Swal.close();
        return response;
    },
    async delete(id){
        Swal.fire({
            title: 'Loading...',
            text: 'Please wait while we update the data.',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });
        const response = await axios.delete(`/order/${id}`);
        Swal.close();
        if(response.status === 200){
            this.refreshGrid();
        }
        return response;
    },
    async findOne(id){
        Swal.fire({
            title: 'Loading...',
            text: 'Please wait while we process the data.',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });
        const response = await axios.get(`/order/${id}`);
        Swal.close();
        return response.data;
    },
    async findCustomer(id){
        Swal.fire({
            title: 'Loading...',
            text: 'Please wait while we process the data.',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });
        const response = await axios.get(`/customer/${id}`);
        Swal.close();
        return response.data;
    }
}