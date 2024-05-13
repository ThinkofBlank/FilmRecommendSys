<template>
  <div :class="['full-screen', wrapName, userStoreIns.token ? m('logined') : '']">
    <div v-if="!userStoreIns.token" :class="[e('title')]">密码重置</div>

    <van-form ref="formRef" :class="[e('form'), 'theme-form']" @submit="onSubmit">
      <van-cell-group inset>
        <van-field
          v-model="form.account"
          :rules="rules['account']"
          name="account"
          placeholder="请输入账号"
          :disabled="!!userStoreIns.token"
          maxlength="20"
        />
        <van-field
          v-model="form.phone"
          :rules="rules['phone']"
          name="phone"
          type="tel"
          :disabled="!!userStoreIns.token"
          placeholder="请输入联系电话"
          maxlength="11"
        />
        <van-field
          v-model="form.password"
          :rules="rules['password']"
          name="password"
          placeholder="请输入密码"
          type="password"
          maxlength="20"
        />
        <van-field
          v-model="form.passwordVerify"
          :rules="rules['passwordVerify']"
          name="passwordVerify"
          placeholder="请再次输入密码"
          type="password"
          maxlength="20"
        />

        <van-button class="operate-line" round block type="primary" native-type="submit"> 立即重置 </van-button>
      </van-cell-group>
    </van-form>
  </div>
</template>

<script setup lang="ts">
import { useWrap } from "@/hooks";
import { notifySuccess } from "@/utils";
import { regExp } from "@/core";
import { userStore } from "@/store";
import { resetPassword } from "@/api";

const { wrapName, b, e, m } = useWrap("reset-pwd");
const userStoreIns = userStore();
const router = useRouter();

const rules = {
  account: [{ required: true, message: "请输入账号名" }],
  phone: [
    { required: true, message: "请输入联系电话" },
    { validator: (val: string) => regExp.phone.test(val), message: "手机号格式不正确" },
  ],
  password: [{ required: true, message: "请输入密码" }],
  passwordVerify: [
    { required: true, message: "请输入密码" },
    {
      validator: (val: string) => form.password === val,
      message: "两次密码不一致",
    },
  ],
};
const form = reactive({
  account: userStoreIns.userInfo?.account ?? "",
  phone: userStoreIns.userInfo?.phone ?? "",
  password: "",
  passwordVerify: "",
});

const onSubmit = (fields: typeof form) =>
  resetPassword(fields).then((res) => notifySuccess("重置成功！", () => router.replace("/login")));
</script>

<style lang="scss" scoped>
@include wrap(reset-pwd) {
  padding: 20px 10px;

  @include e(title) {
    font-size: 18px;
    font-weight: 600;
  }

  @include e(form) {
    margin-top: 30px;
  }

  @include m(logined) {
    padding: 0;
    background-color: $bg-color;
  }
}
</style>
