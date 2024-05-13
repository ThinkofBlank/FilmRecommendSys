import { Api } from "./api";
import { service } from "@/utils";

export function getFilmList(params: PageParam) {
  return service<PageResult<FilmObject>>({
    url: Api.getFilmList,
    params,
  });
}

export function updateFilm(data: FilmObject) {
  return service({
    url: Api.updateFilm,
    method: "post",
    data,
  });
}

export function getRecommendFilm(id: string) {
  return service<FilmObject[]>({
    url: `${Api.getRecommendFilm}/${id}`,
  });
}

export function getFilmInfo(id: string) {
  return service<FilmObject>({
    url: Api.getFilmInfo + id,
  });
}

export function getAppraiseList(params: PageParam) {
  return service<PageResult<AppraiseObject & FilmObject>>({
    url: Api.getAppraiseList,
    params,
  });
}

export function saveAppraise(data: Omit<AppraiseObject, "id">) {
  return service({
    url: Api.saveAppraise,
    method: "post",
    data,
  });
}

export function delAppraise(id: string) {
  return service<boolean>({
    url: Api.delAppraise + id,
  });
}

export function getThumbList(params: PageParam) {
  return service<PageResult<ThumbUpObject & FilmObject>>({
    url: Api.getThumbList,
    params,
  });
}

export function saveThumb(data: ThumbUpObject) {
  return service<string>({
    url: Api.saveThumb,
    method: "post",
    data,
  });
}

export function delThumb(id: string) {
  return service<boolean>({
    url: Api.delThumb + id,
  });
}

export function getThumbNum(id: string) {
  return service<number>({
    url: Api.getThumbNum + id,
  });
}

export function getFilmTop10() {
  return service<FilmObject[]>({
    url: Api.getFilmTop10,
  });
}
