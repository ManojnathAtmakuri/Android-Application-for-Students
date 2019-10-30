package com.example.manu.pleasework;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectionScreen extends Activity {

    Button classCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_screen);
        classCreateButton=(Button)findViewById(R.id.classCreateButton);
    }

    public void teacher(View view){
        startActivity(new Intent(this,Login.class));
    }

    public void student(View view)
    {
        startActivity(new Intent(this,LoginStudent.class));
    }


}
