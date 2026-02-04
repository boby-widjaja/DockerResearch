import Swal from 'sweetalert2';
import axiosAuth from '../../axios-instance/axios-auth';

export default{
    getUsername(){
        let username = localStorage.getItem('username');
        return !username ? 'anonymous' : username;
    },
    getRole(){
        let role =localStorage.getItem('role');
        return !role ? 'guest' : role;
    },
    setRoleDropdown(payload){
        this.roleDropdown = (payload === undefined) ? [] : payload;
    },
    async getRoleDropdown(){
        const response = await axiosAuth.get('/roleDropdown');
        this.setRoleDropdown(response.data);
    },
    async logout(){
        Swal.fire({
            title: 'Loading...',
            text: 'Please wait while we sign you out...',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });
        const response = await axiosAuth.post(`/logout`);
        localStorage.removeItem('username');
        localStorage.removeItem('role');
        Swal.close();
        return response;
    },
    async authenticating(parameters){
        const {inputUsername, inputPassword, isRememberMe} = parameters;
        const payload = {
            "username": inputUsername,
            "password": inputPassword,
            "subject": this.subject,
            "audience": this.audience
        };
        Swal.fire({
            title: 'Loading...',
            text: 'Please wait while we authenticate.',
            allowOutsideClick: false,
            didOpen: () => Swal.showLoading()
        });
        const response = await axiosAuth.post(`/authenticate`, payload).catch(async error => {
            Swal.close();
            const title = `Response ${error.status}`
            let text = 'Bad request from the client-side';
            text = error.status === 401 ? error.response.data : text; 
            await Swal.fire({
                icon: "error",
                title,
                text,
                allowOutsideClick: false,
                confirmButtonText: 'OK'
            });
            return error.response;
        });
        Swal.close();
        if(response.data !== undefined && response.status === 200){
            const {username, role} = response.data;
            localStorage.setItem('username', username);
            localStorage.setItem('role', role);
        }
        return response;
    },
    async register(payload){
        Swal.fire({
            title: 'Loading...',
            text: 'Please wait while we update the data.',
            allowOutsideClick: false,
            didOpen: () => Swal.showLoading()
        });
        const response = await axiosAuth.post('/register', payload).catch(async error => {
            Swal.close();
            const title = `Response ${error.status}`;
            await Swal.fire({
                icon: "error",
                title,
                text: 'Bad request from the client-side',
                allowOutsideClick: false,
                confirmButtonText: 'OK'
            });
            return error.response;
        })
        Swal.close();
        return response;
    },
    async changePassword(payload){
        Swal.fire({
            title: 'Loading...',
            text: 'Please wait while we update the data.',
            allowOutsideClick: false,
            didOpen: () => Swal.showLoading()
        });
        const response = await axiosAuth.patch('/changePassword', payload).then(response => {
            Swal.close();
            return response;
        });;
        Swal.close();
        return response;
    }
}