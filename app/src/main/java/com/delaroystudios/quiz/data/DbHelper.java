package com.delaroystudios.quiz.data;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.delaroystudios.quiz.Question;

import static com.delaroystudios.quiz.data.QuizContract.MovieEntry.KEY_ANSWER;
import static com.delaroystudios.quiz.data.QuizContract.MovieEntry.KEY_ID;
import static com.delaroystudios.quiz.data.QuizContract.MovieEntry.KEY_OPTA;
import static com.delaroystudios.quiz.data.QuizContract.MovieEntry.KEY_OPTB;
import static com.delaroystudios.quiz.data.QuizContract.MovieEntry.KEY_OPTC;
import static com.delaroystudios.quiz.data.QuizContract.MovieEntry.KEY_QUES;
import static com.delaroystudios.quiz.data.QuizContract.MovieEntry.TABLE_QUEST;

public class DbHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 4;
	// Database Name
	private static final String DATABASE_NAME = "triviaQuiz5";
	// tasks table name

	private SQLiteDatabase dbase;
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		dbase=db;
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		db.execSQL(sql);		
		addQuestions();
		//db.close();
	}
	private void addQuestions()
	{
		Question q1=new Question("Если разрешения отсутствуют, то приложение получит эту ошибку во время выполнения","Parser", "SQLiteOpenHelper ", "Security Exception", "Security Exception");
		this.addQuestion(q1);
		Question q2=new Question("База данных с открытым исходным кодом", "SQLite", "BackupHelper", "NetworkInfo", "SQLite");
		this.addQuestion(q2);
		Question q3=new Question("Обмен данными в Android осуществляется через?","Wi-Fi radio", "Service Content Provider","Ducking", "Service Content Provider" );
		this.addQuestion(q3);
		Question q4=new Question("Основной класс, с помощью которого ваше приложение может получить доступ к службам определения местоположения в Android ", "LocationManager", "AttributeSet", "SQLiteOpenHelper","LocationManager");
		this.addQuestion(q4);
		Question q5=new Question("Android основан на?","NetworkInfo","GooglePlay","Linux","Linux");
		this.addQuestion(q5);
		Question q6 = new Question("Какая самая большая пустыня в мире?", "Тар", "Сахара", "Мохав", "Сахара");
		this.addQuestion(q6);
		Question q7 = new Question("Какая самая большая страна, выращивающая кофе в мире?", "Бразилия", "Индия", "Мьянма", "Бразилия");
		this.addQuestion(q7);
		Question q8 = new Question("Какая самая длинная река в мире?", "Ганга", "Амазонка", "Нил", "Нил");
		this.addQuestion(q8);
		Question q9 = new Question("Какая страна известна как страна меди?", "Сомали", "Камерун", "Замбия", "Замбия");
		this.addQuestion(q9);
		Question q10 = new Question("Что является самым старым известным городом в мире?", "Рим", "Дамаск", "Иерусалим", "Дамаск");
		this.addQuestion(q10);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
		// Create tables again
		onCreate(db);
	}
	// Adding new question
	public void addQuestion(Question quest) {
		//SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_QUES, quest.getQuestion());
		values.put(KEY_ANSWER, quest.getAnswer());
		values.put(KEY_OPTA, quest.getOpta());
		values.put(KEY_OPTB, quest.getOptb());
		values.put(KEY_OPTC, quest.getOptc());
		// Inserting Row
		dbase.insert(TABLE_QUEST, null, values);		
	}
	public List<Question> getAllQuestions() {
		List<Question> quesList = new ArrayList<Question>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
		dbase=this.getReadableDatabase();
		Cursor cursor = dbase.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Question quest = new Question();
				quest.setQuestion(cursor.getString(1));
				quest.setAnswer(cursor.getString(2));
				quest.setOpta(cursor.getString(3));
				quest.setOptb(cursor.getString(4));
				quest.setOptc(cursor.getString(5));
				quesList.add(quest);
			} while (cursor.moveToNext());
		}
		// return quest list
		return quesList;
	}




	public int rowcount()
	{
		int row=0;
		String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		row=cursor.getCount();
		return row;
	}
}



