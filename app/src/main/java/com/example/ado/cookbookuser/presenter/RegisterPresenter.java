package com.example.ado.cookbookuser.presenter;

import com.example.ado.cookbookuser.model.User;
import com.example.ado.cookbookuser.view.RegisterActivity;
import org.litepal.crud.DataSupport;
import java.util.List;

public class RegisterPresenter {

    private RegisterActivity registerActivity;

    public RegisterPresenter(RegisterActivity registerActivity){
        this.registerActivity = registerActivity;
    }

    public void userRegister(String name, String password, String passwordCheck){
        if(!password.equals(passwordCheck)){
            registerActivity.onEnterDifferentPassword();
        }else if(this.hasUserName(name)){
            registerActivity.onExistUserName();
        }else{
            registerActivity.onRegisterSucceed(name,password);
        }
    }

    private boolean hasUserName(String name){
        List<User> users = DataSupport.findAll(User.class);
        for(User user:users){
            if(name.equals(user.getName())){
                return true;
            }
        }
        return false;
    }
}
