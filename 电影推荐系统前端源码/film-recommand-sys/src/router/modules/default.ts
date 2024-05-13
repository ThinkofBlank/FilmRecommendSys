import { RouteConfig } from "../types";
import Layout from "@/layouts/Layout.vue";

export default [
  {
    path: "/login",
    name: "Login",
    component: () => import("@/views/login/login.vue"),
  },
  {
    path: "/register",
    name: "Register",
    component: () => import("@/views/register/register.vue"),
  },
  {
    path: "/reset-pwd",
    name: "RestePwd",
    component: () => import("@/views/resetPassword/resetPassword.vue"),
  },
  {
    path: "/",
    name: "Layout",
    component: Layout,
    redirect: "/home",
    children: [
      {
        path: "home",
        name: "Home",
        component: () => import("@/views/home/home.vue"),
        meta: {
          deepth: 1,
          keepAlive: true,
          title: "首页",
        },
      },
      {
        path: "home-admin",
        name: "HomeAdmin",
        component: () => import("@/views/home/homeAdmin.vue"),
        meta: {
          deepth: 1,
          keepAlive: true,
          title: "首页",
        },
      },
      {
        path: "personal",
        name: "Personal",
        component: () => import("@/views/personal/personal.vue"),
        meta: {
          deepth: 1,
          keepAlive: true,
          title: "个人",
        },
      },
      {
        path: "search",
        name: "Search",
        component: () => import("@/views/search/search.vue"),
        meta: {
          deepth: 2,
          title: "搜索",
        },
      },
      {
        path: "my-info",
        name: "MyInfo",
        component: () => import("@/views/userInfo/userInfo.vue"),
        meta: {
          deepth: 2,
          title: "我的信息",
        },
      },
      // 复用
      {
        path: "modify-pwd",
        name: "ModifyPwd",
        component: () => import("@/views/resetPassword/resetPassword.vue"),
        meta: {
          deepth: 2,
          title: "修改密码",
        },
      },
      {
        path: "history-records",
        name: "HistoryRecords",
        component: () => import("@/views/history/iHistoryRecords.vue"),
        meta: {
          deepth: 2,
          title: "历史记录",
        },
      },
      {
        path: "i-manage",
        name: "IManage",
        component: () => import("@/views/manage/iManage.vue"),
        meta: {
          keepAlive: true,
          deepth: 2,
          title: "我的管理",
        },
      },
      {
        path: "people-detail",
        name: "PeopleDetail",
        component: () => import("@/views/manage/peopleDetail.vue"),
        meta: {
          deepth: 3,
          title: "人员信息",
        },
      },
      {
        path: "notice-detail",
        name: "NoticeDetail",
        component: () => import("@/views/manage/noticeDetail.vue"),
        meta: {
          deepth: 3,
          title: "通知信息",
        },
      },
      {
        path: "film-detail",
        name: "FilmDetail",
        component: () => import("@/views/manage/filmDetail.vue"),
        meta: {
          deepth: 3,
          title: "电影信息",
        },
      },
    ],
  },
] as Array<RouteConfig>;
