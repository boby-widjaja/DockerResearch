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
    setCategoryDropdown(payload){
        this.categoryDropdown = (payload === undefined) ? [] : payload;
    },
    setSupplierDropdown(payload){
        this.supplierDropdown = (payload === undefined) ? [] : payload;
    },
    async getCategoryDropdown(){
        const response = await axios.get('/product/categoryDropdown');
        this.setCategoryDropdown(response.data);
    },
    async getSupplierDropdown(){
        const response = await axios.get('/product/supplierDropdown');
        this.setSupplierDropdown(response.data);
    },
    async refreshGrid(){
        const {page, name, categoryId, supplierId} = this;
        Swal.fire({
            title: 'Loading...',
            text: 'Please wait while we process the data.',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });
        const response = await axios.get(`/product?page=${page}&name=${name}&categoryId=${categoryId}&supplierId=${supplierId}`);
        await this.getCategoryDropdown();
        await this.getSupplierDropdown();
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
        const response = await axios[method](`/product`, payload, {
                headers:{'Content-Type':'multipart/form-data'}
            }).then(response => {
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
        const response = await axios.delete(`/product/${id}`);
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
        const response = await axios.get(`/product/${id}`);
        Swal.close();
        return response.data;
    }
}