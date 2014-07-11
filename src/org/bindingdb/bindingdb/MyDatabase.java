package org.bindingdb.bindingdb;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Button;
import android.widget.ImageButton;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabase extends SQLiteAssetHelper {

	private static final String DATABASE_NAME = "bindingDBtim.db";
	private static final String sqlTables = "bindingDBtim";
    private static final int DATABASE_VERSION = 1;
	
    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
    public Cursor getProtein() {

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

//		String [] sqlSelect = {"0 _id", "FullName"}; 


		qb.setTables(sqlTables);
		Cursor c = qb.query(db, null, null, null,
				null, null, null);


		return c;

	}
    
	public ArrayList<Entry> getAllConpoundsByProtainAndId(String protain, String id) {
		 
		 // Select All Query
	    String selectQuery = "SELECT  * " +
	    					 "FROM " + sqlTables + " " +
	    					 "WHERE "+ "UniProt_SwissProt_Recommended_Name_of_Target_Chain" + " = \"" + protain +"\"" +
	    					 " AND "  + "UniProt_SwissProt_Primary_ID_of_Target_Chain" + "= \"" + id +"\"";
	 
	    // get a writable instance of our database 
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // return placeIt list
	    return getEntriesList(cursor);
	}
	 
	public ArrayList<Entry> getAllId(String id) {
	    // Select All Query
	    String selectQuery = "SELECT  * " +
	    					 "FROM " + sqlTables + " " +
	    					 "WHERE "+ "UniProt_SwissProt_Primary_ID_of_Target_Chain" + " = \"" + id +"\"";
	 
	    // get a writable instance of our database 
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // return placeIt list
	    return getEntriesList(cursor);
	}
	
	public ArrayList<Entry> getAllProtain(String protain) {
	    // Select All Query
	    String selectQuery = "SELECT  * " +
	    					 "FROM " + sqlTables + " " +
	    					 "WHERE "+ "UniProt_SwissProt_Recommended_Name_of_Target_Chain" + " LIKE \"" + "%"+ protain + "%" +"\"";
	 
	    // get a writable instance of our database 
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // return placeIt list
	    return getEntriesList(cursor);
	}
	
	private ArrayList<Entry> getEntriesList (Cursor cursor) {
		ArrayList<Entry> list = new ArrayList<Entry>();
		if (cursor.moveToFirst()) {
	        do {
	        	Entry entry = new Entry();
	        	
	        	entry.Ligand_SMILES = cursor.getString(0);
	        	entry.BindingDB_MonomerID = cursor.getString(1);
	        	entry.BindingDB_Ligand_Name = cursor.getString(2);
	        	entry.Ki = cursor.getString(3);
	        	entry.IC50 = cursor.getString(4);
	        	entry.Link = cursor.getString(5);
	        	entry.UniProt_Recommended_Name_of_Target_Chain = cursor.getString(6);
	        	entry.UniProt_Primary_ID_of_Target_Chain = cursor.getString(7);
	        	

	            // Adding placeIt to list
	        	list.add(entry);
	        } while (cursor.moveToNext());
	    }
		return list;
		
	}
    
}

