package com.example.javasb.Fragment;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.TextView;

import com.example.javasb.DataBaseHelper;
import com.example.javasb.R;
import com.example.javasb.databinding.FragmentThirteenBinding;

public class ThirteenFragment extends Fragment {

    private FragmentThirteenBinding binding;
    private EditText searchTXT;
    private TextView txtName;

    ListView userList;
    com.example.javasb.DataBaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentThirteenBinding.inflate(inflater, container, false);
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
        } catch (SQLException sqle) {
            throw sqle;
        }

        txtName = (TextView) view.findViewById(com.example.javasb.R.id.txtName);
        searchTXT = (EditText) view.findViewById(com.example.javasb.R.id.searchTXT);
        userList = (ListView) view.findViewById(com.example.javasb.R.id.userList);
    }

    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.getWritableDatabase();
        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select * from " + DataBaseHelper.TABLE13, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DataBaseHelper.COLUMN_THEME_JUNIT};
        ;
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(getActivity(), com.example.javasb.R.layout.adapter_item,
                userCursor, headers, new int[]{com.example.javasb.R.id.txtName}, 0);

        if (!searchTXT.getText().toString().isEmpty())
            userAdapter.getFilter().filter(searchTXT.getText().toString());


        searchTXT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                userAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                userAdapter.getFilter().filter(s.toString());

            }
        });

        // устанавливаем провайдер фильтрации

        userAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                if (constraint == null || constraint.length() == 0) {

                    return db.rawQuery("select * from " + com.example.javasb.DataBaseHelper.TABLE13, null);
                } else {
                    return db.rawQuery("select * from " + com.example.javasb.DataBaseHelper.TABLE13 + " where " +
                            DataBaseHelper.COLUMN_THEME_JUNIT + " like ?", new String[]{"%" + constraint.toString() + "%"});
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
                bundle.putString("ju", s);
                NavHostFragment.findNavController(ThirteenFragment.this)
                        .navigate(R.id.action_ThirteenFragment_to_JUnitFragment, bundle);
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