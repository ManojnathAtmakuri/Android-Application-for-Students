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

public class RegisterStudent extends Activity {

    EditText nameRegister;
    EditText usernameRegister;
    EditText passwordRegister;
    EditText ageRegister;
    Button registerButtonRegister;
    TextView loginTextRegister;
    EditText uniqueIdRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        nameRegister=(EditText)findViewById(R.id.rollnoRegisterStudent);
        usernameRegister=(EditText)findViewById(R.id.usernameRegisterStudent);
        passwordRegister=(EditText)findViewById(R.id.passwordRegisterStudent);
        ageRegister=(EditText)findViewById(R.id.ageRegisterStudent);
        registerButtonRegister=(Button)findViewById(R.id.registerButtonRegisterStudent);
        loginTextRegister=(TextView)findViewById(R.id.loginTextRegisterStudent);

    }

    public void loginPage(View view)
    {
        startActivity(new Intent(RegisterStudent.this, LoginStudent.class));
    }

    public void registration(View view)
    {
        String rollno=nameRegister.getText().toString();
        String username=usernameRegister.getText().toString();
        String password=passwordRegister.getText().toString();
        int age=Integer.parseInt(ageRegister.getText().toString());


        Response.Listener<String> response=new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try
                {
                    JSONObject jsonObject=new JSONObject(response);
                    boolean success=jsonObject.getBoolean("success");


                    if(success)
                    {
                        Toast.makeText(RegisterStudent.this,"Successfully Registered",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(RegisterStudent.this,"Registration Failed",Toast.LENGTH_LONG).show();
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };

        RegisterRequestStudent registerRequestStudent=new RegisterRequestStudent(rollno,username,password,age,response);
        RequestQueue requestQueue= Volley.newRequestQueue(RegisterStudent.this);
        requestQueue.add(registerRequestStudent);
    }
}