import { createApp } from 'vue';
import { AppVue } from './common/primary/app';
import router from './router/router';
import { createPinia } from 'pinia';
import piniaPersist from 'pinia-plugin-persist';

// jhipster-needle-main-ts-import

const app = createApp(AppVue);
app.use(router);
const pinia = createPinia();
pinia.use(piniaPersist);
app.use(pinia);

// jhipster-needle-main-ts-provider
app.mount('#app');
