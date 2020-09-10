package com.joe.projecta.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joe.projecta.R;
import com.joe.projecta.ui.TopLearner;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.List;

public class TopLearnerAdapter extends RecyclerView.Adapter<TopLearnerAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<TopLearner> topLearner;

    public TopLearnerAdapter(Context context, List<TopLearner> topLearner) {
        this.inflater = LayoutInflater.from(context);
        this.topLearner = topLearner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.toplearnersrow, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.leaderName.setText(topLearner.get(position).getName());
        holder.leaderScore.setText(MessageFormat.format("{0} Learning Hours, {1}", topLearner.get(position).getHours(), topLearner.get(position).getCountry()));
        Picasso.get().load(topLearner.get(position).getBadgeUrl()).into(holder.leaderImage);
    }

    @Override
    public int getItemCount() {
        return topLearner.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView leaderName, leaderScore;
        ImageView leaderImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            leaderName = itemView.findViewById(R.id.leader_name);
            leaderScore = itemView.findViewById(R.id.leader_score);
            leaderImage = itemView.findViewById(R.id.topLearnerImage);


        }
    }
}
