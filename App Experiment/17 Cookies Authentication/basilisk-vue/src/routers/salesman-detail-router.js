import SalesmanDetail from '../components/salesman/SalesmanDetail.vue';
import SalesmanAssignRegion from '../components/salesman/SalesmanAssignRegion.vue';
import SalesmanRevokeRegion from '../components/salesman/SalesmanRevokeRegion.vue';

export default {
    name: 'salesmanDetail',
    path: 'salesmanDetail/:headerId',
    props:true,
    meta: {title: 'salesman details'},
    component: SalesmanDetail,
    children: [
        {
            name: 'salesmanAssignRegion',
            path:'form',
            props:true,
            meta: {title: 'salesman details'},
            component:SalesmanAssignRegion
        },
        {
            name:'salesmanRevokeRegion',
            path:'delete/:id',
            props:true,
            meta: {title: 'salesman details'},
            component:SalesmanRevokeRegion
        }
    ]
}