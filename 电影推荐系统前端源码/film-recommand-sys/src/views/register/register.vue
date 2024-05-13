<template>
  <div :class="['full-screen', wrapName]">
    <div :class="[e('title')]">账号注册</div>

    <van-form ref="formRef" :class="[e('form'), 'theme-form']" @submit="onSubmit">
      <van-cell-group inset>
        <van-field
          v-model="form.nickname"
          :rules="rules['nickname']"
          name="nickname"
          placeholder="请输入昵称"
          maxlength="20"
        />
        <van-field
          v-model="form.username"
          :rules="rules['username']"
          name="username"
          placeholder="请输入姓名"
          maxlength="20"
        />
        <van-field
          v-model="form.account"
          :rules="rules['account']"
          name="account"
          placeholder="请输入账户名"
          maxlength="20"
        />
        <van-field
          v-model="form.phone"
          :rules="rules['phone']"
          name="phone"
          placeholder="请输入手机号"
          type="tel"
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

        <van-field name="role" class="operate-line">
          <template #input>
            <van-radio-group v-model="form.role" direction="horizontal">
              <van-radio name="0">普通用户</van-radio>
              <van-radio name="1">管理员（需审核）</van-radio>
            </van-radio-group>
          </template>
        </van-field>

        <van-button class="operate-line" round block type="primary" native-type="submit" :loading="loading">
          提交
        </van-button>
      </van-cell-group>
    </van-form>
  </div>
</template>

<script setup lang="ts">
import { useWrap } from "@/hooks";
import { notifySuccess } from "@/utils";
import { regExp } from "@/core";
import { register } from "@/api";

const { wrapName, b, e, m } = useWrap("register");
const router = useRouter();

const rules = {
  account: [{ required: true, message: "请输入账号名" }],
  username: [{ required: true, message: "请输入姓名" }],
  phone: [
    { required: true, message: "请输入手机号" },
    { validator: (val: string) => regExp.phone.test(val), message: "手机号格式不正确" },
  ],
  nickname: [{ required: true, message: "请输入昵称" }],
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
  account: "",
  username: "",
  phone: "",
  nickname: "",
  password: "",
  passwordVerify: "",
  role: "0" as "0" | "1",
});
const loading = ref(false);

const onSubmit = (fields: typeof form) => {
  const { passwordVerify, role, ..._fields } = fields;

  loading.value = true;

  register({
    ..._fields,
    adminStatus: role,
    auditStatus: role === "1" ? "1" : "2",
  })
    .then(() => {
      notifySuccess(`提交成功，${role === "1" ? "请等待管理员审核" : "立即开始您的电影推荐之旅吧"}！`, () =>
        router.replace("/login"),
      );
    })
    .finally(() => (loading.value = false));
};
</script>

<style lang="scss" scoped>
@include wrap(register) {
  padding: 20px 10px;

  @include e(title) {
    font-size: 18px;
    font-weight: 600;
  }

  @include e(form) {
    margin-top: 30px;
  }
}
</style>
