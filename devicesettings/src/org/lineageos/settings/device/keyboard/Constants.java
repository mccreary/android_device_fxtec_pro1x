/*
 * Copyright (C) 2022 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.settings.device.keyboard;

public class Constants {
    // Keyboard layout
    public static final String KEYBOARD_LAYOUT_KEY = "keyboard_layout";
    public static final String KEYBOARD_LAYOUT_PROPERTY = "sys.keyboard.layout";
    public static final String KEYBOARD_LAYOUT_SYS_FILE =
            "/sys/devices/platform/soc/4a84000.i2c/i2c-0/0-005b/layout";

    // Keyboard keymap
    public static final String KEYBOARD_KEYMAP_CUSTOM_KEY = "keyboard_keymap_custom";
    public static final String KEYBOARD_KEYMAP_CFG_FILE = "/data/system/keyboard/keymap";
    public static final String KEYBOARD_KEYMAP_SPACEPOWER_KEY = "keyboard_keymap_spacepower";
    public static final String[] KEYBOARD_KEYMAP_SPACEPOWER_TEXT = {
        "48:0039:0074"
    };
    public static final String KEYBOARD_KEYMAP_FNKEYS_KEY = "keyboard_keymap_fnkeys";
    public static final String[] KEYBOARD_KEYMAP_FNKEYS_TEXT = {
        "57:0002:003b",
        "60:0003:003c",
        "8:0004:003d",
        "61:0005:003e",
        "55:0006:003f",
        "19:0007:0040",
        "3:0008:0041",
        "53:0009:0042",
        "13:000a:0043",
        "45:000b:0044",
        "29:000c:0057",
        "37:000d:0058"
    };
    public static final String KEYBOARD_KEYMAP_ALTGR_KEY = "keyboard_keymap_altgr";
    public static final String[] KEYBOARD_KEYMAP_ALTGR_TEXT = {
        "69:0064:0064"
    };
    public static final String KEYBOARD_KEYMAP_SYS_FILE =
            "/sys/devices/platform/soc/4a84000.i2c/i2c-0/0-005b/keymap";

    // Keyboard poll interval
    public static final String KEYBOARD_FASTPOLL_KEY = "keyboard_fastpoll";
    public static final int KEYBOARD_POLL_INTERVAL_DEFAULT = 40;
    public static final int KEYBOARD_POLL_INTERVAL_FAST = 20;
    public static final int KEYBOARD_POLL_INTERVAL_SLOW = 40;
    public static final String KEYBOARD_POLL_INTERVAL_SYS_FILE =
            "/sys/devices/platform/soc/4a84000.i2c/i2c-0/0-005b/poll_interval";
}
