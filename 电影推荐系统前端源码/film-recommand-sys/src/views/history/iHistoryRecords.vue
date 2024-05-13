<template>
  <div :class="wrapName">
    <i-comment v-if="isComment"></i-comment>

    <i-like v-else></i-like>
  </div>
</template>

<script setup lang="ts">
import iComment from "./iComment.vue";
import iLike from "./iLike.vue";
import { useWrap } from "@/hooks";

const { wrapName, b } = useWrap("history");
const route = useRoute();

const isComment = computed(() => route.query.type === "comment");
</script>

<style lang="scss" scoped>
@include wrap(history) {
}
</style>
