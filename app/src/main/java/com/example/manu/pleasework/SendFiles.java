package com.example.manu.pleasework;

import android.*;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;


import android.support.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



public class SendFiles extends AppCompatActivity {

    private Button button;
    ArrayList<String> urls=new ArrayList<String>();
    String content_type;
    String roomname,uniqueid;
    FirebaseStorage storage;
    StorageReference storageRef;
    UploadTask uploadTask;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    TextView textView;
    DataSnapshot s;
    DatabaseReference root;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_files);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://mini-f59d6.appspot.com");
        button = (Button) findViewById(R.id.pickButton);
        Bundle data=getIntent().getExtras();
        roomname=data.getString("roomname");
        uniqueid=data.getString("uniqueid");

        root = FirebaseDatabase.getInstance().getReference().child("files").child(roomname);
       root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                append(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                append(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                append(dataSnapshot);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                append(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        textView=(TextView)findViewById(R.id.urlText);

        textView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent i= new Intent(android.content.Intent.ACTION_VIEW,Uri.parse(textView.getText().toString()));
                startActivity(i);
            }

        });


        arrayAdapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1,urls);

        textView=(TextView)findViewById(R.id.urlText);



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},100);
                return;
            }
        }

        enable_button();
    }

    private void enable_button() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialFilePicker()
                        .withActivity(SendFiles.this)
                        .withRequestCode(10)
                        .start();

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 100 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            enable_button();
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},100);
            }
        }
    }

    ProgressDialog progress;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == 10 && resultCode == RESULT_OK) {


            progress = new ProgressDialog(SendFiles.this);
            progress.setTitle("Uploading");
            progress.setMessage("Please wait...");
            progress.show();


            File f = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
            content_type = getMimeType(f.getPath());

            Uri file = Uri.fromFile(f);
            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setContentType(content_type)
                    .build();
            uploadTask = storageRef.child("images/"+file.getLastPathSegment()).putFile(file,metadata);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                public void onFailure(@NonNull Exception exception) {
                    progress.dismiss();
                    Toast.makeText(getBaseContext(),"failed",Toast.LENGTH_LONG).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    progress.dismiss();

                    Toast.makeText(getBaseContext(),downloadUrl.toString(),Toast.LENGTH_LONG).show();
                    updateList(downloadUrl.toString());

                }
            });
        }
    }
    String temp_key;
    public void updateList(String msg)
    {
        String url=msg;


        Map<String,Object> map = new HashMap<>();
        temp_key = root.push().getKey();
        root.updateChildren(map);

        DatabaseReference message_root = root.child(temp_key);
        Map<String,Object> map2 = new HashMap<>();
        map2.put("name",url);


        message_root.updateChildren(map2);

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                append(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                append(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    private String url;
    private void append(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()){

            url = (String)((DataSnapshot)i.next()).getValue();
            textView.append(url+"\n\n");

        }


    }


    private String getMimeType(String path) {

        String extension = MimeTypeMap.getFileExtensionFromUrl(path);

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }
}