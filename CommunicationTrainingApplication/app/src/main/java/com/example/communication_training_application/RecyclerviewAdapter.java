package com.example.communication_training_application;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.communication_training_application.model.UiseongUitaeData;

import java.util.ArrayList;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> implements Filterable {

    private ArrayList<UiseongUitaeData> mData = null ;
    ArrayList<UiseongUitaeData> unFilteredlist = new ArrayList<>();
    ArrayList<UiseongUitaeData> filteredList = new ArrayList<>();
    int[] id;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                //return null;
                String charString = charSequence.toString();
                if(charString.isEmpty()){
                    filteredList = unFilteredlist;
                }
                else{
                    ArrayList<UiseongUitaeData> filteringList = new ArrayList<>();
                    for(int i = 0; i < unFilteredlist.size(); i++){
                        String name = new String(unFilteredlist.get(i).getAnswer());
                        if(name.toLowerCase().contains(charString.toLowerCase())){
                            filteringList.add(unFilteredlist.get(i));

                        }
                    }
                    //for(String name : unFilteredlist){
                    //    if(name.toLowerCase().contains(charString.toLowerCase())){
                    //        filteringList.add(name);
                    //    }
                    //}
                    filteredList = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return  filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<UiseongUitaeData>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1 ;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            textView1 = itemView.findViewById(R.id.tv) ;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    int position = getAdapterPosition();
                    //long id = getItemId();
                    //단어별 상세 화면으로 넘어가기
                    Intent intent = new Intent(context, WordMeaningActivity.class);

                    intent.putExtra("data-count", getItemCount());
                    //Log.d("데이터 카운트","총"+getItemCount());
                    intent.putExtra("data-position", position);
                    //Log.d("데이터 포지션", "위치"+position);
                    intent.putExtra("data", filteredList);
                    //Log.d("데이터 리스트","데이터"+filteredList);

                    context.startActivity(intent);
                }
            });
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    RecyclerviewAdapter(ArrayList<UiseongUitaeData> ulist) {
        mData = ulist ;
        filteredList = ulist;
        unFilteredlist = ulist;
        //for(int i = 0; i < mData.size(); i++){
        //    filteredList.add(mData.get(i).getAnswer());
        //    unFilteredlist.add(mData.get(i).getAnswer());
            //id[i] = mData.get(i).getId();
        //}
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public RecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false) ;
        RecyclerviewAdapter.ViewHolder vh = new RecyclerviewAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(RecyclerviewAdapter.ViewHolder holder, int position) {
        String text = filteredList.get(position).getAnswer();
        holder.textView1.setText(text) ;
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return filteredList.size() ;
    }
}
