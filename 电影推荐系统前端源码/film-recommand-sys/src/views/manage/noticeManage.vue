<template>
  <div :class="wrapName">
    <van-cell-group>
      <van-cell v-for="item in list" :key="item.id" is-link :to="`/notice-detail?id=${item.id}`">
        <template #title>
          <van-text-ellipsis :content="item.title" />
        </template>
      </van-cell>
    </van-cell-group>

    <div v-if="list.length <= 5" class="btn-wrap">
      <van-button type="primary" rount block to="/notice-detail">新增</van-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useWrap } from "@/hooks";
import { getNoticeList } from "@/api";

defineOptions({
  name: "NoticeManage",
});

const list = ref([]);

const { wrapName, b } = useWrap("noticeManage");

onActivated(() => {
  getNoticeList().then((res) => {
    list.value = res;
  });
});
</script>

<style lang="scss" scoped>
@include wrap(noticeManage) {
  text-align: left;
  margin: 10px 0;
}
</style>
