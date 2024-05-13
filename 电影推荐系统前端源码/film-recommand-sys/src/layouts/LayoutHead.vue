<template>
  <header :class="[wrapName]">
    <van-nav-bar :title="title" left-text="返回" left-arrow safe-area-inset-top @click-left="onClickLeft" />
  </header>
</template>

<script setup lang="ts">
import { useWrap } from "@/hooks";

const { wrapName } = useWrap("layout-header");
const router = useRouter();
const route = useRoute();

const title = computed(() => route.meta.title as string);
const onClickLeft = () => router.back();
</script>

<style lang="scss" scoped>
@include wrap(layout-header) {
}
</style>
