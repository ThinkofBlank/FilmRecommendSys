<template>
  <div :class="wrapName">
    <div :class="b('row')">
      <p :class="b('title')">基本信息</p>

      <person-info :key="form.id" :form="form" readonly />
    </div>

    <div v-if="isUser" :class="b('row')">
      <p :class="b('title')">兴趣爱好</p>

      <div ref="hobbyChartRef" :class="e('charts')"></div>
    </div>

    <div v-if="isUser" :class="b('row')">
      <p :class="b('title')">评论记录</p>

      <van-swipe-cell v-for="item in commentRecords.list" :key="item.id">
        <film-block :detail="item" @click="detailHandle(item.filmId)">
          <p>{{ format(item.appraiseTime) }}评论：{{ item.comment }}</p>
        </film-block>

        <template #right>
          <van-button square type="danger" :class="b('row-del-btn')" text="删除" @click="delComment(item.id)" />
        </template>
      </van-swipe-cell>

      <p v-if="commentRecords.list.length < commentRecords.total" :class="e('loadmore')" @click="loadmoreComment">
        加载更多
      </p>

      <van-empty v-if="commentRecords.total === 0" description="没有找到记录" />
    </div>

    <div v-if="isUser" :class="b('row')">
      <p :class="b('title')">点赞记录</p>

      <van-swipe-cell v-for="item in likeRecords.list" :key="item.id">
        <film-block :detail="item" @click="detailHandle(item.filmId)">
          <p>{{ format(item.thumbUpTime) }}点赞</p>
        </film-block>

        <template #right>
          <van-button square type="danger" :class="b('row-del-btn')" text="删除" @click="delLike(item.id)" />
        </template>
      </van-swipe-cell>

      <p v-if="likeRecords.list.length < likeRecords.total" :class="e('loadmore')" @click="loadmoreLike"> 加载更多 </p>

      <van-empty v-if="likeRecords.total === 0" description="没有找到记录" />
    </div>

    <div :class="[b('row'), b('btn-wrap')]">
      <van-button v-if="form.adminStatus === '1'" type="primary" block round @click="checkHandle('2')">
        通过
      </van-button>
      <van-button v-if="form.adminStatus === '1'" type="danger" block round @click="checkHandle('1')">
        不通过
      </van-button>

      <van-button
        v-if="form.adminStatus === '0' && form.enabled === '1'"
        type="danger"
        block
        round
        @click="checkPersonHandle('0')"
      >
        禁用人员
      </van-button>
      <van-button
        v-if="form.adminStatus === '0' && form.enabled === '0'"
        type="primary"
        block
        round
        @click="checkPersonHandle('1')"
      >
        启用人员
      </van-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useWrap } from "@/hooks";
import { showConfirmDialog } from "vant";
import { useHobbyChart } from "./peopleDetail.constant";
import { getAppraiseList, getThumbList, getLikeRecords, delAppraise, delThumb, updateUserInfo, delUser } from "@/api";
import { format, notifyError, notifySuccess } from "@/utils";

import type { DataType } from "./peopleDetail.constant";

const { wrapName, b, e } = useWrap("peopleDetail");
const router = useRouter();
const route = useRoute();
let renderHobbyChart: (data: DataType[]) => void = null;

const hobbyChartRef = ref();
const form = ref<UserInfo>({
  account: "",
  phone: "",
  nickname: "",
  username: "",
  enabled: "0",
  auditStatus: "1", // 1 待审核
  adminStatus: "0",
});
const commentRecords = reactive({
  current: 1,
  size: 3,
  total: 10,
  list: [],
});
const likeRecords = reactive({
  current: 1,
  size: 3,
  total: 10,
  list: [],
});

const userId = computed(() => route.query.id as string);
const isUser = computed(() => form.value.adminStatus === "0");

onMounted(() => {
  if (userId.value) {
    getLikeRecords({
      userId: userId.value,
    }).then((res) => {
      form.value = res.userInfo;

      if (isUser.value) {
        renderHobbyChart = useHobbyChart(hobbyChartRef.value);
        renderHobbyChart(
          Object.keys(res.eCharts).map((r) => ({
            name: r,
            value: res.eCharts[r],
          })),
        );

        loadmoreComment();
        loadmoreLike();
      }
    });
  }
});

// 人员审核
function checkHandle(auditStatus: "1" | "2") {
  showConfirmDialog({
    message: "确定执行该操作吗？",
  }).then(() => {
    if (auditStatus === "1") {
      return delUser(form.value.id).then(() => {
        notifySuccess("操作成功");
        router.back();
      });
    }

    updateUserInfo({
      ...unref(form),
      enabled: auditStatus === "2" ? "1" : "0",
      auditStatus,
    }).then(() => {
      notifySuccess("操作成功");
      form.value.auditStatus = auditStatus;
      router.back();
    });
  });
}
function checkPersonHandle(enabled: "0" | "1") {
  showConfirmDialog({
    message: "确定执行该操作吗？",
  }).then(() => {
    updateUserInfo({
      ...unref(form),
      enabled,
    }).then(() => {
      notifySuccess("操作成功");
      form.value.enabled = enabled;
    });
  });
}

function delComment(id: string) {
  showConfirmDialog({
    message: "确定删除吗？",
  }).then(() => {
    delAppraise(id).then((res) => {
      if (res) {
        const index = commentRecords.list.findIndex((l) => l.id === id);
        if (index !== -1) {
          commentRecords.list.splice(index, 1);
          commentRecords.total--;
        }

        notifySuccess("删除成功");
      } else {
        notifyError("删除失败");
      }
    });
  });
}

function loadmoreComment() {
  if (commentRecords.list.length >= commentRecords.total) {
    return;
  }

  getAppraiseList({
    current: commentRecords.current,
    size: commentRecords.size,
    userId: userId.value,
  }).then((res) => {
    commentRecords.current = res.current + 1;
    commentRecords.total = res.total;
    commentRecords.list = commentRecords.list.concat(res.records);
  });
}

function delLike(id: string) {
  showConfirmDialog({
    message: "确定删除吗？",
  }).then(() => {
    delThumb(id).then((res) => {
      if (res) {
        const index = likeRecords.list.findIndex((l) => l.id === id);
        if (index !== -1) {
          likeRecords.list.splice(index, 1);
          likeRecords.total--;
        }
        notifySuccess("删除成功");
      } else {
        notifyError("删除失败");
      }
    });
  });
}
function loadmoreLike() {
  if (likeRecords.list.length >= likeRecords.total) {
    return;
  }

  getThumbList({
    current: commentRecords.current,
    size: commentRecords.size,
    userId: userId.value,
  }).then((res) => {
    likeRecords.current = res.current + 1;
    likeRecords.total = res.total;
    likeRecords.list = likeRecords.list.concat(
      res.records.map((r) => ({
        ...r,
        score: r.filmScore,
      })),
    );
  });
}

function detailHandle(id: string) {
  router.push({
    path: "/film-detail",
    query: {
      id,
    },
  });
}
</script>

<style lang="scss" scoped>
@include wrap(peopleDetail) {
  text-align: left;
  font-size: 14px;
  color: $main-color;

  @include b(row) {
    margin-top: 10px;
  }

  @include b(title) {
    line-height: 18px;
    color: $second-color;
    margin: 10px;
  }

  @include b(btn-wrap) {
    margin: 15px 10px;

    :deep() .van-button {
      margin-bottom: 10px;
    }
  }

  @include b(row-del-btn) {
    height: 100%;
  }

  @include e(charts) {
    width: 100%;
    height: 260px;
    overflow: hidden;
  }

  @include e(loadmore) {
    text-align: center;
    color: $second-color;
    margin: 10px 0;
  }
}
</style>
