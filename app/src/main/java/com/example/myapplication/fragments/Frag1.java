package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.SingerItem;
import com.example.myapplication.SingerItemView;
import com.example.myapplication.list;

import java.util.ArrayList;

public class Frag1 extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag1, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {

        //게시판 목록 보여주기
        ListView listView = view.findViewById(R.id.listView);
        SingerAdapter adapter = new SingerAdapter();
        adapter.addItem(new SingerItem("자유 게시판", "자유로운 얘기", R.drawable.icon01));
        adapter.addItem(new SingerItem("창작 게시판", "음악 창작 공유", R.drawable.icon2));
        adapter.addItem(new SingerItem("불만 게시판", "여러가지 불만들", R.drawable.icon3));
        adapter.addItem(new SingerItem("발라드 게시판", "발라드 좋아요", R.drawable.icon4));
        adapter.addItem(new SingerItem("힙합 게시판", "힙합 좋아요", R.drawable.icon5));
        adapter.addItem(new SingerItem("팝송 게시판", "팝송 좋아요", R.drawable.icon6));
        adapter.addItem(new SingerItem("R&B 게시판", "R&B 좋아요", R.drawable.icon7));
        adapter.addItem(new SingerItem("게임 게시판", "같이 게임해요", R.drawable.icon8));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(requireContext(), list.class);
                intent.putExtra("KEY_BOARD", i);
                requireActivity().startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
    }

    public static class SingerAdapter extends BaseAdapter {
        //데이터가 들어가있지 않고, 어떻게 담을지만 정의해뒀다.
        ArrayList<SingerItem> items = new ArrayList<SingerItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(SingerItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // 어댑터가 데이터를 관리하고 뷰도 만듬
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SingerItemView singerItemView = null;
            // 코드를 재사용할 수 있도록
            if (convertView == null) {
                singerItemView = new SingerItemView(parent.getContext());
            } else {
                singerItemView = (SingerItemView) convertView;
            }
            SingerItem item = items.get(position);
            singerItemView.setName(item.getName());
            singerItemView.setMobile(item.getMobile());
            singerItemView.setImage(item.getResId());
            return singerItemView;
        }
    }
}
