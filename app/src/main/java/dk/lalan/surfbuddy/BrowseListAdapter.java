package dk.lalan.surfbuddy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lalan on 02/10/15.
 */
public class BrowseListAdapter extends ArrayAdapter<SurfLocation> {
    private final int layout;
    private final Context context;
    private final List<SurfLocation> data;

    public BrowseListAdapter(Context context, int resource, List<SurfLocation> data) {
        super(context, resource, data);
        this.layout = resource;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        ViewHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layout, parent, false);

            holder = new ViewHolder();
            holder.locationName = (TextView) row.findViewById(R.id.browse_location_textview);
            holder.windSpeed = (TextView) row.findViewById(R.id.browse_wind_textview);
            holder.dist = (TextView) row.findViewById(R.id.browse_distance_textview);
            holder.windDirection = (ImageView) row.findViewById(R.id.browse_wind_imageView);
        }else{
            holder = (ViewHolder) row.getTag();
        }

        SurfLocation surfLocation = data.get(position);

        holder.locationName.setText(surfLocation.getName());
        holder.windSpeed.setText(surfLocation.getWindSpeed() + " knots");
        holder.dist.setText(surfLocation.getDistance() + " km");

        if (surfLocation.isSurfable()){
            holder.windDirection.setImageDrawable(context.getDrawable(R.drawable.arrow_green));
        }else{
            holder.windDirection.setImageDrawable(context.getDrawable(R.drawable.arrow_red));
        }

        RotateAnimation animArrow = new RotateAnimation(0, surfLocation.getWindDir(), 40, 40);
        animArrow.setInterpolator(new LinearInterpolator());
        animArrow.setFillAfter(true);
        animArrow.setDuration(1000);
        holder.windDirection.startAnimation(animArrow);

        return row;
    }

    static class ViewHolder{
        TextView locationName, windSpeed, dist;
        ImageView windDirection;
    }
}
