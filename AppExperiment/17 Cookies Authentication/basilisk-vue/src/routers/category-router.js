import CategoryPage from '../components/category/CategoryPage.vue';
import CategoryUpsert from '../components/category/CategoryUpsert.vue';
import CategoryDelete from '../components/category/CategoryDelete.vue';
import ConflictDialog from '../components/error/ConflictDialog.vue';

export default {
    name: 'category',
    path: 'category',
    meta: {title: 'categories'},
    component: CategoryPage,
    children: [
        {
            name: 'categoryUpsert',
            path:'form/:id',
            props:true,
            meta: {title: 'categories'},
            component:CategoryUpsert
        },
        {
            name:'categoryDelete',
            path:'delete/:id',
            props:true,
            meta: {title: 'categories'},
            component:CategoryDelete
        },
        {
            name: 'categoryConflict',
            path:'conflict',
            props:true,
            meta: {title: 'categories'},
            component:ConflictDialog
        }
    ]
};