package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity
        implements InputFragment.OnOkClickedListener,
        ResultFragment.OnCancelClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new InputFragment())
                    .commit();
        }
    }

    // Викликається з InputFragment при натисканні ОК
    @Override
    public void onOkClicked(String dish, String manufacturers, String prices) {
        ResultFragment resultFragment = ResultFragment.newInstance(dish, manufacturers, prices);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, resultFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // Викликається з ResultFragment при натисканні Cancel
    @Override
    public void onCancelClicked() {
        // Повертаємось назад до InputFragment, він скидає свої поля сам
        getSupportFragmentManager().popBackStack();
    }
}