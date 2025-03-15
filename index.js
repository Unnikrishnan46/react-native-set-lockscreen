import { NativeModules } from "react-native";

const { SetLockscreen } = NativeModules;

/**
 * Sets the lock screen wallpaper
 * @param {string} imageUrl - The image URL to be set as lock screen wallpaper
 * @returns {Promise<string>}
 */
export function setLockScreen(imageUrl) {
  return SetLockscreen.setLockScreen(imageUrl);
}
