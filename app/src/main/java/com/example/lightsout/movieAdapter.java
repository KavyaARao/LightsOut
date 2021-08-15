package com.example.lightsout;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class movieAdapter extends RecyclerView.Adapter<movieAdapter.Viewholder> {
    private static onItemClickListener onItemClickListener;

    private Context context;
    private ArrayList<movieModel> movieModelArrayList;
    String moviename=" ";
    public int c=-1;
    // Constructor
    public movieAdapter(Context context ,ArrayList<movieModel> movieModelArrayList) {
        this.context = context;
        this.movieModelArrayList = movieModelArrayList;
    }

    @NonNull
    @Override
    public movieAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull movieAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        movieModel model = movieModelArrayList.get(position);
        holder.movieNameTV.setText(model.getMovie_title());
        holder.movieNameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                platform p=new platform();
                c = holder.getAdapterPosition();
                moviename=p.movies[c];
                System.out.println("Email:"+p.e);
                Intent in=new Intent(context,Movie.class);
                in.putExtra("MovieName",moviename);
                in.putExtra("Email",p.e);
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return movieModelArrayList.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView movieNameTV;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            movieNameTV = itemView.findViewById(R.id.movieTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context co=v.getContext();
                    System.out.println("HEllo");
                    //Intent in=new Intent(c,Movie.class);
                    //in.putExtra("MovieName",);
                }
            });
        }
    }

}
