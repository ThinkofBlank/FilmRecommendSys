export const globalSettings = {
  ENV: {
    IS_PRODUCTION: import.meta.env.PROD,
  },

  PACKAGE: {
    NAME: import.meta.env.VITE_PACKAGE_NAME as string,
  },

  SYS: {
    TITLE: import.meta.env.VITE_TITLE as string,
  },
};

export const routeWhiteList = ["/login", "/register", "/reset-pwd", "/home"];
