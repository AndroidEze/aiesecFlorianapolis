package me.ezeezegg.aiesecFlorianapolis.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import java.io.InputStream;
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
        new DownloadImageTask(viewHolder.image).execute(info.getImage());
        viewHolder.itemView.setTag(info);
    }

    @Override
    public int getItemCount() {
        return infoArrayList.size();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
