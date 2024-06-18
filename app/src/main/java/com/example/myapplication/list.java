package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.myapplication.database.DataStoreManager;
import com.example.myapplication.fragments.Frag1;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class list extends AppCompatActivity {

    private DataStoreManager dataStoreManager;
    private Gson gson = new Gson();

    private String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initView();
    }

    private void initView() {
        dataStoreManager = new DataStoreManager(this);

        int index = getIntent().getIntExtra("KEY_BOARD", 0);
        if (index == 0) {
            key = "KEY_BOARD_ZERO";
        } else if (index == 1) {
            key = "KEY_BOARD_ONE";
        } else if (index == 2) {
            key = "KEY_BOARD_TWO";
        } else if (index == 3) {
            key = "KEY_BOARD_THREE";
        } else if (index == 4) {
            key = "KEY_BOARD_FOUR";
        } else if (index == 5) {
            key = "KEY_BOARD_FIVE";
        } else if (index == 6) {
            key = "KEY_BOARD_SIX";
        } else {
            key = "KEY_BOARD_SEVEN";
        }
        setBoardList();

        //새 글 작성하기 버튼 클릭 이벤트 처리
        Button write = findViewById(R.id.reg_button);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(list.this, register.class);
                intent.putExtra("KEY_BOARD", list.this.key);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        setBoardList();
    }

    private void setBoardList() {
        String plantTextJson = dataStoreManager.getInformation(key);

        List<SingerItem> items = new ArrayList<>();

        if(!plantTextJson.isEmpty()) {
            Type listType = new TypeToken<List<SingerItem>>() {}.getType();
            List<SingerItem> savedItems = gson.fromJson(plantTextJson, listType);

            if(!savedItems.isEmpty()) {
                items.addAll(savedItems);
            }
        }

        ListView listView = findViewById(R.id.listView);
        Frag1.SingerAdapter adapter = new Frag1.SingerAdapter();
        for(SingerItem item : items) {
            adapter.addItem(item);
        }
        listView.setAdapter(adapter);
    }
}