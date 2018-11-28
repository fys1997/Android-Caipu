package com.example.ado.cookbookuser.view;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.model.User;
import com.example.ado.cookbookuser.presenter.LoginPresenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private Toolbar toolbarLogin;                //LoginActivity的toolbar
    private EditText editUserName;               //输入用户名
    private EditText editUserPassword;           //输入密码
    private Button btnLogin;                     //登录按键
    private TextView btnRegister;                //注册新用户按键

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter(this);

        toolbarLogin = findViewById(R.id.toolbar_login);
        editUserName = findViewById(R.id.edit_user_name);
        editUserPassword = findViewById(R.id.edit_user_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register_user);

        //设置监听事件
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        //设置toolbar
        setSupportActionBar(toolbarLogin);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //清空输入栏
        editUserPassword.setText("");
        editUserName.setText("");
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:{
                String userName = editUserName.getText().toString();
                String userPassword = editUserPassword.getText().toString();

                if(!hasWrongInput(userName,userPassword))
                    loginPresenter.userLogin(userName,userPassword);
                break;
            }
            case R.id.btn_register_user:{
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            }
            default:
                break;
        }
    }

    //检查输入是否为空
    private boolean hasWrongInput(String name,String password){
        if(name.equals("") ){
            Toast.makeText(LoginActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return true;
        }else if(password.equals("")){
            Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    //用户名不存在
    public void onNotExistUserName(){
        Toast.makeText(LoginActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
    }

    //密码错误
    public void onWrongPassword(){
        Toast.makeText(LoginActivity.this,"密码错误，请检查后重新输入",Toast.LENGTH_SHORT).show();
    }

    //登录成功
    public void onLoginSucceed(User user){

        String storageState = Environment.getExternalStorageState();
        //判断是否存在可用的的SD Card，将当前的登录状态存入缓存
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {

            String userForNowFile = getExternalCacheDir().getAbsolutePath()  + File.separator + "userForNowFile.txt";
            File file = new File(userForNowFile);
            if (!file.exists()){
                try{
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileOutputStream outputStream = new FileOutputStream(userForNowFile);
                outputStream.write(user.getName().getBytes());
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //设置当前登录用户
        BaseActivity.userForNow = user;
        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
