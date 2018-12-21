package com.example.rozkladjazdy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BazaDanych {
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAZWAPRZYSTANKU = "nazwa_przystanku";
	public static final String KEY_GODZINA = "godzina";
	public static final String KEY_LINIA = "nr_linii";
	
	public static final String DATABASE_NAME = "RozkladJazdyDB";
	public static final String DATABASE_TABLE = "rozkladTable2";
	public static final int DATABASE_VERSION = 1;
	
	private DbHelper ourHelper;
	private Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	private static class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
			KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
			KEY_NAZWAPRZYSTANKU + " TEXT NOT NULL, " +
			KEY_LINIA + " TEXT NOT NULL, " +
			KEY_GODZINA + " TEXT NOT NULL)"				
					);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXIST " + DATABASE_TABLE);
			onCreate(db);
			
		}
		
	}
	public BazaDanych(Context c){
		ourContext = c;
	}
	public BazaDanych open() throws SQLException{
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	public void close(){
		ourHelper.close();
	}
	public long createWejscie(String nazwa, String linia, String godzina) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAZWAPRZYSTANKU, nazwa);
		cv.put(KEY_LINIA, linia);
		cv.put(KEY_GODZINA, godzina);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
		
	}
	public String getDane() {
		// TODO Auto-generated method stub
		String[] kolumny = new String[]{KEY_ROWID, KEY_NAZWAPRZYSTANKU, KEY_LINIA, KEY_GODZINA};
		Cursor c = ourDatabase.query(DATABASE_TABLE, kolumny, null, null, null, null, KEY_NAZWAPRZYSTANKU + " ASC");
		String wynik = "";
		
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iNazwa = c.getColumnIndex(KEY_NAZWAPRZYSTANKU);
		int iLinia = c.getColumnIndex(KEY_LINIA);
		int iGodzina = c.getColumnIndex(KEY_GODZINA);
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			wynik = wynik + c.getString(iRow) + ". " + c.getString(iNazwa) + ": " + c.getString(iLinia) + "\n" + c.getString(iGodzina) + "\n\n";
			
		}
		return wynik;
	}
	public void edytujWpis(String sId, String eNazwa, String eGodzina,
			String eLinia) {
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(KEY_NAZWAPRZYSTANKU, eNazwa);
		cvUpdate.put(KEY_GODZINA, eGodzina);
		cvUpdate.put(KEY_LINIA, eLinia);
		ourDatabase.update(DATABASE_TABLE, cvUpdate, KEY_ROWID + "=" + sId, null);
		
		
	}
	public String getNazwa(long l) {
		// TODO Auto-generated method stub
		String[] columns = new String[]{KEY_ROWID, KEY_NAZWAPRZYSTANKU, KEY_LINIA, KEY_GODZINA};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		if (c != null){
			c.moveToFirst();
			String nazwa = c.getString(1);
			return nazwa;
		}
		return null;
	}
	public String getNumer(long l) {
		// TODO Auto-generated method stub
		String[] columns = new String[]{KEY_ROWID, KEY_NAZWAPRZYSTANKU, KEY_LINIA, KEY_GODZINA};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		if (c != null){
			c.moveToFirst();
			String numer = c.getString(2);
			return numer;
		}
		return null;
	}
	public String getGodziny(long l) {
		// TODO Auto-generated method stub
		String[] columns = new String[]{KEY_ROWID, KEY_NAZWAPRZYSTANKU, KEY_LINIA, KEY_GODZINA};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
		if (c != null){
			c.moveToFirst();
			String godziny = c.getString(3);
			return godziny;
		}
		return null;
	}
	public void usunPrzystanek(long lId) {
		// TODO Auto-generated method stub
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + lId, null);
		
	}
	
	
	

}
