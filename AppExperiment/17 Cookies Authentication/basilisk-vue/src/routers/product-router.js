import ProductPage from '../components/product/ProductPage.vue';
import ProductUpsert from '../components/product/ProductUpsert.vue';
import ProductDelete from '../components/product/ProductDelete.vue';
import ConflictDialog from '../components/error/ConflictDialog.vue';

export default {
    name: 'product',
    path: 'product',
    meta: {title: 'products'},
    component: ProductPage,
    children: [
        {
            name: 'productUpsert',
            path:'form/:id',
            props:true,
            meta: {title: 'products'},
            component:ProductUpsert
        },
        {
            name:'productDelete',
            path:'delete/:id',
            props:true,
            meta: {title: 'products'},
            component:ProductDelete
        },
        {
            name:'productConflict',
            path:'conflict',
            props:true,
            meta: {title: 'products'},
            component:ConflictDialog
        }
    ]
};