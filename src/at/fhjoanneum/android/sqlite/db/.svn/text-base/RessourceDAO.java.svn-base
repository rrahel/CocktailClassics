package at.fhjoanneum.android.sqlite.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import at.fhjoanneum.android.sqlite.data.Ressource;

/**
 * Class to interact with the database.
 * Here you find all the queries for the ressource table of the data base
 * @author Manuela Mayer, Kahmann Sebastian, Egger Andreas, Cemi Rrahel
 *
 */
public class RessourceDAO extends RessourceDBDAO {

	private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
			+ " =?";

	/**
	 * Constructor which calls the super constructor
	 * @param context Current state of the application
	 */
	public RessourceDAO(Context context) {
		super(context);
	}

	/**
	 * Save a new resource in the resource table
	 * @param ressource Resource to save
	 * @return If the new resource was inserted
	 */
	public long save(Ressource ressource) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NAME_COLUMN, ressource.getName());
		values.put(DataBaseHelper.RESSOURCE_INBAR, ressource.getBar());
		values.put(DataBaseHelper.RESSOURCE_INSHOPPINGLIST, ressource.getInShoppingList());

		return database.insert(DataBaseHelper.RESSOURCE_TABLE, null, values);
	}

	/**
	 * To update a resource
	 * @param ressource Resource to update
	 * @return If the resource was updated
	 */
	public long update(Ressource ressource) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NAME_COLUMN, ressource.getName());
		values.put(DataBaseHelper.RESSOURCE_INBAR, ressource.getBar());
		values.put(DataBaseHelper.RESSOURCE_INSHOPPINGLIST, ressource.getInShoppingList());

		long result = database.update(DataBaseHelper.RESSOURCE_TABLE, values,
				WHERE_ID_EQUALS,
				new String[] { String.valueOf(ressource.getId()) });
		Log.d("Update Result:", "=" + result);
		return result;
	}

	/**
	 * To delete a resource from the table
	 * @param ressource Resource to delete
	 * @return If the resource was deleted
	 */
	public int deleteRessource(Ressource ressource) {
		return database.delete(DataBaseHelper.RESSOURCE_TABLE, WHERE_ID_EQUALS,
				new String[] { ressource.getId() + "" });
	}

	/**
	 * To get a list of all resources which have the filter input in the name of the resource 
	 * @param filterInput Name of the resource you want to find
	 * @return Array list of the found resources
	 */
	public ArrayList<Ressource> getRessources(String filterInput) {
		ArrayList<Ressource> ressources = new ArrayList<Ressource>();
		String query = "SELECT distinct " + DataBaseHelper.ID_COLUMN + ","
				+ DataBaseHelper.NAME_COLUMN + ","
				+ DataBaseHelper.RESSOURCE_INBAR + ","
				+ DataBaseHelper.RESSOURCE_INSHOPPINGLIST + " FROM "
				+ DataBaseHelper.RESSOURCE_TABLE + " WHERE ("
				+ DataBaseHelper.NAME_COLUMN + " LIKE '%" + filterInput
				+ "%') order by " + DataBaseHelper.NAME_COLUMN + " asc ;";

		Log.d("query", query);
		Cursor cursor = database.rawQuery(query, null);
		while (cursor.moveToNext()) {
			Ressource ressource = new Ressource();
			ressource.setId(cursor.getInt(0));
			ressource.setName(cursor.getString(1));
			ressource.setInBar(cursor.getInt(2));
			ressource.setInShoppingList(cursor.getInt(3));

			ressources.add(ressource);
		}
		cursor.close();
		return ressources;
	}

	/**
	 * To get all values of the resource if you only know the id of it
	 * @param id Id of the resource you will find
	 * @return Resource with the entered id or null if no resource was found
	 */
	public Ressource getRessourceById(int id) {
		Cursor cursor = database.query(DataBaseHelper.RESSOURCE_TABLE,
				new String[] { DataBaseHelper.ID_COLUMN,
						DataBaseHelper.NAME_COLUMN,
						DataBaseHelper.RESSOURCE_INBAR,
						DataBaseHelper.RESSOURCE_INSHOPPINGLIST},
				DataBaseHelper.ID_COLUMN + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null) {
			cursor.moveToNext();
		} else {
			Log.e("Get Ressource By Id", "Cursor = null");
		}
		

		try {
			Ressource ressource = new Ressource();
			ressource.setId(cursor.getInt(0));
			ressource.setName(cursor.getString(1));
			ressource.setInBar(cursor.getInt(2));
			ressource.setInShoppingList(cursor.getInt(3));
			cursor.close();
			return ressource;
		} catch (Exception e) {
			Log.e("Get Ressource By Id", "Wrong Ressource Name\n" + e);
			return null;
		}
		
	}
	
	/**
	 * Returns a list of all resources which have set the inbar flag
	 * @return Array list of resources which have the inbar flag set
	 */
	public ArrayList<Ressource> getInBarRessources() {
		ArrayList<Ressource> ressources = new ArrayList<Ressource>();
		String query = "SELECT distinct " + DataBaseHelper.ID_COLUMN + ","
				+ DataBaseHelper.NAME_COLUMN + ","
				+ DataBaseHelper.RESSOURCE_INBAR + ","
				+ DataBaseHelper.RESSOURCE_INSHOPPINGLIST + " FROM "
				+ DataBaseHelper.RESSOURCE_TABLE + " WHERE "
						+ "("+ DataBaseHelper.RESSOURCE_INBAR + "==1) order by " + DataBaseHelper.NAME_COLUMN + " asc ;";

		Log.d("query", query);
		Cursor cursor = database.rawQuery(query, null);
		while (cursor.moveToNext()) {
			Ressource ressource = new Ressource();
			ressource.setId(cursor.getInt(0));
			ressource.setName(cursor.getString(1));
			ressource.setInBar(cursor.getInt(2));
			ressource.setInShoppingList(cursor.getInt(3));

			ressources.add(ressource);
		}
		cursor.close();
		return ressources;
		
	}
	
	/**
	 * Returns a list of all resources which have not set the inbar flag
	 * @return Array list of resources which have not the inbar flag set
	 */
	public ArrayList<Ressource> getNotInBarRessources() {
		ArrayList<Ressource> ressources = new ArrayList<Ressource>();
		String query = "SELECT distinct " + DataBaseHelper.ID_COLUMN + ","
				+ DataBaseHelper.NAME_COLUMN + ","
				+ DataBaseHelper.RESSOURCE_INBAR + ","
				+ DataBaseHelper.RESSOURCE_INSHOPPINGLIST + " FROM "
				+ DataBaseHelper.RESSOURCE_TABLE + " WHERE "
						+ "("+ DataBaseHelper.RESSOURCE_INBAR + "==0) order by " + DataBaseHelper.NAME_COLUMN + " asc ;";

		Log.d("query", query);
		Cursor cursor = database.rawQuery(query, null);
		while (cursor.moveToNext()) {
			Ressource ressource = new Ressource();
			ressource.setId(cursor.getInt(0));
			ressource.setName(cursor.getString(1));
			ressource.setInBar(cursor.getInt(2));
			ressource.setInShoppingList(cursor.getInt(3));

			ressources.add(ressource);
		}
		cursor.close();
		return ressources;
	}
	
	/**
	 * Sets the inbar flag of the resource
	 * @param ressource Resource where you want to set the inbar flag
	 * @param value 1 if you want to set the flag or 0 if you do want to reset the flag
	 */
	public void setInBar(Ressource ressource, int value) {
		ContentValues cv = new ContentValues();
		cv.put("inbar", value);

		int affectedRows = database.update(DataBaseHelper.RESSOURCE_TABLE, cv,
				"id" + " = " + ressource.getId(), null);

		if (affectedRows == 0) {
			Log.e("CategoryDAO: Set In Bar List", "affectedRows = 0");
		} else if (affectedRows > 1) {
			Log.e("CategoryDAO: Set In Bar List", "affectedRows > 1");
		} 
	}
	
	/**
	 * Sets the inshoppinglist flag of the resource
	 * @param ressource Resource where you want to set the inshoppinglist flag
	 * @param value 1 if you want to set the flag or 0 if you do want to reset the flag
	 */
	public void setInShoppinglist(Ressource ressource, int value) {
		ContentValues cv = new ContentValues();
		cv.put("inshoppinglist", value);

		int affectedRows = database.update(DataBaseHelper.RESSOURCE_TABLE, cv,
				"id" + " = " + ressource.getId(), null);

		if (affectedRows == 0) {
			Log.e("CategoryDAO: Set In Shopping List", "affectedRows = 0");
		} else if (affectedRows > 1) {
			Log.e("CategoryDAO: Set In Shopping List", "affectedRows > 1");
		} 
	}
	
	
	/**
	 * Loads all resources in the resource table
	 */
	public void loadRessources() {

		List<Ressource> ressources = new ArrayList<Ressource>();
		// juice
		ressources.add(new Ressource("Orangensaft", 0, 0));
		ressources.add(new Ressource("Limettensaft", 0, 0));
		ressources.add(new Ressource("Zitronensaft", 0, 0));
		ressources.add(new Ressource("Ananassaft", 0, 0));
		ressources.add(new Ressource("Grapefruitsaft", 0, 0));
		// sirups
		ressources.add(new Ressource("Zuckersirup", 0, 0));
		ressources.add(new Ressource("Grenadine", 0, 0));
		ressources.add(new Ressource("Mandelsirup", 0, 0));
		ressources.add(new Ressource("Maracujasirup", 0, 0));
		// alcohol
		ressources.add(new Ressource("Whiskey", 0, 0));
		ressources.add(new Ressource("Cachaca", 0, 0));
		ressources.add(new Ressource("Gin", 0, 0));
		ressources.add(new Ressource("Wodka", 0, 0));
		ressources.add(new Ressource("Aperol", 0, 0));
		ressources.add(new Ressource("Campari", 0, 0));
		ressources.add(new Ressource("Light Rum", 0, 0));
		ressources.add(new Ressource("Jamaika Rum", 0, 0));
		ressources.add(new Ressource("Tequila", 0, 0));
		ressources.add(new Ressource("Maraschino", 0, 0));
		ressources.add(new Ressource("Dry Vermouth", 0, 0));
		ressources.add(new Ressource("Sweet Vermouth", 0, 0));
		ressources.add(new Ressource("Cointreau", 0, 0));
		ressources.add(new Ressource("Benedictine", 0, 0));
		ressources.add(new Ressource("Falernum", 0, 0));
		ressources.add(new Ressource("Curacao Dry Orange", 0, 0));
		ressources.add(new Ressource("Cherry Brandy (Heering)", 0, 0));
		// other
		ressources.add(new Ressource("Rohrzucker", 0, 0));
		ressources.add(new Ressource("Ginger Ale", 0, 0));
		ressources.add(new Ressource("Bitter Lemon", 0, 0));
		ressources.add(new Ressource("Limette", 0, 0));
		ressources.add(new Ressource("Soda", 0, 0));
		ressources.add(new Ressource("Cola", 0, 0));
		ressources.add(new Ressource("Basilikum", 0, 0));
		ressources.add(new Ressource("Angostura Bitters", 0, 0));
		ressources.add(new Ressource("Orange Bitters", 0, 0));
		for (Ressource ressource : ressources) {
			save(ressource);
		}
	}

}
