package com.example.widgetdemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.widgetdemo.databinding.ActivityInfoBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewBindingActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityInfoBinding binding;
    private boolean isRight = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取视图绑定对象
        binding = ActivityInfoBinding.inflate(getLayoutInflater());
        LinearLayout root = binding.getRoot();
        setContentView(root);

        binding.btnConfirm.setOnClickListener(this);
        binding.etUserphone.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String phone = binding.etUserphone.getText().toString();
                if (!TextUtils.isEmpty(phone) && !validatePhone(phone)) {
                    binding.phoneLayout.setError("请输入正确的手机号");
                    binding.etUserphone.setText("");
                    isRight = false;
                } else {
                    isRight = true;
                    binding.phoneLayout.setError("");
                }
            }
        });
        // 获取传递的值
        final Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        if (!TextUtils.isEmpty(username)) {
            binding.etUsername.setText(username);
        }
    }

    public String getCheckBoxText() {
        String result = "";
        if (binding.cbMath.isChecked()) {
            result += binding.cbMath.getText() + ",";
        }
        if (binding.cbJava.isChecked()) {
            result += binding.cbJava.getText() + ",";
        }
        if (binding.cbEnglish.isChecked()) {
            result += binding.cbEnglish.getText() + ",";
        }
        if (binding.cbAndroid.isChecked()) {
            result += binding.cbAndroid.getText() + ",";
        }
        return result.substring(0, result.length() - 1);
    }

    @Override
    public void onClick(View v) {
        int id = binding.rgSex.getCheckedRadioButtonId();
        String sex = binding.rbMale.getText().toString();
        if (id == R.id.rb_female) {
            sex = binding.rbFemale.getText().toString();
        }
        if (isRight) {
            String info = "用户名：" + binding.etUsername.getText()
                    + "，手机号：" + binding.etUserphone.getText()
                    + "，性别：" + sex + "\n喜欢的课程：" + getCheckBoxText();
            Snackbar.make(binding.llMain, info, Snackbar.LENGTH_SHORT)
                    .setAction("确认", null).show();
        }
    }

    private static final String PHONE_PATTERN = "^1[3-9]\\d{9}$";
    private boolean validatePhone(String phone) {
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}
