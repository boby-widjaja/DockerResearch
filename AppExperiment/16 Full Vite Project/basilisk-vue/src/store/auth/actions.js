import { jwtDecode } from 'jwt-decode';
import Swal from 'sweetalert2';
import axiosAuth from '../../axios-instance/axios-auth';

export default{
    getRememberMe(){
        let rememberMe = localStorage.getItem('rememberMe') === 'true' ? true : false;
        return rememberMe;
    },
    getUsername(){
        let username = this.getRememberMe() ? localStorage.getItem('username') : sessionStorage.getItem('username');
        return !username ? 'anonymous' : username;
    },
    getRole(){
        let role = this.getRememberMe() ? localStorage.getItem('role') : sessionStorage.getItem('role');
        return !role ? 'guest' : role;
    },
    getToken(){
        let token = this.getRememberMe() ? localStorage.getItem('token') : sessionStorage.getItem('token');
        return token;
    },
    setUsername(username){
        if(this.getRememberMe()){
            localStorage.setItem('username', username);
        } else {
            sessionStorage.setItem('username', username);
        }
    },
    setRole(role){
        if(this.getRememberMe()){
            localStorage.setItem('role', role);
        } else {
            sessionStorage.setItem('role', role);
        }
    },
    setToken(token){
        if(this.getRememberMe()){
            localStorage.setItem('token', token);
        } else {
            sessionStorage.setItem('token', token);
        }
    },
    setRememberMe(payload){
        localStorage.setItem('rememberMe', payload);
    },
    setRoleDropdown(payload){
        this.roleDropdown = (payload === undefined) ? [] : payload;
    },
    async getRoleDropdown(){
        const response = await axiosAuth.get('/roleDropdown');
        this.setRoleDropdown(response.data);
    },
    logout(){
        localStorage.removeItem('rememberMe');
        localStorage.removeItem('username');
        localStorage.removeItem('role');
        localStorage.removeItem('token');
        sessionStorage.removeItem('username');
        sessionStorage.removeItem('role');
        sessionStorage.removeItem('token');
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
            const token = response.data;
            const {username, role} = jwtDecode(token);
            this.setRememberMe(isRememberMe);
            this.setUsername(username);
            this.setRole(role);
            this.setToken(token);
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