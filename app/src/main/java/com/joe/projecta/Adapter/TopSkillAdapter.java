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
import com.joe.projecta.ui.TopSkill;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.List;

public class TopSkillAdapter extends RecyclerView.Adapter<TopSkillAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<TopSkill> topSkillList;

    public TopSkillAdapter(Context context, List<TopSkill> topSkillList){
        this.inflater = LayoutInflater.from(context);
        this.topSkillList = topSkillList;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.topskillrow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.skillname.setText(topSkillList.get(position).getName());
        holder.skillscore.setText(MessageFormat.format("{0} Skill IQ Score, {1}", topSkillList.get(position).getScore(), topSkillList.get(position).getCountry()));
        Picasso.get().load(topSkillList.get(position).getBadgeUrl()).into(holder.skillimage);
    }

    @Override
    public int getItemCount() {
        return topSkillList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView skillname, skillscore;
        ImageView skillimage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            skillname = itemView.findViewById(R.id.skill_name);
            skillscore = itemView.findViewById(R.id.skill_score);
            skillimage = itemView.findViewById(R.id.topSkillImage);
        }
    }
}
