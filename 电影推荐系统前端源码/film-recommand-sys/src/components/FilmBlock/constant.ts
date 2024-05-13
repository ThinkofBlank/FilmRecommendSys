import { definePropType } from "@/utils";

export const acceptParams = {
  detail: {
    type: definePropType<FilmObject>(Object),
    required: true,
  },
};

export type FilmBlockProps = typeof acceptParams;
