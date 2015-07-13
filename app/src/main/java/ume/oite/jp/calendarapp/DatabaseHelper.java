package ume.oite.jp.calendarapp;

/**
 * Created by Ume on 2015/07/11.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/************************************************************
 * DatabaseHelperクラス<br>
 * <br>
 * データベースにアクセスするために必ず作るヘルパークラス。<br>
 * SQLiteOpenHelperクラスを継承している。<br>
 * Database.dbというデータベースにSampleというテーブルを作成している。<br>
 * @author FuyukiUmeta
 ************************************************************/
public class DatabaseHelper extends SQLiteOpenHelper {

    //データベースの名前
    public static final String DATABASE_NAME = "Database.db";
    //データベースのバージョン
    public static final int DATABASE_VERSION = 1;

    /************************************************************
     * コンストラクタ<br>
     * （オーバーライド）<br>
     ************************************************************/
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /************************************************************
     * onCreateメソッド<br>
     * アプリのデータベースにテーブルを作成する。<br>
     ************************************************************/
    public void onCreate(SQLiteDatabase db) {

        //テーブル作成SQL
        //Sampleテーブルに　ID　Date　Schedule　の3つの要素を用意する。
        String sql = "CREATE TABLE Sample ("
                + " _id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " Date TEXT,"
                + " BeginTime TEXT,"
                + " EndTime TEXT,"
                + " Schedule TEXT"
                + ");";
        db.execSQL(sql);
    }

    /************************************************************
     * onUpgradeメソッド<br>
     * データベースがアップグレードした時の処理<br>
     ************************************************************/
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //未設定
    }
}
