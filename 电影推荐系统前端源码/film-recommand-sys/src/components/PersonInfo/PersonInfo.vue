<template>
  <van-form :class="wrapName" label-align="right" :readonly="readonly" colon @submit="onSubmit">
    <van-cell-group inset>
      <van-field
        v-model="formReactive.account"
        readonly
        required
        name="account"
        label="账号"
        placeholder="请输入账号"
        :rules="rules['account']"
      />
      <van-field
        v-model="formReactive.nickname"
        required
        name="nickname"
        label="昵称"
        placeholder="请输入昵称"
        :rules="rules['nickname']"
      />
      <van-field
        v-model="formReactive.username"
        required
        name="username"
        label="姓名"
        placeholder="请输入姓名"
        :rules="rules['username']"
      />
      <van-field
        v-model="formReactive.phone"
        required
        name="phone"
        label="联系方式"
        placeholder="请输入手机号"
        :rules="rules['phone']"
      />

      <slot></slot>
    </van-cell-group>

    <div v-if="!readonly" :class="b('btn-wrap')">
      <van-button type="primary" block round native-type="submit">保存</van-button>
    </div>
  </van-form>
</template>

<script setup lang="ts">
import { useWrap } from "@/hooks";
import { acceptParams, form, rules } from "./constant";
import { showConfirmDialog } from "vant";

defineOptions({
  name: "PersonInfo",
});
const props = defineProps(acceptParams);
const emits = defineEmits(["onSubmit"]);

const { wrapName, b } = useWrap("personInfo");

const formReactive = reactive({
  ...form,
  ...props.form,
});

const onSubmit = (val: typeof form) => {
  showConfirmDialog({
    confirmButtonColor: "#e95c5c",
    message: "确认修改吗？",
  }).then(() => emits("onSubmit", val));
};
</script>

<style lang="scss" scoped>
@include wrap(personInfo) {
  :deep() .van-cell-group--inset {
    margin: 15px 0;
    border-radius: 0;
  }

  @include b(btn-wrap) {
    margin: 15px 10px;
  }
}
</style>
