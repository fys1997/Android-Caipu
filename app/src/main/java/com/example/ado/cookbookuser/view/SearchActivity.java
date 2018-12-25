package com.example.ado.cookbookuser.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ado.cookbookuser.R;

public class SearchActivity extends BaseActivity  {
    private TextView textView;
    private EditText editText;
    private TextView search;
    private Toolbar toolbarSearch;
    private String menu=new String();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_ui);
        editText=findViewById(R.id.search_edit);
        search=findViewById(R.id.search);
        toolbarSearch = findViewById(R.id.toolbar_search);

        setToolbar(toolbarSearch);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu=editText.getText().toString();
                if(!menu.isEmpty()){
                    Intent intent=new Intent(SearchActivity.this,SearchResultActivity.class);
                    intent.putExtra("name",menu);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
        initTextView();
    }

    public void initTextView(){
        textView=findViewById(R.id.search1);
        textView.setOnClickListener(listener);
        textView=findViewById(R.id.search2);
        textView.setOnClickListener(listener);
        textView=findViewById(R.id.search3);
        textView.setOnClickListener(listener);
        textView=findViewById(R.id.search4);
        textView.setOnClickListener(listener);
        textView=findViewById(R.id.search5);
        textView.setOnClickListener(listener);
        textView=findViewById(R.id.search6);
        textView.setOnClickListener(listener);
    }
     View.OnClickListener listener=new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             TextView view=(TextView)v;
             Intent intent=new Intent(SearchActivity.this, SearchResultActivity.class);
             intent.putExtra("name",view.getText());
             intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
             startActivity(intent);
         }
     };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                break;
            }
        }
        return true;
    }
}
