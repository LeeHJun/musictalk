package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증처리
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText nsignname, nsignID, nsignBirth, nsignBirth2, // 로그인 입력필드
            nsignBirth3, nsignPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Mutalk");

        nsignID = findViewById(R.id.editID);
        nsignPW = findViewById(R.id.ediPassword);

        Button signin = (Button) findViewById(R.id.signin);

        signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                Intent intent = new Intent(getApplicationContext(), sign.class);
                startActivity(intent);
            }
        });

        Button loginButton = (Button) findViewById(R.id.loginbutton);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){

                //로그인 요청

                String strID = nsignID.getText().toString();
                String strPW = nsignPW.getText().toString();

                mFirebaseAuth.signInWithEmailAndPassword(strID, strPW).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            //로그인 성공!!!
                            Intent intent = new Intent(MainActivity.this, loginok.class);
                            startActivity(intent);
                            finish(); // 현재 액티비티 삭제
                        } else {
                            //로그인 실패
                            Intent intent = new Intent(MainActivity.this, loginfailed.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });


    }
}