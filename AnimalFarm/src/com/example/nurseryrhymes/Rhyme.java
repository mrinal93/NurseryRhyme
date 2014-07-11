/**
 * The app picturises the famous nursery rhyme - Old MacDonald had a farm.
 */
package com.example.nurseryrhymes;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Rhyme extends Activity {
		Animation animation;
		MediaPlayer mp,here,there,end;
		private int height;
		float density;
		int voiceSupport=1;
		GifWebView gifView2;
		private static final int REQUEST_CODE = 1234;
		GifWebView gifView_here,gifView_there;
		ArrayList<String> farmerGif = new ArrayList<String>();
		ArrayList<String> hereGif = new ArrayList<String>();
		ArrayList<Integer> sound = new ArrayList<Integer>();
		ArrayList<Integer> soundStart = new ArrayList<Integer>();
		ArrayList<Integer> hereSound = new ArrayList<Integer>();
		ArrayList<Integer> endSound = new ArrayList<Integer>();
		int counter=0;
		double screenSize;
		private int width;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_rhyme);
			DisplayMetrics displaymetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
			height = displaymetrics.heightPixels; //432
			width = displaymetrics.widthPixels;	  //800
			loadActivity();	
	}

	/**
	 * Intializes the arrays with the appropriate size images. 
	 */
	private void arrayInitialise() {
		if(screenSize >2 && screenSize < 5){
			farmerGif.add("file:///android_asset/farmer_cow_ldpi.gif");
			hereGif.add("file:///android_asset/cow_mooleft_ldpi.gif");
			farmerGif.add("file:///android_asset/farmer_horse2_ldpi.gif");
			hereGif.add("file:///android_asset/horse_left_neigh_ldpi.gif");
			farmerGif.add("file:///android_asset/pig_med_ldpi.gif");
			hereGif.add("file:///android_asset/pig_oinkleft_ldpi.gif");
			farmerGif.add("file:///android_asset/farmer_sheep_ldpi.gif");
			hereGif.add("file:///android_asset/sheep_baa_ldpi.gif");
			farmerGif.add("file:///android_asset/farmer_duck_ldpi.gif");
			hereGif.add("file:///android_asset/duck_left_ldpi.gif");
		}
		else if(screenSize>=7 && density>1){			
			farmerGif.add("file:///android_asset/farmer_cow_hdpi.gif");
			hereGif.add("file:///android_asset/cow_mooleft_hdpi.gif");
			farmerGif.add("file:///android_asset/farmer_horse2_hdpi.gif");
			hereGif.add("file:///android_asset/horse_left_neigh_hdpi.gif");
			farmerGif.add("file:///android_asset/pig_med_hdpi.gif");
			hereGif.add("file:///android_asset/pig_oinkleft_hdpi.gif");
			farmerGif.add("file:///android_asset/farmer_sheep_hdpi.gif");
			hereGif.add("file:///android_asset/sheep_baa_hdpi.gif");
			farmerGif.add("file:///android_asset/farmer_duck_hdpi.gif");
			hereGif.add("file:///android_asset/duck_left_hdpi.gif");
		}
		else{
			farmerGif.add("file:///android_asset/farmer_cow.gif");
			hereGif.add("file:///android_asset/cow_mooleft.gif");
			farmerGif.add("file:///android_asset/farmer_horse2.gif");
			hereGif.add("file:///android_asset/horse_left_neigh.gif");
			farmerGif.add("file:///android_asset/pig_med.gif");
			hereGif.add("file:///android_asset/pig_oinkleft.gif");
			farmerGif.add("file:///android_asset/farmer_sheep.gif");
			hereGif.add("file:///android_asset/sheep_baa.gif");
			farmerGif.add("file:///android_asset/farmer_duck.gif");
			hereGif.add("file:///android_asset/duck_left.gif");
		}
		
		sound.add(R.raw.cow_moo_edited);
		sound.add(R.raw.horse_neigh_edited);
		sound.add(R.raw.pig_oink_edited);
		sound.add(R.raw.sheep_baa_edited);
		sound.add(R.raw.duck_quack_edited);
		hereSound.add(R.raw.cow_here_edited);
		hereSound.add(R.raw.horse_here_edited);
		hereSound.add(R.raw.pig_here_edited);
		hereSound.add(R.raw.sheep_here_edited);
		hereSound.add(R.raw.duck_here_edited);
		endSound.add(R.raw.end_edited);
		endSound.add(R.raw.end_edited);
		endSound.add(R.raw.end_edited);
		endSound.add(R.raw.end_edited);
		endSound.add(R.raw.end_edited);
		soundStart.add(R.raw.cow_start_edited);
		soundStart.add(R.raw.horse_start_edited);
		soundStart.add(R.raw.pig_start_edited);
		soundStart.add(R.raw.sheep_start_edited);
		soundStart.add(R.raw.duck_start_edited);
		}

	/**
	 * Loads activity for each animal in the rhyme.
	 */
	private void loadActivity() {
		setContentView(R.layout.activity_rhyme);
		loadSpeechActivity();
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		height = displaymetrics.heightPixels; //432
		width = displaymetrics.widthPixels;	  //800
		
		density = getResources().getDisplayMetrics().density;		
		
		/*Load the windmill gif*/
		LinearLayout linearLayout=(LinearLayout) findViewById(R.id.linearlayout);
		GifWebView gifView;
		DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
		float screenWidth=dm.widthPixels / dm.xdpi;
	    float screenHeight=dm.heightPixels / dm.ydpi;
	    screenSize = Math.sqrt(Math.pow(screenWidth,2)+Math.pow(screenHeight, 2));
	    arrayInitialise();
	    /* Load the gif for the part "here..there.."*/
	    gifView_here	= new GifWebView(this, hereGif.get(counter));
	    if(screenSize > 2 && screenSize < 5)
		{
			gifView	= new GifWebView(this, "file:///android_asset/fan_ldpi2.gif");
		}
		else if(screenSize >= 7 && density > 1)
		{
			gifView	= new GifWebView(this, "file:///android_asset/fan_hdpi.gif");
		}
		else 
		{
			gifView	= new GifWebView(this, "file:///android_asset/fan_mdpi2.gif");
		}
		gifView.setBackgroundColor(0x00000000);
		gifView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
		linearLayout.setBackgroundColor(Color.TRANSPARENT);
		linearLayout.addView(gifView);
		
		/*Load the main farmer-cart GIF*/
		RelativeLayout relativeLayout=(RelativeLayout) findViewById(R.id.rl1);
		gifView2	= new GifWebView(this, farmerGif.get(counter));
		gifView2.setBackgroundColor(0x00000000);
		gifView2.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
		relativeLayout.setBackgroundColor(Color.TRANSPARENT);			
		relativeLayout.addView(gifView2);
		relativeLayout.setVisibility(View.VISIBLE);
		EditText et = (EditText) findViewById(R.id.editText1);
		et.setVisibility(View.INVISIBLE);
		ImageButton speakButton = (ImageButton) findViewById(R.id.speakButton);
		speakButton.setVisibility(View.INVISIBLE);
		
		RelativeLayout rl3=(RelativeLayout) findViewById(R.id.rl3);
		rl3.setVisibility(View.INVISIBLE);
		
		screenSet();
		
		moveViewToScreenCenter(gifView2);
		
		mp = MediaPlayer.create(this,soundStart.get(counter));
		mp.start();
		mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
		    public void onCompletion(MediaPlayer mp) {
		    	if(voiceSupport==0){
			    	EditText et = (EditText) findViewById(R.id.editText1);
					et.setVisibility(View.VISIBLE);
		    	}
		    	else
		    	{
		    		ImageButton speakButton = (ImageButton) findViewById(R.id.speakButton);
		    		speakButton.setVisibility(View.VISIBLE);
		    	}
			
		    }
		});
		/*Check when the user is done typing*/
		et.setOnEditorActionListener(
			    new EditText.OnEditorActionListener() 
			    {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) 
			{
			    if (actionId == EditorInfo.IME_ACTION_SEARCH ||
			            actionId == EditorInfo.IME_ACTION_DONE ||
			            event.getAction() == KeyEvent.ACTION_DOWN &&
			            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) 
			    {
			           // the user is done typing.
			    	try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			    	InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE); 

			    	inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                               InputMethodManager.HIDE_NOT_ALWAYS);
			        	EditText sound1 = (EditText)findViewById(R.id.editText1);
			        	
			        	String input = sound1.getText().toString();
			        	checkAnswer(input);			        	
			           return true; // consume.
			                       
			    }
			    return false; // pass on to other listeners. 
			}

			
	 });	
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rhyme, menu);
		return true;
	}
	public void screenSet()
	{
		LinearLayout linearLayout=(LinearLayout) findViewById(R.id.linearlayout);
		linearLayout.getLayoutParams().height = height/2;
		linearLayout.getLayoutParams().width = width/5;
	}
	public void onBackPressed()
	{
		if(mp.isPlaying())
		{
			mp.stop();
			mp.release();
		}
		if(here!=null && here.isPlaying()){
			here.stop();
			here.release();
		}
		if(there!=null && there.isPlaying()){
			there.stop();
			there.release(); 
		}
		if(end!=null && end.isPlaying()){
			end.stop();
			end.release();
		}
		Intent intent = new Intent(this,MainActivity.class);
		finish();
		startActivity(intent);
	}
	MediaPlayer correct_main;
	
	/**
	 * Executed when the user correctly answers
	 */
	public void resume()
	{
		EditText et = (EditText) findViewById(R.id.editText1);
		et.setVisibility(View.INVISIBLE);
		ImageButton speakButton = (ImageButton) findViewById(R.id.speakButton);
		speakButton.setVisibility(View.INVISIBLE);
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// 
			e.printStackTrace();
		}
		gifView2.setBackgroundColor(0x00000000);
		try {
			Thread.sleep(15);
		} catch (InterruptedException e) {
			// 
			e.printStackTrace();
		}
		correct_main = MediaPlayer.create(this, sound.get(counter));
		correct_main.start();
		here = MediaPlayer.create(this, hereSound.get(counter));
		end = MediaPlayer.create(this, endSound.get(counter));
		Handler handler=new Handler();
		final Runnable r = new Runnable()
		{
		    public void run() 
		    { 
		    	end.start();
				RelativeLayout relativeLayout=(RelativeLayout) findViewById(R.id.rl1);
		    	moveViewOut(relativeLayout);
		    	LinearLayout linearLayout=(LinearLayout) findViewById(R.id.here_left);
		    	linearLayout.setVisibility(View.INVISIBLE);
		    	end.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				    public void onCompletion(MediaPlayer mp) {
				    	if(counter<4){
					    	counter++;
					    	loadActivity();
				    	}
				    	else
				    	{
				    		onBackPressed();
				    	}
					}
				});
		    	
		    }
		};
		handler.postDelayed(r, 7950);
		
		Handler handler2=new Handler();
		final Runnable r2 = new Runnable()
		{
		    public void run() 
		    { 
		    	here.start();
		    	LinearLayout linearLayout=(LinearLayout) findViewById(R.id.here_left);
				gifView_here.setBackgroundColor(0x00000000);
				gifView_here.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
				linearLayout.setBackgroundColor(Color.TRANSPARENT);			
				linearLayout.addView(gifView_here);
		    }
		};
		handler2.postDelayed(r2, 2350);	
	}
	/*Not used, but maybe of use later*/
	public void soundSeek(int id,int time)
	{
		if (correct_main.isPlaying())
		{
			correct_main.stop();
		}
		MediaPlayer correct = MediaPlayer.create(this, id);	
		correct.seekTo(time);
		correct.start();
	}
	/**
	 * Executed when the the answer is wrong
	 */
	public void wrong()
	{
		MediaPlayer wrong = MediaPlayer.create(this, R.raw.wrong);
		wrong.start();
	}
	
	/**
	 * Moves a view to the centre of the screen. Used for farmer GIF.
	 * @param view
	 */
	private void moveViewToScreenCenter( View view )
	{
	    DisplayMetrics dm = new DisplayMetrics();
	    this.getWindowManager().getDefaultDisplay().getMetrics( dm );
	    
	    int originalPos[] = new int[2];
	    view.getLocationOnScreen( originalPos );
	    int xDest = dm.widthPixels/2 - dm.widthPixels/4;
	    TranslateAnimation anim = new TranslateAnimation(dm.widthPixels,xDest , 0, 0 );
	    anim.setDuration(16000);
	    anim.setFillAfter( true );
	    view.startAnimation(anim);
	}
	/**
	 * Moves a out of the screen.
	 * @param view
	 */
	private void moveViewOut( View view )
	{
	    DisplayMetrics dm = new DisplayMetrics();
	    this.getWindowManager().getDefaultDisplay().getMetrics( dm );
	    int originalPos[] = new int[2];
	    view.getLocationOnScreen( originalPos );
	    //int xDest = dm.widthPixels/2 - dm.widthPixels/4;
	    //xDest -= (view.getMeasuredWidth()/2);
	   // int yDest = dm.heightPixels/2 - (view.getMeasuredHeight()/2) - statusBarOffset;
	    //TranslateAnimation anim = new TranslateAnimation( -100,xDest  , 0, 0 );
	    TranslateAnimation anim = new TranslateAnimation( 0,-dm.widthPixels   , 0, 0 );
	    anim.setDuration(7000);
	    anim.setFillAfter( true );
	    view.startAnimation(anim);
	}
	/**
	 * Checks whether the written answer is correct or not.
	 * @param input, the answer entered by the user.
	 */
	private void checkAnswer(String input) {
		if(counter==0)
		{
			if(input.toLowerCase(Locale.getDefault()).equals("moo"))
        	{
        		resume();
        	}
        	else
        	{
        		wrong();
        	}
		}
		if(counter==1)
		{
			if(input.toLowerCase(Locale.getDefault()).equals("neigh"))
        	{
        		resume();
        	}
        	else
        	{
        		wrong();
        	}
		}
		if(counter==2)
		{
			if(input.toLowerCase(Locale.getDefault()).equals("oink"))
        	{
        		resume();
        	}
        	else
        	{
        		wrong();
        	}
		}
		if(counter==3)
		{
			if(input.toLowerCase(Locale.getDefault()).equals("baa"))
        	{
        		resume();
        	}
        	else
        	{
        		wrong();
        	}
		}
		if(counter==4)
		{
			if(input.toLowerCase(Locale.getDefault()).equals("quack"))
        	{
        		resume();
        	}
        	else
        	{
        		wrong();
        	}
		}
		
	}
	/**
	 * Initialises the speech activity.
	 */
	private void loadSpeechActivity() {
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0)
        {
            voiceSupport=0;
        }
		
	}
	/**
     * Handle the action of the button being clicked
     */
    public void speakButtonClicked(View v)
    {
        startVoiceRecognitionActivity();
    }
 
    /**
     * Fire an intent to start the voice recognition activity.
     */
    private void startVoiceRecognitionActivity()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice recognition Demo...");
        startActivityForResult(intent, REQUEST_CODE);
    }
 
    /**
     * Handle the results from the voice recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            // Populate the wordsList with the String values the recognition engine thought it heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            
            for(int i=0;i<matches.size();i++)
            {
            	if(counter==0)
            	{
            		
	            	if(matches.get(i).equals("moo") || matches.get(i).equals("moore") ||
	            			matches.get(i).equals("mou") || matches.get(i).equals("moon") ||
	            			matches.get(i).equals("movie") || matches.get(i).equals("boo")
	            	|| matches.get(i).equals("boohoo") || matches.get(i).equals("boom"))
	            	{
	            		
	            	
	            		resume();
	            		break;
	            	}
	            	else{
	            		wrong();
	            		}
            	}
            	else if(counter==1){
            		
	            	if(matches.get(i).equals("name") || matches.get(i).equals("names") ||
	            			matches.get(i).equals("nay") || matches.get(i).equals("neigh") ||
	            			matches.get(i).equals("nee") || matches.get(i).equals("nays")
	            			|| matches.get(i).equals("knee") || matches.get(i).equals("nees")
	            			|| matches.get(i).equals("ne") || matches.get(i).equals("ni")){
	            		
	            		resume();
	            		break;
	            	}
	            	else
	            		wrong();
	            	
            	}
            	else if(counter==2){
	            	if(matches.get(i).equals("oink") || matches.get(i).equals("on") ||
	            			matches.get(i).equals("oint") || matches.get(i).equals("point") ||
	            			matches.get(i).equals("online") || matches.get(i).equals("going")
	            			|| matches.get(i).equals("awning") || matches.get(i).equals("wapking")){
	            		
	            	
	            		resume();
	            		break;
	            	}
	            	else
	            		wrong();
	            	
            	}
            	else if(counter==3){
            		//TextView tv = (TextView) findViewById(R.id.text);
            		//tv.setText("Sheep says?");
	            	if(matches.get(i).equals("ba") || matches.get(i).equals("baa") ||
	            			matches.get(i).equals("bar") || matches.get(i).equals("bah") ||
	            			matches.get(i).equals("baba") || matches.get(i).equals("bleach")
	            			|| matches.get(i).equals("please") || matches.get(i).equals("plate")
	            			|| matches.get(i).equals("pa") || matches.get(i).equals("paa")){
	            		
	            		resume();
	            		break;
	            	}
	            	else
	            		wrong();
            	}
            	else if(counter==4){
	            	if(matches.get(i).equals("quack") || matches.get(i).equals("crack") ||
	            			matches.get(i).equals("kodak") || matches.get(i).equals("back") ||
	            			matches.get(i).equals("call back") || matches.get(i).equals("quake")
	            			|| matches.get(i).equals("quiet")|| matches.get(i).equals("quiet")
	            			|| matches.get(i).equals("whack")
	            			|| matches.get(i).equals("quack quack")){
	            		if(counter<4)
	            		{
	            			counter++;
	            			loadActivity();
	            		}
	            		resume();
	            		break;
	            	}
	            	else
	            		wrong();
            	}
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
