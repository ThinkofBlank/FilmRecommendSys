import router from "@/router";
import NProgress from "nprogress";
import { globalSettings, routeWhiteList } from "./settings";
import { userStore } from "@/store";

router.beforeEach((to, from, next) => {
  NProgress.start();
  const userStoreIns = userStore();
  if (userStoreIns.token) {
    if (["/login", "/register", "/reset-pwd"].includes(to.path)) {
      next("/");
    } else {
      next();
    }
  } else if (routeWhiteList.includes(to.path)) {
    next();
  } else {
    next({
      path: "/login",
      replace: true,
    });
  }
});

router.afterEach((to, from) => {
  NProgress.done();

  if (to.meta.title) {
    document.title = `${globalSettings.SYS.TITLE}-${to.meta.title}`;
  } else {
    document.title = globalSettings.SYS.TITLE;
  }
});
