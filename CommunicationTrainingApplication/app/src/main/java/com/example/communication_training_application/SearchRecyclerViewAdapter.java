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

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> implements Filterable {

    private ArrayList<UiseongUitaeData> mData = null ;
    ArrayList<String> unFilteredlist;
    ArrayList<String> filteredList;

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
                    ArrayList<String> filteringList = new ArrayList<>();
                    for(String name : unFilteredlist){
                        if(name.toLowerCase().contains(charString.toLowerCase())){
                            filteringList.add(name);
                        }
                    }
                    filteredList = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return  filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<String>)filterResults.values;
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
                    //단어별 상세 화면으로 넘어가기
                    Intent intent = new Intent(context, WordMeaningActivity.class);

                    intent.putExtra("data-count", getItemCount());
                    intent.putExtra("data-position", position);
                    intent.putExtra("data", mData);

                    context.startActivity(intent);
                }
            });
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    SearchRecyclerViewAdapter(ArrayList<UiseongUitaeData> ulist) {
        mData = ulist ;
        for(int i = 0; i < mData.size(); i++){
            filteredList.add(mData.get(i).getAnswer());
            unFilteredlist.add(mData.get(i).getAnswer());
        }
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public SearchRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false) ;
        SearchRecyclerViewAdapter.ViewHolder vh = new SearchRecyclerViewAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(SearchRecyclerViewAdapter.ViewHolder holder, int position) {
        String text = filteredList.get(position);
        holder.textView1.setText(text) ;
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return filteredList.size() ;
    }
}
