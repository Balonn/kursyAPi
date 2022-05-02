package com.example.api3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    TextView textViewResult;
    EditText input;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.textViewResult);
        input = findViewById(R.id.input);
        button = findViewById(R.id.button);
    }

    public void start(View view) {
        OkHttpClient client = new OkHttpClient();

        String url = "https://api.nbp.pl/api/exchangerates/rates/a/" + input.getText() + "/?format=json";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();


                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject reader = new JSONObject(myResponse);
                                JSONArray data1 = reader.getJSONArray("rates");
                                JSONObject data2 = data1.getJSONObject(0);
                                String kurs = data2.getString("mid") ;
                                String nazwa = reader.getString("currency") ;


                                textViewResult.setText(nazwa + ": " + kurs + "zł");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            }
        });
    }
}