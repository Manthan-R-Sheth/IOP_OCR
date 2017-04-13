package org.self.iopocr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button cameraClick;
    private static final int CAMERA_REQUEST = 1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraClick = (Button) findViewById(R.id.cameraClick);
        cameraClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
        }
        //Cognitive Services
        receivePhoto();
        //update DB
        updateDB();
    }

    private void updateDB() {
        URL url;
        HttpURLConnection conn = null;
        try {
            url = new URL("172.25.13.126:8000/");
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setUseCaches(false);

            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("language", "unk")
                    .appendQueryParameter("language", "unk")
                    .appendQueryParameter("language", "unk")
                    .appendQueryParameter("language", "unk")
                    .appendQueryParameter("detectOrientation ", "true");
            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            conn.connect();
            int responseCode = conn.getResponseCode();
            String response = "";
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
            Log.e("Response", response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receivePhoto() {
        URL url;
        HttpURLConnection conn = null;
        try {
            url = new URL("https://westus.api.cognitive.microsoft.com/vision/v1.0/ocr");
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Ocp-Apim-Subscription-Key", "d296f30e432b4a0fa8409fab2131a3ef");
            conn.setUseCaches(false);

            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("language", "unk")
                    .appendQueryParameter("detectOrientation ", "true");
            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            conn.connect();
            int responseCode = conn.getResponseCode();
            String response = "";
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
            Log.e("Response", response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

