package com.example.ado.cookbookuser.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.model.DES;
import com.example.ado.cookbookuser.model.User;
import com.example.ado.cookbookuser.presenter.RegisterPresenter;

import java.io.ByteArrayOutputStream;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private EditText editNewUserName;                    //输入新用户名
    private EditText editNewUserPassword;                //输入新用户密码
    private EditText editNewUserPasswordCheck;           //再次输入密码
    private Toolbar toolbarRegister;                     //RegisterActivity的toolbar
    private Button register;                             //注册按键

    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerPresenter = new RegisterPresenter(this);

        editNewUserName = findViewById(R.id.edit_new_user_name);
        editNewUserPassword = findViewById(R.id.edit_new_user_password);
        editNewUserPasswordCheck =findViewById(R.id.edit_new_user_password_makeSure);
        toolbarRegister = findViewById(R.id.toolbar_register);
        register = findViewById(R.id.btn_register);

        setToolbar(toolbarRegister);

        //设置监听事件
        register.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //清空输入框
        editNewUserName.setText("");
        editNewUserPassword.setText("");
        editNewUserPasswordCheck.setText("");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_register:{
                String newUserName = editNewUserName.getText().toString();
                String newUserPassword = editNewUserPassword.getText().toString();
                String newUserPasswordCheck = editNewUserPasswordCheck.getText().toString();

                if(!hasWrongInput(newUserName,newUserPassword,newUserPasswordCheck))
                    registerPresenter.userRegister(newUserName,newUserPassword,newUserPasswordCheck);
            }
            default:
                break;
        }
    }

    //检查是否输入为空
    private boolean hasWrongInput(String name,String password,String passwordCheck){
        if(name.equals("") ){
            Toast.makeText(RegisterActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return true;
        }else if(password.equals("")){
            Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return true;
        }else if(passwordCheck.equals("")){
            Toast.makeText(RegisterActivity.this,"确认密码不能为空",Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    //两次密码不一致
    public void onEnterDifferentPassword(){
        Toast.makeText(RegisterActivity.this,"两次输入的密码不一致，请重新输入",Toast.LENGTH_SHORT).show();
    }

    //用户已存在
    public void onExistUserName(){
        Toast.makeText(RegisterActivity.this,"该用户已存在",Toast.LENGTH_SHORT).show();
    }

    //注册成功
    public void onRegisterSucceed(String name,String password){
        //用户默认信息
        User user = new User();
        user.setName(name);

        try {
            user.setPassword(DES.encryptDES(password,KEY_PASSWORD));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.animal);
        user.setHeadShot(bitmapToByteArray(bitmap));
        user.setGender("未设置");
        user.setBirthday("2018-1-1");
        user.save();
        Toast.makeText(RegisterActivity.this,"用户注册成功",Toast.LENGTH_SHORT).show();
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
