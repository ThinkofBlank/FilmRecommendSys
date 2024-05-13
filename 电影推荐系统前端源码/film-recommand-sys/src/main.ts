import "amfe-flexible";
import "virtual:svg-icons-register";

import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import "@/core/vant";
import "@/core/permission";
import { createPinia } from "pinia";
import piniaPluginPersistedState from "pinia-plugin-persistedstate";

import "nprogress/nprogress.css";
import "@/assets/style/rest.scss";

const app = createApp(App);
const pinia = createPinia();

pinia.use(piniaPluginPersistedState);

app.use(router).use(pinia).mount("#app");
