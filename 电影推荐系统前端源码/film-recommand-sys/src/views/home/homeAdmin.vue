<template>
  <div :class="wrapName">
    <div v-if="noticeLen > 0" :class="[e('notice'), 'white-block']">
      <router-link
        :to="{
          path: '/i-manage',
          query: {
            type: 'people',
          },
        }"
      >
        您有 <span class="warning-text">{{ noticeLen }}</span>
        条申请记录待处理
      </router-link>
    </div>

    <p :class="b('title')">素材库分类统计</p>
    <div ref="filmRef" :class="[e('chart'), 'white-block']"> </div>

    <p :class="b('title')">top10 最受欢迎电影</p>
    <div ref="filmTop10Ref" :class="[e('chart'), 'white-block']"> </div>
  </div>
</template>

<script setup lang="ts">
import { useWrap } from "@/hooks";
import { useFilmsChart, useFilmsTop10Chart } from "./homeAdmin.constant";
import { getUserCount, getLikeRecords, getFilmTop10 } from "@/api";

defineOptions({
  name: "HomeAdmin",
});

const { wrapName, b, e } = useWrap("homeAdmin");
const noticeLen = ref(0);
const filmRef = ref();
const filmTop10Ref = ref();

getUserCount({ auditStatus: "1" }).then((res) => {
  noticeLen.value = res;
});

onMounted(() => {
  const filmChartRender = useFilmsChart(filmRef.value);
  const filmTop10ChartRender = useFilmsTop10Chart(filmTop10Ref.value);

  getLikeRecords().then(({ eCharts }) => {
    filmChartRender(
      Object.keys(eCharts).map((k) => ({
        name: k,
        value: eCharts[k],
      })),
    );
  });
  getFilmTop10().then((res) => {
    filmTop10ChartRender(
      res.map((r) => ({
        name: r.title.split("(")[0],
        value: Number(r.score),
      })),
    );
  });
});
</script>

<style lang="scss" scoped>
@include wrap("homeAdmin") {
  @include e(notice) {
    font-size: 14px;
  }

  @include b(title) {
    font-size: 14px;
    color: $main-color;
    margin: 10px;
    text-align: left;
  }

  @include e(chart) {
    width: 100%;
    height: 300px;
    overflow: hidden;
    box-sizing: border-box;
  }
}
</style>
