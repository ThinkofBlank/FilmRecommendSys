import { Api } from "./api";
import { service } from "@/utils";

export function getNoticeList() {
  return service<NoticeObject[]>({
    url: Api.getNoticeList,
  });
}

export function saveNotice(data: NoticeObject) {
  return service({
    url: Api.saveNotice,
    method: "post",
    data,
  });
}

export function updateNotice(data: NoticeObject) {
  return service({
    url: Api.updateNotice,
    method: "post",
    data,
  });
}

export function delNotice(id: string) {
  return service({
    url: `${Api.delNotice}/${id}`,
  });
}

export function getNotice(id: string) {
  return service<NoticeObject>({
    url: `${Api.getNotice}/${id}`,
  });
}
