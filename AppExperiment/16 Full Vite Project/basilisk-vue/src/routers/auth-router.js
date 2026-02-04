import TheAuthLayout from '../components/layout/TheAuthLayout.vue';
import LoginPage from '../components/auth/LoginPage.vue';
import RegisterPage from '../components/auth/RegisterPage.vue';

export default {
    name:'authLayout',
    path:'/auth',
    component:TheAuthLayout,
    children:[
        {
            name: 'login',
            path: 'login',
            component: LoginPage,
        },
        {
            name: 'register',
            path: 'register',
            component: RegisterPage,
        }
    ]
}