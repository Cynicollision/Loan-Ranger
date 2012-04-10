package vast.loanranger;

import vast.loanranger.R;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.res.*;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.*;


public class FormMainActivity extends TabActivity 
{
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.form_main);

    	// Required Variables
    	Resources res = getResources();
    	TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
    	Intent intent;
    	TabHost.TabSpec spec;

    	// Adding tab1
    	intent = new Intent().setClass(this, FormTab1Activity.class);
    	spec = tabHost.newTabSpec("tab1").setIndicator("Tab 1", res.getDrawable(R.drawable.ic_launcher)).setContent(intent);
    	tabHost.addTab(spec);

    	// Adding tab2
    	intent = new Intent().setClass(this, FormTab2Activity.class);
    	spec = tabHost.newTabSpec("tab2").setIndicator("Tab 2", res.getDrawable(R.drawable.ic_launcher)).setContent(intent);
    	tabHost.addTab(spec);

    	// Adding tab3
    	intent = new Intent().setClass(this, FormTab3Activity.class);
    	spec = tabHost.newTabSpec("tab3").setIndicator("Tab 3", res.getDrawable(R.drawable.ic_launcher)).setContent(intent);
    	tabHost.addTab(spec);

    	// Set default tab
    	tabHost.setCurrentTab(0);
    }
}