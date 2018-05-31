package com.example.francisco.proj_kcs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText nome, email, tlm, username, password, rua, cp;
    Button regist;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new DatabaseHelper(this);
        nome = findViewById(R.id.editTextNome);
        email = findViewById(R.id.editTextMail);
        tlm = findViewById(R.id.editTextTlm);
        username = findViewById(R.id.editTextUser);
        password = findViewById(R.id.editTextPassword);
        rua = findViewById(R.id.editTextRua);
        cp = findViewById(R.id.editTextRua);
        regist = findViewById(R.id.confirmRegister);


        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (db.verificaUser(String.valueOf(username.getText()))) {
                    Toast.makeText(getApplicationContext(), "Username já utilizado!", Toast.LENGTH_SHORT).show();
                } else {
                    db.createUser(String.valueOf(nome.getText()), String.valueOf(email.getText()), String.valueOf(tlm.getText()),
                            String.valueOf(username.getText()), String.valueOf(password.getText()), String.valueOf(rua.getText()), String.valueOf(cp.getText()));
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        });
    }
}
