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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class sign extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증처리
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText nsignname, nsignID, nsignBirth, nsignBirth2, // 회원가입 입력필드
            nsignBirth3, nsignPW, nsignmail, PWok;
    private Button nsignupbutton, checkIDButton;  // 회원가입 버튼
    private boolean isIDChecked = false;  // ID 중복 확인 여부

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
        PWok = findViewById(R.id.signPW2);
        checkIDButton = findViewById(R.id.checkIDButton);

        checkIDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strID = nsignID.getText().toString();
                if (strID.isEmpty()) {
                    Toast.makeText(sign.this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                checkIDAvailability(strID);
            }
        });

        nsignupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isIDChecked) {
                    Toast.makeText(sign.this, "아이디 중복 확인을 해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

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

                                    Toast.makeText(sign.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(sign.this, "회원가입에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkIDAvailability(final String strID) {
        mDatabaseRef.child("UserAccount").orderByChild("emailId").equalTo(strID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Toast.makeText(sign.this, "아이디가 이미 존재합니다.", Toast.LENGTH_SHORT).show();
                            isIDChecked = false;
                        } else {
                            Toast.makeText(sign.this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                            isIDChecked = true;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(sign.this, "아이디 확인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
