package kr.co.company.vegan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//import com.nhn.android.naverlogin.OAuthLogin;
//import com.nhn.android.naverlogin.OAuthLoginHandler;
//import com.naver.auth.OAuthLogin;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증 처리
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private EditText mEtEmail;
    private EditText mEtPwd; // 로그인 입력 필드
    private CheckBox checkBox1;
    private CheckBox checkBox2; //자동로그인,아이디저장
    private SharedPreferences appData; //SharedPreferences 객체 변수 생성
    private boolean saveLoginData, saveIdData;
    private String id, pwd;
    private CheckBox cb_save;
    private Context mContext;

    private ImageButton naverLogin;
    private OAuthLogin mOAuthLoginModule;
    private Context mContext2;

    // 로그인
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        mContext = this; // 이거 필수!

        mEtEmail = (EditText) findViewById( R.id.id_textView );
        mEtPwd = (EditText) findViewById( R.id.pwd_textView );
        cb_save = (CheckBox) findViewById( R.id.checkBox1 );


        //자동 로그인
        // shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        boolean boo = sharedPreferences.getBoolean("check", false); // Check if "check" is true
        String savedEmail = sharedPreferences.getString("id", "");
        String savedPassword = sharedPreferences.getString("pw", "");

        if (boo) {
            // If "check" is true, set the retrieved email, password, and checkbox state
            mEtEmail.setText(savedEmail);
            mEtPwd.setText(savedPassword);
            cb_save.setChecked(true); // Checkbox should remain checked
        }


        Button button1 = findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEtEmail.getText().toString();
                String password = mEtPwd.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "아이디/암호를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("id", email);
                    intent.putExtra("pw", password);

                    // 이메일, 비밀번호 저장
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("id", email);
                    editor.putString("pw", password);
                    editor.apply();

                    startActivity(intent);
                }
            }
        });

        cb_save.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { // checked
                    // Save the email and password to shared preferences
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("id", mEtEmail.getText().toString());
                    editor.putString("pw", mEtPwd.getText().toString());
                    editor.putBoolean("check", true);
                    editor.apply();
                } else { // unchecked
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("id");
                    editor.remove("pw");
                    editor.putBoolean("check", false);
                    editor.apply();
                }
            }
        });


//        ImageButton loginButton = findViewById( R.id.kakaoLogin );
//
//        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
//            @Override
//            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
//                if (oAuthToken != null) {
//                    Intent intent = new Intent(LoginActivity.this, MyPageActivity.class);
//                    startActivity( intent );
//                    finish();
//
//                }
//                if (throwable != null) {
//
//                }
//                updateKakaoLoginUi();
//                return null;
//            }
//        };
//
//        loginButton.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable( LoginActivity.this )) {
//                    UserApiClient.getInstance().loginWithKakaoTalk( LoginActivity.this, callback );
//                } else {
//                    UserApiClient.getInstance().loginWithKakaoAccount( LoginActivity.this, callback );
//                }
//            }
//        } );
//        updateKakaoLoginUi();


        try {
            Log.d( "getKeyHash", "" + getKeyHash( LoginActivity.this ) );
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException( e );
        }

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference( "Veganing" );

        mEtEmail = findViewById( R.id.id_textView );
        mEtPwd = findViewById( R.id.pwd_textView );


        Button button = findViewById( R.id.button );
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그인 요청
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();


                mFirebaseAuth.signInWithEmailAndPassword( strEmail, strPwd ).addOnCompleteListener( LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //로그인 성공 -> 이동할 페이지 수정하기
                            Intent intent = new Intent( LoginActivity.this, MainActivity.class );
                            startActivity( intent );
                            finish(); // 현재 액티비티 파괴
                        } else {
                            Toast.makeText( LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT ).show();


                        }
                    }
                } );



            }
        } );

        // 회원가입 버튼
        Button button12 = findViewById( R.id.button12 );
        button12.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity( intent );
            }
        } );

//        //네이버 로그인
//        mContext2 = getApplicationContext();
//        naverLogin = findViewById(R.id.naverLogin);
//
//        naverLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mOAuthLoginModule = OAuthLogin.getInstance();
//                mOAuthLoginModule.init(
//                        mContext
//                        ,getString(R.string.naver_client_id)
//                        ,getString(R.string.naver_client_secret)
//                        ,getString(R.string.naver_client_name)
//                        //,OAUTH_CALLBACK_INTENT
//                        // SDK 4.1.4 버전부터는 OAUTH_CALLBACK_INTENT변수를 사용하지 않습니다.
//                );
//
//                @SuppressLint("HandlerLeak")
//                OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
//                    @Override
//                    public void run(boolean success) {
//                        if (success) {
//                            String accessToken = mOAuthLoginModule.getAccessToken(mContext2);
//                            String refreshToken = mOAuthLoginModule.getRefreshToken(mContext2);
//                            long expiresAt = mOAuthLoginModule.getExpiresAt(mContext2);
//                            String tokenType = mOAuthLoginModule.getTokenType(mContext2);
//
//                            Log.i("LoginData","accessToken : "+ accessToken);
//                            Log.i("LoginData","refreshToken : "+ refreshToken);
//                            Log.i("LoginData","expiresAt : "+ expiresAt);
//                            Log.i("LoginData","tokenType : "+ tokenType);
//
//                        } else {
//                            String errorCode = mOAuthLoginModule
//                                    .getLastErrorCode(mContext2).getCode();
//                            String errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext2);
//                            Toast.makeText(mContext, "errorCode:" + errorCode
//                                    + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
//                        }
//                    };
//                };
//
//                mOAuthLoginModule.startOauthLoginActivity(LoginActivity.this, mOAuthLoginHandler);
//            }
//        });

    }

//    private void updateKakaoLoginUi() {
//        UserApiClient.getInstance().me( new Function2<User, Throwable, Unit>() {
//            @Override
//            public Unit invoke(User user, Throwable throwable) {
//                // 로그인이 되어있으면
//                if (user != null) {
//                    // 유저의 아이디
//                    Log.d( TAG, "invoke: id" + user.getId() );
//                    // 유저의 어카운트정보에 이메일
//                    Log.d( TAG, "invoke: nickname" + user.getKakaoAccount().getEmail() );
//                    // 유저의 어카운트 정보의 프로파일에 닉네임
//                    Log.d( TAG, "invoke: email" + user.getKakaoAccount().getProfile().getNickname() );
//                    // 유저의 어카운트 파일의 성별
//                    Log.d( TAG, "invoke: gerder" + user.getKakaoAccount().getGender() );
//                    // 유저의 어카운트 정보에 나이
//                    Log.d( TAG, "invoke: age" + user.getKakaoAccount().getAgeRange() );
//
////                    nickName.setText( user.getKakaoAccount().getProfile().getNickname() );
//
////                    Glide.with( profileImage ).load( user.getKakaoAccount().
////                            getProfile().getProfileImageUrl() ).circleCrop().into( profileImage );
////                    loginButton.setVisibility( View.GONE );
////                    logoutButton.setVisibility( View.VISIBLE );
//                } else {
//                    // 로그인이 되어 있지 않다면 위와 반대로
////                    nickName.setText( null );
////                    profileImage.setImageBitmap( null );
////                    loginButton.setVisibility( View.VISIBLE );
////                    logoutButton.setVisibility( View.GONE );
//                }
//                return null;
//            }
//        } );
//    }

    public String getKeyHash(final Context context) throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo PackageInfo = pm.getPackageInfo( context.getPackageName(), PackageManager.GET_SIGNATURES );
            if (PackageInfo.packageName == null)
                return null;

            for (Signature signature : PackageInfo.signatures) {
                try {
                    MessageDigest md = MessageDigest.getInstance( "SHA" );
                    md.update( signature.toByteArray() );
                    return Base64.encodeToString( md.digest(), Base64.NO_WRAP );
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                return null;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class OAuthLogin {
    }

    private class OAuthLoginHandler {
    }
}

