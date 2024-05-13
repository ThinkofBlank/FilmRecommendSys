import SvgIcon from "./SvgIcon.vue";
import { propsParams } from "./svg-icon.constant";

import type { ExtractPropTypes } from "vue";

export type SvgIconInstance = InstanceType<typeof SvgIcon>;
export type SvgIconProps = ExtractPropTypes<typeof propsParams>;
