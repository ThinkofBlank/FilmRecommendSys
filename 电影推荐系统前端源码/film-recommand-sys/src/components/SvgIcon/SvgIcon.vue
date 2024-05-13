<template>
  <svg :class="svgClass" aria-hidden="true">
    <use :xlink:href="iconName" />
  </svg>
</template>

<script setup lang="ts">
import { propsParams } from "./svg-icon.constant";

const props = defineProps(propsParams);

const iconName = computed(() => {
  return `#icon-${props.iconClass}`;
});
const svgClass = computed(() => {
  return props.className ? "svg-icon " + props.className : "svg-icon";
});
</script>
<style scoped lang="scss">
.svg-icon {
  fill: currentColor;
}
</style>
