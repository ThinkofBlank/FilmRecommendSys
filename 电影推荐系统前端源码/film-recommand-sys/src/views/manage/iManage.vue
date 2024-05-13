<template>
  <div :class="wrapName">
    <film-manage v-if="componentName === 'film'"></film-manage>
    <notice-manage v-else-if="componentName === 'notice'"></notice-manage>
    <people-manage v-else-if="componentName === 'people'" :key="key"></people-manage>
  </div>
</template>

<script setup lang="ts">
import { useWrap } from "@/hooks";
import filmManage from "./filmManage.vue";
import noticeManage from "./noticeManage.vue";
import peopleManage from "./peopleManage.vue";

defineOptions({
  name: "IManage",
});

const { wrapName, b } = useWrap("iManage");

const route = useRoute();

const key = ref(0);
const componentName = ref(
  (
    route.query as {
      type: "film" | "notice" | "people";
    }
  ).type,
);

onActivated(() => key.value++);
</script>

<style lang="scss" scoped>
@include wrap(iManage) {
}
</style>
