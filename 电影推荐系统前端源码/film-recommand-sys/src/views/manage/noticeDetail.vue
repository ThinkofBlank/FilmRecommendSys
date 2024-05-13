<template>
  <van-form :class="wrapName" label-align="right" :readonly="readonly" colon @submit="saveHandle">
    <van-cell-group inset>
      <van-field
        v-model="form.title"
        required
        name="title"
        label="标题"
        placeholder="请输入标题"
        :rules="rules['title']"
      />

      <van-field
        v-model="form.comment"
        rows="2"
        label="内容"
        autosize
        name="comment"
        type="textarea"
        maxlength="250"
        placeholder="请输入通知信息"
        show-word-limit
        :rules="rules['comment']"
      />
    </van-cell-group>

    <div v-if="!readonly" class="btn-wrap">
      <van-button type="primary" rount block native-type="submit">保存</van-button>
      <van-button v-if="route.query.id" type="danger" rount block @click="delHandle">删除</van-button>
    </div>
  </van-form>
</template>

<script setup lang="ts">
import { useWrap } from "@/hooks";
import { userStore } from "@/store";
import { showConfirmDialog } from "vant";
import { notifySuccess } from "@/utils";
import { delNotice, saveNotice, updateNotice, getNotice } from "@/api";

const { wrapName } = useWrap("noticeDetail");
const route = useRoute();
const router = useRouter();
const userStoreIns = userStore();

const form = reactive({
  title: "",
  comment: "",
});
const rules = {
  title: [{ required: true, message: "请输入标题" }],
  comment: [{ required: true, message: "请输入内容" }],
};
const readonly = computed(() => userStoreIns.userRole !== "1");

if (route.query.id) {
  getNotice(route.query.id as string).then((res) => {
    form.title = res.title;
    form.comment = res.comment;
  });
}

const saveHandle = (val: typeof form) => {
  showConfirmDialog({
    message: "确定保存并发布该通知吗？",
  }).then(() => {
    if (route.query.id) {
      updateNotice({
        id: route.query.id as string,
        ...val,
      }).then(() => {});
    } else {
      saveNotice(val).then(() => {});
    }
    notifySuccess("发布成功！", router.back);
  });
};

const delHandle = () => {
  showConfirmDialog({
    message: "确定删除该通知吗？",
  }).then(() => {
    delNotice(route.query.id as string).then(() => notifySuccess("删除成功！", router.back));
  });
};
</script>

<style lang="scss" scoped>
@include wrap(noticeDetail) {
  margin-top: 10px;
}
</style>
