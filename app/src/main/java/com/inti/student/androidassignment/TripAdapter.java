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

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {

    Context context;
    ArrayList<Schedule> details;
    ArrayList<Route> routeList;
    String routeID;

    public TripAdapter(Context c, ArrayList<Schedule> detail, ArrayList<Route> routeLists)
    {
        context = c;
        details = detail;
        routeList = routeLists;
    }



    @Override
    public TripAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tripselection, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(routeList.get(position).getRouteID().equals(details.get(position).getRouteID()))
        {
            holder.mText3.setText("" + routeList.get(position).getBoardingTime());
            holder.mText4.setText("" + routeList.get(position).getDestinationTime());

        }
        else
        {
            holder.mText3.setText("" + routeList.get(position+1).getBoardingTime());
            holder.mText4.setText("" + routeList.get(position+1).getDestinationTime());
        }

        holder.mText1.setText("Bus Name : " + routeList.get(position).getBusName());
        holder.mText2.setText("Seats Remaining :" + details.get(position).getTotalSeats());



            final int pos = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    routeID = routeList.get(pos).getRouteID();
                    Toast.makeText(context, details.get(pos).getScheduleID(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, SeatSelectionActivity.class);
                    i.putExtra("scheduleID", details.get(pos).getScheduleID());
                    i.putExtra("routeID", routeID);
                    context.startActivity(i);
                }
            });



    }



    public static class ViewHolder extends RecyclerView.ViewHolder  {
        TextView mText1;
        TextView mText2;
        TextView mText3;
        TextView mText4;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mText1 = itemView.findViewById(R.id.tripBusID);
            mText2 = itemView.findViewById(R.id.tripSeatTotalID);
            mText3 = itemView.findViewById(R.id.tripBoardingTimeID);
            mText4 = itemView.findViewById(R.id.tripDestinationTimeID);


        }


    }
    @Override
    public int getItemCount() {

        return details.size();

    }
}
