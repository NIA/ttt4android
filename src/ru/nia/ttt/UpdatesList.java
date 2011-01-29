package ru.nia.ttt;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.datatype.Duration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;

public class UpdatesList extends Activity {
    private String mApiKey;
    private String mHost;

    private DecimalFormat mHoursFormat = new DecimalFormat("#.##");

    private Vector<Update> mUpdates = new Vector<Update>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updates_list);

        // read config
        // NB: create your own config.xml with format like in .config.xml.sample
        // for this to work
        mApiKey = getString(R.string.api_key);
        mHost = getString(R.string.host);

        final ListView updatesList = (ListView) findViewById(R.id.updates_list);
        final Button refresh = (Button) findViewById(R.id.refresh);
        refresh.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    // connect to URL
                    URL url = new URL(mHost + "updates.json?api_key=" + mApiKey);
                    URLConnection connection = url.openConnection();
                    BufferedReader buf = new BufferedReader(
                                             new InputStreamReader(
                                             connection.getInputStream()));

                    // collect all lines into one StringBuilder
                    StringBuilder rawJSON = new StringBuilder();
                    String line;
                    while ((line = buf.readLine()) != null) {
                        rawJSON.append(line).append("\n");
                    }
                    buf.close();

                    // parse JSON
                    JSONArray jsonUpdates = new JSONArray(rawJSON.toString());
                    String[] strUpdates = new String[jsonUpdates.length()];
                    JSONObject jsonObject;
                    for(int i = 0; i < jsonUpdates.length(); ++i) {
                        jsonObject = jsonUpdates.getJSONObject(i);
                        strUpdates[i] = jsonObject.toString(2);
                        mUpdates.add(new Update(jsonObject));
                    }

                    updatesList.setAdapter(
                            new UpdatesAdapter(UpdatesList.this, R.layout.updates_list_item, mUpdates, mHoursFormat));
                } catch (IOException e) {
                    Toast.makeText(UpdatesList.this, R.string.io_error, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(UpdatesList.this, R.string.json_error, Toast.LENGTH_SHORT).show();
                    Log.e("JSON", "JSON error", e);
                } catch (ParseException e) {
                    Toast.makeText(UpdatesList.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}