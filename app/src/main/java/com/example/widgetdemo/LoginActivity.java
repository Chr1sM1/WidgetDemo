package com.example.widgetdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //1.获取界面的控件对象
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        Button btnLogin = findViewById(R.id.btn_login);
        //2.监听登陆按钮的点击事件
        btnLogin.setOnClickListener(v -> {

            //3.登陆的业务逻辑处理
            //3.1获取编辑框输入的数据
            final String username=etUsername.getText().toString();
            final String password=etPassword.getText().toString();


            //3.2 判断用户名、密码是否正确，根据判断结果进行处理，成功则跳转，错误给出提示
            if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
                Toast.makeText(LoginActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
            }else if(!username.equals("android")||!password.equals("123456")){
                Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                etPassword.setText("");
                etPassword.requestFocus();
            }else{
                Toast.makeText(LoginActivity.this,"登录信息"+username+","+password,
                        Toast.LENGTH_SHORT).show();

                // 成功后将username传递给下一个Activity界面
                // 使用Intent对象传递数据
                final Intent intent=new Intent(LoginActivity.this, InfoActivity.class);
                intent.putExtra("username",username);
                // 跳转到另一个界面
                startActivity(intent);
                finish();
            }
        });
        // etPassword为密码框控件对象
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard();
                }
            }
        });
    }
    public void hideKeyboard() {
        //得到InputMethodManager的实例
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果开启
        if (imm.isActive()) {
            //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}