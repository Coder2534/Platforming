package com.android.platforming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.platforming.clazz.TableItem;
import com.android.platforming.R;

import java.util.ArrayList;

public class ScheduleEditAdapter extends RecyclerView.Adapter<ScheduleEditAdapter.ViewHolder> {

    ArrayList<TableItem> tableItems;
    ArrayList<ViewHolder> viewHolders = new ArrayList<>();

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        EditText subject;
        EditText teacher;
        ImageView delete;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            time = itemView.findViewById(R.id.tv_schedule_edit_time);
            subject = itemView.findViewById(R.id.et_schedule_edit_subject);
            teacher = itemView.findViewById(R.id.et_schedule_edit_teacher);
            delete = itemView.findViewById(R.id.iv_schedule_edit_delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        tableItems.remove(pos);
                        saveTableItems(pos);
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public ScheduleEditAdapter(ArrayList<TableItem> tableItems) {
        this.tableItems = tableItems;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_recyclerview_schedule_edit, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolders.add(viewHolder);
        return viewHolder;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.time.setText((position + 1) + "교시");
        holder.subject.setText(tableItems.get(position).getMainText());
        holder.teacher.setText(tableItems.get(position).getSubText());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return tableItems.size() ;
    }

    public void saveTableItems(){
        for(int i = 0; i < tableItems.size(); ++i){
            setItemsByViewHolder(tableItems.get(i), viewHolders.get(i));
        }
    }

    public void saveTableItems(int pos){
        for(int i = 0; i < tableItems.size(); ++i){
            if (i < pos)
                setItemsByViewHolder(tableItems.get(i), viewHolders.get(i));
            else
                setItemsByViewHolder(tableItems.get(i), viewHolders.get(i + 1));
        }
    }

    private void setItemsByViewHolder(TableItem tableItem, ViewHolder viewHolder){
        tableItem.setMainText(viewHolder.subject.getText().toString());
        tableItem.setSubText(viewHolder.teacher.getText().toString());
    }
}