package com.example.elena.udemydownloadwebcontent;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {
    String result;
    ImageView imageView;

    public class DownloadTask extends AsyncTask <String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            Log.i("URL", urls[0]);

            //String result ="";
            URL url;
            HttpURLConnection urlConnection;
            try {

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();
                while (data != -1){
                    char current = (char) data;
                    result += current;
                    data= reader.read();
                }
                return result;
            }
            catch (Exception e){
                e.printStackTrace();
                return "Failed";
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editText = (EditText)findViewById(R.id.editText);

        DownloadTask task = new DownloadTask();
        try {
            result = task.execute("https://www.ecowebhosting.co.uk/").get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Log.i("content of URL",result);
        editText.setText(result);

    }
}
