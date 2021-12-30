package com.example.javasb.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;

import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import androidx.cursoradapter.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.javasb.FragmentText.OopFragment;
import com.example.javasb.R;
import com.example.javasb.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private EditText searchTXT;
    private TextView txtName;

    ListView userList;
    com.example.javasb.DataBaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            databaseHelper = new com.example.javasb.DataBaseHelper(getActivity());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        // создаем базу данных
        try {
            databaseHelper.updateDataBase();
        } catch (java.io.IOException ioe) {
            throw new Error("Не возможно инициализировать базу данных");
        }

        try {
            db = databaseHelper.getWritableDatabase();
        } catch (android.database.SQLException sqle) {
            throw sqle;
        }

        txtName = (android.widget.TextView) view.findViewById(com.example.javasb.R.id.txtName);
        searchTXT = (android.widget.EditText) view.findViewById(com.example.javasb.R.id.searchTXT);
        userList = (android.widget.ListView) view.findViewById(com.example.javasb.R.id.userList);

//        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(SecondFragment.this)
//                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.getWritableDatabase();
        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select * from " + com.example.javasb.DataBaseHelper.TABLE2, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{com.example.javasb.DataBaseHelper.COLUMN_THEME_OOP};
        ;
        // создаем адаптер, передаем в него курсор
        userAdapter = new androidx.cursoradapter.widget.SimpleCursorAdapter(getActivity(), com.example.javasb.R.layout.adapter_item,
                userCursor, headers, new int[]{com.example.javasb.R.id.txtName}, 0);

        if (!searchTXT.getText().toString().isEmpty())
            userAdapter.getFilter().filter(searchTXT.getText().toString());


        searchTXT.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                userAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
                userAdapter.getFilter().filter(s.toString());

            }
        });

        // устанавливаем провайдер фильтрации

        userAdapter.setFilterQueryProvider(new android.widget.FilterQueryProvider() {
            @Override
            public android.database.Cursor runQuery(CharSequence constraint) {
                if (constraint == null || constraint.length() == 0) {

                    return db.rawQuery("select * from " + com.example.javasb.DataBaseHelper.TABLE2, null);
                } else {
                    return db.rawQuery("select * from " + com.example.javasb.DataBaseHelper.TABLE2 + " where " +
                            com.example.javasb.DataBaseHelper.COLUMN_THEME_OOP + " like ?", new String[]{"%" + constraint.toString() + "%"});
                }
            }
        });
        userList.setAdapter(userAdapter);

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                TextView textView = (TextView) view.findViewById(R.id.txtName);
                String s = textView.getText().toString();
                bundle.putString("oop", s);
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_OopFragment, bundle);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        db.close();
        userCursor.close();
    }

}