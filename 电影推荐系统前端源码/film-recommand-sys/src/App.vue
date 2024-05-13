<template>
  <!-- <van-watermark v-if="userStoreIns.userId" :content="userStoreIns.userName" opacity="0.5" z-index="1"></van-watermark> -->

  <router-view />
</template>

<script setup lang="ts">
import { userStore } from "@/store";

const userStoreIns = userStore();
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  width: 100%;
  height: 100%;
  overflow: hidden;
}
</style>
