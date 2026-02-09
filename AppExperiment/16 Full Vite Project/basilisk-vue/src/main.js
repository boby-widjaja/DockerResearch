import { createApp, defineAsyncComponent } from 'vue'

import './assets/css/normalize.css';
import './assets/css/font-awesome.css';

import App from './App.vue'
import router from './routers/router';
import configureAxios from './axios-config';
import { createPinia } from 'pinia';
import VueApexCharts from "vue3-apexcharts";

const BaseBooleanInput = defineAsyncComponent(() => import('./components/ui-element/BaseBooleanInput.vue'));
const BaseButton = defineAsyncComponent(() => import('./components/ui-element/BaseButton.vue'));
const BaseCard = defineAsyncComponent(() => import('./components/ui-element/BaseCard.vue'));
const BaseDateInput = defineAsyncComponent(() => import('./components/ui-element/BaseDateInput.vue'));
const BaseDateSearch = defineAsyncComponent(() => import('./components/ui-element/BaseDateSearch.vue'));
const BaseDecimalInput = defineAsyncComponent(() => import('./components/ui-element/BaseDecimalInput.vue'));
const BaseDetailLink = defineAsyncComponent(() => import('./components/ui-element/BaseDetailLink.vue'));
const BaseDiagramCard = defineAsyncComponent(() => import('./components/ui-element/BaseDiagramCard.vue'));
const BaseDialog = defineAsyncComponent(() => import('./components/ui-element/BaseDialog.vue'));
const BaseDropdownInput = defineAsyncComponent(() => import('./components/ui-element/BaseDropdownInput.vue'));
const BaseDropdownSearch = defineAsyncComponent(() => import('./components/ui-element/BaseDropdownSearch.vue'));
const BaseErrorDialog = defineAsyncComponent(() => import('./components/ui-element/BaseErrorDialog.vue'));
const BaseErrorContainer = defineAsyncComponent(() => import('./components/ui-element/BaseErrorContainer.vue'));
const BaseFileInput = defineAsyncComponent(() => import('./components/ui-element/BaseFileInput.vue'));
const BaseHeader = defineAsyncComponent(() => import('./components/ui-element/BaseHeader.vue'));
const BaseImageTile = defineAsyncComponent(() => import('./components/ui-element/BaseImageTile.vue'));
const BaseLogo = defineAsyncComponent(() => import('./components/ui-element/BaseLogo.vue'));
const BaseNumberInput = defineAsyncComponent(() => import('./components/ui-element/BaseNumberInput.vue'));
const BasePagination = defineAsyncComponent(() => import('./components/ui-element/BasePagination.vue'));
const BasePasswordInput = defineAsyncComponent(() => import('./components/ui-element/BasePasswordInput.vue'));
const BaseTable = defineAsyncComponent(() => import('./components/ui-element/BaseTable.vue'));
const BaseTextArea = defineAsyncComponent(() => import('./components/ui-element/BaseTextArea.vue'));
const BaseTextInput = defineAsyncComponent(() => import('./components/ui-element/BaseTextInput.vue'));
const BaseTextSearch = defineAsyncComponent(() => import('./components/ui-element/BaseTextSearch.vue'));
const BaseTileContainer = defineAsyncComponent(() => import('./components/ui-element/BaseTileContainer.vue'));
const BaseUpdateDelete = defineAsyncComponent(() => import('./components/ui-element/BaseUpdateDelete.vue'));
const BaseValidationMessage = defineAsyncComponent(() => import('./components/ui-element/BaseValidationMessage.vue'));
const BaseWarningNote = defineAsyncComponent(() => import('./components/ui-element/BaseWarningNote.vue'));


let app = createApp(App);
const pinia = createPinia();
app.use(pinia);
app.use(router);
configureAxios(router);
app.use(VueApexCharts);

app.component('base-boolean-input', BaseBooleanInput);
app.component('base-button', BaseButton);
app.component('base-card', BaseCard);
app.component('base-date-input', BaseDateInput);
app.component('base-date-search', BaseDateSearch);
app.component('base-decimal-input', BaseDecimalInput);
app.component('base-detail-link', BaseDetailLink);
app.component('base-diagram-card', BaseDiagramCard);
app.component('base-dialog', BaseDialog);
app.component('base-dropdown-input', BaseDropdownInput);
app.component('base-dropdown-search', BaseDropdownSearch);
app.component('base-error-dialog', BaseErrorDialog);
app.component('base-error-container', BaseErrorContainer);
app.component('base-file-input', BaseFileInput);
app.component('base-header', BaseHeader);
app.component('base-image-tile', BaseImageTile);
app.component('base-logo', BaseLogo);
app.component('base-number-input', BaseNumberInput);
app.component('base-pagination', BasePagination);
app.component('base-password-input', BasePasswordInput);
app.component('base-table', BaseTable);
app.component('base-text-area', BaseTextArea);
app.component('base-text-input', BaseTextInput);
app.component('base-text-search', BaseTextSearch);
app.component('base-tile-container', BaseTileContainer);
app.component('base-update-delete', BaseUpdateDelete);
app.component('base-validation-message', BaseValidationMessage);
app.component('base-warning-note', BaseWarningNote);

app.mount('#app');
