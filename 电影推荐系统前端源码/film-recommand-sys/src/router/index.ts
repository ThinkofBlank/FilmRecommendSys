import { createRouter, createWebHashHistory, createWebHistory } from "vue-router";
import { globalSettings } from "../core/settings";
import { RouteConfig } from "./types";

const modules = import.meta.globEager("./modules/**/*.ts");
const routes: RouteConfig[] = [];

Object.keys(modules).forEach((key) => {
  const mod = modules[key].default || {};
  const modList = Array.isArray(mod) ? [...mod] : [mod];
  routes.push(...modList);
});

const router = createRouter({
  history: globalSettings.ENV.IS_PRODUCTION
    ? createWebHistory(`/${globalSettings.PACKAGE.NAME}/`)
    : createWebHashHistory(),
  routes,
  scrollBehavior(to, from, savePosition) {
    if (savePosition) {
      return savePosition;
    }
    return { left: 0, top: 0 };
  },
});

export default router;
