package com.android.platforming.fragment;

import android.app.Dialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.platforming.activity.MainActivity;
import com.android.platforming.clazz.CustomDialog;
import com.android.platforming.clazz.FirestoreManager;
import com.android.platforming.clazz.ThemeUtil;
import com.android.platforming.clazz.User;
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class PointStoreFragment extends Fragment {
    Dialog fontdialog,themedialog;

    TextView tv_pointstore_point,tv_pointstore_theme_point;
    EditText et_pointstore_testtext;
    Button btn_pointstore_font,btn_pointstore_theme,btn_pointstore_font_slow,btn_pointstore_font_again,btn_pointstore_font_noting1,btn_pointstore_font_noting,btn_pointstore_getout,btn_pointstore_buyfont,btn_pointstore_applyfont,btn_pointstore_theme_pink,btn_pointstore_theme_bule, btn_pointstore_theme_green, btn_pointstore_theme_black,btn_pointstore_buytheme,btn_pointstore_applytheme,btn_pointstore_theme_getout;

    FirestoreManager firestoreManager = new FirestoreManager();
    int point;
    long checkfont;
    List<Long> boughtfont;
    int themeindex;
    List<Long> boughttheme;
    static final String THEME_KEY = "theme_value";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pointstore, container, false);
        setView(view);
        setDialog();
        //폰트 다이얼 로그
        btn_pointstore_font.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                setFontdialogview();
                boughtfont = User.getUser().getFonts();
                for (int i=0; i<boughtfont.size();++i){
                    if (boughtfont.get(i)==1){
                        btn_pointstore_font_slow.setTextColor(getResources().getColor(R.color.red));
                    }
                    else if(boughtfont.get(i)==2){
                        btn_pointstore_font_again.setTextColor(getResources().getColor(R.color.red));
                    }
                    else if(boughtfont.get(i)==3){
                        btn_pointstore_font_noting1.setTextColor(getResources().getColor(R.color.red));
                    }
                    else if(boughtfont.get(i)==4){
                        btn_pointstore_font_noting.setTextColor(getResources().getColor(R.color.red));
                    }
                }
                point = User.getUser().getPoint();
                tv_pointstore_point.setText(point+ "포인트");
                fontdialog.show();



                btn_pointstore_font_slow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        et_pointstore_testtext.setTypeface(getFont(1));
                    }
                });
                btn_pointstore_font_again.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        et_pointstore_testtext.setTypeface(getFont(2));
                    }
                });
                btn_pointstore_font_noting1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        et_pointstore_testtext.setTypeface(getFont(3));
                    }
                });
                btn_pointstore_font_noting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        et_pointstore_testtext.setTypeface(getFont(4));
                    }
                });
                btn_pointstore_buyfont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CustomDialog customDialog = new CustomDialog();
                        if(boughtfont.contains(checkfont)){
                            customDialog.messageDialog(getActivity(),"이미 구입한 상품입니다.");
                        }
                        else {
                            if(point >=100){
                                point-=100;
                                HashMap<String,Object> storemap = new HashMap<>();
                                storemap.put("point",point);
                                firestoreManager.updateUserData(storemap, new ListenerInterface() {
                                    @Override
                                    public void onSuccess() {
                                        User.getUser().setPoint(point);
                                        tv_pointstore_point.setText(point+"포인트");
                                        customDialog.messageDialog(getActivity(),"구입했습니다.");

                                    }
                                });
                            }
                            else customDialog.messageDialog(getActivity(),"포인트가 부족합니다.");
                        }
                    }
                });
                btn_pointstore_applyfont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //파이어 베이스에 font변수에 담아져 있는걸로 파베에 "적용시킬 폰트"로저장 하고
                    }
                });
                btn_pointstore_getout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fontdialog.dismiss();
                    }
                });


            }
        });
        //테마 다이얼로그
        btn_pointstore_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setThemedialogView();
                boughttheme = User.getUser().getThemes();
                for (int i=0; i<boughttheme.size();++i){
                    if (boughttheme.get(i)==1){
                        btn_pointstore_theme_pink.setTextColor(getResources().getColor(R.color.red));
                    }
                    else if(boughttheme.get(i)==2){
                        btn_pointstore_theme_bule.setTextColor(getResources().getColor(R.color.red));
                    }
                    else if(boughttheme.get(i)==3){
                        btn_pointstore_theme_green.setTextColor(getResources().getColor(R.color.red));
                    }
                    else if(boughttheme.get(i)==4){
                        btn_pointstore_theme_black.setTextColor(getResources().getColor(R.color.red));
                    }
                }
                point = User.getUser().getPoint();
                tv_pointstore_point.setText(point + "포인트");
                themedialog.show();

                btn_pointstore_theme_pink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity.getActivity().theme(themeindex);
                        ThemeUtil.applyTheme(view.getContext(), 0);

                        TaskStackBuilder.create(getActivity())
                                .addNextIntent(new Intent(getActivity(), MainActivity.class))
                                .addNextIntent(getActivity().getIntent())
                                .startActivities();
                        //변수에 테마 저장해서 저장이나 살때 테마 확인해야함

                    }
                });
                btn_pointstore_theme_bule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //변수에 테마 저장해서 저장이나 살때 테마 확인해야함
                    }
                });
                btn_pointstore_theme_green.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //변수에 테마 저장해서 저장이나 살때 테마 확인해야함
                    }
                });
                btn_pointstore_theme_black.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ThemeUtil.applyTheme(view.getContext(), 4);
                    }
                });
                btn_pointstore_buytheme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CustomDialog customDialog = new CustomDialog();
                        if(boughttheme.contains(themeindex)){
                            customDialog.messageDialog(getActivity(),"이미 구입한 상품입니다.");
                        }
                        else {
                            if(point >=100){
                                point-=100;
                                HashMap<String,Object> storemap = new HashMap<>();
                                storemap.put("point",point);
                                firestoreManager.updateUserData(storemap, new ListenerInterface() {
                                    @Override
                                    public void onSuccess() {
                                        User.getUser().setPoint(point);
                                        tv_pointstore_point.setText(point+"포인트");
                                        customDialog.messageDialog(getActivity(),"구입했습니다.");

                                    }
                                });
                            }
                            else customDialog.messageDialog(getActivity(),"포인트가 부족합니다.");
                        }
                        //파이어 베이스 형
                    }
                });
                btn_pointstore_applytheme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //적용시킬 테마 폰에 저장
                    }
                });
                btn_pointstore_theme_getout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        themedialog.dismiss();
                    }
                });

            }
        });

    return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private Typeface getFont(int i){
        switch (i){
            case 0:checkfont = 0;  return null;
            case 1:checkfont = 1;  return getResources().getFont(R.font.nanum_handwriting_slow1);
            case 2:checkfont = 2;  return getResources().getFont(R.font.nanum_handwriting_again2);
            case 3:checkfont = 3;  return null;
            case 4:checkfont = 4;  return null;
        }
        return null;
    }
    private void setView(View view){
        btn_pointstore_font = view.findViewById(R.id.btn_pointstore_font);
        btn_pointstore_theme = view.findViewById(R.id.btn_pointstore_theme);
        tv_pointstore_point = fontdialog.findViewById(R.id.tv_pointstore_font_point);
    }
    private void setDialog(){
        fontdialog = new Dialog(getContext());
        themedialog = new Dialog(getContext());
        fontdialog.setContentView(R.layout.dialog_pointstore_font);
        themedialog.setContentView(R.layout.dialog_pointstore_theme);
    }
    private void setFontdialogview(){
        btn_pointstore_font_slow = fontdialog.findViewById(R.id.btn_pointstore_font_slow);
        btn_pointstore_font_again = fontdialog.findViewById(R.id.btn_pointstore_font_again);
        btn_pointstore_font_noting1 = fontdialog.findViewById(R.id.btn_pointstore_font_noting1);
        btn_pointstore_font_noting = fontdialog.findViewById(R.id.btn_pointstore_font_noting);
        btn_pointstore_getout = fontdialog.findViewById(R.id.btn_pointstore_getout);
        btn_pointstore_buyfont = fontdialog.findViewById(R.id.btn_pointstore_buyfont);
        btn_pointstore_applyfont = fontdialog.findViewById(R.id.btn_pointstore_applyfont);
        et_pointstore_testtext = fontdialog.findViewById(R.id.et_pointstore_testtext);
    }
    private void setThemedialogView(){
        tv_pointstore_theme_point = themedialog.findViewById(R.id.tv_pointstore_theme_point);
        btn_pointstore_theme_pink = themedialog.findViewById(R.id.btn_pointstore_theme_pink);
        btn_pointstore_theme_bule = themedialog.findViewById(R.id.btn_pointstore_theme_bule);
        btn_pointstore_theme_green = themedialog.findViewById(R.id.btn_pointstore_theme_green);
        btn_pointstore_theme_black = themedialog.findViewById(R.id.btn_pointstore_theme_black);
        btn_pointstore_buytheme = themedialog.findViewById(R.id.btn_pointstore_buytheme);
        btn_pointstore_applytheme = themedialog.findViewById(R.id.btn_pointstore_applytheme);
        btn_pointstore_theme_getout = themedialog.findViewById(R.id.btn_pointstore_theme_getout);
    }


}
