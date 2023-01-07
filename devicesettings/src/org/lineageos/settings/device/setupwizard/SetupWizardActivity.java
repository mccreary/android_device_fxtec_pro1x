/*
 * Copyright (C) 2022 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.settings.device.setupwizard;

import android.os.SystemProperties;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import org.lineageos.internal.util.FileUtils;
import org.lineageos.settings.device.keyboard.Constants;
import org.lineageos.settings.device.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SetupWizardActivity extends SetupWizardBaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.setupwizard;
    }

    @Override
    protected int getTitleResId() {
        return R.string.setupwizard_title;
    }

    @Override
    protected int getIconResId() {
        return R.drawable.ic_keyboard_outline;
    }

    @Override
    protected void setupPage() {
        final Spinner spinner = (Spinner) findViewById(R.id.kbd_layout_list);
        final SimpleAdapter adapter = constructKeylayoutAdapter();
        spinner.setAdapter(adapter);
        spinner.setSelection(getSelectedIndex());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] values = getResources().getStringArray(R.array.keyboard_layout_values);
                SystemProperties.set(Constants.KEYBOARD_LAYOUT_PROPERTY, values[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });
    }

    private SimpleAdapter constructKeylayoutAdapter() {
        final String KEY_DISPLAYNAME = "name";

        final String[] choices = getResources().getStringArray(R.array.keyboard_layout_titles);
        final String[] from = new String[] { KEY_DISPLAYNAME };
        final int[] to = new int[] {android.R.id.text1};

        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        for (int i = 0 ; i < choices.length; i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(KEY_DISPLAYNAME, choices[i]);
            arrayList.add(hashMap);
        }

        // we use different layouts for the spinner when opened and closed to imitate the look
        // of other SuW pages (e.g. the timezone picker when closed)
        final SimpleAdapter adapter = new SimpleAdapter(this, arrayList,
                R.layout.kbd_layout_list_selected, from, to);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        return adapter;
    }

    private int getSelectedIndex() {
        String value = FileUtils.readOneLine(Constants.KEYBOARD_LAYOUT_SYS_FILE);
        value = value.substring(0, 6);
        String[] values = getResources().getStringArray(R.array.keyboard_layout_values);
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(value)) {
                return i;
            }
        }
        return 0;
    }
}
