import { createApp } from 'vue'

import './assets/css/normalize.css';
import './assets/css/font-awesome.css';

import App from './App.vue'
import router from './routers/router';
import configureAxios from './axios-config';
import { createPinia } from 'pinia';
import VueApexCharts from "vue3-apexcharts";

import BaseBooleanInput from './components/ui-element/BaseBooleanInput.vue';
import BaseButton from './components/ui-element/BaseButton.vue';
import BaseCard from './components/ui-element/BaseCard.vue';
import BaseDateInput from './components/ui-element/BaseDateInput.vue';
import BaseDateSearch from './components/ui-element/BaseDateSearch.vue';
import BaseDecimalInput from './components/ui-element/BaseDecimalInput.vue';
import BaseDetailLink from './components/ui-element/BaseDetailLink.vue';
import BaseDiagramCard from './components/ui-element/BaseDiagramCard.vue';
import BaseDialog from './components/ui-element/BaseDialog.vue';
import BaseDropdownInput from './components/ui-element/BaseDropdownInput.vue';
import BaseDropdownSearch from './components/ui-element/BaseDropdownSearch.vue';
import BaseErrorDialog from './components/ui-element/BaseErrorDialog.vue';
import BaseErrorContainer from './components/ui-element/BaseErrorContainer.vue';
import BaseFileInput from './components/ui-element/BaseFileInput.vue';
import BaseHeader from './components/ui-element/BaseHeader.vue';
import BaseImageTile from './components/ui-element/BaseImageTile.vue';
import BaseLogo from './components/ui-element/BaseLogo.vue';
import BaseNumberInput from './components/ui-element/BaseNumberInput.vue';
import BasePagination from './components/ui-element/BasePagination.vue';
import BasePasswordInput from './components/ui-element/BasePasswordInput.vue';
import BaseTable from './components/ui-element/BaseTable.vue';
import BaseTextArea from './components/ui-element/BaseTextArea.vue';
import BaseTextInput from './components/ui-element/BaseTextInput.vue';
import BaseTextSearch from './components/ui-element/BaseTextSearch.vue';
import BaseTileContainer from './components/ui-element/BaseTileContainer.vue';
import BaseUpdateDelete from './components/ui-element/BaseUpdateDelete.vue';
import BaseValidationMessage from './components/ui-element/BaseValidationMessage.vue';
import BaseWarningNote from './components/ui-element/BaseWarningNote.vue';

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
