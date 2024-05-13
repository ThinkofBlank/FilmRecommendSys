import { service } from "@/utils";
import { Api } from "./api";

export function getUserList(params: PageParam & Partial<UserInfo>) {
  return service<PageResult<UserInfo[]>>({
    url: Api.getUserList,
    params,
  });
}

export function getUserCount(data: Partial<UserInfo>) {
  return service<number>({
    url: Api.getUserCount,
    method: "post",
    data,
  });
}

export function getLikeRecords(params?: { userId?: string }) {
  return service<{
    eCharts: Recordable<number>;
    userInfo: UserInfo;
  }>({
    url: Api.getLike,
    params,
  });
}
