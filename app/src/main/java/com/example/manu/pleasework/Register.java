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

public class Register extends Activity {

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
        setContentView(R.layout.activity_register);

        nameRegister=(EditText)findViewById(R.id.nameRegister);
        usernameRegister=(EditText)findViewById(R.id.usernameRegister);
        passwordRegister=(EditText)findViewById(R.id.passwordRegister);
        ageRegister=(EditText)findViewById(R.id.ageRegister);
        registerButtonRegister=(Button)findViewById(R.id.registerButtonRegister);
        loginTextRegister=(TextView)findViewById(R.id.loginTextRegister);
        uniqueIdRegister=(EditText)findViewById(R.id.uniqueIdRegister);

    }

    public void loginPage(View view)
    {
        startActivity(new Intent(Register.this, Login.class));
    }

    public void registration(View view)
    {
        String name=nameRegister.getText().toString();
        String username=usernameRegister.getText().toString();
        String password=passwordRegister.getText().toString();
        int age=Integer.parseInt(ageRegister.getText().toString());
        String uniqueid=uniqueIdRegister.getText().toString();

        Response.Listener<String> response=new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try
                {
                    JSONObject jsonObject=new JSONObject(response);
                    boolean success=jsonObject.getBoolean("success");


                    if(success)
                    {
                        Toast.makeText(Register.this,"Successfully Registered",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(Register.this,"Registration Failed",Toast.LENGTH_LONG).show();
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };

        RegisterRequest registerRequest=new RegisterRequest(name,username,password,age,uniqueid,response);
        RequestQueue requestQueue= Volley.newRequestQueue(Register.this);
        requestQueue.add(registerRequest);
    }
}