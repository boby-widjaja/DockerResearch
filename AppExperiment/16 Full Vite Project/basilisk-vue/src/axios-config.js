import axios from 'axios';
import useStore from './store/auth/auth-store.js';

export default function configure(router){
    const store = useStore();
    axios.defaults.baseURL = 'http://localhost:7070/api';

    const isOk = status => status >= 200 && status <= 299;
    const isRedirected = status => status >= 300 && status <= 399;
    const isValidated = status => status === 422;
    const isConflict = status => status === 409;
    const isServerError = status => status >= 500;
    const isForbidden = status => status === 403;
    const isUnauthorized = status => status === 401;
    
    axios.defaults.validateStatus = status => isValidated(status) || isConflict(status) || isOk(status) || isRedirected(status);

    axios.interceptors.request.use(request => {
        const token = store.getToken();
        request.headers.Authorization = token;
        return request;
    });

    axios.interceptors.response.use(response => {
        return response;
    }, error => {
        const status = error.status;
        if (isUnauthorized(status)){
            store.logout();
            router.push('/auth/login');
            return error.response;
        } else if (isForbidden(status)){
            router.push('/forbidden');
            return error.response;
        } else if (isServerError(status)){
            router.push('/serverError');
            return error.response;
        }
        return Promise.reject(error);
    });
}