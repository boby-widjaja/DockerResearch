import RegionDetail from '../components/region/RegionDetail.vue';
import RegionAssignSalesman from '../components/region/RegionAssignSalesman.vue';
import RegionRevokeSalesman from '../components/region/RegionRevokeSalesman.vue';

export default {
    name: 'regionDetail',
    path: 'regionDetail/:headerId',
    props:true,
    meta: {title: 'region details'},
    component: RegionDetail,
    children: [
        {
            name: 'regionAssignSalesman',
            path:'form',
            props:true,
            meta: {title: 'region details'},
            component:RegionAssignSalesman
        },
        {
            name:'regionRevokeSalesman',
            path:'delete/:id',
            props:true,
            meta: {title: 'region details'},
            component:RegionRevokeSalesman
        } 
    ]
}