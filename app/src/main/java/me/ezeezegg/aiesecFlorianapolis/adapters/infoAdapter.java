package me.ezeezegg.aiesecFlorianapolis.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.ezeezegg.aiesecFlorianapolis.R;
import me.ezeezegg.aiesecFlorianapolis.models.Info;

/**
 * Created by ezeezegg on 25/03/2015.
 */
public class infoAdapter extends RecyclerView.Adapter<infoAdapter.ViewHolder> {

    private ArrayList<Info> infoArrayList;
    private int itemLayout;

    public infoAdapter(ArrayList<Info> data, int itemLayout){
        infoArrayList = data;
        this.itemLayout = itemLayout;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener{

        public ImageView image;
        public TextView title,date,authors;


        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            authors = (TextView) itemView.findViewById(R.id.address);
            date = (TextView) itemView.findViewById(R.id.date);

        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public infoAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate( itemLayout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(infoAdapter.ViewHolder viewHolder, int i) {
        Info info = infoArrayList.get(i);
        viewHolder.title.setText(info.getTitle());
        viewHolder.authors.setText(info.getAuthors());
        viewHolder.date.setText(info.getDate());
        viewHolder.itemView.setTag(info);
    }

    @Override
    public int getItemCount() {
        return infoArrayList.size();
    }
}
