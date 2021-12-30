package com.example.javasb.Fragment;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.widget.AdapterView;

import com.example.javasb.R;
import com.example.javasb.databinding.FragmentFirstBinding;

import java.io.IOException;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
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


        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            databaseHelper = new com.example.javasb.DataBaseHelper(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // создаем базу данных
        try {
            databaseHelper.updateDataBase();
        } catch (IOException ioe) {
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


//        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.getWritableDatabase();
        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select * from " + com.example.javasb.DataBaseHelper.TABLE, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{com.example.javasb.DataBaseHelper.COLUMN_THEME};
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

                    return db.rawQuery("select * from " + com.example.javasb.DataBaseHelper.TABLE, null);
                } else {
                    return db.rawQuery("select * from " + com.example.javasb.DataBaseHelper.TABLE + " where " +
                            com.example.javasb.DataBaseHelper.COLUMN_THEME + " like ?", new String[]{"%" + constraint.toString() + "%"});
                }
            }
        });
        userList.setAdapter(userAdapter);

//        userList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(MainActivity.this, CorrectDel.class);
//                intent.putExtra("id", id);
//                startActivity(intent);
//                return true;
//            }
//        });
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long l) {
                if (i == 0) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_SecondFragment);
                }
                if (i == 1) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_ThirdFragment);
                }
                if (i == 2) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_FourFragment);
                }
                if (i == 3) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_FiveFragment);
                }
                if (i == 4) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_SixFragment);
                }
                if (i == 5) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_SevenFragment);
                }
                if (i == 6) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_EightFragment);
                }
                if (i == 7) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_NineFragment);
                }
                if (i == 8) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_TenFragment);
                }
                if (i == 9) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_ElevenFragment);
                }
                if (i == 10) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_TwelveFragment);
                }
                if (i == 11) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_ThirteenFragment);
                }
                if (i == 12) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_FourteenFragment);
                }
                if (i == 13) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_FifteenFragment);
                }
                if (i == 14) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_SixteenFragment);
                }
                if (i == 15) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_SeventeenFragment);
                }
                if (i == 16) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_EighteenFragment);
                }
                if (i == 17) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_NineteenFragment);
                }
                if (i == 18) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_TwelvesFragment);
                }
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