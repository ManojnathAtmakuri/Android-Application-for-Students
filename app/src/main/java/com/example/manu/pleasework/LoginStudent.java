package com.example.manu.pleasework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginStudent extends Activity {

    Button loginButtonLoginStudent;
    TextView registerTextLoginStudent;
    EditText usernameLoginStudent;
    EditText passwordLoginStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);

        loginButtonLoginStudent=(Button) findViewById(R.id.loginButtonLoginStudent);
        registerTextLoginStudent=(TextView)findViewById(R.id.registerTextLoginStudent);
        usernameLoginStudent=(EditText)findViewById(R.id.usernameLoginStudent);
        passwordLoginStudent=(EditText)findViewById(R.id.passwordLoginStudent);
    }

    public void registerPage(View view)
    {
        startActivity(new Intent(this,RegisterStudent.class));
    }

    public void login(View view)
    {
        String username=usernameLoginStudent.getText().toString();
        String password=passwordLoginStudent.getText().toString();

        Response.Listener<String> response=new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success=jsonObject.getBoolean("success");
                    String rollno=jsonObject.getString("rollno");

                    if(success)
                    {
                        Intent intent=new Intent(LoginStudent.this, AskStudent.class);
                        intent.putExtra("rollno",rollno);
                        startActivity(intent);
                        Toast.makeText(LoginStudent.this,"successfully logged",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(LoginStudent.this,"login failed",Toast.LENGTH_LONG).show();

                    }
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        };
        LoginRequestStudent loginRequestStudent=new LoginRequestStudent(username,password,response);
        RequestQueue requestQueue= Volley.newRequestQueue(LoginStudent.this);
        requestQueue.add(loginRequestStudent);
    }
}
