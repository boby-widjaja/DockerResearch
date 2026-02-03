import SalesmanPage from '../components/salesman/SalesmanPage.vue';
import SalesmanUpsert from '../components/salesman/SalesmanUpsert.vue';
import SalesmanDelete from '../components/salesman/SalesmanDelete.vue';
import ConflictDialog from '../components/error/ConflictDialog.vue';

export default {
    name: 'salesman',
    path: 'salesman',
    meta: {title: 'salesmen'},
    component: SalesmanPage,
    children: [
        {
            name: 'salesmanUpsert',
            path:'form/:id',
            props:true,
            meta: {title: 'salesmen'},
            component:SalesmanUpsert
        },
        {
            name:'salesmanDelete',
            path:'delete/:id',
            props:true,
            meta: {title: 'salesmen'},
            component:SalesmanDelete
        },
        {
            name:'salesmanConflict',
            path:'conflict',
            props:true,
            meta: {title: 'salesmen'},
            component:ConflictDialog
        }
    ]
};