<template>
    <base-dialog :title="'Product Form'" :closeDialog="closeDialog">
        <input type="hidden" v-model="input.id">
        <base-text-input 
            :label="'name *'" 
            :id="'product-name'" 
            v-model="input.name" 
            :validation="validationMessages.name">
        </base-text-input>
        <base-dropdown-input
            :label="'category *'" 
            :id="'product-category'" 
            v-model="input.categoryId" 
            :validation="validationMessages.categoryId"
            :placeholder="'Choose Category'"
            :options="categoryDropdown">
        </base-dropdown-input>
        <base-dropdown-input
            :label="'supplier'" 
            :id="'product-supplier'" 
            v-model="input.supplierId" 
            :validation="validationMessages.supplierId"
            :placeholder="'No Specific Supplier'"
            :options="supplierDropdown">
        </base-dropdown-input>
        <base-decimal-input 
            :label="'price *'" 
            :id="'product-price'" 
            v-model="input.price"
            :validation="validationMessages.price">
        </base-decimal-input>
        <base-number-input 
            :label="'stock *'" 
            :id="'product-stock'" 
            v-model="input.stock"
            :validation="validationMessages.stock">
        </base-number-input>
        <base-number-input 
            :label="'on order *'" 
            :id="'product-on-order'" 
            v-model="input.onOrder"
            :validation="validationMessages.onOrder">
        </base-number-input>
        <base-boolean-input 
            :label="'is discontinue *'" 
            :id="'product-discontinue'" 
            v-model="input.discontinue"
            :validation="validationMessages.discontinue">
        </base-boolean-input>
        <base-text-area 
            :label="'description'" 
            id="'product-description'" 
            v-model="input.description"
            :validation="validationMessages.description">
        </base-text-area>
        <base-file-input 
            :label="'photo *'" 
            :id="'product-photo'" 
            :validation="validationMessages.imagePath"
            :uploadingImage="uploadingImage">
        </base-file-input>
        <input type="hidden" v-model="input.imagePath">
        <base-button :event="submitForm">
            <i class="fas fa-save"></i>
            <span>save</span>
        </base-button>
    </base-dialog>
</template>

<script setup>
    import useStore from '../../store/product/product-store.js';
    import { ref, onBeforeMount, toRaw} from 'vue';
    import { useRouter } from 'vue-router';

    const props = defineProps(['id']);
    const store = useStore();
    const router = useRouter();
    const id = props.id;
    const categoryDropdown = ref([]);
    const supplierDropdown = ref([]);
    const input = ref({});
    const validationMessages = ref({});
    const imageFile = ref(null);

    const uploadingImage = (event) => {
        console.log("Uploading image");
        imageFile.value = event.target.files[0];
    }

    onBeforeMount(async() => {
        await store.getCategoryDropdown();
        await store.getSupplierDropdown();
        categoryDropdown.value = store.categoryDropdown;
        supplierDropdown.value = store.supplierDropdown;
        input.value = (id == 0) ? { discontinue:false } : await store.findOne(id);
    });

    const closeDialog = () => {
        validationMessages.value = {};
        router.push('/product');
    }

    const normalizeNullValue = payload => {
        for(let [property, value] of Object.entries(toRaw(payload))){
            if(value === 'null'){
                payload[property] = null;
            }
        }
    }

    const errorValidation = validations => {
        for(let validation of validations){
            validationMessages.value[validation.field] = validation.message;
        }
    }

    const submitForm = async() => {
        validationMessages.value = {};
        const method = (id == 0) ? 'post' : 'put';
        normalizeNullValue(input.value);

        let formData = new FormData();
        if(imageFile.value !== null){
            formData.append('image', new Blob([imageFile.value],{type:'image/jpeg'}));
        } else {
            formData.append('image',new Blob([''],{type:'application/json'}));
        }
        formData.append('dto', new Blob([JSON.stringify(input.value)], {type:'application/json'}));

        const {status, data} = await store.upsert({method, payload: formData});
        if(status === 422){
            errorValidation(data);
        } else {
            closeDialog();
        }
    }
</script>