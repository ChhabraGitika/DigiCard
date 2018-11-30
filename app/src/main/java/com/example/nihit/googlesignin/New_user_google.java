package com.example.nihit.googlesignin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.nihit.googlesignin.MainActivity.userid;

public class New_user_google extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<EditText>> listDataChild;
    private FirebaseAuth mAuth;
    private EditText designation,addrs,name1,email1;
    private Button signin, signup,clear;
    private Intent intent1;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_google);
        //expListView=(ExpandableListView)findViewById(R.id.websites);

        //prepareListData();
       // listAdapter=new com.example.nihit.googlesignin.ExpandableListAdapter(this,listDataHeader,listDataChild);
        //listAdapter=new ExpandableListAdapter(,listDataHeader,listDataChild);
        //expListView.setAdapter(listAdapter);
        Intent i2 = getIntent();
        Bundle b = i2.getExtras();

        //prepareListData();
      //  listAdapter=new com.example.nihit.googlesignin.ExpandableListAdapter(this,listDataHeader,listDataChild);
        //listAdapter=new ExpandableListAdapter(,listDataHeader,listDataChild);
       // expListView.setAdapter(listAdapter);
        mAuth = FirebaseAuth.getInstance(); // important Call

        // signin = (Button)findViewById(R.id.);
        email1 = (EditText)findViewById(R.id.input_mail);
        name1 = (EditText)findViewById(R.id.input_name);
        signup = (Button)findViewById(R.id.submit);
        clear = (Button)findViewById(R.id.clear);
        final String email = b.getString("email");
        final String name = b.getString("name");
        designation=(EditText)findViewById(R.id.input_designation);
        addrs=(EditText)findViewById(R.id.input_address);
        name1.setText(name);
        email1.setText(email);
        final String email2 = "Email:"+b.getString("email")+"\n";
        final String name2 = "Name:"+b.getString("name")+"\n";
        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //com.example.nihit.googlesignin.ExpandableListAdapter expandableListAdapter=(com.example.nihit.googlesignin.ExpandableListAdapter) expListView.getExpandableListAdapter();
               /* EditText fp=(EditText) expandableListAdapter.getChild(0,0);
                EditText lp=(EditText) expandableListAdapter.getChild(0,1);
                EditText pp=(EditText) expandableListAdapter.getChild(0,2);*/

                if( designation.getText().toString().isEmpty()){
                    designation.setError( "Designation is required!" );
                }

                else {


                    String getdesignation = "Designation:"+designation.getText().toString().trim()+"\n";
                    String getaddres = "Address"+addrs.getText().toString().trim()+"\n";
                    intent1 = new Intent(getApplicationContext(), Swipe.class);
                    intent1.putExtra("Name", name2);
                    intent1.putExtra("Designation", getdesignation);
                    intent1.putExtra("Address", getaddres);
                    intent1.putExtra("Email", email2);
                    Toast.makeText(getApplicationContext(),"User successfully registered!!",Toast.LENGTH_SHORT).show();
                    userid =  mAuth.getCurrentUser().getUid();
                    DatabaseReference current_user =  mDatabase.child(userid);
                    current_user.child("Designation").setValue(getdesignation);
                    current_user.child("Address").setValue(getaddres);
                    startActivity(intent1);
                  //  callsignup(email, getepassword);
                    //finish();
                }

            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addrs.setText("");
                addrs.setError(null);
                designation.setText("");
                designation.setText(null);
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
}