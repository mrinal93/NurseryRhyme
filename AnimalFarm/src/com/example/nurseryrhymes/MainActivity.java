package com.example.nurseryrhymes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;

public class MainActivity extends Activity {
	
	Animation animation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		/*TextView title = (TextView) findViewById(R.id.textView1);
		Typeface pastel = Typeface.createFromAsset(getAssets(),
				"fonts/pastel.ttf");
		title.setTypeface(pastel);
		title.setText("Nursery Rhymes");*/
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void play(View view)
	{
		Intent intent = new Intent(this, Rhyme.class);
        finish();
        startActivity(intent);
	}

}
