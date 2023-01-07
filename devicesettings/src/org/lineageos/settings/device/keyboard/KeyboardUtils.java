/*
 * Copyright (C) 2022 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.settings.device.keyboard;

import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.OutputStreamWriter;

public class KeyboardUtils {

    private static final String TAG = KeyboardUtils.class.getSimpleName();
    private static final boolean DEBUG = false;

    public static boolean installCustomKeymap() {
        if (DEBUG) Log.d(TAG, "Installing custom keymap");
        BufferedReader in = null;
        BufferedWriter out = null;

        try {
            in = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(
                        new File(Constants.KEYBOARD_KEYMAP_CFG_FILE)
                    )
                )
            );
            if (DEBUG) Log.d(TAG, "Opened input: " + Constants.KEYBOARD_KEYMAP_CFG_FILE);

            out = new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream(
                        new File(Constants.KEYBOARD_KEYMAP_SYS_FILE)
                    )
                )
            );
            if (DEBUG) Log.d(TAG, "Opened output: " + Constants.KEYBOARD_KEYMAP_SYS_FILE);

            for (String line; (line = in.readLine()) != null;) {
                out.write(line);
                out.newLine();
            }
        } catch(FileNotFoundException e) {
            Log.e(TAG, "FileNotFoundException: ", e);
            return false;
        } catch(IOException e) {
            Log.e(TAG, "IOException: ", e);
            return false;
        } finally {
            try {
                out.close();
                in.close();
            } catch(IOException e) {
                Log.e(TAG, "IOException: ", e);
                return false;
            }
        }

        if (DEBUG) Log.d(TAG, "Wrote custom keymap to kernel");
        return true;
    }

    public static void setKeyboardKeymap(SharedPreferences prefs) {
        if (!prefs.contains(Constants.KEYBOARD_KEYMAP_CUSTOM_KEY)) {
            File f = new File(Constants.KEYBOARD_KEYMAP_CFG_FILE);
            prefs.edit().putBoolean(Constants.KEYBOARD_KEYMAP_CUSTOM_KEY, f.exists()).commit();
        }
        if (prefs.getBoolean(Constants.KEYBOARD_KEYMAP_CUSTOM_KEY, false)) {
            installCustomKeymap();
        } else {
            if (prefs.getBoolean(Constants.KEYBOARD_KEYMAP_SPACEPOWER_KEY, false)) {
                for (int i = 0; i < Constants.KEYBOARD_KEYMAP_SPACEPOWER_TEXT.length; ++i) {
                    writeFile(Constants.KEYBOARD_KEYMAP_SYS_FILE,
                            Constants.KEYBOARD_KEYMAP_SPACEPOWER_TEXT[i] + "\n");
                }
            }

            if (prefs.getBoolean(Constants.KEYBOARD_KEYMAP_FNKEYS_KEY, false)) {
                for (int i = 0; i < Constants.KEYBOARD_KEYMAP_FNKEYS_TEXT.length; ++i) {
                    writeFile(Constants.KEYBOARD_KEYMAP_SYS_FILE,
                            Constants.KEYBOARD_KEYMAP_FNKEYS_TEXT[i] + "\n");
                }
            }

            if (prefs.getBoolean(Constants.KEYBOARD_KEYMAP_ALTGR_KEY, false)) {
                for (int i = 0; i < Constants.KEYBOARD_KEYMAP_ALTGR_TEXT.length; ++i) {
                    writeFile(Constants.KEYBOARD_KEYMAP_SYS_FILE,
                            Constants.KEYBOARD_KEYMAP_ALTGR_TEXT[i] + "\n");
                }
            }
        }
    }

    public static void setKeyboardPollInterval(SharedPreferences prefs) {
        final int interval = prefs.getBoolean(Constants.KEYBOARD_FASTPOLL_KEY, false) ?
                Constants.KEYBOARD_POLL_INTERVAL_FAST : Constants.KEYBOARD_POLL_INTERVAL_SLOW;
        writeFile(Constants.KEYBOARD_POLL_INTERVAL_SYS_FILE, Integer.toString(interval));
    }

    public static boolean writeFile(String filename, String text) {
        boolean result = false;
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(text);
            writer.flush();
            result = true;
        }
        catch (Exception e) { /* Ignore */ }
        return result;
    }
}
