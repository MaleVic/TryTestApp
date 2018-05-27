package com.example.gamesmannna.trytestapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gamesmannna.trytestapp.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {
EditText edtPhone, edtPassword;
Button SignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        SignInButton = (Button) findViewById(R.id.SignUpButton);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
       final DatabaseReference table_user = database.getReference("User") ;

        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("Ждите . . .");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child(edtPhone.getText().toString()).exists()){
                        mDialog.dismiss();
                        User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                        if (user.getpassword().equals(edtPassword.getText().toString()))
                        {
                            Toast.makeText(SignIn.this, "Вход подтверждён!", Toast.LENGTH_SHORT).show();
                        }

                        else

                        {
                            Toast.makeText(SignIn.this, "Ошибка входа", Toast.LENGTH_SHORT).show();
                        }

                        }
                        else
                        {
                            Toast.makeText(SignIn.this, "Такой пользователь не существует", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
