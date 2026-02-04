import OrderPage from '../components/order/OrderPage.vue';
import OrderUpsert from '../components/order/OrderUpsert.vue';
import OrderDelete from '../components/order/OrderDelete.vue';

export default {
    name: 'order',
    path: 'order',
    meta: {title: 'orders'},
    component: OrderPage,
    children: [
        {
            name: 'orderUpsert',
            path:'form/:id',
            props:true,
            meta: {title: 'orders'},
            component:OrderUpsert
        },
        {
            name:'orderDelete',
            path:'delete/:id',
            props:true,
            meta: {title: 'orders'},
            component:OrderDelete
        }
    ]
}