import { definePropType } from "@/utils";

export const propsParams = {
  iconClass: {
    type: definePropType<string>(String),
    required: true,
  },
  className: {
    type: definePropType<string>(String),
    default: "",
  },
};
