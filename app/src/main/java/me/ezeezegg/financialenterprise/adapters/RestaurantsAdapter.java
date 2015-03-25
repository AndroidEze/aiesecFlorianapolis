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
import me.ezeezegg.financialenterprise.models.Restaurants;

/**
 * Created by ezeezegg on 25/03/2015.
 */
public class RestaurantsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Restaurants> restaurantsArrayList;
    private int itemLayout;

    public RestaurantsAdapter(ArrayList<Restaurants> data, int itemLayout){
        restaurantsArrayList = data;
        this.itemLayout = itemLayout;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener{

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate( itemLayout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RestaurantsAdapter.ViewHolder viewHolder, int i) {
        Restaurants restaurants = restaurantsArrayList.get(i);
        viewHolder.title.setText(restaurants.getTitle());
        viewHolder.address.setText(restaurants.getAddress());
        viewHolder.phone.setText(restaurants.getPhone());
        viewHolder.itemView.setTag(restaurants);
    }

    @Override
    public int getItemCount() {
        return restaurantsArrayList.size();
    }
}
