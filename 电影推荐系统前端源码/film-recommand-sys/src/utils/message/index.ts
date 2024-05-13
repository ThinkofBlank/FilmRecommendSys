import { showToast, showDialog, showNotify, showImagePreview } from "vant";

export const notifySuccess = (message: string, onClose?: () => void) =>
  showNotify({
    type: "success",
    message,
    onClose,
  });

export const notifyError = (message: string, onClose?: () => void) =>
  showNotify({
    type: "danger",
    message,
    onClose,
  });
