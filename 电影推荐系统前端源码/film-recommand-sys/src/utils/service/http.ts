import axios, { AxiosRequestConfig } from "axios";
import NProgress from "nprogress";
import { userStore, logout } from "@/store";
import { notifyError } from "@/utils";

const service = axios.create({
  responseType: "text",
  baseURL: import.meta.env.VITE_BASE_URL,
  timeout: 6 * 1000,
  withCredentials: true,
  headers: {
    "content-type": "application/json",
  },
} as AxiosRequestConfig);

service.interceptors.request.use(
  (config): AxiosRequestConfig<any> => {
    NProgress.start();

    const userStoreIns = userStore();
    if (userStoreIns.token) {
      config.headers.ticket = userStoreIns.token;
    }

    return config;
  },
  (error) => {
    return error;
  },
);

service.interceptors.response.use(
  (res) => {
    NProgress.done();

    const { code, message, data } = res.data;

    if (code === "200") {
      return data;
    } else if (code === "401") {
      notifyError("token过期", logout);
    }

    notifyError(message || "系统内部错误");

    return Promise.reject(message);
  },
  (error) => {
    const status = error.response?.status;

    if (status === 401) {
      notifyError("token过期", logout);
    } else {
      notifyError(error.response?.message || error.message || "系统内部错误");
    }

    NProgress.done();

    return Promise.reject(error);
  },
);

export default service;
