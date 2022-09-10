package com.android.platforming.fragment;

import static com.android.platforming.clazz.User.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.platforming.adapter.TableAdapter;
import com.android.platforming.clazz.CustomDialog;
import com.android.platforming.clazz.TableItem;
import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;

import java.util.ArrayList;

public class ViewPagerTimetableFragment extends Fragment {

    GridView timetable;

    TableItem crossCriterion = new TableItem("시간");
    ArrayList<TableItem> columnCriteria = new ArrayList<TableItem>(){{
        add(new TableItem("월"));
        add(new TableItem("화"));
        add(new TableItem("수"));
        add(new TableItem("목"));
        add(new TableItem("금"));
    }};
    ArrayList<TableItem> rowCriteria = new ArrayList<>();

    TableAdapter tableAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager_timetable, container, false);

        timetable = view.findViewById(R.id.gv_timetable);

        tableAdapter = new TableAdapter(crossCriterion, columnCriteria, rowCriteria, user.getSchedules());

        timetable.setAdapter(tableAdapter);

        ImageButton expand = view.findViewById(R.id.btn_timetable_expand);
        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog customDialog = new CustomDialog();
                customDialog.expandSchedule(getActivity(), tableAdapter);
            }
        });

        ImageButton edit = view.findViewById(R.id.btn_timetable_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog customDialog = new CustomDialog();
                customDialog.editSchedule(getActivity(), new ListenerInterface() {
                    @Override
                    public void onSuccess() {
                        tableAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        return view;
    }

    /*
        ImageView imageView;
        String imgName = "default.png";    // 이미지 이름

        imageView = view.findViewById(R.id.iv_viewpager_timetable);
        try {
            String imgpath = view.getContext().getCacheDir() + "/" + imgName;   // 내부 저장소에 저장되어 있는 이미지 경로
            Bitmap bm = BitmapFactory.decodeFile(imgpath);
            imageView.setImageBitmap(bm);   // 내부 저장소에 저장된 이미지를 이미지뷰에 셋
            Toast.makeText(view.getContext(), "파일 로드 성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "파일 로드 실패", Toast.LENGTH_SHORT).show();
        }

        ImageButton button = view.findViewById(R.id.btn_viewpager_timetable_edit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });*/

    /*public void edit() {    // 이미지 선택 누르면 실행됨 이미지 고를 갤러리 오픈
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == getActivity().RESULT_OK) {
                deleteFile();

                Uri fileUri = data.getData();
                ContentResolver resolver = getActivity().getContentResolver();
                try {
                    InputStream instream = resolver.openInputStream(fileUri);
                    Bitmap imgBitmap = BitmapFactory.decodeStream(instream);
                    imageView.setImageBitmap(imgBitmap);    // 선택한 이미지 이미지뷰에 셋
                    instream.close();   // 스트림 닫아주기
                    saveBitmapToJpeg(imgBitmap);    // 내부 저장소에 저장
                    Toast.makeText(getContext(), "파일 불러오기 성공", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "파일 불러오기 실패", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void saveBitmapToJpeg(Bitmap bitmap) {   // 선택한 이미지 내부 저장소에 저장
        File tempFile = new File(getContext().getCacheDir(), imgName);    // 파일 경로와 이름 넣기
        try {
            tempFile.createNewFile();   // 자동으로 빈 파일을 생성하기
            FileOutputStream out = new FileOutputStream(tempFile);  // 파일을 쓸 수 있는 스트림을 준비하기
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);   // compress 함수를 사용해 스트림에 비트맵을 저장하기
            out.close();    // 스트림 닫아주기
            Toast.makeText(getContext(), "파일 저장 성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "파일 저장 실패", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteFile() {    // 이미지 삭제
        try {
            File file = getContext().getCacheDir();  // 내부저장소 캐시 경로를 받아오기
            File[] flist = file.listFiles();
            for (int i = 0; i < flist.length; i++) {    // 배열의 크기만큼 반복
                if (flist[i].getName().equals(imgName)) {   // 삭제하고자 하는 이름과 같은 파일명이 있으면 실행
                    flist[i].delete();  // 파일 삭제
                    Toast.makeText(getContext(), "파일 삭제 성공", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "파일 삭제 실패", Toast.LENGTH_SHORT).show();
        }
    }*/
}
