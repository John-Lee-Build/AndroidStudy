package com.lobo.lobotools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences preferences;//kay,value 键值方式存储数据（例如账号和密码）
    private CheckBox checkBox;
    private EditText etLoginUsername, etLoginPsw;
    //写死账号密码
    private String userName = "LOBO";
    //private String psw = "123321";
    private String psw;

    /*//获取系统时间
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    //月
    int month = calendar.get(Calendar.MONTH)+1;

    int num = year * 100 + month;
    double yushu = num / 314 *1000;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //psw = yushu + "";
        //psw =psw.substring(0,5);//截取前5位


        psw = createPsw();

        //Toast.makeText(this, psw, Toast.LENGTH_SHORT).show();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        checkBox = findViewById(R.id.cb_login_rememberpsw);
        etLoginUsername = findViewById(R.id.et_login_username);
        etLoginPsw = findViewById(R.id.et_login_psw);

        boolean isRemember = preferences.getBoolean("remember", false);
        if (isRemember) {
            etLoginUsername.setText(preferences.getString("username", ""));
            etLoginPsw.setText(preferences.getString("psw", ""));
            checkBox.setChecked(true);
        }
    }

    public String createPsw() {//创造密码
        Calendar calendar = Calendar.getInstance();//获取系统日期
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH) + 1;

        float num = year * 100 + month;
        float num2 = num / month;
        String num3 = num2 + "";// + “” 表示转换成字符
        num3 = num3.substring(1,5);//截取前2-5位
        //Toast.makeText(this, num3, Toast.LENGTH_SHORT).show();
        return num3;
    }

    public void setLoginBtn(View view) {
        //检测是否记住密码，如果是那么就保存用户名和密码
        boolean remember = checkBox.isChecked();
        SharedPreferences.Editor editor = preferences.edit();
        if (remember) {//打勾状态
            String getUserName = etLoginUsername.getText().toString();
            String getPsw = etLoginPsw.getText().toString();
            //checkUserNameAndPsw();

            if (OnClickUtils.isFastClick()) {//防止频繁点击
                // 进行点击事件后的逻辑操作
                if (userName.equals(etLoginUsername.getText().toString())) {

                    if (psw.equals(etLoginPsw.getText().toString())) {

                        editor.putString("username", getUserName);
                        editor.putString("psw", getPsw);
                        editor.putBoolean("remember", true);
                        editor.apply();

                        //Toast.makeText(this, "用户名和密码已保存", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    } else {
                        Toast.makeText(this, "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                        etLoginPsw.setText("");
                    }

                } else {
                    Toast.makeText(this, "此用户不存在，请重新输入", Toast.LENGTH_SHORT).show();
                    etLoginUsername.setText("");
                }
            }



        } else {//不打勾状态
            editor.clear();//清空保存
            editor.apply();
            if (userName.equals(etLoginUsername.getText().toString())) {

                if (psw.equals(etLoginPsw.getText().toString())) {

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                    etLoginPsw.setText("");
                }

            } else {
                Toast.makeText(this, "此用户不存在，请重新输入", Toast.LENGTH_SHORT).show();
                etLoginUsername.setText("");
            }
        }
    }
}
