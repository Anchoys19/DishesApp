package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class InputFragment extends Fragment {

    public interface OnOkClickedListener {
        void onOkClicked(String dish, String manufacturers, String prices);
    }

    private OnOkClickedListener listener;

    private Spinner spinnerDishes;
    private CheckBox cbManufacturer1, cbManufacturer2, cbManufacturer3;
    private CheckBox cbPrice1, cbPrice2, cbPrice3;

    private String selectedDish = null;

    private final String[] dishes = {
            "-- Оберіть посуд --", "Тарілка", "Чашка", "Каструля",
            "Сковорода", "Миска", "Чайник"
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnOkClickedListener) {
            listener = (OnOkClickedListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnOkClickedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerDishes = view.findViewById(R.id.spinnerDishes);
        cbManufacturer1 = view.findViewById(R.id.cbManufacturer1);
        cbManufacturer2 = view.findViewById(R.id.cbManufacturer2);
        cbManufacturer3 = view.findViewById(R.id.cbManufacturer3);
        cbPrice1 = view.findViewById(R.id.cbPrice1);
        cbPrice2 = view.findViewById(R.id.cbPrice2);
        cbPrice3 = view.findViewById(R.id.cbPrice3);
        Button btnOk = view.findViewById(R.id.btnOk);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, dishes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDishes.setAdapter(adapter);

        spinnerDishes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                selectedDish = (position == 0) ? null : dishes[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedDish = null;
            }
        });

        btnOk.setOnClickListener(v -> onOkClicked());
    }

    private void onOkClicked() {
        boolean dishSelected = selectedDish != null;
        boolean manufacturerSelected = cbManufacturer1.isChecked()
                || cbManufacturer2.isChecked() || cbManufacturer3.isChecked();
        boolean priceSelected = cbPrice1.isChecked()
                || cbPrice2.isChecked() || cbPrice3.isChecked();

        if (!dishSelected || !manufacturerSelected || !priceSelected) {
            Toast.makeText(requireContext(),
                    "Будь ласка, завершіть введення всіх даних!", Toast.LENGTH_LONG).show();
            return;
        }

        // Збираємо рядки для передачі у ResultFragment
        StringBuilder manufacturers = new StringBuilder();
        if (cbManufacturer1.isChecked()) manufacturers.append(cbManufacturer1.getText()).append("\n");
        if (cbManufacturer2.isChecked()) manufacturers.append(cbManufacturer2.getText()).append("\n");
        if (cbManufacturer3.isChecked()) manufacturers.append(cbManufacturer3.getText()).append("\n");

        StringBuilder prices = new StringBuilder();
        if (cbPrice1.isChecked()) prices.append(cbPrice1.getText()).append("\n");
        if (cbPrice2.isChecked()) prices.append(cbPrice2.getText()).append("\n");
        if (cbPrice3.isChecked()) prices.append(cbPrice3.getText()).append("\n");

        listener.onOkClicked(selectedDish, manufacturers.toString().trim(), prices.toString().trim());
    }

    // Скидає форму (викликається ззовні або через popBackStack)
    public void resetForm() {
        if (spinnerDishes != null) spinnerDishes.setSelection(0);
        if (cbManufacturer1 != null) {
            cbManufacturer1.setChecked(false);
            cbManufacturer2.setChecked(false);
            cbManufacturer3.setChecked(false);
        }
        if (cbPrice1 != null) {
            cbPrice1.setChecked(false);
            cbPrice2.setChecked(false);
            cbPrice3.setChecked(false);
        }
        selectedDish = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}