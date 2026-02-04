import CustomerPage from '../components/customer/CustomerPage.vue';
import CustomerUpsert from '../components/customer/CustomerUpsert.vue';
import CustomerDelete from '../components/customer/CustomerDelete.vue';

export default {
    name: 'customer',
    path: 'customer',
    meta: {title: 'customers'},
    component: CustomerPage,
    children: [
        {
            name: 'customerUpsert',
            path:'form/:id',
            props:true,
            meta: {title: 'customers'},
            component:CustomerUpsert
        },
        {
            name:'customerDelete',
            path:'delete/:id',
            props:true,
            meta: {title: 'customers'},
            component:CustomerDelete
        }
    ]
}