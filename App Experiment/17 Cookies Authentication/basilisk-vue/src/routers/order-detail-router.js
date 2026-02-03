import OrderDetail from '../components/order/OrderDetail.vue';
import OrderDetailUpsert from '../components/order/OrderDetailUpsert.vue';
import OrderDetailDelete from '../components/order/OrderDetailDelete.vue';

export default {
    name:'orderDetail',
    path:'orderDetail/:headerId',
    props:true,
    meta:{title: 'order details'},
    component: OrderDetail,
    children: [
        {
            name:'orderDetailUpsert',
            path:'form/:id',
            props:true,
            meta:{title:'order details'},
            component:OrderDetailUpsert
        },
        {
            name:'orderDetailDelete',
            path:'delete/:id',
            props:true,
            meta:{title:'order details'},
            component:OrderDetailDelete
        }
    ]
}