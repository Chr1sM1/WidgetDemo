package com.example.widgetdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.NoCopySpan;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener  {
    private LinearLayout mainLayout;
    private EditText etUsername,etPhone;
    private RadioGroup rgSex;
    private TextInputLayout phoneLayout;

    //checkbox选中的文本字符串
    private String selected = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //初始化布局对象
        mainLayout = findViewById(R.id.ll_main);
        phoneLayout = findViewById(R.id.phone_layout);

        //初始化输入框，单选空间对象
        etUsername = findViewById(R.id.et_username);
        etPhone = findViewById(R.id.et_userphone);
        rgSex = findViewById(R.id.rg_Sex);

        //初始化复选按钮控件
        CheckBox cbAndroid = findViewById(R.id.cb_android);
        CheckBox cbJava = findViewById(R.id.cb_java);
        CheckBox cbEnglish = findViewById(R.id.cb_english);
        CheckBox cbMath = findViewById(R.id.cb_math);

        //获取按钮对象，设置它的点击事件监听器
        Button btnConfirm = findViewById(R.id.btn_confirm);
        //设置事件监听器
        cbJava.setOnCheckedChangeListener(this);
        cbAndroid.setOnCheckedChangeListener(this);
        cbEnglish.setOnCheckedChangeListener(this);
        cbMath.setOnCheckedChangeListener(this);
        btnConfirm.setOnClickListener(this);

        //获取传递的值
        final Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        if(!TextUtils.isEmpty(username)){
            etUsername.setText(username);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        CheckBox checkBox = (CheckBox) buttonView;
        if (isChecked){
            selected += checkBox.getText().toString()+" ";
        }else{
            selected = selected.replace(checkBox.getText().toString()+" ","");
        }
        Snackbar.make(mainLayout,selected,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        //获取输入的值
        String username = etUsername.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        //验证手机号
        if(!validatePhone(phone)){
            phoneLayout.setError("请输入正确的手机号");
            etPhone.setText("");
            etPhone.requestFocus();
            return;
        }

        //获取RadioButton选项的值
//        String sex ="男";
        int id = rgSex.getCheckedRadioButtonId();
//        if(id== R.id.rb_female){
//            sex="女";
//        }
        RadioButton btn =findViewById(id);
        String sex = btn.getText().toString();

        //将数据组合成字符串
        String info = "用户名:" + username + ",手机号" + phone + "，性别:"
                + sex + "\n喜欢的课程: " + selected;

        //使用Snackbar显示信息
        Snackbar.make(mainLayout,info,Snackbar.LENGTH_LONG)
                .setAction("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InfoActivity.this,
                        "信息已确认",Toast.LENGTH_SHORT).show();
            }
        }).show();
    }
    private static final String PHONE_PATTERN="^1[3-9]\\d{9}$";
    private boolean validatePhone(String phone){
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}
