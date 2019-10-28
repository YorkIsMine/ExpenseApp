package com.yorkismine.expenseapp.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.yorkismine.expenseapp.R;

import java.util.List;

import static com.yorkismine.expenseapp.utils.Constants.EXTRA_CURRENCY;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_CURRENCY_RUB;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_CURRENCY_USD;
import static com.yorkismine.expenseapp.utils.Constants.EXTRA_CURRENCY_YEN;

public class SettingsFragment extends Fragment {

    private SettingsViewModel notificationsViewModel;
    private RadioButton usd;
    private RadioButton rub;
    private RadioButton yen;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        usd = root.findViewById(R.id.settings_rb_usd);
        rub = root.findViewById(R.id.settings_rb_rub);
        yen = root.findViewById(R.id.settings_rb_yen);

        checkCurrency();

        usd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (usd.isChecked()) {
                    EXTRA_CURRENCY = EXTRA_CURRENCY_USD;
                    rub.setChecked(false);
                    yen.setChecked(false);
                }
            }
        });
        rub.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rub.isChecked()) {
                    EXTRA_CURRENCY = EXTRA_CURRENCY_RUB;
                    usd.setChecked(false);
                    yen.setChecked(false);
                }
            }
        });
        yen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (yen.isChecked()) {
                    EXTRA_CURRENCY = EXTRA_CURRENCY_YEN;
                    usd.setChecked(false);
                    rub.setChecked(false);
                }
            }
        });
        return root;
    }

    private void checkCurrency() {
        switch (EXTRA_CURRENCY) {
            case EXTRA_CURRENCY_USD:
                usd.setChecked(true);
                rub.setChecked(false);
                yen.setChecked(false);
                break;
            case EXTRA_CURRENCY_RUB:
                rub.setChecked(true);
                usd.setChecked(false);
                yen.setChecked(false);
                break;
            case EXTRA_CURRENCY_YEN:
                yen.setChecked(true);
                usd.setChecked(false);
                rub.setChecked(false);
                break;
        }
    }
}