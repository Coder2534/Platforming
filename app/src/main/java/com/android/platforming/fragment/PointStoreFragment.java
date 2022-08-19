package com.android.platforming.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.platforming.R;

public class PointStoreFragment extends Fragment {
    Dialog fontdialog;
    Dialog themedialog;
    Dialog textcolordialog;

    Button btn_pointstore_font,btn_pointstore_theme,btn_pointstore_textcolor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pointstore, container, false);
        setview(view);
        setDialog();
        btn_pointstore_font.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fontdialog.show();
            }
        });
        btn_pointstore_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themedialog.show();

            }
        });
        btn_pointstore_textcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textcolordialog.show();
            }
        });



    return view;
    }
    private void setDialog(){
        fontdialog = new Dialog(getContext());
        themedialog = new Dialog(getContext());
        textcolordialog = new Dialog(getContext());
        fontdialog.setContentView(R.layout.dialog_pointstore_font);
        themedialog.setContentView(R.layout.dialog_pointstore_theme);
        textcolordialog.setContentView(R.layout.dialog_pointstore_textcolor);
    }
    private void setview(View view){
        btn_pointstore_font = view.findViewById(R.id.btn_pointstore_font);
        btn_pointstore_theme = view.findViewById(R.id.btn_pointstore_theme);
        btn_pointstore_textcolor = view.findViewById(R.id.btn_pointstore_textcolor);
    }
}
