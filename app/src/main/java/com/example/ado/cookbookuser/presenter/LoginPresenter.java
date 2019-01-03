package com.example.ado.cookbookuser.presenter;

import com.example.ado.cookbookuser.model.DES;
import com.example.ado.cookbookuser.model.User;
import com.example.ado.cookbookuser.view.BaseActivity;
import com.example.ado.cookbookuser.view.LoginActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

public class LoginPresenter {

    private LoginActivity loginActivity;

    public LoginPresenter(LoginActivity loginActivity){
        this.loginActivity = loginActivity;
    }

    //从数据库中找到用户，判断用户名和密码是否匹配
    public void userLogin(String name,String password){
        List<User> users = DataSupport.findAll(User.class);
        for(User user:users){
            if(name.equals(user.getName())){
                try {
                    //解码并判断
                    if(password.equals(DES.decryptDES(user.getPassword(), BaseActivity.KEY_PASSWORD))){
                        loginActivity.onLoginSucceed(user);
                        return;
                    }else{
                        loginActivity.onWrongPassword();
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //无用户
        loginActivity.onNotExistUserName();
    }
}
