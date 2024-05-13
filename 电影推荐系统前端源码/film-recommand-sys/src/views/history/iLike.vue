<template>
  <div :class="wrapName">
    <van-pull-refresh v-model="result.refreshing" @refresh="onRefresh">
      <van-list v-model:loading="result.loading" :finished="result.finished" finished-text="没有更多了" @load="onLoad">
        <film-block
          v-for="detail in result.list"
          :key="detail.id"
          :detail="detail"
          @click="detailHandle(detail.filmId)"
        >
          <p>{{ format(detail.thumbUpTime) }} 点赞</p>
        </film-block>
      </van-list>
    </van-pull-refresh>
  </div>
</template>

<script setup lang="ts">
import { useWrap } from "@/hooks";
import { getThumbList } from "@/api";
import { userStore } from "@/store";
import { format } from "@/utils";

const { wrapName, b } = useWrap("like");
const router = useRouter();
const userStoreIns = userStore();

const result = reactive({
  loading: false,
  finished: false,
  refreshing: false,

  current: 1,
  size: 10,
  list: [],
});

const onLoad = () => {
  if (result.refreshing) {
    result.current = 1;
    result.list.splice(0);
    result.refreshing = false;
  }

  getThumbList({
    current: result.current,
    size: result.size,
    userId: userStoreIns.userId,
  }).then((res) => {
    result.list = result.list.concat(res.records);
    result.current = res.current + 1;

    result.loading = false;

    if (result.list.length >= res.total) {
      result.finished = true;
      result.current = 1;
    }
  });
};

const onRefresh = () => {
  // 清空列表数据
  result.finished = false;

  // 重新加载数据
  // 将 loading 设置为 true，表示处于加载状态
  result.loading = true;
  onLoad();
};

const detailHandle = (id: string) =>
  router.push({
    path: "/film-detail",
    query: {
      id,
    },
  });
</script>

<style lang="scss" scoped>
@include wrap(like) {
  padding-top: 10px;
}
</style>
