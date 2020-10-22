package com.divy.prakash.paathsala.bahikhata.ui;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.divy.prakash.paathsala.bahikhata.utils.CustomerConstants;

/* Recycler View Adapter */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {
    private Context mContext;
    private Cursor mCursor;


    private SQLiteDatabase mDatabase;

    public CustomerAdapter(Context Context, Cursor Cursor) {
        mContext = Context;
        mCursor = Cursor;
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageCustomer;
        public ImageView mImagePay;
        public ImageView mImageView;
        public ImageView mImageEdit;
        public TextView mCustomerName;
        public TextView mCutomerAmount;
        public TextView mCutomerId;

        public CustomerViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageCustomer = itemView.findViewById(R.id.imageView_customer);
            mImagePay = itemView.findViewById(R.id.imageView_pay);
            mImageView = itemView.findViewById(R.id.imageView_view);
            mImageEdit = itemView.findViewById(R.id.imageView_edit);
            mCustomerName = itemView.findViewById(R.id.textView_customerName);
            mCutomerAmount = itemView.findViewById(R.id.textView_customerAmount);
            mCutomerId = itemView.findViewById(R.id.textView_customerid);
            /* Define Listener On Each Component Of Recycler View */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            String custID = mCutomerId.getText().toString();
                            String custname = mCustomerName.getText().toString();

                            listener.onItemClick(position,custID,custname);
                        }
                    }
                }
            });
            mImagePay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            String custID = mCutomerId.getText().toString();
                            String custname = mCustomerName.getText().toString();
                            listener.onPay(position,custID,custname);
                        }
                    }
                }
            });
            mImageEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {


                            String custID = mCutomerId.getText().toString();
                            String custname = mCustomerName.getText().toString();
                            listener.onEdit(position, view, custID,custname);

                        }
                    }
                }
            });
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            String custID = mCutomerId.getText().toString();
                            String custname = mCustomerName.getText().toString();
                            listener.onView(position,custID,custname);
                        }
                    }
                }
            });
        }
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.row_layout_add_customer, parent, false);
        CustomerViewHolder evh = new CustomerViewHolder(view, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        /* Get Data From Database To Set Value On Component Of Recycler View */
        String name = mCursor.getString(mCursor.getColumnIndex(CustomerConstants.Customer_Data.COLUMN_NAME));
        double amount = mCursor.getInt(mCursor.getColumnIndex(CustomerConstants.Customer_Data.COLUMN_AMOUNT));
        long id = mCursor.getLong(mCursor.getColumnIndex(CustomerConstants.Customer_Data._ID));

        if (amount==0.0)
        {
            holder.mCutomerAmount.setTextColor(Color.BLUE);
            holder.mCutomerAmount.setText(String.valueOf(amount));
        }
        else if (amount>0.0)
        {
            holder.mCutomerAmount.setTextColor(Color.GREEN);
            holder.mCutomerAmount.setText("+"+amount);
        } else if (amount<0.0)
        {
            holder.mCutomerAmount.setTextColor(Color.RED);
            holder.mCutomerAmount.setText(String.valueOf(amount));
        }
        /* Set Value On Each Component Of Recycler View */
        holder.mCustomerName.setText(name);

        holder.mImagePay.setImageResource(R.drawable.ic_rupee);
        holder.mImageCustomer.setImageResource(R.drawable.customer);
        holder.mImageCustomer.setBackground(null);
        holder.mImageEdit.setImageResource(R.drawable.ic_edit);
        holder.mImageView.setImageResource(R.drawable.ic_visibility);
        holder.mImageView.setVisibility(View.INVISIBLE);
        holder.mCutomerId.setText(String.valueOf(id));
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    /* Notify Changes In Recycler View */
    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    /* Define Method Used By Recycler View Component */
    public interface OnItemClickListener {
        void onItemClick(int position, String custid,String custname);

        void onView(int position, String custid,String custname);

        void onPay(int position, String custid,String custname);

        void onEdit(int position, View view, String custid,String custname);

    }
}




