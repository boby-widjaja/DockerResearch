import {createRouter, createWebHistory} from 'vue-router';

import TheLayout from '../components/layout/TheLayout.vue';
import DashboardPage from '../components/dashboard/DashboardPage.vue';
import ChangePassword from '../components/auth/ChangePassword.vue';
import NotFoundPage from '../components/error/NotFoundPage.vue';
import ForbiddenPage from '../components/error/ForbiddenPage.vue';
import ServerErrorPage from '../components/error/ServerErrorPage.vue';
import authRouter from './auth-router';
import categoryRouter from './category-router';
import customerRouter from './customer-router';
import deliveryRouter from './delivery-router';
import productRouter from './product-router';
import regionRouter from './region-router';
import regionDetailRouter from './region-detail-router';
import salesmanRouter from './salesman-router';
import salesmanDetailRouter from './salesman-detail-router';
import supplierRouter from './supplier-router';
import orderRouter from './order-router';
import orderDetailRouter from './order-detail-router';

const router = createRouter({
    history: createWebHistory(),
    routes: [
        authRouter,
        {
            name:'layout',
            path:'/',
            component: TheLayout,
            children: [
                {
                    name: 'dashboard',
                    path: 'dashboard',
                    alias: '/',
                    meta: {title: 'dashboard'},
                    component: DashboardPage,
                },
                categoryRouter,
                customerRouter,
                deliveryRouter,
                productRouter,
                regionRouter,
                regionDetailRouter,
                salesmanRouter,
                salesmanDetailRouter,
                supplierRouter,
                orderRouter,
                orderDetailRouter,
                {
                    name:'changePassword',
                    path:'changePassword',
                    meta:{title: 'change password'},
                    component: ChangePassword
                }
            ]
        },
        {
            name: 'forbidden',
            path: '/forbidden',
            component: ForbiddenPage
        },
        {
            name: 'serverError',
            path: '/serverError',
            component: ServerErrorPage
        },
        { 
            name: 'not-found',
            path: '/:notFound(.*)',
            component: NotFoundPage
        }
    ]
});

export default router;