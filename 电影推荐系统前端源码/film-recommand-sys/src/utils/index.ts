import type { AxiosRequestConfig } from "axios";
import axios from "./service/http";

export * from "./vue/vue";
export * from "./message";
export * from "./image";
export * from "./dayjs";
export const service: <T>(config: AxiosRequestConfig) => Promise<T> = (config) => axios(config) as Promise<any>;
