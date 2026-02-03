import axios from 'axios';

const instance = axios.create();
instance.defaults.baseURL = 'http://localhost:7070/api/account';
delete instance.defaults.headers.common.Authorization;

const isOk = status => status >= 200 && status <= 299;
const isRedirected = status => status >= 300 && status <= 399;
const isValidated = status => status === 422;
const isServerError = status => status >= 500;

instance.defaults.validateStatus = status => isValidated(status) || isOk(status) || isRedirected(status);

instance.interceptors.response.use(response => {
    return response;
}, error => {
    const status = error.response.status;
    if(isServerError(status)){
        router.push('/serverError');
        return error.response;
    }
    return Promise.reject(error);
});

export default instance;