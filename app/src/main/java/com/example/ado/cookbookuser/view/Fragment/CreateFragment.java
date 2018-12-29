package com.example.ado.cookbookuser.view.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ado.cookbookuser.R;
import com.example.ado.cookbookuser.view.BaseActivity;

import org.w3c.dom.Text;

public class CreateFragment extends Fragment {

    private TextView myUserName;
    private TextView myBirthday;
    private TextView myGender;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_pager,container,false);

        myUserName = view.findViewById(R.id.my_user_name);
        myBirthday = view.findViewById(R.id.my_birthday);
        myGender = view.findViewById(R.id.my_gender);

        initMyInformation();

        return view;
    }

    private void initMyInformation(){
        myUserName.setText(BaseActivity.userForNow.getName().toString());
        myBirthday.setText(BaseActivity.userForNow.getBirthday().toString());
        myGender.setText(BaseActivity.userForNow.getGender().toString());
    }
}
