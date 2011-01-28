package ru.nia.ttt;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import javax.xml.datatype.Duration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UpdatesList extends Activity {
    // NB: create your own config.xml with format like in .config.xml.sample
    // for this to work
    String mApiKey;
    String mHost;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updates_list);

        // read config
        mApiKey = getString(R.string.api_key);
        mHost = getString(R.string.host);

        // final variables to be used in onClickListener
        final String apiKey = mApiKey;
        final TextView rawResponse = (TextView) findViewById(R.id.raw_response);
        final Button refresh = (Button) findViewById(R.id.refresh);
        refresh.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    URL url = new URL(mHost + "updates.json?api_key=" + mApiKey);
                    URLConnection connection = url.openConnection();
                    BufferedReader buf = new BufferedReader(
                                             new InputStreamReader(
                                             connection.getInputStream()));
                    rawResponse.setText("");
                    String inputLine;
                    while ((inputLine = buf.readLine()) != null)
                        rawResponse.append(inputLine);
                    buf.close();

                } catch (IOException e) {
                    Toast.makeText(UpdatesList.this, R.string.io_error, Toast.LENGTH_SHORT);
                }
            }
        });
    }
}