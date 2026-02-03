import DeliveryPage from '../components/delivery/DeliveryPage.vue';
import DeliveryUpsert from '../components/delivery/DeliveryUpsert.vue';
import DeliveryDelete from '../components/delivery/DeliveryDelete.vue';
import ConflictDialog from '../components/error/ConflictDialog.vue';

export default {
    name: 'delivery',
    path: 'delivery',
    meta: {title: 'deliveries'},
    component: DeliveryPage,
    children: [
        {
            name: 'deliveryUpsert',
            path:'form/:id',
            props:true,
            meta: {title: 'deliveries'},
            component:DeliveryUpsert
        },
        {
            name:'deliveryDelete',
            path:'delete/:id',
            props:true,
            meta: {title: 'deliveries'},
            component:DeliveryDelete
        },
        {
            name: 'deliveryConflict',
            path:'conflict',
            props:true,
            meta: {title: 'deliveries'},
            component:ConflictDialog
        }
    ]
}