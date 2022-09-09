package com.android.platforming.fragment;

import static com.android.platforming.clazz.User.user;

import android.app.Activity;
import android.app.Dialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.android.platforming.InitApplication;
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
    Button btn_pointstore_font,btn_pointstore_theme,btn_pointstore_font_pyeong,btn_pointstore_font_vitorcore,btn_pointstore_font_galmuri,btn_pointstore_font_kyobo,btn_pointstore_getout,btn_pointstore_buyfont,btn_pointstore_theme_pink,btn_pointstore_theme_bule, btn_pointstore_theme_green, btn_pointstore_theme_black,btn_pointstore_buytheme,btn_pointstore_theme_getout;

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
                boughtfont = user.getFonts();
                Log.d("asd", String.valueOf(boughtfont));
                if (boughtfont.contains(1L)){
                    btn_pointstore_font_pyeong.setTextColor(getResources().getColor(R.color.red));
                }
                if(boughtfont.contains(2L)){
                    btn_pointstore_font_vitorcore.setTextColor(getResources().getColor(R.color.red));
                }
                if(boughtfont.contains(3L)){
                    btn_pointstore_font_galmuri.setTextColor(getResources().getColor(R.color.red));
                }
                if(boughtfont.contains(4L)){
                    btn_pointstore_font_kyobo.setTextColor(getResources().getColor(R.color.red));
                }
                point = user.getPoint();
                tv_pointstore_font_point.setText(point+ "포인트");
                fontdialog.show();

                btn_pointstore_font_pyeong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkfont = 1;
                        et_pointstore_testtext.setTypeface(getFont(1));
                    }
                });
                btn_pointstore_font_vitorcore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkfont = 2;
                        et_pointstore_testtext.setTypeface(getFont(2));
                    }
                });
                btn_pointstore_font_galmuri.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkfont = 3;
                        et_pointstore_testtext.setTypeface(getFont(3));
                    }
                });
                btn_pointstore_font_kyobo.setOnClickListener(new View.OnClickListener() {
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
                            if(point >=50){
                                boughtfont.add(checkfont, Long.valueOf(checkfont));
                                storeMap.put("point",point - 50);
                                storeMap.put("fonts",boughtfont);
                                firestoreManager.updateUserData(storeMap, new ListenerInterface() {
                                    @Override
                                    public void onSuccess() {
                                        user.setPoint(point - 50);
                                        tv_pointstore_font_point.setText(point+"포인트");
                                        if (checkfont == 1){
                                            btn_pointstore_font_pyeong.setTextColor(getResources().getColor(R.color.red));
                                        }
                                        else if(checkfont == 2){
                                            btn_pointstore_font_vitorcore.setTextColor(getResources().getColor(R.color.red));
                                        }
                                        else if(checkfont == 3){
                                            btn_pointstore_font_galmuri.setTextColor(getResources().getColor(R.color.red));
                                        }
                                        else if(checkfont == 4){
                                            btn_pointstore_font_kyobo.setTextColor(getResources().getColor(R.color.red));
                                        }
                                        customDialog.messageDialog(getActivity(),"구입했습니다.");
                                    }
                                });
                            }
                            else customDialog.messageDialog(getActivity(),"포인트가 부족합니다.");
                        }
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
                boughttheme = user.getThemes();
                if(boughttheme.contains(1L)){
                    btn_pointstore_theme_pink.setTextColor(getResources().getColor(R.color.red));
                }
                if(boughttheme.contains(2L)){
                    btn_pointstore_theme_bule.setTextColor(getResources().getColor(R.color.red));
                }
                if(boughttheme.contains(3L)){
                    btn_pointstore_theme_green.setTextColor(getResources().getColor(R.color.red));
                }
                if(boughttheme.contains(4L)){
                    btn_pointstore_theme_black.setTextColor(getResources().getColor(R.color.red));
                }
                point = user.getPoint();
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
                            if(point >=50){
                                point-=50;
                                boughttheme.add(Long.valueOf(themeindex));
                                storeMap.put("point",point);
                                storeMap.put("themes",boughttheme);
                                firestoreManager.updateUserData(storeMap, new ListenerInterface() {
                                    @Override
                                    public void onSuccess() {
                                        user.setPoint(point);
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
                        //파이어 베이스 형 -> 니가해 updateUserData
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
            case 0:checkfont = 0;  return getResources().getFont(R.font.leferipointwhiteoblique0);
            case 1:checkfont = 1;  return getResources().getFont(R.font.pyeongchangpeace1);
            case 2:checkfont = 2;  return getResources().getFont(R.font.vitorcore2);
            case 3:checkfont = 3;  return getResources().getFont(R.font.galmuri93);
            case 4:checkfont = 4;  return getResources().getFont(R.font.kyobohandwriting20204);
        }
        return null;
    }

    private void saveFont(Context context, int fontIndex){
        //PreferenceManager.getDefaultSharedPreferences(context).edit().putInt("font", fontIndex).apply();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("font", fontIndex);
        editor.apply();

        ((InitApplication)getActivity().getApplication()).setAppliedFont(fontIndex);
    }

    private void saveTheme(Context context, int fontIndex){
        //PreferenceManager.getDefaultSharedPreferences(context).edit().putInt("font", themeindex).apply(); <- 한줄
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("theme", fontIndex);
        editor.apply();

        ((InitApplication)getActivity().getApplication()).setAppliedTheme(fontIndex);
    }

    private void setFontdialogview(){
        btn_pointstore_font_pyeong = fontdialog.findViewById(R.id.btn_pointstore_font_pyeong);
        btn_pointstore_font_vitorcore = fontdialog.findViewById(R.id.btn_pointstore_font_vitorcore);
        btn_pointstore_font_galmuri = fontdialog.findViewById(R.id.btn_pointstore_font_galmuri);
        btn_pointstore_font_kyobo = fontdialog.findViewById(R.id.btn_pointstore_font_kyobo);
        btn_pointstore_getout = fontdialog.findViewById(R.id.btn_pointstore_getout);
        btn_pointstore_buyfont = fontdialog.findViewById(R.id.btn_pointstore_buyfont);
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
        btn_pointstore_theme_getout = themedialog.findViewById(R.id.btn_pointstore_theme_getout);
        tv_pointstore_theme_point = themedialog.findViewById(R.id.tv_pointstore_theme_point);
    }


}
