import { ref, onBeforeMount, toRaw } from 'vue';
import { useRouter } from 'vue-router';

const useUpsertForm = ({id, store, closePath, callback, nonGenerated = false, insertValue = {}}) => {
    const router = useRouter();
    const input = ref({});
    const validationMessages = ref({});
    
    onBeforeMount(async() => {
      if(callback){
        callback(id);
      }
      input.value = (id == 0) ? insertValue : await store.findOne(id);
    });

    const normalizeNullValue = payload => {
      for(let [property, value] of Object.entries(toRaw(payload))){
        if(value === 'null'){
          payload[property] = null;
        }
      }
    }

    const submitForm = async() => {
      validationMessages.value = {};
      const method = (id == 0 && !nonGenerated) ? 'post' : 'put';
      normalizeNullValue(input.value);
      const {status, data} = await store.upsert({method, payload: input.value});
      if(status === 422){
        errorValidation(data);
      } else {
        closeDialog();
      }
    }

    const errorValidation = validations => {
        for(let validation of validations){
            validationMessages.value[validation.field] = validation.message;
        }
    }

    const closeDialog = () => {
        validationMessages.value = {};
        router.push(closePath);
    }

    return {input, validationMessages, submitForm, closeDialog};
}

export default useUpsertForm;