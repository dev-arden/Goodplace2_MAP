package com.example.sm_pc.goodplace;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegistActivity extends AppCompatActivity {

    private TextView text_result;
    private EditText editemail;
    private EditText editpassword;
    private static String IP_ADDRESS = "52.79.88.159:3000";
    private static String TAG = "join and login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        text_result = (TextView) findViewById(R.id.textView_result);
        editemail = (EditText) findViewById(R.id.editText_email);
        editpassword = (EditText) findViewById(R.id.editText_password);
        Button buttonregist = (Button) findViewById(R.id.button_regist);
        Button buttoncancel = (Button) findViewById(R.id.button_cancel);

        buttonregist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editemail.getText().toString();
                String passwd = editpassword.getText().toString();

                regist task = new regist();
                task.execute("http://" + IP_ADDRESS + "/join", name,passwd);


                editemail.setText("");editpassword.setText("");
                //mEditTextCountry.setText("");
            }
        });
    }



    class regist extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(RegistActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            text_result.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String email = (String) params[1];
            String password = (String)params[2];

            String serverURL = (String) params[0];
            //String postParameters = "name=" + name;
            String postParameters = "email=" + email + "&password=" + password;


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
}

