<template>
  <footer :class="[wrapName]">
    <van-tabbar :fixed="false" route active-color="#e95c5c">
      <van-tabbar-item v-for="item in tabs" :key="item.id" replace :icon="item.icon" :to="item.to">
        {{ item.name }}
      </van-tabbar-item>
    </van-tabbar>
  </footer>
</template>

<script setup lang="ts">
import { useWrap } from "@/hooks";
import { userStore } from "@/store";

const { wrapName } = useWrap("layout-foot");
const userStoreIns = userStore();

const tabs = ref([
  {
    id: "0",
    name: "主页",
    to: userStoreIns.userRole === "1" ? "/home-admin" : "/home",
    icon: "home-o",
  },
  {
    id: "1",
    name: "我的",
    to: "/personal",
    icon: "friends-o",
  },
]);
</script>

<style lang="scss" scoped>
@include wrap(layout-foot) {
  border-top: 1px solid #ededed;
  box-sizing: border-box;
  box-shadow: 0 0 5px #999;
}
</style>
