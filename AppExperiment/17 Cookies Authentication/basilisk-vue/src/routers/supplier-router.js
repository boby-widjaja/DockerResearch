import SupplierPage from '../components/supplier/SupplierPage.vue';
import SupplierUpsert from '../components/supplier/SupplierUpsert.vue';
import SupplierDelete from '../components/supplier/SupplierDelete.vue';

export default {
    name: 'supplier',
    path: 'supplier',
    meta: {title: 'suppliers'},
    component: SupplierPage,
    children: [
        {
            name: 'supplierUpsert',
            path:'form/:id',
            props:true,
            meta: {title: 'suppliers'},
            component:SupplierUpsert
        },
        {
            name:'supplierDelete',
            path:'delete/:id',
            props:true,
            meta: {title: 'suppliers'},
            component:SupplierDelete
        }
    ]
}