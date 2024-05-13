<template>
  <div v-if="form" :class="wrapName">
    <img :class="b('face')" :src="getStoreImageUrl(form.imageUrl)" alt="加载封面失败" />

    <div :class="e('wrap')">
      <div :class="['white-block', b('film')]">
        <p :class="bm('film', 'title')">
          {{ form.title }} <van-tag v-if="form.enabled === 1" type="primary">已上架</van-tag>
        </p>
        <p :class="bm('film', 'title-en')">{{ form.directors }} | {{ form.playwright }}</p>
        <p>{{ form.types }}</p>
        <p>{{ form.releaseTime }}</p>

        <van-button v-if="!isAdmin" icon="like-o" size="mini" @click="likeHandle">
          {{ thumbStatus ? "已" : "" }}想看
        </van-button>
        <van-button v-if="!isAdmin" icon="star-o" size="mini" @click="startComment">评分</van-button>
      </div>

      <van-tabs v-model:active="activeName" sticky offset-top="45px" :class="b('comment')">
        <van-tab title="简介">
          <van-text-ellipsis rows="3" :content="form.introduction" expand-text="展开" collapse-text="收起" />

          <van-empty v-if="!form.introduction" description="暂无数据" />
        </van-tab>

        <van-tab :title="`影评${comments.total ?? ''}`">
          <div v-for="item in comments.list" :key="item.id" :class="bm('comment', 'row')">
            <p :class="bm('comment', 'title')">
              {{ item.nickName }}&nbsp;&nbsp;
              <span v-if="item.appraiseTime" :class="bm('comment', 'time')">{{ format(item.appraiseTime) }}</span>
            </p>
            <p>{{ item.comment }}</p>

            <p v-if="isAdmin" class="align-r">
              <van-tag size="medium" @click="delComment(item.id)">删除</van-tag>
            </p>
            <van-rate
              v-model="item.score"
              :class="bm('comment', 'score')"
              :size="10"
              allow-half
              color="#ffd21e"
              void-icon="star"
              void-color="#eee"
              readonly
            />
          </div>

          <van-empty v-if="comments.total <= 0" description="暂无数据" />

          <p v-if="comments.total > comments.list.length" :class="bm('comment', 'more')" @click="loadMore">加载更多</p>
        </van-tab>
      </van-tabs>
    </div>

    <div :class="[e('foot'), 'btn-wrap']">
      <van-button v-if="!isAdmin" type="primary" round block @click="startComment">写个评价</van-button>

      <van-button v-if="isAdmin && form.enabled === 0" type="primary" round block @click="changeStatus(1)">
        上架
      </van-button>

      <van-button v-if="isAdmin && form.enabled === 1" type="danger" round block @click="changeStatus(0)">
        下架
      </van-button>
    </div>

    <van-action-sheet v-model:show="comments.picker.show" title="评价" teleport="body">
      <van-form :class="b('comment-picker')" @submit="onSubmit">
        <van-cell-group inset>
          <van-field name="score" label="评分">
            <template #input>
              <van-rate v-model="comments.picker.score" allow-half color="#ffd21e" void-icon="star" void-color="#eee" />
            </template>
          </van-field>

          <van-field
            v-model="comments.picker.comment"
            name="comment"
            rows="2"
            required
            autosize
            label="评价内容"
            type="textarea"
            maxlength="250"
            placeholder="请输入评价内容"
            show-word-limit
            :rules="comments.pickerRules.comment"
          />
        </van-cell-group>

        <van-button round block type="primary" native-type="submit"> 提交 </van-button>
      </van-form>
    </van-action-sheet>
  </div>
</template>

<script setup lang="ts">
import { useWrap } from "@/hooks";
import { notifySuccess, getStoreImageUrl, format } from "@/utils";
import { showConfirmDialog } from "vant";
import { userStore } from "@/store";
import {
  getFilmInfo,
  getAppraiseList,
  saveAppraise,
  delAppraise,
  getThumbList,
  saveThumb,
  delThumb,
  updateFilm,
} from "@/api";

defineOptions({
  name: "FilmDetail",
});

const { wrapName, b, e, bm } = useWrap("filmDetail");
const route = useRoute();
const userStoreIns = userStore();

const form: Ref<FilmObject> = ref(null);
const activeName = ref("profile");
const comments = reactive({
  page: 1,
  size: 10,
  total: 0,
  list: [],

  picker: {
    show: false,
    score: 0,
    comment: "",
  },

  pickerRules: {
    comment: [{ required: true, message: "请填写评价内容" }],
  },
});
const thumbStatus = ref<string>(null);

const id = computed(() => route.query.id as string);
const isAdmin = computed(() => userStoreIns.userRole === "1");

if (id.value) {
  getFilmInfo(id.value).then((res) => {
    form.value = res;
  });

  loadMore();

  if (isAdmin.value) {
  } else {
    getThumbList({
      current: 1,
      size: 1,
      filmId: id.value,
      userId: userStoreIns.userId,
    }).then((res) => {
      thumbStatus.value = res.total > 0 ? res.records[0].id : null;
    });
  }
}

const likeHandle = async () => {
  if (thumbStatus.value) {
    await delThumb(thumbStatus.value);
    thumbStatus.value = null;
  } else {
    thumbStatus.value = await saveThumb({
      filmId: id.value,
      userId: userStoreIns.userId,
    });
  }
};

function loadMore() {
  getAppraiseList({
    current: comments.page,
    size: comments.size,
    filmId: id.value,
  }).then((res) => {
    comments.list = comments.list.concat(
      res.records.map((r) => ({
        ...r,
        score: Number(r.score || 0),
      })),
    );
    comments.total = res.total;
    comments.page = res.current + 1;
  });
}

function startComment() {
  comments.picker.score = 0;
  comments.picker.comment = "";
  comments.picker.show = true;
}

function changeStatus(enabled: 0 | 1) {
  showConfirmDialog({
    message: "确定执行该操作吗？",
  }).then(async () => {
    await updateFilm({
      ...unref(form),
      enabled,
    });
    notifySuccess("操作成功");
    form.value.enabled = enabled;
  });
}

function delComment(id: string) {
  showConfirmDialog({
    message: "确定执行该操作吗？",
  }).then(async () => {
    await delAppraise(id);
    notifySuccess("操作成功");
    const index = comments.list.findIndex((l) => l.id === id);
    if (index > -1) {
      comments.list.splice(index, 1);
      comments.total--;
    }
  });
}

function onSubmit(val: typeof comments.picker) {
  showConfirmDialog({
    message: "确认发布该评论吗？",
  }).then(async () => {
    await saveAppraise({
      userId: userStoreIns.userId,
      filmId: id.value,
      score: val.score,
      comment: val.comment,
    });

    comments.page = 1;
    comments.list.splice(0);
    loadMore();

    notifySuccess("发布成功");
    comments.picker.show = false;
  });
}
</script>

<style lang="scss" scoped>
@include wrap(filmDetail) {
  @include b(face) {
    width: 100%;
    height: 280px;
  }

  @include e(wrap) {
    position: relative;
    top: -60px;
    background-color: #efefef;
    border-radius: 10px 10px 0 0;
    padding: 10px;
    box-sizing: border-box;
    margin-bottom: 65px;

    @include b("film") {
      font-size: 12px;
      color: $main-color;
      line-height: 20px;
      text-align: left;

      &--title {
        font-size: 16px;
      }

      &--title-en {
        line-height: 14px;
      }
    }

    @include b(comment) {
      margin-top: 15px;
      font-size: 14px;
      text-align: left;
      line-height: 16px;

      &--row {
        margin-bottom: 15px;
        position: relative;
        padding: 10px 0;
        box-sizing: border-box;
        border-bottom: 1px solid var(--van-border-color);
      }

      &--title {
        font-size: 16px;
        font-weight: 600;
        margin-bottom: 5px;
      }

      &--time {
        color: $second-color;
        font-size: 12px;
        margin-bottom: 15px;
      }

      &--score {
        position: absolute;
        right: 10px;
        top: 10px;
      }

      &--more {
        text-align: center;
        margin: 20px 0;
      }

      :deep() .van-tabs {
        &__content {
          background-color: $bg-color;
          padding: 10px;
          box-sizing: border-box;
        }
      }
    }
  }

  @include e(foot) {
    position: fixed;
    bottom: 0;
    width: 100%;
    margin: 0;
    background-color: $bg-color;
  }

  @include b(comment-picker) {
    padding: 10px;
  }
}
</style>
