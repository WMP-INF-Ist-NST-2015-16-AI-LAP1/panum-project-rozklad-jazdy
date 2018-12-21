package com.example.rozkladjazdy;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RozkladJazdy extends Activity implements OnClickListener, android.view.View.OnClickListener{
	
	Button dodajPrzystanek, pokazListe, edytujButton, usunButton, pokazInfoButton, autorzyButton, aplikacjaButton;
	EditText sqlNazwa, sqlGodziny, sqlLinia, sqlWpiszID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rozklad_jazdy);
		
		dodajPrzystanek = (Button) findViewById(R.id.dodajPrzystanekButton);
		pokazListe = (Button) findViewById(R.id.ListaPrzystankowButton);
		edytujButton = (Button) findViewById(R.id.edytujButton);
		usunButton = (Button) findViewById(R.id.usunButton);
		pokazInfoButton = (Button) findViewById(R.id.infoButton);
		autorzyButton = (Button) findViewById(R.id.oAutorachButton);
		aplikacjaButton = (Button) findViewById(R.id.oAplikacjiButton);
		edytujButton.setOnClickListener(this);
		usunButton.setOnClickListener(this);
		dodajPrzystanek.setOnClickListener(this);
		pokazListe.setOnClickListener(this);
		pokazInfoButton.setOnClickListener(this);
		autorzyButton.setOnClickListener(this);
		aplikacjaButton.setOnClickListener(this);
		
		sqlNazwa = (EditText) findViewById(R.id.nazwaPrzystanku);
		sqlGodziny = (EditText) findViewById(R.id.godzinyOdjazdow);
		sqlLinia = (EditText) findViewById(R.id.numerLinii);
		sqlWpiszID = (EditText) findViewById(R.id.wpiszNumer);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rozklad_jazdy, menu);
		return true;
	}

	
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		switch(arg0.getId()){
		case R.id.dodajPrzystanekButton:
			
			boolean czyDziala = true;
			try{
			String nazwa = sqlNazwa.getText().toString();
			String godzina = sqlGodziny.getText().toString();
			String linia = sqlLinia.getText().toString();
			
			BazaDanych wejscie = new BazaDanych(RozkladJazdy.this);
			wejscie.open();
			wejscie.createWejscie(nazwa, linia, godzina);
			wejscie.close();
			}catch(Exception e){
				czyDziala = false;
				String blad = e.toString();
				Dialog d = new Dialog(this);
				d.setTitle("Wynik");
				TextView tv = new TextView(this);
				tv.setText(blad);
				d.setContentView(tv);
				d.show();
			}finally{
				if(czyDziala){
					Dialog d = new Dialog(this);
					d.setTitle("Rozklad jazdy");
					TextView tv = new TextView(this);
					tv.setText("Przystanek " + sqlNazwa.getText().toString() + " zostal dodany");
					d.setContentView(tv);
					d.show();
				}
			}
			break;
		case R.id.ListaPrzystankowButton:
			Intent i = new Intent("com.example.rozkladjazdy.SQLView");
			startActivity(i);
			break;
		
		case R.id.infoButton:
			
			String s = sqlWpiszID.getText().toString();
			long l;
			if(s != null && s.length() > 0)
				l = Long.parseLong(s);
			else
				break;
			BazaDanych db = new BazaDanych(this);
			db.open();
			String pobranaNazwa = db.getNazwa(l);
			String pobranyNr = db.getNumer(l);
			String pobraneGodziny = db.getGodziny(l);			
			db.close();
			
			sqlNazwa.setText(pobranaNazwa);
			sqlGodziny.setText(pobraneGodziny);
			sqlLinia.setText(pobranyNr);
			
			break;
		
		case R.id.edytujButton:
			boolean dziala = true;
			try{
			String eNazwa = sqlNazwa.getText().toString();
			String eGodzina = sqlGodziny.getText().toString();
			String eLinia = sqlLinia.getText().toString();
			
			String sId = sqlWpiszID.getText().toString();
			BazaDanych ex = new BazaDanych(this);
			ex.open();			
			ex.edytujWpis(sId, eNazwa, eGodzina, eLinia);
			ex.close();
			}catch(Exception e){
				dziala = false;
				String blad = e.toString();
				Dialog d = new Dialog(this);
				d.setTitle("Edycja linii");
				TextView tv = new TextView(this);
				tv.setText(blad);
				d.setContentView(tv);
				d.show();
			}finally{
				if(dziala){
					Dialog d = new Dialog(this);
					d.setTitle("Edycja linii");
					TextView tv = new TextView(this);
					tv.setText("Przystanek " + sqlNazwa.getText().toString() + " Zaktualizowano");
					d.setContentView(tv);
					d.show();
				}
			}
			break;
			
		case R.id.usunButton:
			boolean usunelo = true;
			try{
			String sId = sqlWpiszID.getText().toString();
			long lId = Long.parseLong(sId);
			
			BazaDanych ex = new BazaDanych(this);
			ex.open();		
			ex.usunPrzystanek(lId);
			ex.close();
			}catch(Exception e){
				usunelo = false;
				String blad = e.toString();
				Dialog d = new Dialog(this);
				d.setTitle("Edytowanie");
				TextView tv = new TextView(this);
				tv.setText(blad);
				d.setContentView(tv);
				d.show();
			}finally{
				if(usunelo){
					Dialog d = new Dialog(this);
					d.setTitle("Usuwanie");
					TextView tv = new TextView(this);
					tv.setText("Przystanek usunięty");
					d.setContentView(tv);
					d.show();
				}
			}
			
			break;
		case R.id.oAutorachButton:
			Dialog d = new Dialog(this);
			d.setTitle("Twórcy aplikacji");
			TextView tv = new TextView(this);
			tv.setText("Bartmański Adrian\n\nCzarnota Dariusz\n\nMacko Grzegorz\n\nPerczyński Sebastiann\n\n\nProgramowanie aplikacji na urządzenia mobilne");
			d.setContentView(tv);
			d.show();
			break;
		case R.id.oAplikacjiButton:
			Dialog d2 = new Dialog(this);
			d2.setTitle("Informacje o aplikacji");
			TextView tv2 = new TextView(this);
			tv2.setText("Wersja 1\nOstatnia aktualizacja: 19-12-2018\n\nProjekt na zaliczenie");
			d2.setContentView(tv2);
			d2.show();
			break;
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}

}
