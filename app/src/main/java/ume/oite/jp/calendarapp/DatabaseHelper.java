package ume.oite.jp.calendarapp;

/**
 * Created by Ume on 2015/07/11.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/************************************************************
 * DatabaseHelper�N���X<br>
 * <br>
 * �f�[�^�x�[�X�ɃA�N�Z�X���邽�߂ɕK�����w���p�[�N���X�B<br>
 * SQLiteOpenHelper�N���X���p�����Ă���B<br>
 * Database.db�Ƃ����f�[�^�x�[�X��Sample�Ƃ����e�[�u�����쐬���Ă���B<br>
 * @author FuyukiUmeta
 ************************************************************/
public class DatabaseHelper extends SQLiteOpenHelper {

    //�f�[�^�x�[�X�̖��O
    public static final String DATABASE_NAME = "Database.db";
    //�f�[�^�x�[�X�̃o�[�W����
    public static final int DATABASE_VERSION = 1;

    /************************************************************
     * �R���X�g���N�^<br>
     * �i�I�[�o�[���C�h�j<br>
     ************************************************************/
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /************************************************************
     * onCreate���\�b�h<br>
     * �A�v���̃f�[�^�x�[�X�Ƀe�[�u�����쐬����B<br>
     ************************************************************/
    public void onCreate(SQLiteDatabase db) {

        //�e�[�u���쐬SQL
        //Sample�e�[�u���Ɂ@ID�@Date�@Schedule�@��3�̗v�f��p�ӂ���B
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
     * onUpgrade���\�b�h<br>
     * �f�[�^�x�[�X���A�b�v�O���[�h�������̏���<br>
     ************************************************************/
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //���ݒ�
    }
}
