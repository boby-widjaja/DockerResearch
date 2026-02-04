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
    setProductDropdown(payload){
        this.productDropdown = (payload === undefined) ? [] : payload;
    },
    async getProductDropdown(id){
        const response = await axios.get(`/orderDetail/productDropdown/${id}`);
        this.setProductDropdown(response.data);
    },
    async refreshGrid(){
        const {page, headerId} = this;
        Swal.fire({
            title: 'Loading...',
            text: 'Please wait while we process the data.',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });
        const response = await axios.get(`/orderDetail?invoiceNumber=${headerId}&page=${page}`);
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
        const response = await axios[method](`/orderDetail`, payload).then(response => {
            Swal.close();
            this.refreshGrid();
            return response;
        });
        Swal.close();
        return response;
    },
    async delete({invoiceNumber, productId}){
        Swal.fire({
            title: 'Loading...',
            text: 'Please wait while we update the data.',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });
        const response = await axios.delete(`/orderDetail/${invoiceNumber}/${productId}`);
        Swal.close();
        if(response.status === 200){
            this.refreshGrid();
        }
        return response;
    },
    async findHeader(id){
        Swal.fire({
            title: 'Loading...',
            text: 'Please wait while we process the data.',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });
        const response = await axios.get(`/order/${id}`);
        const customer = await axios.get(`/customer/${response.data.customerId}`);
        const salesman = await axios.get(`/salesman/${response.data.salesEmployeeNumber}`);
        const header = {
            invoiceNumber: response.data.invoiceNumber,
            customerCompany: customer.data.companyName,
            salesmanName: `${salesman.data.firstName} ${salesman.data.lastName}`,
            orderDate: response.data.orderDate
        };
        Swal.close();
        return header;
    },
    async findOne({invoiceNumber, productId}){
        Swal.fire({
            title: 'Loading...',
            text: 'Please wait while we process the data.',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });
        const response = await axios.get(`/orderDetail/${invoiceNumber}/${productId}`);
        Swal.close();
        console.log()
        return response.data;
    }
}