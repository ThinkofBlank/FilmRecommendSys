<template>
  <div :class="wrapName">
    <person-info :form="userStoreIns.userInfo" :readonly="readonly" @on-submit="saveForm" />

    <div :class="b('btn-wrap')">
      <van-button v-show="readonly" type="primary" block round @click="readonly = !readonly">修改个人信息</van-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useWrap } from "@/hooks";
import { userStore } from "@/store";
import { updateUserInfo } from "@/api";
import { notifySuccess } from "@/utils";

import type { PersonInfoForm } from "@/components/PersonInfo";

defineOptions({
  name: "MyInfo",
});

const { wrapName, b } = useWrap("userInfo");
const userStoreIns = userStore();

const readonly = ref(true);

const saveForm = async (form: PersonInfoForm) => {
  await updateUserInfo({
    ...userStoreIns.userInfo,
    ...form,
  });
  notifySuccess("修改成功！");
  readonly.value = !readonly.value;

  userStoreIns.setUserInfo(form);
};
</script>

<style lang="scss" scoped>
@include wrap(userInfo) {
  @include b(btn-wrap) {
    margin: 15px 10px;
  }
}
</style>
