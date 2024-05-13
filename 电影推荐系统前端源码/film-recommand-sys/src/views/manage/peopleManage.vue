<template>
  <div :class="wrapName">
    <van-pull-refresh v-model="result.refreshing" @refresh="onRefresh">
      <div v-if="result.preAdminList.length > 0" :class="b('row')">
        <p :class="b('title')">待审核管理员列表</p>

        <van-contact-card
          v-for="item in result.preAdminList"
          :key="item.id"
          type="edit"
          :tel="item.phone"
          :name="`${item.nickname}（${item.username}）`"
          @click="personDetail(item.id)"
        ></van-contact-card>
      </div>

      <div v-if="result.adminList.length > 0" :class="b('row')">
        <p :class="b('title')">管理员列表</p>

        <van-contact-card
          v-for="item in result.adminList"
          :key="item.id"
          type="edit"
          :tel="item.phone"
          :name="`${item.nickname}（${item.username}）`"
          :editable="false"
        ></van-contact-card>
      </div>

      <div :class="b('row')">
        <p :class="b('title')">人员列表</p>

        <van-list
          v-model:loading="result.loading"
          :finished="result.finished"
          finished-text="没有更多了"
          @load="onLoad"
        >
          <van-contact-card
            v-for="item in result.list"
            :key="item.id"
            type="edit"
            :tel="item.phone"
            :name="`${item.nickname}（${item.username}）`"
            @click="personDetail(item.id)"
          ></van-contact-card>
        </van-list>

        <van-empty v-if="result.list.length <= 0" description="暂无数据" />
      </div>
    </van-pull-refresh>
  </div>
</template>

<script setup lang="ts">
import { useWrap } from "@/hooks";
import { getUserList } from "@/api";

defineOptions({
  name: "PeopleManage",
});

const { wrapName, b } = useWrap("peopleManage");
const router = useRouter();

const result = reactive({
  loading: false,
  finished: false,
  refreshing: false,

  current: 1,
  size: 10,
  list: [],

  adminList: [],
  preAdminList: [],
});

// 待审核管理员列表
getUserList({
  current: 1,
  size: 20,
  auditStatus: "1",
  adminStatus: "1",
}).then((p) => {
  result.preAdminList = p.records;
});

// 管理员列表
getUserList({
  current: 1,
  size: 20,
  auditStatus: "2",
  adminStatus: "1",
}).then((p) => {
  result.adminList = p.records;
});

const onLoad = () => {
  if (result.refreshing) {
    result.current = 1;
    result.list.splice(0);
    result.refreshing = false;
  }

  getUserList({
    current: result.current,
    size: result.size,
    adminStatus: "0",
  }).then((p) => {
    result.current = p.current + 1;
    result.list = result.list.concat(p.records);
    result.loading = false;

    if (result.list.length >= p.total) {
      result.current = 1;
      result.finished = true;
    }
  });
};

const onRefresh = () => {
  // 清空列表数据
  result.finished = false;

  // 重新加载数据
  // 将 loading 设置为 true，表示处于加载状态
  result.loading = true;
  onLoad();
};

const personDetail = (id: string) =>
  router.push({
    path: "/people-detail",
    query: {
      id,
    },
  });
</script>

<style lang="scss" scoped>
@include wrap(peopleManage) {
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
}
</style>
