package com.example.ado.cookbookuser.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import com.example.ado.cookbookuser.R;

public class ThemeActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener,View.OnClickListener{

    private Toolbar toolbarTheme;
    private SwitchCompat switchDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        toolbarTheme = findViewById(R.id.toolbar_theme);
        switchDark = findViewById(R.id.switch_dark_theme);

        switchDark.setOnCheckedChangeListener(this);

        setSupportActionBar(toolbarTheme);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.switch_dark_theme:{
                if(isChecked){
                    switchToDark();
//                    ThemeActivity.this.recreate();
                    startActivity(new Intent(this,ThemeActivity.class));
                    finish();
                }else{
                    switchToDefault();
                }
                break;
            }
        }
    }

    private void switchToDefault(){
        getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    private void switchToDark(){
        getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

    }
}
