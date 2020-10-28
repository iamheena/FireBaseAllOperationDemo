package com.example.firebasealloperationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    EditText name,course,rollno,stud;
    Button insert,show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stud=(EditText)findViewById(R.id.stud);
        name=(EditText)findViewById(R.id.name);
        course=(EditText)findViewById(R.id.course);
        rollno=(EditText)findViewById(R.id.rollno);
        insert=(Button)findViewById(R.id.insert);
        show=(Button)findViewById(R.id.show);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studd=stud.getText().toString().trim();
                String namee=name.getText().toString().trim();
                String coursee=course.getText().toString().trim();
                String rollnoo= rollno.getText().toString().trim();

                Person person=new Person(namee,coursee,rollnoo);
                FirebaseDatabase db=FirebaseDatabase.getInstance();
                DatabaseReference node=db.getReference("students");
                node.child(studd).setValue(person);

                stud.setText("");
                name.setText("");
                course.setText("");
                rollno.setText("");
                Toast.makeText(MainActivity.this,"Inserted.....",Toast.LENGTH_SHORT).show();

            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,RecycleDataShow.class);
                startActivity(i);

            }
        });




    }
}