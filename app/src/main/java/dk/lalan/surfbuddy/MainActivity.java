package dk.lalan.surfbuddy;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



//Inspired by: http://treyrobinson.net/blog/android-l-tutorials-part-3-recyclerview-and-cardview/
public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CardviewAdapter mAdapter;

    public List<SurfLocation> favorites = new ArrayList<>();
    public TextView warningText;

    private Database db;
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        //Inspiration: http://www.vogella.com/tutorials/AndroidServices/article.html
        @Override
        public void onReceive(Context context, Intent intent){
            if(intent.getAction().equals(WeatherService.WEATHER_UPDATE)) {
                for (SurfLocation sf : db.getAllLocations()) {
                }
                favorites = db.getAllLocations();
                if(!favorites.isEmpty()) {
                    mAdapter = new CardviewAdapter(favorites, R.layout.main_activity_card_view, getApplicationContext());
                    mRecyclerView.setAdapter(mAdapter);
                    warningText.setText("");
                }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        warningText = (TextView) findViewById(R.id.background_text_warning);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        db = new Database(getApplicationContext());

        favorites = db.getAllLocations();

        db.addLocation("Klitmøller", 120, 1, 56.75, 10.09);

        if(!favorites.isEmpty()){
            mAdapter = new CardviewAdapter(favorites, R.layout.main_activity_card_view, this);
            mRecyclerView.setAdapter(mAdapter);

        }else{
            warningText.setText(R.string.background_warning_text);
        }

        Intent bgService = new Intent(getApplicationContext(), WeatherService.class);
        startService(bgService);

        IntentFilter intentFilter = new IntentFilter(WeatherService.WEATHER_UPDATE);
        registerReceiver(receiver, intentFilter);

        //Handle interaction with fab button
        FloatingActionButton myFab = (FloatingActionButton)  this.findViewById(R.id.myFAB);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BrowseActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
