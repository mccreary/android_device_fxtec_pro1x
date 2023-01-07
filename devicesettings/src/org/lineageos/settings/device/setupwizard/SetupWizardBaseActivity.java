/*
 * Copyright (C) 2022 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.settings.device.setupwizard;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.android.settingslib.Utils;

import com.google.android.setupcompat.template.FooterButtonStyleUtils;
import com.google.android.setupdesign.GlifLayout;

import org.lineageos.settings.device.R;

public class SetupWizardBaseActivity extends Activity implements View.OnClickListener {

    private static final int WINDOW_FLAGS = View.STATUS_BAR_DISABLE_HOME
            | View.STATUS_BAR_DISABLE_RECENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(WINDOW_FLAGS);

        initLayout();
    }

    @Override
    public void onClick(View view) {
        // Keep this for navigating purposes
        if (view.getId() == R.id.btn_next) {
            setResult(RESULT_OK, null);
            finish();
        }
    }

    private void initLayout() {
        if (getLayoutResId() != -1) {
            setContentView(getLayoutResId());
        }

        final GlifLayout layout = getGlifLayout();
        final Context context = layout.getContext();

        if (getTitleResId() != -1) {
            final CharSequence headerText = TextUtils.expandTemplate(getText(getTitleResId()));
            layout.setHeaderText(headerText);
        }
        if (getIconResId() != -1) {
            final Drawable icon = getDrawable(getIconResId()).mutate();
            icon.setTintList(Utils.getColorAccent(context));
            layout.setIcon(icon);
        }

        Button buttonNext = findViewById(R.id.btn_next);
        buttonNext.setOnClickListener(this);

        FooterButtonStyleUtils.applySecondaryButtonPartnerResource(context, buttonNext, true);

        setupPage();
    }

    protected GlifLayout getGlifLayout() {
        return requireViewById(R.id.setup_wizard_layout);
    }

    protected int getLayoutResId() {
        return -1;
    }

    protected int getTitleResId() {
        return -1;
    }

    protected int getIconResId() {
        return -1;
    }

    protected void setupPage() {
        return;
    }
}
