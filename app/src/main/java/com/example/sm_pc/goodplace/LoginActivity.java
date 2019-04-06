package com.example.sm_pc.goodplace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText mEditTextemail;
    private EditText mEditTextpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditTextemail = (EditText) findViewById(R.id.editText_email);
        mEditTextpassword = (EditText) findViewById(R.id.editText_password);

        Button login = (Button) findViewById(R.id.button_login);
        Button regist = (Button) findViewById(R.id.button_regist);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                String ID = mEditTextemail.getText().toString();

                intent.putExtra("name",ID); /*송신*/

                startActivity(intent);
            }
        });

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistActivity.class);
                startActivity(intent);
            }
        });
    }
}
