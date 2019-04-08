package processing.test.sharks_n_minnows;


import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;

/**
 * Created by i77ki on 10/1/2017.
 */
public class GameHandler {

	private Context context;
	private static final String TAG = "GameHandler";
	private MediaPlayer mp;

	GameHandler(Context context){
		this.context=context;
	}

	GameHandler(sharks_n_minnows sharks_n_minnows){}

	private void sound(String fileName){
		mp = MediaPlayer.create(this.context, Uri.parse("android.resource://processing.test.sharks_n_minnows/raw/" + fileName));
		mp.start();

	}


	public void allyClose(){
		Vibrator v = (Vibrator) this.context.getSystemService(this.context.VIBRATOR_SERVICE);
		v.vibrate(500);
		sound("sonar");
	}

	public void gameStart(){
		Vibrator v = (Vibrator) this.context.getSystemService(this.context.VIBRATOR_SERVICE);
		v.vibrate(10);

		sound("splash");
	}

	public void appStart(){
		sound("waves");
	}

	public void buttonPressed(){
		gameStart();
	}

}
