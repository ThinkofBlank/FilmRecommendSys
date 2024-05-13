<template>
  <div :class="wrapName">
    <van-search v-model="result.keyword" shape="round" placeholder="请输入搜索关键词" @click-right-icon="onRefresh">
      <template #right-icon>
        <van-button size="mini" round>搜索</van-button>
      </template>
    </van-search>
    <van-tabs v-model:active="result.activeName" @click-tab="onRefresh">
      <van-tab title="全部" name="-1"></van-tab>
      <van-tab title="未上映" name="1"></van-tab>
      <van-tab title="已上映" name="2"></van-tab>
    </van-tabs>

    <div :class="e('wrap')">
      <van-pull-refresh v-model="result.refreshing" @refresh="onRefresh">
        <van-list
          v-model:loading="result.loading"
          :finished="result.finished"
          finished-text="没有更多了"
          @load="onLoad"
        >
          <film-block v-for="detail in result.list" :key="detail.id" :detail="detail" @click="detailHandle(detail.id)">
            <van-tag v-if="detail.status === '1'" type="danger">未上映</van-tag>
            <van-tag v-else-if="detail.status === '2'" type="primary">已上映</van-tag>
          </film-block>

          <van-empty v-if="result.list.length <= 0" description="暂无数据" />
        </van-list>
      </van-pull-refresh>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useWrap } from "@/hooks";
import { getFilmList } from "@/api";

const { wrapName, e } = useWrap("search");
const route = useRoute();
const router = useRouter();

const result = reactive({
  activeName: (route.query.type as string) ?? "-1",

  keyword: "",

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

  getFilmList({
    current: result.current,
    size: result.size,
    type: result.activeName === "-1" ? "" : result.activeName,
    keyword: result.keyword,
  }).then((res) => {
    result.list = result.list.concat(res.records);
    result.loading = false;

    if (result.list.length >= res.total) {
      result.finished = true;
      res.current = 0;
    }

    result.current = res.current + 1;
  });
};

const onRefresh = () => {
  result.refreshing = true;
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
@include wrap(search) {
  overflow: hidden;
  display: flex;
  flex-direction: column;

  @include e(wrap) {
    margin-top: 15px;
    flex: 1;
    overflow-x: hidden;
    overflow-y: auto;
  }
}
</style>
