<template>
  <div :class="wrapName">
    <van-tabs v-model:active="result.activeName" @click-tab="onRefresh">
      <van-tab title="全部" name="-1"></van-tab>
      <van-tab title="未上架" name="0"></van-tab>
      <van-tab title="已上架" name="1"></van-tab>
    </van-tabs>

    <div :class="b('content')">
      <van-pull-refresh v-model="result.refreshing" @refresh="onRefresh">
        <van-list
          v-model:loading="result.loading"
          :finished="result.finished"
          finished-text="没有更多了"
          @load="onLoad"
        >
          <van-swipe-cell v-for="detail in result.list" :key="detail.id">
            <film-block :detail="detail" @click="clickHandle(detail.id)">
              <van-tag v-if="detail.enabled === 1" type="primary">已上架</van-tag>
            </film-block>

            <template #right>
              <van-button
                v-if="detail.enabled === 1"
                square
                text="下架"
                type="danger"
                class="swipe-btn"
                @click="changeStatus(detail.id, 0)"
              />
              <van-button
                v-else
                square
                type="primary"
                text="上架"
                class="swipe-btn"
                @click="changeStatus(detail.id, 1)"
              />
            </template>
          </van-swipe-cell>
        </van-list>
      </van-pull-refresh>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useWrap } from "@/hooks";
import { showConfirmDialog } from "vant";
import { getFilmList, updateFilm } from "@/api";

defineOptions({
  name: "FilmManage",
});

const { wrapName, b } = useWrap("filmManage");
const router = useRouter();

const result = reactive({
  activeName: "-1",

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
    enabled: result.activeName,
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
  result.refreshing = true;
  // 清空列表数据
  result.finished = false;

  // 重新加载数据
  // 将 loading 设置为 true，表示处于加载状态
  result.loading = true;

  result.list.splice(0);
  onLoad();
};

const clickHandle = (id: string) => {
  router.push({
    path: "/film-detail",
    query: {
      id,
    },
  });
};

const changeStatus = (id: string, enabled: 0 | 1) => {
  showConfirmDialog({
    message: "确定执行此操作吗？",
  }).then(async () => {
    const index = result.list.findIndex((l) => l.id === id);
    if (index !== -1) {
      await updateFilm({
        ...result.list[index],
        enabled,
      });

      onRefresh();
    }
  });
};
</script>

<style lang="scss" scoped>
.full {
  width: 100%;
  height: 100%;
  overflow: hidden;
}

@include wrap(filmManage) {
  @extend .full;
  display: flex;
  flex-direction: column;
  overflow: hidden;

  :deep() .van-tabs {
    &__content {
      display: none;
    }
  }

  @include b(content) {
    margin: 10px 0;
    flex: 1;
    overflow-y: auto;

    :deep() .swipe-btn {
      height: 100%;
    }
  }
}
</style>
