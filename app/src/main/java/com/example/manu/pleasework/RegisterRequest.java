package com.example.manu.pleasework;

import android.test.mock.MockApplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL="http://manojnath.16mb.com/Register.php";
    private Map<String,String> params;

    public RegisterRequest(String name,String username,String password,int age,String uniqueid,Response.Listener<String> listener) {
        super(Method.POST,REGISTER_REQUEST_URL, listener, null);

        params=new HashMap<>();
        params.put("name",name);
        params.put("username",username);
        params.put("password",password);
        params.put("age",age+"");
        params.put("uniqueid",uniqueid);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
