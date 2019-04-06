package com.example.sm_pc.goodplace;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.Signature;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "52.79.88.159:3000";
    private static String TAG = "phptest";

    //private static String TAG = "phpquerytest";

//    private static final String TAG_JSON="webnautes";
//    private static final String TAG_ID = "STATION_CD";
//    private static final String TAG_NAME = "STATION_NM";
//    private static final String TAG_ADDRESS ="LINE_NUM";

    private EditText mEditTextName;
    //private EditText mEditTextCountry;
    private TextView mTextViewinsert;
    private TextView mTextViewID;
    private TextView mTextViewshowID;
    private TextView mTextViewroute;
    private TextView mTextViewResult;
    private ListView mListViewList;
    //private EditText mEditTextSe
    private ProgressDialog progressDialog;
    //private TextView textviewJSONText;
    //private TextView mTextViewResult;
    //ArrayList<HashMap<String, String>> mArrayList;
    //ListView mListViewList;
    //EditText mEditTextSearchKeyword;
    //String mJsonString;
    private RadioButton r_btn1, r_btn2;
    private RadioGroup rg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTextViewID = (TextView) findViewById(R.id.textView_id);
        mTextViewshowID = (TextView) findViewById(R.id.textView_showid);
        mTextViewinsert = (TextView) findViewById(R.id.textView_main_name);
//        mTextViewroute = (TextView) findViewById(R.id.textView_main_route);
        mEditTextName = (EditText) findViewById(R.id.editText_main_name);
        //mEditTextCountry = (EditText)findViewById(R.id.editText_main_country);
        mTextViewResult = (TextView) findViewById(R.id.textView_main_result);

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());
        Button buttonselect = (Button) findViewById(R.id.button_main_select);
        Button buttonInsert = (Button) findViewById(R.id.button_main_insert);
        Button buttonresult = (Button) findViewById(R.id.button_main_result);
        Button buttonranking = (Button) findViewById(R.id.button_show_ranking);
        Button buttonrouteshow = (Button) findViewById(R.id.button_route_show);

        rg = (RadioGroup)findViewById(R.id.radioGroup);
        //final Boolean[] result = new Boolean[1];
        r_btn1 = (RadioButton) findViewById(R.id.r_btn1);
        r_btn2 = (RadioButton) findViewById(R.id.r_btn2);

        r_btn1.setOnClickListener(optionOnClickListener);
        r_btn2.setOnClickListener(optionOnClickListener);


        Intent intent = getIntent(); /*데이터 수신*/

        String name = intent.getExtras().getString("name"); /*String형*/
        mTextViewshowID.setText(name);


//        Button button = (Button) findViewById(R.id.subActivity);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
//                startActivity(intent);
//            }
//        });




        buttonInsert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String name = mEditTextName.getText().toString();
                //String userID = mTextViewshowID.getText().toString();
                Intent intent = getIntent(); /*데이터 수신*/

                String userID = intent.getExtras().getString("name"); /*String형*/

                String result =  "0";
                //String country = mEditTextCountry.getText().toString()
                if(r_btn1.isChecked())
                    result = "0";
                else if(r_btn2.isChecked())
                    result = "1";
                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insert", name, result,userID);


                mEditTextName.setText("");
                //mEditTextCountry.setText("");

            }
        });

        buttonselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mEditTextName.getText().toString();
                //String country = mEditTextCountry.getText().toString();

                selectData task = new selectData();
                //task.execute("http://" + IP_ADDRESS + "/insert", name,country);
                task.execute("http://" + IP_ADDRESS + "/select", name);


                mEditTextName.setText("");
                //mEditTextCountry.setText("");




            }
        });

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

                rankingData task = new rankingData();

                //task.execute("http://" + IP_ADDRESS + "/insert", name,country);
                task.execute("http://" + IP_ADDRESS + "/userresult", name);


                //mEditTextName.setText("");
                //mEditTextCountry.setText("");

            }
        });

        buttonrouteshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditTextName.getText().toString();
                //String country = mEditTextCountry.getText().toString();

                showroute task = new showroute();

                //task.execute("http://" + IP_ADDRESS + "/insert", name,country);
                task.execute("http://" + IP_ADDRESS + "/showroute", name);


                //mEditTextName.setText("");
                //mEditTextCountry.setText("");

            }
        });


    }


    RadioButton.OnClickListener optionOnClickListener
            = new RadioButton.OnClickListener() {

        public void onClick(View v) {
            Toast.makeText(
                    MainActivity.this,
                    "최단거리 : " + r_btn1.isChecked() + "\n"
                            + "최소환승 : " + r_btn2.isChecked() + "\n",
                    Toast.LENGTH_LONG).show();

        }
    };


    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this,
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
            String userRoute = (String)params[2];
            String userID = (String)params[3];

            String serverURL = (String) params[0];
            //String postParameters = "name=" + name;
            String postParameters = "name=" + name + "&userRoute=" + userRoute + "&userID=" + userID;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
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

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

    class selectData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this,
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
            //String country = (String)params[2];

            String serverURL = (String) params[0];
            String postParameters = "name=" + name;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
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
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    sb.append(gson.toJson(line));
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "SelectData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

    class resultData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this,
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
            //String country = (String)params[2];

            String serverURL = (String) params[0];
            String postParameters = "name=" + name;


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

            progressDialog = ProgressDialog.show(MainActivity.this,
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
            //String country = (String)params[2];

            String serverURL = (String) params[0];
            String postParameters = "name=" + name;


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

            progressDialog = ProgressDialog.show(MainActivity.this,
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
            //String country = (String)params[2];

            String serverURL = (String) params[0];
            String postParameters = "name=" + name;


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