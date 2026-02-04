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
    async refreshGrid(){
        const {page, company, contact} = this;
        Swal.fire({
            title: 'Loading...',
            text: 'Please wait while we process the data.',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });
        const response = await axios.get(`/customer?page=${page}&company=${company}&contact=${contact}`);
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
        const response = await axios[method](`/customer`, payload).then(response => {
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
        const response = await axios.delete(`/customer/${id}`);
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
        const response = await axios.get(`/customer/${id}`);
        Swal.close();
        return response.data;
    }
}