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


    public void userLogin(String name,String password){
        List<User> users = DataSupport.findAll(User.class);
        for(User user:users){
            if(name.equals(user.getName())){
                try {
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
        loginActivity.onNotExistUserName();
    }
}
