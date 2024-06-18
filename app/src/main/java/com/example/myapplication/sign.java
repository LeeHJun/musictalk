package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sign extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증처리
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText nsignname, nsignID, nsignBirth, nsignBirth2, // 회원가입 입력필드
            nsignBirth3, nsignPW, nsignmail;
    private Button nsignupbutton;  // 회원가입 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Mutalk");

        nsignname = findViewById(R.id.signName);
        nsignID = findViewById(R.id.signID);
        nsignBirth = findViewById(R.id.signBirth);
        nsignBirth2 = findViewById(R.id.signBirth2);
        nsignBirth3 = findViewById(R.id.signBirth3);
        nsignPW = findViewById(R.id.signPW);
        nsignupbutton = findViewById(R.id.signupbutton);

        nsignupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 처리 시작
                String strname = nsignname.getText().toString();
                String strID = nsignID.getText().toString();
                String strBirth = nsignBirth.getText().toString();
                String strBirth2 = nsignBirth2.getText().toString();
                String strBirth3 = nsignBirth3.getText().toString();
                String strPW = nsignPW.getText().toString();

                mFirebaseAuth.createUserWithEmailAndPassword(strID, strPW).addOnCompleteListener
                        (sign.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser FirebaseUser = mFirebaseAuth.getCurrentUser();
                                    UserAccount account = new UserAccount();
                                    account.setIdToken(FirebaseUser.getUid());
                                    account.setEmailId(strID);
                                    account.setPassword(strPW);
                                    account.setName(strname);
                                    account.setBirth(strBirth);
                                    account.setBirth2(strBirth2);
                                    account.setBirth3(strBirth3);


                                    //setValue = 데이터베이스에 insert(삽입) 행위
                                    mDatabaseRef.child("UserAccount").child(FirebaseUser.getUid()).setValue(account);
                                    mDatabaseRef.child("UserAccount").child(strname).setValue(account);
                                    mDatabaseRef.child("UserAccount").child(strBirth).setValue(account);
                                    mDatabaseRef.child("UserAccount").child(strBirth2).setValue(account);
                                    mDatabaseRef.child("UserAccount").child(strBirth3).setValue(account);

                                    Toast.makeText(sign.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(sign.this, "회원가입에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
            }
        });


        Button back = (Button) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}