package ume.oite.jp.calendarapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTabHost tabHost = (FragmentTabHost)this.findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.container);

        TabHost.TabSpec tabSpec1,tabSpec2;

        tabSpec1 = tabHost.newTabSpec("tab1");
        tabSpec1.setIndicator("BBS");
        tabHost.addTab(tabSpec1, BBSFragment.class, null);

        tabSpec2 = tabHost.newTabSpec("tab2");
        tabSpec2.setIndicator("Calendar");
        tabHost.addTab(tabSpec2, CalendarPagerFragment.class, null);

        ((TextView)((LinearLayout)tabHost.getTabWidget().getChildAt(0)).getChildAt(1)).setTextColor(Color.WHITE);
        ((TextView)((LinearLayout)tabHost.getTabWidget().getChildAt(1)).getChildAt(1)).setTextColor(Color.WHITE);

    }

    protected void onResume(){
        super.onResume();
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}