package hoangviet.ndhv.com;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
Button button_signUp;
EditText edt_dangki_username,edt_dangki_password,edt_dangki_Repassword;
Button btn_dangki_Huy,btn_dangki_Xacnhan,btn_signIn;
TextInputLayout usernameWrapper,passwordWraper;
    public static TextInputEditText edt_dangnhap_username;
    TextInputEditText edt_dangnhap_password;



    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();



        edt_dangnhap_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0){
                usernameWrapper.setError("Bạn phải nhập Username");
            }else {
                usernameWrapper.setError(null);
            }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_dangnhap_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0){
                    passwordWraper.setError("Bạn phải nhập Password");

                }else {
                    passwordWraper.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // sự kiện buttonsignin
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dangnhapAuth();
            }
        });
        button_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDangKi();
            }
        });




    }
    private void dialogDangKi(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dang_ki);
        dialog.setCanceledOnTouchOutside(false);


        // ánh xạ
        edt_dangki_username   = (EditText)dialog.findViewById(R.id.editText_dangki_username);
        edt_dangki_password   = (EditText)dialog.findViewById(R.id.editText_dangki_passwword);
        edt_dangki_Repassword = (EditText)dialog.findViewById(R.id.editText_dangki_repassword);
        btn_dangki_Huy        = (Button)dialog.findViewById(R.id.button_dangki_huy);
        btn_dangki_Xacnhan    = (Button)dialog.findViewById(R.id.button_dangki_xacnhan);
        btn_dangki_Xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangkiAuth();
            }
        });
        btn_dangki_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void dangnhapAuth(){
        mAuth = FirebaseAuth.getInstance();
        final String email = edt_dangnhap_username.getText().toString();
        String password =  edt_dangnhap_password.getText().toString();
        if (email.isEmpty() ||  password.isEmpty() || email.isEmpty() && password.isEmpty()){
            Toast.makeText(this, "Bạn chưa nhập Email hoặc password.", Toast.LENGTH_SHORT).show();

        }else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this,second_activity.class);
                                intent.putExtra("user",email);

                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Lỗi đăng nhập", Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }


    }
    private void dangkiAuth(){
        mAuth = FirebaseAuth.getInstance();
        String emaildk    = edt_dangki_username.getText().toString();
        String passworddk = edt_dangki_password.getText().toString();
        String re_passworddk = edt_dangki_Repassword.getText().toString();
        if (passworddk.equals(re_passworddk)){
            mAuth.createUserWithEmailAndPassword(emaildk, passworddk)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Chúc mừng bạn, tạo tài khoản thành công!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Lỗi tạo tài khoản, xin vui lòng kiểm tra lại!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }else {
            Toast.makeText(this, "Xác nhận mật khẩu không đúng, xin vui lòng kiểm tra lại!", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //
        return super.onOptionsItemSelected(item);
    }

    private void anhXa() {
        button_signUp = (Button)findViewById(R.id.button_SignUp);
        edt_dangnhap_username = (TextInputEditText) findViewById(R.id.editTex_Username);
        edt_dangnhap_password = (TextInputEditText) findViewById(R.id.editText_Password);
        btn_signIn = (Button)findViewById(R.id.button_SignIn);
        usernameWrapper = (TextInputLayout)findViewById(R.id.Username_Wrapper);
        passwordWraper = (TextInputLayout)findViewById(R.id.Password_Wrapper);
    }

}
