package com.example.ado.cookbookuser.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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

import org.litepal.crud.DataSupport;

import java.util.List;

public class ChangePwdActivity extends BaseActivity implements View.OnClickListener{

    private Toolbar toolbarChangePwd;
    private EditText oldPasswordEdit;
    private EditText newPasswordEdit;
    private Button submitChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        toolbarChangePwd = findViewById(R.id.toolbar_change_pwd);
        oldPasswordEdit = findViewById(R.id.old_password);
        newPasswordEdit = findViewById(R.id.new_password);
        submitChange = findViewById(R.id.submit_button);

        setToolbar(toolbarChangePwd);

        submitChange.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        newPasswordEdit.setText("");
        oldPasswordEdit.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_button:{
                if(isInputTrue()){
                    String oldPwd = oldPasswordEdit.getText().toString();
                    String newPwd = newPasswordEdit.getText().toString();
                    String name = BaseActivity.userForNow.getName();
                    List<User> users = DataSupport.findAll(User.class);
                    for(User user:users){
                        if(name.equals(user.getName())){
                            try {
                                String oldPassword = DES.decryptDES(user.getPassword(), BaseActivity.KEY_PASSWORD);
                                if(oldPwd.equals(oldPassword)){
                                    try {
                                        user.setPassword(DES.encryptDES(newPwd,KEY_PASSWORD));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    user.save();
                                    Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                    return;
                                }else{
                                    Toast.makeText(this, "旧密码输入错误", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                break;
            }
        }
    }

    private boolean isInputTrue(){
        if (oldPasswordEdit.getText().toString().equals("")){
            Toast.makeText(this, "请输入您的旧密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (newPasswordEdit.getText().toString().equals("")){
            Toast.makeText(this, "请输入您的新密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (oldPasswordEdit.getText().toString().equals(newPasswordEdit.getText().toString())){
            Toast.makeText(this, "两次输入的密码不能相同", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
