package com.android.platforming.fragment;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.platforming.clazz.FirestoreManager;
import com.android.platforming.clazz.User;
import com.example.platforming.R;

import java.util.List;

public class PointStoreFragment extends Fragment {
    Dialog fontdialog,themedialog,textcolordialog;

    TextView tv_pointstore_point,tv_pointstore_theme_point;
    EditText et_pointstore_testtext;
    Button btn_pointstore_font,btn_pointstore_theme,btn_pointstore_textcolor,btn_pointstore_font_slow,btn_pointstore_font_again,btn_pointstore_font_Baedalofrace,btn_pointstore_getout,btn_pointstore_buyfont,btn_pointstore_savefont,btn_pointstore_buytheme,btn_pointstore_savetheme,btn_pointstore_theme_getout;
    ImageButton ibtn_pointstore_theme_pink,ibtn_pointstore_theme_bule, ibtn_pointstore_theme_green, ibtn_pointstore_theme_black;

    FirestoreManager firestoreManager = new FirestoreManager();
    int point;
    int checkfont;
    List<Integer> boughtfont;
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
                fontdialog.show();
                setFontdialogview();
                point = User.getUser().getPoint();
                tv_pointstore_point.setText(Integer.toString(point)+ "포인트");

                btn_pointstore_font_slow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        et_pointstore_testtext.setTypeface(getFont(0));

                    }
                });
                btn_pointstore_font_again.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        et_pointstore_testtext.setTypeface(getFont(1));

                    }
                });
                btn_pointstore_font_Baedalofrace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        et_pointstore_testtext.setTypeface(getFont(2));
                    }
                });
                btn_pointstore_buyfont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        boughtfont = User.getUser().getFonts();
                        Log.d("apply", String.valueOf(boughtfont));
                        for(int i=0;i<boughtfont.toArray().length;++i){
                            if(boughtfont.get(i).equals(checkfont)){
                                Toast.makeText(getContext(), "이미 구매하였습니다", Toast.LENGTH_SHORT).show();
                            }
                        }
                        //파이어 베이스에 구매되있는지확인 안되있으면 font변수에 담아져 있는걸로 사고 사져있으면 토스트로 띄우기?
                        //사고 포인트 띄우는거도 해주세용  point 변수 있어요
                        // 대충 형이 구현 해주세요
                        //그리고 구매 안되있는거랑 되있는거 구분을 해야하는데 이미지로 하면 좀 귀찮으니까 색깔로 구별 어때요
                    }
                });
                btn_pointstore_savefont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //파이어 베이스에 font변수에 담아져 있는걸로 파베에 "적용시킬 폰트"로저장 하고
                        //font에 담아져있는게 구매가 안되있다면 토스트로 띄우기
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
                themedialog.show();
                setThemedialogView();
                ibtn_pointstore_theme_pink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //변수에 테마 저장해서 저장이나 살때 테마 확인해야함

                    }
                });
                ibtn_pointstore_theme_bule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //변수에 테마 저장해서 저장이나 살때 테마 확인해야함
                    }
                });
                ibtn_pointstore_theme_green.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //변수에 테마 저장해서 저장이나 살때 테마 확인해야함
                    }
                });
                ibtn_pointstore_theme_black.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //변수에 테마 저장해서 저장이나 살때 테마 확인해야함
                    }
                });
                btn_pointstore_buytheme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //파이어 베이스 형
                    }
                });
                btn_pointstore_savetheme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //파이어 베이스  형
                        Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.PinkTheme);
                        LayoutInflater localInflater = getActivity().getLayoutInflater().cloneInContext(contextThemeWrapper);
                        view = localInflater.inflate(R.layout.fragment_pointstore, container, false);
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
        btn_pointstore_textcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textcolordialog.show();
                setTextcolordialogView();
            }
        });

    return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private Typeface getFont(int i){
        switch (i){
            case 0:checkfont = 0;  return getResources().getFont(R.font.nanum_handwriting_slow0);
            case 1:checkfont = 1;  return getResources().getFont(R.font.nanum_handwriting_again1);
            case 2:checkfont = 2;  return getResources().getFont(R.font.baedalofrace2);
        }
        return null;
    }
    private void setView(View view){
        btn_pointstore_font = view.findViewById(R.id.btn_pointstore_font);
        btn_pointstore_theme = view.findViewById(R.id.btn_pointstore_theme);
        btn_pointstore_textcolor = view.findViewById(R.id.btn_pointstore_textcolor);
    }
    private void setDialog(){
        fontdialog = new Dialog(getContext());
        themedialog = new Dialog(getContext());
        textcolordialog = new Dialog(getContext());
        fontdialog.setContentView(R.layout.dialog_pointstore_font);
        themedialog.setContentView(R.layout.dialog_pointstore_theme);
        textcolordialog.setContentView(R.layout.dialog_pointstore_textcolor);

    }
    private void setFontdialogview(){
        btn_pointstore_font_slow = fontdialog.findViewById(R.id.btn_pointstore_font_slow);
        btn_pointstore_font_again = fontdialog.findViewById(R.id.btn_pointstore_font_again);
        btn_pointstore_font_Baedalofrace = fontdialog.findViewById(R.id.btn_pointstore_font_Baedalofrace);
        btn_pointstore_getout = fontdialog.findViewById(R.id.btn_pointstore_getout);
        btn_pointstore_buyfont = fontdialog.findViewById(R.id.btn_pointstore_buyfont);
        btn_pointstore_savefont = fontdialog.findViewById(R.id.btn_pointstore_savefont);
        et_pointstore_testtext = fontdialog.findViewById(R.id.et_pointstore_testtext);
        tv_pointstore_point = fontdialog.findViewById(R.id.tv_pointstore_font_point);

    }
    private void setThemedialogView(){
        tv_pointstore_theme_point = themedialog.findViewById(R.id.tv_pointstore_theme_point);
        ibtn_pointstore_theme_pink = themedialog.findViewById(R.id.ibtn_pointstore_theme_pink);
        ibtn_pointstore_theme_bule = themedialog.findViewById(R.id.ibtn_pointstore_theme_bule);
        ibtn_pointstore_theme_green = themedialog.findViewById(R.id.ibtn_pointstore_theme_green);
        ibtn_pointstore_theme_black = themedialog.findViewById(R.id.ibtn_pointstore_theme_black);
        btn_pointstore_buytheme = themedialog.findViewById(R.id.btn_pointstore_buytheme);
        btn_pointstore_savetheme = themedialog.findViewById(R.id.btn_pointstore_savetheme);
        btn_pointstore_theme_getout = themedialog.findViewById(R.id.btn_pointstore_theme_getout);

    }
    private void setTextcolordialogView(){

    }

}
