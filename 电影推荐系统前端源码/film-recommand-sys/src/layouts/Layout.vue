<template>
  <section :class="['full-screen', wrapName]">
    <layout-head v-if="$route.meta.deepth && $route.meta.deepth > 1" />

    <main :class="[e('main')]">
      <div :class="em('main', 'bar')">
        <van-search
          v-if="$route.name === 'Home'"
          readonly
          shape="round"
          placeholder="请输入搜索关键词"
          @click-input="searchHandle"
        />

        <van-notice-bar
          v-if="$route.name === 'Home' && noticeSwiper.length > 0"
          left-icon="volume-o"
          :scrollable="false"
        >
          <van-swipe vertical :class="m('notice-swipe')" :autoplay="3000" :touchable="false" :show-indicators="false">
            <van-swipe-item v-for="item in noticeSwiper" :key="item.id" @click="route2url(item.id)">
              {{ item.title }}
            </van-swipe-item>
          </van-swipe>
        </van-notice-bar>
      </div>

      <router-view v-slot="{ Component }" :class="e('routerMain')">
        <keep-alive :include="cacheRoutes" :max="20">
          <component :is="Component" :key="$route.fullPath"></component>
        </keep-alive>
      </router-view>
    </main>

    <layout-foot v-if="$route.meta.deepth && $route.meta.deepth === 1" />
  </section>
</template>

<script lang="ts" setup>
import { useWrap } from "@/hooks";
import LayoutHead from "./LayoutHead.vue";
import LayoutFoot from "./LayoutFoot.vue";
import { getNoticeList } from "@/api";
import { userStore } from "@/store";

const { wrapName, e, m, em } = useWrap("layout-wrap");
const $router = useRouter();
const $route = useRoute();
const userStoreIns = userStore();

const noticeSwiper = ref([]);
const cacheRoutes = ref([]);

watch(
  () => userStoreIns.userId,
  (val) => {
    if (val && $route.name === "Home") {
      getNoticeList().then((res) => (noticeSwiper.value = res));
    }
  },
  { immediate: true },
);

const searchHandle = () => $router.push("/search");

const route2url = (id: string) =>
  $router.push({
    path: "/notice-detail",
    query: {
      id,
    },
  });

onBeforeRouteUpdate((to, from) => {
  if (to.meta.keepAlive) {
    !cacheRoutes.value.includes(to.name) && cacheRoutes.value.push(to.name);
  }

  if (from.meta.keepAlive && (to.meta.deepth || 1) < (from.meta.deepth || 1)) {
    const index = cacheRoutes.value.indexOf(from.name);
    index > -1 && cacheRoutes.value.splice(index, 1);
  }
});
</script>

<style scoped lang="scss">
@include wrap(layout-wrap) {
  display: flex;
  flex-direction: column;

  @include e(main) {
    flex: 1;
    display: flex;
    flex-direction: column;
    overflow: hidden;
    background-color: var(--f-doc-bg-color);

    @include m(notice-swipe) {
      height: 40px;
      line-height: 40px;
    }

    @include e(routerMain) {
      flex: 1;
      overflow-x: hidden;
      overflow-y: auto;
    }
  }
}
</style>
