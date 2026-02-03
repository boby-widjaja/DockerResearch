import RegionPage from '../components/region/RegionPage.vue';
import RegionUpsert from '../components/region/RegionUpsert.vue';
import RegionDelete from '../components/region/RegionDelete.vue';

export default {
    name: 'region',
    path: 'region',
    meta: {title: 'regions'},
    component: RegionPage,
    children: [
        {
            name: 'regionUpsert',
            path:'form/:id',
            props:true,
            meta: {title: 'regions'},
            component:RegionUpsert
        },
        {
            name:'regionDelete',
            path:'delete/:id',
            props:true,
            meta: {title: 'regions'},
            component:RegionDelete
        }
    ]
}