import { globalSettings } from "@/core/settings";

export const useWrap = (component: string) => {
  const wrapName = globalSettings.PACKAGE.NAME.endsWith("/")
    ? `${globalSettings.PACKAGE.NAME.substring(0, globalSettings.PACKAGE.NAME.length - 1)}-${component}`
    : `${globalSettings.PACKAGE.NAME}-${component}`;

  const b = (block: string) => `${wrapName}-${block}`;
  const e = (element: string) => `${wrapName}_${element}`;
  const m = (modify: string) => `${wrapName}--${modify}`;
  const be = (block: string, element: string) => `${wrapName}-${block}_${element}`;
  const bm = (block: string, modify: string) => `${wrapName}-${block}--${modify}`;
  const em = (element: string, modify: string) => `${wrapName}_${element}--${modify}`;
  const bem = (block: string, element: string, modify: string) => `${wrapName}-${block}_${element}--${modify}`;

  return {
    wrapName,
    b,
    e,
    m,
    be,
    bm,
    em,
    bem,
  };
};
