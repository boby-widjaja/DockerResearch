import { onBeforeMount } from 'vue';

const usePagedTable = (store, callback) => {
    onBeforeMount(() => {
      if(callback){
        callback();
      }
      store.refreshGrid();
    });
    const selectPage = page => {
      store.page = page;
      store.refreshGrid();
    };
    const firstPage = () => selectPage(1);
    const lastPage = () => selectPage(store.totalPages);
    const resetPage = () => {
        store.page = 1;
        store.refreshGrid();
    }
    const searchBy = (event, property) => {
      store[property] = event.target.value;
      resetPage();
    }
    return {selectPage, firstPage, lastPage, resetPage, searchBy};
}

export default usePagedTable;