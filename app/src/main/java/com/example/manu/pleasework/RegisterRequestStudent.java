package com.example.manu.pleasework;

import android.test.mock.MockApplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class RegisterRequestStudent extends StringRequest {

    private static final String REGISTER_REQUEST_URL="http://manojnath.16mb.com/RegisterStudent.php";
    private Map<String,String> params;

    public RegisterRequestStudent(String rollno,String username,String password,int age,Response.Listener<String> listener) {
        super(Method.POST,REGISTER_REQUEST_URL, listener, null);

        params=new HashMap<>();
        params.put("rollno",rollno);
        params.put("username",username);
        params.put("password",password);
        params.put("age",age+"");

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
