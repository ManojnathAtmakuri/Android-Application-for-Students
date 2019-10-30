package com.example.manu.pleasework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AskStudent extends AppCompatActivity {

    EditText unique;
    String rollno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_student);
        unique=(EditText)findViewById(R.id.uniqueid);
        rollno=getIntent().getExtras().getString("rollno");
    }

    public void listrooms(View view)
    {
        String uniqueid=unique.getText().toString();
        Intent i= new Intent(this,MainActivity.class);
        i.putExtra("uniqueid",uniqueid);
        i.putExtra("name",rollno);
        startActivity(i);
    }
}
