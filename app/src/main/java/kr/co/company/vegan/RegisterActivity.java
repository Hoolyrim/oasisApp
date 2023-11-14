package kr.co.company.vegan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증 처리
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private EditText mEtEmail, mEtPwd, mEtName, mEtAllergy; // 회원가입 입력 필드
    //    private Button mBtnRegister; // 회원가입 입력 버튼

    EditText firstText, secondText;
    TextView txt_check;
    ImageView setImage;


    @SuppressLint({ "WrongViewCast", "MissingInflatedId" })
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference( "Veganing" );

        mEtEmail = findViewById( R.id.id_textView2 );
        mEtPwd = findViewById( R.id.id_textView3 );
        mEtName = findViewById( R.id.id_textView5 );
        mEtAllergy = findViewById( R.id.id_textView13 );


//        // 새 EditText 생성
//        EditText mEtNew = new EditText( RegisterActivity.this );
//        mEtNew.setHint( "새 항목" );
//        mEtNew.setId( R.id.id_textView13 );
//
//        // 새 입력창의 위치와 크기 설정
//        mEtNew.setX( mEtEmail.getX() );
//        mEtNew.setY( mEtEmail.getY() + mEtEmail.getHeight() + 20 );
//        mEtNew.setWidth( mEtEmail.getWidth() );
//        mEtNew.setHeight( mEtEmail.getHeight() );
//
//        String strNew = mEtNew.getText().toString(); // 새 EditText의 문자열 가져오기
//
//        // 알러지 추가하기 버튼
//        Button AllergyBtn = findViewById( R.id.button10 );
//        AllergyBtn.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String strNew = AllergyBtn.getText().toString();
//                String strEmail = mEtEmail.getText().toString();
//
//                Task<AuthResult> authResultTask = mFirebaseAuth.createUserWithEmailAndPassword( strEmail, strNew ).addOnCompleteListener( RegisterActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
//                            UserAccount account = new UserAccount();
//                            account.setIdToken( firebaseUser.getUid() );
//                            account.setEmailId( firebaseUser.getEmail() );
//
//
//                            String userId = mFirebaseAuth.getCurrentUser().getUid();
//
//
//                            DatabaseReference userRef = mDatabaseRef.child( "users" ).child( userId );
//
//                            userRef.child( "new" ).setValue( strNew ); // 새 EditText의 문자열 파이어베이스 추가
//
//
//                            //setValue: database에 insert(삽입) 행위
//                            mDatabaseRef.child( "UserAccount" ).child( firebaseUser.getUid() ).setValue( account );
//
//                            Toast.makeText( RegisterActivity.this, "알러지를 추가하였습니다", Toast.LENGTH_SHORT ).show();
//                        }
//
//                    }
//                } );
//            }
//        });

        // 회원가입 입력 버튼
        Button mBtnRegister = findViewById( R.id.button9 );

        mBtnRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 처리 시작
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();
                String strName = mEtName.getText().toString();
                String strAllergy = mEtAllergy.getText().toString();

                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                startActivity( intent );


                // Firebase Auth 진행
                mFirebaseAuth.createUserWithEmailAndPassword( strEmail, strPwd ).addOnCompleteListener( RegisterActivity.this, new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            account.setIdToken( firebaseUser.getUid() );
                            account.setEmailId( firebaseUser.getEmail() );
                            account.setPassword( strPwd );

                            String userId = mFirebaseAuth.getCurrentUser().getUid();


                            DatabaseReference userRef = mDatabaseRef.child( "users" ).child( userId );
                            userRef.child( "email" ).setValue( strEmail );
                            userRef.child( "password" ).setValue( strPwd );
                            userRef.child( "name" ).setValue( strName );
                            userRef.child( "allergy" ).setValue( strAllergy );
//                            userRef.child( "new" ).setValue( strNew ); // 새 EditText의 문자열 파이어베이스 추가


                            //setValue: database에 insert(삽입) 행위
                            mDatabaseRef.child( "UserAccount" ).child( firebaseUser.getUid() ).setValue( account );

                            Toast.makeText( RegisterActivity.this, "회원가입에 성공하셨습니다", Toast.LENGTH_SHORT ).show();
                        } else {
                            Toast.makeText( RegisterActivity.this, "회원가입에 실패하셨습니다", Toast.LENGTH_SHORT ).show();
                        }

                    }
                } );
            }
        } );


//        TextWatcher textWatcher = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // 두 EditText의 텍스트가 일치하는지 확인
//                String pw1 = firstText.getText().toString();
//                String pw2 = secondText.getText().toString();
//
//                if (pw1.equals(pw2)) {
//                    // 비밀번호가 일치하는 경우
//                    txt_check.setText("비밀번호가 일치합니다.");
//                    txt_check.setTextColor(getResources().getColor(R.color.black));
//
//                    // 가입하기 버튼 활성화
//                    Button mBtnRegister = findViewById(R.id.button9);
//                    mBtnRegister.setEnabled(true);
//                } else {
//                    // 비밀번호가 일치하지 않는 경우
//                    txt_check.setText("비밀번호가 일치하지 않습니다.");
//                    txt_check.setTextColor(getResources().getColor(R.color.purple_200));
//
//                    // 가입하기 버튼 비활성화
//                    Button mBtnRegister = findViewById(R.id.button9);
//                    mBtnRegister.setEnabled(false);
//                }
//            }
//        };
//
//        // 두 EditText에 텍스트 변경 콜백 함수 설정
//        firstText.addTextChangedListener(textWatcher);
//        secondText.addTextChangedListener(textWatcher);


//        // EditText 객체에 TextWatcher 리스너 추가
//        if (firstText != null && secondText != null) {
//            TextWatcher textWatcher = null;
//            firstText.addTextChangedListener(textWatcher);
//            secondText.addTextChangedListener(textWatcher);
//        }
//        // TextWatcher 구현
//        TextWatcher textWatcher = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // 입력 전
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // 텍스트 변경 중
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // 텍스트 변경 후
//                if (firstText != null && secondText != null) {
//                    checkPasswordMatch(); // 비밀번호 일치 여부 확인 메서드 호출
//                }
//            }
//
//            private void checkPasswordMatch() {
//                String pw1 = firstText.getText().toString();
//                String pw2 = secondText.getText().toString();
//
//                if (pw1.equals( pw2 )) {
//                    // 비밀번호가 일치하는 경우
//                    // "비밀번호가 일치합니다." 메시지를 Toast로 표시
//                    Toast.makeText( this, "비밀번호가 일치합니다.", Toast.LENGTH_SHORT ).show();
//
//                } else {
//                    // 비밀번호가 일치하지 않는 경우
//                    // "비밀번호가 일치하지 않습니다." 메시지를 Toast로 표시
//                    Toast.makeText( this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT ).show();
//                }
//            }
//        };
//
//
//    }


        //새로운 시작

//        checkview = (TextView) findViewById(R.id.textView13);
//        setImage = (ImageView) findViewById( R.id.imageView7 );


//        if (secondText != null && secondText.getText().toString() != null) {
//            secondText.addTextChangedListener(new TextWatcher() {
//                // TextWatcher의 메서드 구현
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    // EditText에 문자 입력 전
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    // EditText에 변화가 있을 경우
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    Log.d("MyActivity", "afterTextChanged called"); // 로그 메시지 추가
//                    if (secondText.getText().toString() != null) {
//                        if (firstText.getText().toString().equals(secondText.getText().toString())) {
//                            txt_check.setText("일치합니다.");
//                        } else {
//                            txt_check.setText("일치하지 않습니다.");
//                        }
//                    } else {
//                        Log.e("MyActivity", "secondText.getText().toString() is null"); // 오류 메시지 추가
//                    }
//                }
//
//
//
//
//            });
//        }


        firstText = findViewById( R.id.textView3 );
        secondText = findViewById( R.id.textView4 );
        txt_check = findViewById( R.id.txt_check );


        if (secondText != null && secondText.getVisibility() == View.VISIBLE) {
            secondText.addTextChangedListener( new TextWatcher() {
                // TextWatcher의 메서드 구현
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // EditText에 문자 입력 전
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // EditText에 변화가 있을 경우
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // EditText에 텍스트가 변경된 경우
                    String pw1 = firstText.getText().toString();
                    String pw2 = secondText.getText().toString();

                    if (pw1.equals( pw2 )) {
                        txt_check.setText( "일치합니다." );
                    } else {
                        txt_check.setText( "일치하지 않습니다." );
                    }
                }

            } );
        }
    }
}



//        if(secondText != null){
//            secondText.addTextChangedListener(new TextWatcher(){
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                    if(firstText.getText().toString().equals(secondText.getText().toString())) {
//                        setImage.setImageResource(R.drawable.sign_up_password_right);
//                    } else {
//
//                        setImage.setImageResource(R.drawable.sign_up_password_correct);
//                    }
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//
//                }
//
//            });
//        }





