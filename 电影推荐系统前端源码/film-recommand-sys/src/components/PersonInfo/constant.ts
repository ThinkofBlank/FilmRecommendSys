import { definePropType } from "@/utils";
import { regExp } from "@/core";

export const acceptParams = {
  readonly: Boolean,
  form: {
    type: definePropType<typeof form>(Object),
    required: true,
  },
};

export const form: UserInfo = {
  id: "",
  nickname: "",
  username: "",
  account: "",
  phone: "",
  adminStatus: "0" as "0" | "1",
  auditStatus: "1",
};

export const rules = {
  account: [{ required: true, message: "请输入账号名" }],
  username: [{ required: true, message: "请输入姓名" }],
  phone: [
    { required: true, message: "请输入手机号" },
    { validator: (val: string) => regExp.phone.test(val), message: "手机号格式不正确" },
  ],
  nickname: [{ reqired: true, message: "请输入昵称" }],
};

export type PersonInfoProps = typeof acceptParams;
export type PersonInfoForm = typeof form;
