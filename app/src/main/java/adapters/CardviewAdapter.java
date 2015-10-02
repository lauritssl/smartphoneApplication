package adapters;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Debug;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dk.lalan.surfbuddy.R;
import models.DummySurfer;
import models.SurfLocation;

/**
 * Created by banditlev on 02/10/15.
 */
//Inspired by: http://treyrobinson.net/blog/android-l-tutorials-part-3-recyclerview-and-cardview/
public class CardviewAdapter extends RecyclerView.Adapter<CardviewAdapter.ViewHolder> {

    private List<DummySurfer> favorites;
    private int rowLayout;
    private Context context;

    public CardviewAdapter(List<DummySurfer> favorites, int rowLayout, Context _context){
        this.favorites = favorites;
        this.rowLayout = rowLayout;
        this.context = _context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i){
        DummySurfer favorite = favorites.get(i);

        viewHolder.favoriteName.setText(favorite.getName());
        viewHolder.favoriteDescription.setText(favorite.getDescription());
        viewHolder.favoriteWindDirection.setText(favorite.getWindDirection());
        viewHolder.favoriteTemp.setText(Double.toString(favorite.getTemp()) + "º C");
        viewHolder.favoriteWindSpeed.setText(Double.toString(favorite.getWindSpeed()) + " knots");

        //If surfable use green arrow and ring else use red
        //Inspired by: http://www.learn-android-easily.com/2013/07/imageview-animation-in-android.html
        if(favorite.isSurfable()){
            RotateAnimation animRing = new RotateAnimation(0, 180, 120, 120);
            RotateAnimation animArrow = new RotateAnimation(0, -130, 60, 60);
            animRing.setInterpolator(new LinearInterpolator());
            animArrow.setInterpolator(new LinearInterpolator());
            animRing.setFillAfter(true);
            animArrow.setFillAfter(true);
            animRing.setDuration(2000);
            animArrow.setDuration(2000);
            viewHolder.favoriteWindDirectionRing.setImageDrawable(context.getDrawable(R.mipmap.ic_direction_green));
            viewHolder.favoriteWindDirectionArrow.setImageDrawable(context.getDrawable(R.mipmap.ic_arrow_green));
            viewHolder.favoriteWindDirectionArrow.startAnimation(animArrow);
            viewHolder.favoriteWindDirectionRing.startAnimation(animRing);
        }
    }

    @Override
    public int getItemCount() {
        return favorites == null ? 0 : favorites.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView favoriteName, favoriteDescription, favoriteWindDirection, favoriteWindSpeed, favoriteTemp;
        public ImageView favoriteWindDirectionRing, favoriteWindDirectionArrow;

        public ViewHolder(View itemView) {
            super(itemView);
            favoriteName = (TextView) itemView.findViewById(R.id.favoriteName);
            favoriteDescription = (TextView) itemView.findViewById(R.id.favoriteDescription);
            favoriteWindDirection = (TextView) itemView.findViewById(R.id.favoriteWindDirection);
            favoriteWindSpeed = (TextView) itemView.findViewById(R.id.favoriteWindSpeed);
            favoriteTemp = (TextView) itemView.findViewById(R.id.favoriteTemp);
            favoriteWindDirectionRing = (ImageView) itemView.findViewById(R.id.favoriteWindDirectionRing);
            favoriteWindDirectionArrow = (ImageView) itemView.findViewById(R.id.favoriteWindDirectionArrow);
        }

    }
}
