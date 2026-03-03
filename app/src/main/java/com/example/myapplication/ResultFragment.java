package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ResultFragment extends Fragment {

    private static final String ARG_DISH = "dish";
    private static final String ARG_MANUFACTURERS = "manufacturers";
    private static final String ARG_PRICES = "prices";

    public interface OnCancelClickedListener {
        void onCancelClicked();
    }

    private OnCancelClickedListener listener;

    public static ResultFragment newInstance(String dish, String manufacturers, String prices) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DISH, dish);
        args.putString(ARG_MANUFACTURERS, manufacturers);
        args.putString(ARG_PRICES, prices);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCancelClickedListener) {
            listener = (OnCancelClickedListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnCancelClickedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvResult = view.findViewById(R.id.tvResult);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        if (getArguments() != null) {
            String dish = getArguments().getString(ARG_DISH, "");
            String manufacturers = getArguments().getString(ARG_MANUFACTURERS, "");
            String prices = getArguments().getString(ARG_PRICES, "");

            StringBuilder sb = new StringBuilder();
            sb.append("=== Результат вибору ===\n\n");
            sb.append("Посуд: ").append(dish).append("\n\n");
            sb.append("Виробник:\n");
            for (String m : manufacturers.split("\n")) {
                sb.append("  ✓ ").append(m).append("\n");
            }
            sb.append("\nДіапазон цін:\n");
            for (String p : prices.split("\n")) {
                sb.append("  ✓ ").append(p).append("\n");
            }
            tvResult.setText(sb.toString());
        }

        btnCancel.setOnClickListener(v -> {
            if (listener != null) listener.onCancelClicked();
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}