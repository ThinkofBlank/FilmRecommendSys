<template>
  <div :class="b('block')">
    <div :class="b('left')">
      <img :src="getStoreImageUrl(detail.imageUrl)" alt="封面加载失败" />
    </div>

    <div :class="[b('right'), 'align-l']">
      <p :class="[b('title'), 'overflow-ellipsis']">{{ detail.title }}</p>

      <p>
        <span v-if="detail.score" :class="b('score')">
          评分 <i>{{ detail.score }}</i>
          &nbsp;|&nbsp;
        </span>
        {{ detail.thumbCount || 0 }}人想看
      </p>

      <p> {{ detail.directors }}&nbsp;/&nbsp;{{ detail.playwright }} </p>

      <p>{{ detail.types }}</p>

      <p>{{ detail.releaseTime }}</p>

      <slot></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useWrap } from "@/hooks";
import { acceptParams } from "./constant";
import { getStoreImageUrl } from "@/utils";

defineOptions({
  name: "FilmBlock",
});

const { b } = useWrap("film");
const props = defineProps(acceptParams);
</script>

<style lang="scss" scoped>
@include wrap(film) {
  @include b(block) {
    background-color: $bg-color;
    border-radius: 5px;
    margin: 0 10px 10px;
    padding: 10px;
    box-sizing: border-box;
    overflow: hidden;
    display: flex;
    gap: 8px;
    color: $second-color;
    font-size: $main-size;
    line-height: 18px;
  }

  @include b(left) {
    width: 90px;
    overflow: hidden;
    position: relative;

    img {
      width: 100%;
      height: 140px;
      border-radius: 5px;
    }

    span {
      position: absolute;
      top: 2px;
      left: 2px;
      font-size: 12px;
      color: #fff;
      background-color: rgba(0, 0, 0, 0.8);
    }
  }

  @include b(right) {
    flex: 1;
    overflow: hidden;

    p {
      margin-bottom: 10px;
    }
  }

  @include b(title) {
    color: $main-color;
    font-size: 16px;
  }

  @include b(score) {
    color: $theme-color;
    font-size: 16px;
  }
}
</style>
