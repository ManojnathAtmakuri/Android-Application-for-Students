package com.example.manu.pleasework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import org.w3c.dom.Text;

public class Login extends Activity {

    Button loginButtonLogin;
    TextView registerTextLogin;
    EditText usernameLogin;
    EditText passwordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButtonLogin=(Button) findViewById(R.id.loginButtonLogin);
        registerTextLogin=(TextView)findViewById(R.id.registerTextLogin);
        usernameLogin=(EditText)findViewById(R.id.usernameLogin);
        passwordLogin=(EditText)findViewById(R.id.passwordLogin);
    }

    public void registerPage(View view)
    {
        startActivity(new Intent(this,Register.class));
    }

    public void login(View view)
    {
        String username=usernameLogin.getText().toString();
        String password=passwordLogin.getText().toString();

        Response.Listener<String> response=new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success=jsonObject.getBoolean("success");
                    String name=jsonObject.getString("name");
                    String uniqueid=jsonObject.getString("uniqueid");
                    if(success)
                    {
                        Intent intent=new Intent(Login.this, MainActivity.class);
                        intent.putExtra("name",name);
                        intent.putExtra("uniqueid",uniqueid);
                        startActivity(intent);
                       Toast.makeText(Login.this,uniqueid,Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                       Toast.makeText(Login.this,"login failed",Toast.LENGTH_LONG).show();

                    }
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        };
        LoginRequest loginRequest=new LoginRequest(username,password,response);
        RequestQueue requestQueue= Volley.newRequestQueue(Login.this);
        requestQueue.add(loginRequest);
    }
}
