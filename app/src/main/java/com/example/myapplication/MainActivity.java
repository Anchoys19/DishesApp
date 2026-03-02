package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerDishes;
    private CheckBox cbManufacturer1, cbManufacturer2, cbManufacturer3;
    private CheckBox cbPrice1, cbPrice2, cbPrice3;
    private TextView tvResult;

    private String selectedDish = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerDishes = findViewById(R.id.spinnerDishes);
        cbManufacturer1 = findViewById(R.id.cbManufacturer1);
        cbManufacturer2 = findViewById(R.id.cbManufacturer2);
        cbManufacturer3 = findViewById(R.id.cbManufacturer3);
        cbPrice1 = findViewById(R.id.cbPrice1);
        cbPrice2 = findViewById(R.id.cbPrice2);
        cbPrice3 = findViewById(R.id.cbPrice3);
        tvResult = findViewById(R.id.tvResult);
        Button btnOk = findViewById(R.id.btnOk);

        // Заповнення списку посуду
        String[] dishes = {"-- Оберіть посуд --", "Тарілка", "Чашка", "Каструля", "Сковорода", "Миска", "Чайник"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dishes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDishes.setAdapter(adapter);

        spinnerDishes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedDish = null;
                } else {
                    selectedDish = dishes[position];
                }
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
        boolean manufacturerSelected = cbManufacturer1.isChecked() || cbManufacturer2.isChecked() || cbManufacturer3.isChecked();
        boolean priceSelected = cbPrice1.isChecked() || cbPrice2.isChecked() || cbPrice3.isChecked();

        if (!dishSelected || !manufacturerSelected || !priceSelected) {
            Toast.makeText(this, "Будь ласка, завершіть введення всіх даних!", Toast.LENGTH_LONG).show();
            return;
        }

        // Збір результату
        StringBuilder sb = new StringBuilder();
        sb.append("=== Результат вибору ===\n\n");
        sb.append("Посуд: ").append(selectedDish).append("\n\n");

        sb.append("Виробник:\n");
        if (cbManufacturer1.isChecked()) sb.append("  ✓ ").append(cbManufacturer1.getText()).append("\n");
        if (cbManufacturer2.isChecked()) sb.append("  ✓ ").append(cbManufacturer2.getText()).append("\n");
        if (cbManufacturer3.isChecked()) sb.append("  ✓ ").append(cbManufacturer3.getText()).append("\n");

        sb.append("\nДіапазон цін:\n");
        if (cbPrice1.isChecked()) sb.append("  ✓ ").append(cbPrice1.getText()).append("\n");
        if (cbPrice2.isChecked()) sb.append("  ✓ ").append(cbPrice2.getText()).append("\n");
        if (cbPrice3.isChecked()) sb.append("  ✓ ").append(cbPrice3.getText()).append("\n");

        tvResult.setText(sb.toString());
    }
}