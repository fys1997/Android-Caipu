package com.example.ado.cookbookuser.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.ado.cookbookuser.R;

public class CreateCookbookActivity extends BaseActivity {

    private Toolbar toolbarCreateCookbook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cookbook);

        setToolbar(toolbarCreateCookbook);
    }
}
