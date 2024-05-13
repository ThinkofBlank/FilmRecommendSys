<template>
  <div :class="[wrapName]">
    <div :class="e('static')">
      <div :class="em('static', 'title')">
        <span>热映影片</span>
        <router-link v-if="films.hot.length > 0" to="/search?type=2">更多 <van-icon name="arrow" /></router-link>
      </div>
      <div :class="em('static', 'wrap')">
        <router-link
          v-for="item in films.hot"
          :key="item.id"
          v-slot="{ navigate }"
          :to="{
            path: '/film-detail',
            query: {
              id: item.id,
            },
          }"
        >
          <div class="film-block" @click="navigate">
            <div class="film-face" :style="{ 'background-image': `url(${getStoreImageUrl(item.imageUrl)})` }">
              <p v-if="item.score" class="film-score">评分&nbsp;&nbsp;{{ item.score }}</p>
            </div>
            <p class="overflow-ellipsis">{{ item.title }}</p>
          </div>
        </router-link>

        <van-empty v-if="films.hot.length <= 0" description="暂无数据" />
      </div>
    </div>

    <div :class="e('static')">
      <div :class="em('static', 'title')">
        <span>即将上映</span>
        <router-link v-if="films.future.length > 0" to="/search?type=1">更多 <van-icon name="arrow" /></router-link>
      </div>
      <div :class="em('static', 'wrap')">
        <router-link
          v-for="item in films.future"
          :key="item.id"
          v-slot="{ navigate }"
          :to="{
            path: '/film-detail',
            query: {
              id: item.id,
            },
          }"
        >
          <div class="film-block" @click="navigate">
            <div class="film-face" :style="{ 'background-image': `url(${getStoreImageUrl(item.imageUrl)})` }"> </div>
            <p class="overflow-ellipsis">{{ item.title }}</p>
          </div>
        </router-link>

        <van-empty v-if="films.future.length <= 0" description="暂无数据" />
      </div>
    </div>

    <div v-if="userStoreIns.userId" :class="[e('masonry'), 'align-l']">
      <div :class="em('masonry', 'title')">猜你喜欢</div>

      <div :class="em('masonry', 'wrap')">
        <router-link
          v-for="item in refreshList.list"
          :key="item.id"
          v-slot="{ navigate }"
          :to="{
            path: '/film-detail',
            query: {
              id: item.id,
            },
          }"
        >
          <div class="film-block" @click="navigate">
            <div class="film-face" :style="{ 'background-image': `url(${getStoreImageUrl(item.imageUrl)})` }"></div>
            <div class="film-detail">
              <p class="overflow-ellipsis">{{ item.title }}</p>
              <p>
                <span v-if="item.score">评分 {{ item.score }}</span>
              </p>
            </div>
          </div>
        </router-link>

        <van-empty v-if="refreshList.list.length <= 0" description="暂无数据" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useWrap } from "@/hooks";
import { userStore } from "@/store";
import { getFilmList, getRecommendFilm } from "@/api";
import { getStoreImageUrl } from "@/utils";

defineOptions({
  name: "Home",
});

const { wrapName, e, em } = useWrap("home");
const userStoreIns = userStore();

const masonryRef = ref();
const refreshList = reactive({
  list: [], // 猜你喜欢
});
const films = reactive({
  hot: [],
  future: [],
});

getFilmList({
  current: 1,
  size: 5,
  type: 0,
}).then((res) => (films.hot = res.records));
getFilmList({
  current: 1,
  size: 5,
  type: 1,
}).then((res) => (films.future = res.records));

if (userStoreIns.userId) {
  getRecommendFilm(userStoreIns.userId).then((res) => (refreshList.list = res));
}
</script>

<style lang="scss" scoped>
@include wrap(home) {
  padding: 20px 10px;
  box-sizing: border-box;
  width: 100%;

  @include e(static) {
    overflow: hidden;
    background-color: $bg-color;
    border-radius: 5px;
    padding: 10px;
    box-sizing: border-box;
    width: 100%;
    margin-bottom: 15px;

    &--title {
      display: flex;
      justify-content: space-between;
      color: $theme-color;
      font-size: 16px;

      & > a:last-child {
        font-size: 14px;
        color: $second-color;
      }
    }

    &--wrap {
      margin-top: 15px;
      overflow-x: auto;
      overflow-y: hidden;
      width: 100%;
      font-size: 14px;
      color: $main-color;
      white-space: nowrap;

      .film-block {
        width: 100px;
        display: inline-block;
        margin-right: 8px;

        & > p {
          margin: 10px 0;
        }
      }

      .film {
        &-face {
          border-radius: 4px;
          height: 150px;
          background-size: 100% 100%;
          background-repeat: no-repeat no-repeat;
          padding: 5px;
          box-sizing: border-box;
          text-align: left;
          position: relative;
          line-height: 18px;
          font-size: 14px;
          color: #fff;
        }

        &-score {
          position: absolute;
          bottom: 5px;
        }
      }
    }
  }

  @include e(masonry) {
    margin-top: 15px;
    font-size: 16px;
    font-weight: 600;
    color: $main-color;

    &--wrap {
      margin-top: 15px;

      & > a {
        display: inline-block;
        width: calc(50% - 5px);

        &:nth-child(2n) {
          margin-left: 10px;
        }
      }

      .film {
        &-block {
          background-color: #fff;
          border-radius: 5px;
          margin-bottom: 10px;
        }

        &-face {
          height: 240px;
          background-size: 100% 100%;
          background-repeat: no-repeat;
        }

        &-detail {
          padding: 10px;
          box-sizing: border-box;

          & > p {
            font-size: 15px;
            font-weight: 500;
            line-height: 1.7;

            &:last-child {
              font-size: 13px;
              font-weight: 400;
              color: $second-color;
              display: flex;
              gap: 8px;
            }
          }
        }
      }
    }
  }
}
</style>
