package processing.test.sharks_n_minnows;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by i77ki on 9/24/2017.
 */

class AsyncT extends AsyncTask<Void,Void,Void> {
	private static final String TAG = "AsyncT";
	private User user;

	AsyncT(User user){
		this.user = user;
	}

	@Override
	protected Void doInBackground(Void... params) {

		try {
			URL url = new URL("http://192.168.1.5:3000"); //Enter URL here
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
			conn.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
			conn.setRequestProperty("Accept", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
			conn.setDoOutput(true);
			conn.setDoInput(true);
			//conn.connect();

			//create JSON object
			JSONObject jsonObject = new JSONObject();
			//set up how the JSON data will be set up
			jsonObject.put("id", this.user.getId());
			jsonObject.put("latitude", this.user.getLatitude());
			jsonObject.put("longitude", this.user.getLongitude());

			//set JSON data into array for log
			JSONArray jsonArray = new JSONArray();
			jsonArray.put(jsonObject);

			Log.v(TAG, jsonArray.toString());

			//create data output stream so it can be sent as string
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(jsonObject.toString());
			wr.flush();
			wr.close();

			//report back status
			Log.i("STATUS", String.valueOf(conn.getResponseCode()));
			Log.i("MSG" , conn.getResponseMessage());

			//exception handling
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}
}