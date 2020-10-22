package com.divy.prakash.paathsala.bahikhata.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.divy.prakash.paathsala.bahikhata.utils.AddItem;

import java.util.ArrayList;

public class AddItemAdapter extends RecyclerView.Adapter<AddItemAdapter.AddItemViewHolder>{
    private ArrayList<AddItem> mItemArrayList;

    public AddItemAdapter(ArrayList<AddItem> mItemArrayList) {
        this.mItemArrayList = mItemArrayList;
    }

    @NonNull
    @Override
    public AddItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_add_data, parent, false);
       AddItemViewHolder addItemViewHolder = new AddItemViewHolder(view);
        return addItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddItemViewHolder holder, int position) {
        AddItem item = mItemArrayList.get(position);
        holder.productname.setText(item.getProductname());
        holder.productmunit.setText(item.getProductmunit());
        holder.productquantity.setText(String.valueOf(item.getQuantity()));
        holder.productprice.setText(String.valueOf(item.getPrice()));
        holder.totalprice.setText(String.valueOf(item.getTotalprice()));

    }

    @Override
    public int getItemCount() {
        if (mItemArrayList==null)
            return 0 ;
        return mItemArrayList.size();
    }



    public  static  class AddItemViewHolder extends RecyclerView.ViewHolder
    {

        public TextView productname;
        public TextView productquantity;
        public TextView productmunit;
        public TextView productprice;
        public TextView totalprice;

        public AddItemViewHolder(@NonNull View itemView ) {
            super(itemView);

            productname = itemView.findViewById(R.id.textViewproductname);
            productquantity = itemView.findViewById(R.id.textViewproductquantity);
            productmunit = itemView.findViewById(R.id.textViewproductmunit);
            productprice = itemView.findViewById(R.id.textViewprice);
            totalprice = itemView.findViewById(R.id.textViewtotalprice);


        }
    }
}
