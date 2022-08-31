package com.android.platforming.fragment;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.android.platforming.clazz.User;
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;

import java.util.HashMap;
import java.util.List;


public class PointStoreFragment extends Fragment {
    Dialog fontdialog,themedialog;

    TextView tv_pointstore_font_point,tv_pointstore_theme_point;
    EditText et_pointstore_testtext;
    Button btn_pointstore_font,btn_pointstore_theme,btn_pointstore_font_slow,btn_pointstore_font_again,btn_pointstore_font_noting1,btn_pointstore_font_noting,btn_pointstore_getout,btn_pointstore_buyfont,btn_pointstore_applyfont,btn_pointstore_theme_pink,btn_pointstore_theme_bule, btn_pointstore_theme_green, btn_pointstore_theme_black,btn_pointstore_buytheme,btn_pointstore_applytheme,btn_pointstore_theme_getout;

    FirestoreManager firestoreManager = new FirestoreManager();
    HashMap<String,Object> storeMap = new HashMap<>();
    int point;
    int checkfont;
    List<Long> boughtfont;
    int themeindex;
    List<Long> boughttheme;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pointstore, container, false);

        btn_pointstore_font = view.findViewById(R.id.btn_pointstore_font);
        btn_pointstore_theme = view.findViewById(R.id.btn_pointstore_theme);
        fontdialog = new Dialog(getContext());
        themedialog = new Dialog(getContext());
        fontdialog.setContentView(R.layout.dialog_pointstore_font);
        themedialog.setContentView(R.layout.dialog_pointstore_theme);

        //폰트 다이얼 로그
        btn_pointstore_font.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                setFontdialogview();
                boughtfont = User.getUser().getFonts();
                Log.d("asd", String.valueOf(boughtfont));
                if (boughtfont.contains((long) 1)){
                    btn_pointstore_font_slow.setTextColor(getResources().getColor(R.color.red));
                }
                if(boughtfont.contains((long) 2)){
                    btn_pointstore_font_again.setTextColor(getResources().getColor(R.color.red));
                }
                if(boughtfont.contains((long) 3)){
                    btn_pointstore_font_noting1.setTextColor(getResources().getColor(R.color.red));
                }
                if(boughtfont.contains((long) 4)){
                    btn_pointstore_font_noting.setTextColor(getResources().getColor(R.color.red));
                }
                point = User.getUser().getPoint();
                tv_pointstore_font_point.setText(point+ "포인트");
                fontdialog.show();

                btn_pointstore_font_slow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkfont = 1;
                        et_pointstore_testtext.setTypeface(getFont(1));
                    }
                });
                btn_pointstore_font_again.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkfont = 2;
                        et_pointstore_testtext.setTypeface(getFont(2));
                    }
                });
                btn_pointstore_font_noting1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkfont = 3;
                        et_pointstore_testtext.setTypeface(getFont(3));
                    }
                });
                btn_pointstore_font_noting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkfont = 4;
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
                                boughtfont.add(checkfont, Long.valueOf(checkfont));
                                storeMap.put("point",point);
                                storeMap.put("fonts",boughtfont);
                                firestoreManager.updateUserData(storeMap, new ListenerInterface() {
                                    @Override
                                    public void onSuccess() {
                                        User.getUser().setPoint(point);
                                        tv_pointstore_font_point.setText(point+"포인트");
                                        if (checkfont == 1){
                                            btn_pointstore_font_slow.setTextColor(getResources().getColor(R.color.red));
                                        }
                                        else if(checkfont == 2){
                                            btn_pointstore_font_again.setTextColor(getResources().getColor(R.color.red));
                                        }
                                        else if(checkfont == 3){
                                            btn_pointstore_font_noting1.setTextColor(getResources().getColor(R.color.red));
                                        }
                                        else if(checkfont == 4){
                                            btn_pointstore_font_noting.setTextColor(getResources().getColor(R.color.red));
                                        }
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

                        // "적용시킬 폰트"폰에 저장 하고
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
                if(boughttheme.contains((long) 1)){
                    btn_pointstore_theme_pink.setTextColor(getResources().getColor(R.color.red));
                }
                if(boughttheme.contains((long) 2)){
                    btn_pointstore_theme_bule.setTextColor(getResources().getColor(R.color.red));
                }
                if(boughttheme.contains((long) 3)){
                    btn_pointstore_theme_green.setTextColor(getResources().getColor(R.color.red));
                }
                if(boughttheme.contains((long) 4)){
                    btn_pointstore_theme_black.setTextColor(getResources().getColor(R.color.red));
                }
                point = User.getUser().getPoint();
                tv_pointstore_theme_point.setText(point + "p");
                themedialog.show();

                btn_pointstore_theme_pink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        themeindex = 1;
                        //변수에 테마 저장해서 저장이나 살때 테마 확인해야함

                    }
                });
                btn_pointstore_theme_bule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        themeindex = 2;
                        //변수에 테마 저장해서 저장이나 살때 테마 확인해야함
                    }
                });
                btn_pointstore_theme_green.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        themeindex = 3;
                        //변수에 테마 저장해서 저장이나 살때 테마 확인해야함
                    }
                });
                btn_pointstore_theme_black.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        themeindex = 4;
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
                                boughttheme.add(themeindex, Long.valueOf(themeindex));
                                storeMap.put("point",point);
                                storeMap.put("themes",boughttheme);
                                firestoreManager.updateUserData(storeMap, new ListenerInterface() {
                                    @Override
                                    public void onSuccess() {
                                        User.getUser().setPoint(point);
                                        tv_pointstore_theme_point.setText(point+"p");
                                        if(themeindex==1){
                                            btn_pointstore_theme_pink.setTextColor(getResources().getColor(R.color.red));
                                        }
                                        else if(themeindex==2){
                                            btn_pointstore_theme_bule.setTextColor(getResources().getColor(R.color.red));
                                        }
                                        else if(themeindex==3){
                                            btn_pointstore_theme_green.setTextColor(getResources().getColor(R.color.red));
                                        }
                                        else if(themeindex==4){
                                            btn_pointstore_theme_black.setTextColor(getResources().getColor(R.color.red));
                                        }
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
                        MainActivity.getActivity().theme(themeindex);
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

    private void setFontdialogview(){
        btn_pointstore_font_slow = fontdialog.findViewById(R.id.btn_pointstore_font_slow);
        btn_pointstore_font_again = fontdialog.findViewById(R.id.btn_pointstore_font_again);
        btn_pointstore_font_noting1 = fontdialog.findViewById(R.id.btn_pointstore_font_noting1);
        btn_pointstore_font_noting = fontdialog.findViewById(R.id.btn_pointstore_font_noting);
        btn_pointstore_getout = fontdialog.findViewById(R.id.btn_pointstore_getout);
        btn_pointstore_buyfont = fontdialog.findViewById(R.id.btn_pointstore_buyfont);
        btn_pointstore_applyfont = fontdialog.findViewById(R.id.btn_pointstore_applyfont);
        et_pointstore_testtext = fontdialog.findViewById(R.id.et_pointstore_testtext);
        tv_pointstore_font_point = fontdialog.findViewById(R.id.tv_pointstore_font_point);
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
        tv_pointstore_theme_point = themedialog.findViewById(R.id.tv_pointstore_theme_point);
    }


}
