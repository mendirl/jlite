import { provideForAuth } from '@/auth/application/AuthProvider';
import { AxiosHttp } from '@/shared/http/infrastructure/secondary/AxiosHttp';
import axios from 'axios';
import { createApp } from 'vue';
import AppVue from './AppVue.vue';
import router from './router';

// jhipster-needle-main-ts-import

const app = createApp(AppVue);
app.use(router);
const axiosInstance = axios.create({ baseURL: 'http://localhost:8080/' });
const axiosHttp = new AxiosHttp(axiosInstance);
provideForAuth(axiosHttp);

// jhipster-needle-main-ts-provider
app.mount('#app');
