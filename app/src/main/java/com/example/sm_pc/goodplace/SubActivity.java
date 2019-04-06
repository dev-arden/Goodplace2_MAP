package com.example.sm_pc.goodplace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class SubActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "52.79.88.159:3000";
    private static String TAG = "phptest";
    private TextView mTextViewResult;
    private EditText mEditTextName;

    private TextView mTextViewID;
    private TextView mTextViewshowID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        mTextViewResult = (TextView) findViewById(R.id.textView_main_result);
        mEditTextName = (EditText) findViewById(R.id.editText_main_name);

        mTextViewID = (TextView) findViewById(R.id.textView_id);
        mTextViewshowID = (TextView) findViewById(R.id.textView_showid);

        Button buttonresult = (Button) findViewById(R.id.button_main_result);
        Button buttonranking = (Button) findViewById(R.id.button_show_ranking);
        Button buttonrouteshow = (Button) findViewById(R.id.button_route_show);


        Intent intent2 = getIntent(); /*데이터 수신*/

        String name = intent2.getExtras().getString("name"); /*String형*/


        mTextViewshowID.setText(name);

        buttonresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditTextName.getText().toString();
                //String country = mEditTextCountry.getText().toString();

                resultData task = new resultData();
                //task.execute("http://" + IP_ADDRESS + "/insert", name,country);
                task.execute("http://" + IP_ADDRESS + "/result", name);


                //mEditTextName.setText("");
                //mEditTextCountry.setText("");

            }
        });

        buttonranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditTextName.getText().toString();
                //String country = mEditTextCountry.getText().toString();

                Intent intent2 = getIntent(); /*데이터 수신*/

                String userID = intent2.getExtras().getString("name"); /*String형*/

                rankingData task = new rankingData();

                //task.execute("http://" + IP_ADDRESS + "/insert", name,country);
                task.execute("http://" + IP_ADDRESS + "/userresult", name,userID);


                //mEditTextName.setText("");
                //mEditTextCountry.setText("");

            }
        });

        buttonrouteshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditTextName.getText().toString();
                //String country = mEditTextCountry.getText().toString();

                Intent intent2 = getIntent(); /*데이터 수신*/

                String userID = intent2.getExtras().getString("name"); /*String형*/

                showroute task = new showroute();

                //task.execute("http://" + IP_ADDRESS + "/insert", name,country);
                task.execute("http://" + IP_ADDRESS + "/showroute", name,userID);


                //mEditTextName.setText("");
                //mEditTextCountry.setText("");

            }
        });
    }

    class resultData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SubActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String name = (String) params[1];


            String serverURL = (String) params[0];
            String postParameters = "name=" + name ;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(500000);
                httpURLConnection.setConnectTimeout(500000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


//                return sb.toString();
                return "완료되었습니다";


            } catch (Exception e) {

                Log.d(TAG, "SelectData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }


    class rankingData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SubActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String name = (String) params[1];
            String userID = (String)params[2];

            String serverURL = (String) params[0];
            String postParameters = "name=" + name + "&userID=" + userID;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(50000);
                httpURLConnection.setConnectTimeout(50000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                //JSONObject job = new JSONObject(sb.toString());
//                JSONArray jarray = new JSONArray(sb.toString());   // JSONArray 생성
//                for(int i=0; i < jarray.length(); i++){
//                    JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출
//                    String user = jObject.getString("user");
//                    String rank = jObject.getString("rank");
//                    String namename = jObject.getString("name");
//
//                    sb2.append("사용자:" + user + "랭킹:" + rank + "지하철명:" + namename + "\n");
//
//                }





                bufferedReader.close();


                return sb.toString();
                //return "완료되었습니다";


            } catch (Exception e) {

                Log.d(TAG, "SelectData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }



    class showroute extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SubActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String name = (String) params[1];
            String userID = (String)params[2];

            String serverURL = (String) params[0];
            String postParameters = "name=" + name + "&userID=" + userID;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(50000);
                httpURLConnection.setConnectTimeout(50000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                //JSONObject job = new JSONObject(sb.toString());
//                JSONArray jarray = new JSONArray(sb.toString());   // JSONArray 생성
//                for(int i=0; i < jarray.length(); i++){
//                    JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출
//                    String user = jObject.getString("user");
//                    String rank = jObject.getString("rank");
//                    String namename = jObject.getString("name");
//
//                    sb2.append("사용자:" + user + "랭킹:" + rank + "지하철명:" + namename + "\n");
//
//                }





                bufferedReader.close();


                return sb.toString();
                //return "완료되었습니다";


            } catch (Exception e) {

                Log.d(TAG, "SelectData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }
}
