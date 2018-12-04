package com.inti.student.androidassignment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    Context context;
    ArrayList<BookingDetails> details;
    ArrayList<String> IDs;

    public ListAdapter(Context c, ArrayList<BookingDetails> detail, ArrayList<String> ID)
    {
        context = c;
        details = detail;
        IDs = ID;
    }



    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return new ListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_userfragment,parent,false));

        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_userfragment, parent, false);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

            holder.mText1.setText("Booking ID " + IDs.get(position).toString());
            holder.mText2.setText("Order Date " + details.get(position).getOrderDate());
            holder.mText3.setText("Order Status : " + details.get(position).getOrderStatus());
            holder.mText4.setText("Seats No " + details.get(position).getSeatNo());
            holder.mText5.setText("Passenger Name : " + details.get(position).getPassengerName());
            holder.mText6.setText("Passenger's Contact : " + details.get(position).getPassengerContact());
            final int pos = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, IDs.get(pos).toString(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, singleBookingActivity.class);
                    i.putExtra("scheduleID", details.get(pos).getScheduleID());
                    context.startActivity(i);
                }
            });



    }



    public static class ViewHolder extends RecyclerView.ViewHolder  {
         TextView mText1;
        TextView mText2;
        TextView mText3;
        TextView mText4;
        TextView mText5;
        TextView mText6;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mText1 = itemView.findViewById(R.id.listID1);
            mText2 = itemView.findViewById(R.id.listID2);
            mText3 = itemView.findViewById(R.id.listID3);
            mText4 = itemView.findViewById(R.id.listID4);
            mText5 = itemView.findViewById(R.id.listID5);
            mText6 = itemView.findViewById(R.id.listID6);

        }


    }
    @Override
    public int getItemCount() {

            return details.size();

    }
}
