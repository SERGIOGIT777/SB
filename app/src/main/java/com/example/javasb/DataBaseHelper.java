package com.example.javasb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.javasb.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH; // полный путь к базе данных
    private static String DB_NAME = "quiz.db";
    private static final int SCHEMA = 1; // версия базы данных
    public static final String TABLE = "quastion"; // название таблицы в бд
    public static final String TABLE2 = "quastionOOP";
    public static final String TABLE3 = "quastionJavaCore";
    public static final String TABLE4 = "quastionCF";
    public static final String TABLE5 = "quastionJava8";
    public static final String TABLE6 = "quastionInOut";
    public static final String TABLE7 = "quastionMreading";
    public static final String TABLE8 = "quastionSeriliz";
    public static final String TABLE9 = "quastionJDBC";
    public static final String TABLE10 = "quastionJSJ";
    public static final String TABLE11 = "quastionBD";
    public static final String TABLE12 = "quastionSQL";
    public static final String TABLE13 = "quastionJunit";
    public static final String TABLE14 = "quastionLog4J";
    public static final String TABLE15 = "quastionUML";
    public static final String TABLE16 = "quastionXML";
    public static final String TABLE17 = "quastionDP";
    public static final String TABLE18 = "quastionHTML";
    public static final String TABLE19 = "quastionCSS";
    public static final String TABLE20 = "quastionWEB";
    // названия столбцов
    static final String COLUMN_ID = "_id";
    public static final String COLUMN_THEME = "theme";
    public static final String COLUMN_THEME_OOP = "themeOOP";
    public static final String COLUMN_THEME_JC = "JavaCore";
    public static final String COLUMN_THEME_CF = "theme_CF";
    public static final String COLUMN_THEME_J8 = "java8theme";
    public static final String COLUMN_THEME_IN_OUT = "inout_theme";
    public static final String COLUMN_THEME_JMR = "multithreading";
    public static final String COLUMN_THEME_JSZ = "serilization";
    public static final String COLUMN_THEME_JDBC = "jdbc";
    public static final String COLUMN_THEME_JSJ = "jsj";
    public static final String COLUMN_THEME_BD = "baseDB";
    public static final String COLUMN_THEME_SQL = "sqlField";
    public static final String COLUMN_THEME_JUNIT = "junitField";
    public static final String COLUMN_THEME_LOG4J = "log4jfields";
    public static final String COLUMN_THEME_UML = "umlFields";
    public static final String COLUMN_THEME_XML = "xmlFields";
    public static final String COLUMN_THEME_DP = "dpFields";
    public static final String COLUMN_THEME_HTML = "htmlFields";
    public static final String COLUMN_THEME_CSS = "CSSFields";
    public static final String COLUMN_THEME_WEB = "webFields";

    public SQLiteDatabase database;
    private final Context myContext;
    private boolean mNeedUpdate = false;

    public DataBaseHelper(Context context) throws IOException {
        super(context, DB_NAME, null, SCHEMA);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.myContext = context;

        copyDataBase();

        this.getReadableDatabase();
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    private void copyDataBase() throws IOException {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            copyDBFile();
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = myContext.getResources().openRawResource(R.raw.quiz);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion>newVersion){
            mNeedUpdate = true;
        }
    }
}
