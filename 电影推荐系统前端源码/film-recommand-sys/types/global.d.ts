interface UserInfo {
  id?: string;
  account: string;
  username: string;
  phone: string;
  nickname: string;
  password?: string;
  enabled?: "0" | "1"; // 0: 禁用 1：启用
  adminStatus: "0" | "1"; // 0： 不是 1： 是
  auditStatus: "1" | "2"; // 1: 未通过 2：已通过
}

type FilmType = -1 | 0 | 1 | 2; // -1：全部 0： 热映 1：未上映 2： 已上映

type FilmObject = {
  id: string;
  title: string;
  imageUrl: string;
  directors: string;
  playwright: string; // 主演
  actors: string; // 演员
  types: string;
  releaseTime: string; // 发布时间
  score: string | number;
  introduction: string;
  enabled: 0 | 1; // 0：不可使用 1： 可使用
  thumbCount?: number; // 点赞数量
  status?: FilmType;
};

type NoticeObject = {
  id?: string;
  comment: string;
  title: string;
  enabled?: 0 | 1;
  noticeTime?: number;
};

type AppraiseObject = {
  id?: string;
  filmId: string;
  userId: string;
  nickName?: string;
  comment: string;
  score: string | number;
  filmScore?: string | number;
  appraiseTime?: string;
};

type ThumbUpObject = {
  id?: string;
  filmId: string;
  userId: string;
  nickName?: string;
  thumbUpTime?: string;
  filmScore?: string | number;
};

type PageParam = {
  current: number;
  size: number;
  [K: string]: any;
};

type PageResult<T> = {
  current: number;
  size: number;
  total: number;
  records: T[];
};

type Recordable<T = any> = Record<string, T>;
