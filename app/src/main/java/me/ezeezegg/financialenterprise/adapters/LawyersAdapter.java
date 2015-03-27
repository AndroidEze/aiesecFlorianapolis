package me.ezeezegg.financialenterprise.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.ezeezegg.financialenterprise.R;
import me.ezeezegg.financialenterprise.models.Lawyers;

/**
 * Created by ezeezegg on 25/03/2015.
 */
public class LawyersAdapter extends RecyclerView.Adapter<LawyersAdapter.ViewHolder> {

    private ArrayList<Lawyers> lawyersArrayList;
    private int itemLayout;

    public LawyersAdapter(ArrayList<Lawyers> data, int itemLayout){
        lawyersArrayList = data;
        this.itemLayout = itemLayout;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener{

        public ImageView image;
        public TextView title,address,phone;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            address = (TextView) itemView.findViewById(R.id.address);
            phone = (TextView) itemView.findViewById(R.id.phone);

        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public LawyersAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate( itemLayout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LawyersAdapter.ViewHolder viewHolder, int i) {
        Lawyers lawyers = lawyersArrayList.get(i);
        viewHolder.title.setText(lawyers.getTitle());
        viewHolder.address.setText(lawyers.getAddress());
        viewHolder.phone.setText(lawyers.getPhone());
        viewHolder.itemView.setTag(lawyers);
    }

    @Override
    public int getItemCount() {
        return lawyersArrayList.size();
    }
}
