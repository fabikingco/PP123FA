package com.example.fabik.parkingapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fabik.parkingapp.R;


public class DialogDataFragment extends DialogFragment {

    private int request_code = 0;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public void setmListener(OnFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    public DialogDataFragment() {
        // Required empty public constructor
    }

    public void setRequestCode(int request_code) {
        this.request_code = request_code;
    }

    public static DialogDataFragment newInstance(String param1, String param2) {
        DialogDataFragment fragment = new DialogDataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private View rootView;
    private TextView tvToolbar;
    private EditText etDato;
    private ImageView bt_close;
    private Button bt_save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_dialog_data, container, false);
        tvToolbar = rootView.findViewById(R.id.tvToolbar);
        etDato = rootView.findViewById(R.id.etDato);
        bt_close = rootView.findViewById(R.id.bt_close);
        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        bt_save = rootView.findViewById(R.id.bt_save);
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etDato.getText().toString().trim().equals("")){
                    if (mListener != null) {
                        mListener.onFragmentInteraction(request_code, etDato.getText().toString());
                    }
                    dismiss();
                }

            }
        });



        tvToolbar.setText(mParam1);
        etDato.setHint(mParam2);

        return rootView;
    }

    private void enviarData() {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int requestCode, Object obj);
    }
}
