import { Api } from "./api";
import { service } from "@/utils";

export function register(data: UserInfo) {
  return service({
    url: Api.register,
    method: "post",
    data,
  });
}

export function login(data: { account: string; password: string; adminStatus: string }) {
  return service<string>({
    url: Api.login,
    method: "post",
    data,
  });
}

export function resetPassword(data: { account: string; phone: string; password: string }) {
  return service<null>({
    url: Api.resetPassword,
    method: "post",
    data,
  });
}

export function updateUserInfo(data: Partial<UserInfo>) {
  return service({
    url: Api.update,
    method: "post",
    data,
  });
}

export function logout(id: string) {
  return service({
    url: Api.logout + id,
  });
}

export function delUser(id: string) {
  return service({
    url: Api.delUser + id,
  });
}

export function getUserInfo(userId?: string) {
  return service<UserInfo>({
    url: Api.getUserInfo,
    params: {
      userId,
    },
  });
}
