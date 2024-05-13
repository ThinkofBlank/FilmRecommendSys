import { RouteRecordRaw } from "vue-router"

export type RouteConfig = RouteRecordRaw & { hidden?: boolean } // 额外属性
