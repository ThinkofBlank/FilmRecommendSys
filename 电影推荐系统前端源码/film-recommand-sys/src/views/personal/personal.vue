<template>
  <div :class="wrapName">
    <div :class="['white-block', 'align-l', b('info')]">
      <div :class="b('avator')">{{ userNameSimple }}</div>

      <div :class="b('name')">
        <p>{{ userStoreIns.userName }}-{{ userStoreIns.userRole === "1" ? "管理员" : "普通用户" }}</p>
        <p>{{ userStoreIns.userInfo.phone }}</p>
      </div>
    </div>

    <div :class="['white-block', 'align-l']">
      <van-cell title="个人信息" icon="user-o" is-link to="/my-info" />

      <van-cell v-for="item in cellList" :key="item.id" :title="item.title" :icon="item.icon" is-link :to="item.to" />
    </div>

    <div :class="b('logout')">
      <van-button color="linear-gradient( 135deg, #FEB692 10%, #EA5455 100%)" block @click="logoutHandle">
        注销登录
      </van-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useWrap } from "@/hooks";
import { userStore, logout } from "@/store";
import { showConfirmDialog } from "vant";

defineOptions({
  name: "Personal",
});

const { wrapName, b, e } = useWrap("personal");
const userStoreIns = userStore();

const userNameSimple = computed(() =>
  userStoreIns.userInfo?.nickname?.substring(userStoreIns.userInfo?.nickname.length - 1),
);
const cellList = computed(() => {
  if (userStoreIns.userRole === "0") {
    return [
      {
        id: "0",
        title: "历史评价",
        icon: "comment-o",
        to: "/history-records?type=comment",
      },
      {
        id: "1",
        title: "历史点赞",
        icon: "like-o",
        to: "/history-records?type=like",
      },
      {
        id: "2",
        title: "密码重置",
        icon: "setting-o",
        to: "/modify-pwd",
      },
    ];
  }
  return [
    {
      id: "0",
      title: "人员管理",
      icon: "friends-o",
      to: "/i-manage?type=people",
    },
    {
      id: "1",
      title: "通知管理",
      icon: "service-o",
      to: "/i-manage?type=notice",
    },
    {
      id: "2",
      title: "电影管理",
      icon: "video-o",
      to: "/i-manage?type=film",
    },
    {
      id: "3",
      title: "修改密码",
      icon: "setting-o",
      to: "/modify-pwd",
    },
  ];
});

const logoutHandle = () => {
  showConfirmDialog({
    message: "确认注销登陆吗？",
    confirmButtonColor: "#e95c5c",
  })
    .then(() => {
      // on confirm
      logout();
    })
    .catch(() => {
      // on cancel
    });
};
</script>

<style lang="scss" scoped>
@include wrap(personal) {
  @include b(info) {
    display: flex;
    gap: 10px;
    font-size: 14px;
  }

  @include b(avator) {
    padding: 10px;
    box-sizing: border-box;
    border-radius: 50%;
    background-image: linear-gradient(135deg, #90f7ec 10%, #32ccbc 100%);
    color: #fff;
    font-size: 16px;
    line-height: 1;
    width: 36px;
    height: 36px;
  }

  @include b(name) {
    p {
      margin-bottom: 10px;
    }
  }

  @include b(logout) {
    margin: 10px;
  }
}
</style>
