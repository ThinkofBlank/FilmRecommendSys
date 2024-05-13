import dayjs from "dayjs";

export const format = (time?: string | number, formatter = "YYYY-MM-DD HH:mm") => dayjs(time).format(formatter);
