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
          <p>{{ format(detail.appraiseTime) }} 评论：{{ detail.comment }}</p>
        </film-block>
      </van-list>
    </van-pull-refresh>
  </div>
</template>

<script setup lang="ts">
import { useWrap } from "@/hooks";
import { getAppraiseList } from "@/api";
import { userStore } from "@/store";
import { format } from "@/utils";

const { wrapName, b } = useWrap("comment");
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

  getAppraiseList({
    current: result.current,
    size: result.size,
    userId: userStoreIns.userId,
  }).then((res) => {
    result.current = res.current + 1;
    result.list = result.list.concat(res.records);

    result.loading = false;

    if (result.list.length >= res.total) {
      result.current = 1;
      result.finished = true;
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
@include wrap(comment) {
  padding-top: 10px;
}
</style>
