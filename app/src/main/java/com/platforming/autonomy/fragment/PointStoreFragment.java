package com.platforming.autonomy.fragment;

import android.app.Dialog;
import android.graphics.Paint;
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

import com.platforming.autonomy.activity.MainActivity;
import com.platforming.autonomy.clazz.CustomDialog;
import com.platforming.autonomy.clazz.FirestoreManager;
import com.platforming.autonomy.interfaze.ListenerInterface;
import com.android.autonomy.R;
import com.platforming.autonomy.clazz.User;

import java.util.HashMap;
import java.util.List;





public class PointStoreFragment extends Fragment {
    Dialog fontdialog,themedialog;

    TextView tv_pointstore_font_point,tv_pointstore_theme_point;
    EditText et_pointstore_testtext;
    Button btn_pointstore_font,btn_pointstore_theme,btn_pointstore_font_pyeong,btn_pointstore_font_vitorcore,btn_pointstore_font_galmuri,btn_pointstore_font_tokki,btn_pointstore_getout,btn_pointstore_buyfont,btn_pointstore_theme_pink,btn_pointstore_theme_bule, btn_pointstore_theme_green, btn_pointstore_theme_black,btn_pointstore_buytheme,btn_pointstore_theme_getout;

    FirestoreManager firestoreManager = new FirestoreManager();
    HashMap<String,Object> storeMap = new HashMap<>();
    long checkfont;
    List<Long> boughtfont;
    long themeindex;
    List<Long> boughttheme;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pointstore, container, false);

        ((MainActivity)getActivity()).setTitle("포인트 상점");

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
                boughtfont = User.user.getFonts();
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
                    btn_pointstore_font_tokki.setTextColor(getResources().getColor(R.color.red));
                }

                tv_pointstore_font_point.setText(User.user.getPoint()+ "p");
                fontdialog.show();

                btn_pointstore_font_pyeong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkfont = 1;
                        et_pointstore_testtext.setTypeface(getFont(1));
                        btn_pointstore_font_pyeong.setPaintFlags(btn_pointstore_font_pyeong.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                        btn_pointstore_font_galmuri.setPaintFlags(0);
                        btn_pointstore_font_tokki.setPaintFlags(0);
                        btn_pointstore_font_vitorcore.setPaintFlags(0);
                    }
                });
                btn_pointstore_font_vitorcore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkfont = 2;
                        et_pointstore_testtext.setTypeface(getFont(2));
                        btn_pointstore_font_pyeong.setPaintFlags(0);
                        btn_pointstore_font_galmuri.setPaintFlags(0);
                        btn_pointstore_font_tokki.setPaintFlags(0);
                        btn_pointstore_font_vitorcore.setPaintFlags(btn_pointstore_font_vitorcore.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                    }
                });
                btn_pointstore_font_galmuri.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkfont = 3;
                        et_pointstore_testtext.setTypeface(getFont(3));
                        btn_pointstore_font_pyeong.setPaintFlags(0);
                        btn_pointstore_font_galmuri.setPaintFlags(btn_pointstore_font_galmuri.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                        btn_pointstore_font_tokki.setPaintFlags(0);
                        btn_pointstore_font_vitorcore.setPaintFlags(0);
                    }
                });
                btn_pointstore_font_tokki.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkfont = 4;
                        et_pointstore_testtext.setTypeface(getFont(4));
                        btn_pointstore_font_pyeong.setPaintFlags(0);
                        btn_pointstore_font_galmuri.setPaintFlags(0);
                        btn_pointstore_font_tokki.setPaintFlags(btn_pointstore_font_tokki.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                        btn_pointstore_font_vitorcore.setPaintFlags(0);
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
                            if(User.user.getPoint() >=50){
                                boughtfont.add(checkfont);
                                storeMap.put("point", User.user.getPoint() - 50);
                                storeMap.put("fonts",boughtfont);
                                firestoreManager.updateUserData(storeMap, new ListenerInterface() {
                                    @Override
                                    public void onSuccess() {
                                        User.user.setPoint(User.user.getPoint() - 50);
                                        tv_pointstore_font_point.setText(User.user.getPoint() +"p");
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
                                            btn_pointstore_font_tokki.setTextColor(getResources().getColor(R.color.red));
                                        }
                                        customDialog.messageDialog(getActivity(),"구입했습니다.");
                                        ((MainActivity)getActivity()).setHeader();
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
                boughttheme = User.user.getThemes();
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

                tv_pointstore_theme_point.setText(User.user.getPoint() + "p");
                themedialog.show();

                btn_pointstore_theme_pink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        themeindex = 1;
                        btn_pointstore_theme_pink.setPaintFlags(btn_pointstore_theme_pink.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                        btn_pointstore_theme_bule.setPaintFlags(0);
                        btn_pointstore_theme_black.setPaintFlags(0);
                        btn_pointstore_theme_green.setPaintFlags(0);

                    }
                });
                btn_pointstore_theme_bule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        themeindex = 2;
                        btn_pointstore_theme_pink.setPaintFlags(0);
                        btn_pointstore_theme_bule.setPaintFlags(btn_pointstore_theme_bule.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                        btn_pointstore_theme_black.setPaintFlags(0);
                        btn_pointstore_theme_green.setPaintFlags(0);
                    }
                });
                btn_pointstore_theme_green.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        themeindex = 3;
                        btn_pointstore_theme_pink.setPaintFlags(0);
                        btn_pointstore_theme_bule.setPaintFlags(0);
                        btn_pointstore_theme_black.setPaintFlags(0);
                        btn_pointstore_theme_green.setPaintFlags(btn_pointstore_theme_green.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                    }
                });
                btn_pointstore_theme_black.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        themeindex = 4;
                        btn_pointstore_theme_pink.setPaintFlags(0);
                        btn_pointstore_theme_bule.setPaintFlags(0);
                        btn_pointstore_theme_black.setPaintFlags(btn_pointstore_theme_black.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                        btn_pointstore_theme_green.setPaintFlags(0);
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
                            if(User.user.getPoint() >=100){
                                boughttheme.add(themeindex);
                                storeMap.put("point", User.user.getPoint() - 100);
                                storeMap.put("themes",boughttheme);
                                firestoreManager.updateUserData(storeMap, new ListenerInterface() {
                                    @Override
                                    public void onSuccess() {
                                        User.user.setPoint(User.user.getPoint() - 100);
                                        tv_pointstore_theme_point.setText(User.user.getPoint() + "p");
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
                                        ((MainActivity)getActivity()).setHeader();

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
            case 0:checkfont = 0;  return getResources().getFont(R.font.pretendard0);
            case 1:checkfont = 1;  return getResources().getFont(R.font.snow1);
            case 2:checkfont = 2;  return getResources().getFont(R.font.bmeuljiro10yearslate2);
            case 3:checkfont = 3;  return getResources().getFont(R.font.establishretrosans3);
            case 4:checkfont = 4;  return getResources().getFont(R.font.eulyoo1945regular4);
        }
        return null;
    }

    private void setFontdialogview(){
        btn_pointstore_font_pyeong = fontdialog.findViewById(R.id.btn_pointstore_font_pyeong);
        btn_pointstore_font_vitorcore = fontdialog.findViewById(R.id.btn_pointstore_font_vitorcore);
        btn_pointstore_font_galmuri = fontdialog.findViewById(R.id.btn_pointstore_font_galmuri);
        btn_pointstore_font_tokki = fontdialog.findViewById(R.id.btn_pointstore_font_tokki);
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
