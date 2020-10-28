package com.example.firebasealloperationdemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter extends FirebaseRecyclerAdapter<Person,Adapter.MyViewHolder> {
    Context context;

    public Adapter(@NonNull FirebaseRecyclerOptions<Person> options,Context context) {
        super(options);
        this.context=context;
    }
    class  MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,rollno,course;
        Button update,delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.txt_name);
            rollno=(TextView) itemView.findViewById(R.id.txt_rollno);
            course=(TextView) itemView.findViewById(R.id.txt_course);
            update=(Button)itemView.findViewById(R.id.updata);
            delete=(Button)itemView.findViewById(R.id.delete);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.showdeta,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull Person person) {

        myViewHolder.name.setText(person.getName());
        myViewHolder.rollno.setText(person.getRollno());
        myViewHolder.course.setText(person.getCourse());
        myViewHolder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialog=DialogPlus.newDialog(context)
                        .setGravity(Gravity.CENTER)
                        .setContentHolder(new ViewHolder(R.layout.update_data))
                        .setExpanded(true).create();

                View myview=dialog.getHolderView();
                final EditText name=myview.findViewById(R.id.nm);
                final EditText rollno=myview.findViewById(R.id.rlno);
                final EditText course=myview.findViewById(R.id.cour);
                Button submit=myview.findViewById(R.id.update_data);

                name.setText(person.getName());
                rollno.setText(person.getRollno());
                course.setText(person.getCourse());

                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("course",course.getText().toString());
                        map.put("rollno",rollno.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("students")
                                .child(getRef(i).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialog.dismiss();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.dismiss();

                            }
                        });
                    }
                });
            }
        });
        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Delete panel");
                builder.setMessage("Delete?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("students")
                                .child(getRef(i).getKey()).removeValue();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

    }
}
