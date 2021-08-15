package com.example.lightsout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class reviewAdapter extends RecyclerView.Adapter<reviewAdapter.Viewholder> {
    private static onItemClickListener onItemClickListener;

    private Context context;
    private ArrayList<reviewModel> reviewModelArrayList;
    String moviename=" ";
    public int c=-1;
    // Constructor
    public reviewAdapter(Context context ,ArrayList<reviewModel> reviewModelArrayList) {
        this.context = context;
        this.reviewModelArrayList = reviewModelArrayList;
    }

    @NonNull
    @Override
    public reviewAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_review, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull reviewAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        reviewModel model = reviewModelArrayList.get(position);
        holder.RatingTV.setText(String.valueOf(model.getRating()));
        holder.ReviewTV.setText(model.getReview());
        holder.AuthorTV.setText(model.getAuthor());

    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return reviewModelArrayList.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView RatingTV;
        private TextView ReviewTV;
        private TextView AuthorTV;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            RatingTV = itemView.findViewById(R.id.rating);
            ReviewTV = itemView.findViewById(R.id.review);
            AuthorTV = itemView.findViewById(R.id.authors);

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
