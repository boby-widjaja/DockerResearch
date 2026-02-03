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
    setEmployeeLevelDropdown(payload){
        this.employeeLevelDropdown = (payload === undefined) ? [] : payload;
    },
    setSalesmanDropdown(payload){
        this.salesmanDropdown = (payload === undefined) ? [] : payload;
    },
    async getEmployeeLevelDropdown(){
        const response = await axios.get('/salesman/employeeLevelDropdown');
        this.setEmployeeLevelDropdown(response.data);
    },
    async getSalesmanDropdown(id){
        const response = await axios.get(`/regionDetail/salesmanDropdown/${id}`);
        this.setSalesmanDropdown(response.data);
    },
    async refreshGrid(){
        const {page, employeeNumber, name, employeeLevel, superiorName, headerId} = this;
        Swal.fire({
            title: 'Loading...',
            text: 'Please wait while we process the data.',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });
        const response = await axios.get(`/regionDetail?id=${headerId}&page=${page}&employeeNumber=${employeeNumber}&name=${name}&employeeLevel=${employeeLevel}&superiorName=${superiorName}`);
        await this.getEmployeeLevelDropdown();
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
        const response = await axios[method](`/regionDetail`, payload).then(response => {
            Swal.close();
            this.refreshGrid();
            return response;
        });
        Swal.close();
        return response;
    },
    async delete({regionId, employeeNumber}){
        Swal.fire({
            title: 'Loading...',
            text: 'Please wait while we update the data.',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });
        const response = await axios.delete(`/regionDetail/regionId=${regionId}/employeeNumber=${employeeNumber}`);
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
        const response = await axios.get(`/region/${id}`);
        Swal.close();
        return response.data;
    }
}