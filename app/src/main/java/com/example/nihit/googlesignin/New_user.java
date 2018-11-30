package com.example.nihit.googlesignin;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class New_user extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<EditText>> listDataChild;
    private FirebaseAuth mAuth;
    private EditText email,password,name,designation,addrs;
    private ImageView profile;
    private Button signin, signup,clear;
    private Intent intent1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        expListView=(ExpandableListView)findViewById(R.id.websites);
        prepareListData();
        listAdapter=new com.example.nihit.googlesignin.ExpandableListAdapter(this,listDataHeader,listDataChild);
        //listAdapter=new ExpandableListAdapter(,listDataHeader,listDataChild);
        expListView.setAdapter(listAdapter);
        mAuth = FirebaseAuth.getInstance(); // important Call

       // signin = (Button)findViewById(R.id.);
        signup = (Button)findViewById(R.id.submit);
        clear = (Button)findViewById(R.id.clear);
        email = (EditText)findViewById(R.id.input_mail);
        password = (EditText)findViewById(R.id.input_password);
        name = (EditText)findViewById(R.id.input_name);
        designation=(EditText)findViewById(R.id.input_designation);
        addrs=(EditText)findViewById(R.id.input_address);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( email.getText().toString().isEmpty()){
                    email.setError( "Email id is required!" );
                }
                if( password.getText().toString().isEmpty()){
                    password.setError( "Password is required!" );
                }
                if( designation.getText().toString().isEmpty()){
                    designation.setError( "Designation is required!" );
                }
                if( name.getText().toString().isEmpty()){
                    name.setError( "First name is required!" );
                }

                else {


                    String getemail = "Email:"+email.getText().toString().trim()+"\n";
                    String getepassword = password.getText().toString().trim();
                    String getname = "Name:"+name.getText().toString().trim()+"\n";
                    String getdesignation = "Designation:"+designation.getText().toString().trim()+"\n";
                    String getaddres = "Address:"+addrs.getText().toString().trim()+"\n";

                    intent1 = new Intent(getApplicationContext(), Swipe.class);
                    intent1.putExtra("Name", getname);
                    intent1.putExtra("Designation", getdesignation);
                    intent1.putExtra("Address", getaddres);
                    intent1.putExtra("Email", getemail);
                    //startActivity(intent1);

                    callsignup(getemail, getepassword);
                    //finish();
                }

            }
        });
        profile =(ImageView)findViewById(R.id.profileImage);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//// Show only images, no videos or anything else
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//// Always show the chooser (if there are multiple options available)
//
                Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, 1);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 2);
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                name.setError(null);
                password.setText("");
                password.setText(null);
                addrs.setText("");
                addrs.setError(null);
                email.setText("");
                email.setError(null);
                designation.setText("");
                designation.setText(null);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView viewImage;
        viewImage = (ImageView) findViewById(R.id.profileImage);
        if (requestCode == 1) {
            Uri selectedImage = data.getData();

            String[] filePath = {MediaStore.Images.Media.DATA};

            Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);

            c.moveToFirst();

            int columnIndex = c.getColumnIndex(filePath[0]);

            String picturePath = c.getString(columnIndex);

            c.close();

            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

            Toast.makeText(getApplicationContext(), "Image added", Toast.LENGTH_SHORT).show();

            viewImage.setImageBitmap(thumbnail);
        }
        if (requestCode == 2) {
            if (requestCode == 2 && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                viewImage.setImageBitmap(imageBitmap);
            }
        }
    }
    //Create Account
    private void callsignup(String email,String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TESTING", "Sign up Successful" + task.isSuccessful());

// If sign in fails, display a message to the user. If sign in succeeds
// the auth state listener will be notified and logic to handle the
// signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.d("TESTING", "qqqqqqq ssful" + task.isSuccessful());
                            Toast.makeText(New_user.this, "Signed up Failed", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            userProfile();
                            Toast.makeText(New_user.this, "Created Account", Toast.LENGTH_SHORT).show();
                            Log.d("TESTING", "Created Account");
                            startActivity(intent1);
                        }
                    }
                });
    }
    private void prepareListData(){
        listDataHeader = new ArrayList<String>();
        listDataHeader.add("Add more websites");
        listDataChild=new HashMap<String, List<EditText>>();
        List<EditText> webs=new ArrayList<EditText>();
        EditText editText1=new EditText(getApplicationContext());
        editText1.setHint("Facebook Profile");
        EditText editText2=new EditText(getApplicationContext());
        editText2.setHint("Linkedin Profile");
        EditText editText3=new EditText(getApplicationContext());
        editText3.setHint("Personal Website");
        webs.add(editText1);
        webs.add(editText2);
        webs.add(editText3);
        listDataChild.put(listDataHeader.get(0),webs);
    }

    //Set UserDisplay Name
    private void userProfile()
    {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!= null)
        {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name.getText().toString().trim())
//.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg")) // here you can set image link also.
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("TESTING", "User profile updated.");
                            }
                        }
                    });
        }
    }
}