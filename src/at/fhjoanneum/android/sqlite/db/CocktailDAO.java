package at.fhjoanneum.android.sqlite.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import at.fhjoanneum.android.sqlite.data.Category;
import at.fhjoanneum.android.sqlite.data.Cocktail;

/**
 * Class to interact with the database.
 * Here you find all the queries for the cocktail table of the data base
 * @author Manuela Mayer, Kahmann Sebastian, Egger Andreas, Cemi Rrahel
 *
 */
public class CocktailDAO extends CocktailDBDAO {

	public static final String COCKTAIL_ID_WITH_PREFIX = "cocktail.id";
	public static final String COCKTAIL_NAME_WITH_PREFIX = "cocktail.name";
	public static final String CATEGORY_NAME_WITH_PREFIX = "category.name";

	private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
			+ " =?";

	/**
	 * Constructor which calls the super constructor
	 * @param context Current state of the application
	 */
	public CocktailDAO(Context context) {
		super(context);
	}

	/**
	 * Save a new cocktail in the cocktail table
	 * @param cocktail Cocktail to save
	 * @return If the new cocktail was inserted
	 */
	public long save(Cocktail cocktail) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NAME_COLUMN, cocktail.getName());
		values.put(DataBaseHelper.COCKTAIL_RAITING, cocktail.getRating());
		values.put(DataBaseHelper.COCKTAIL_RECIPIE, cocktail.getRecipie());
		values.put(DataBaseHelper.COCKTAIL_NOTE, cocktail.getNote());
		values.put(DataBaseHelper.COCKTAIL_ALCOHOL, cocktail.getAlcohol());
		values.put(DataBaseHelper.COCKTAIL_CATEGORY_ID, cocktail.getCategory()
				.getId());

		return database.insert(DataBaseHelper.COCKTAIL_TABLE, null, values);
	}

	/**
	 * To update a cocktail
	 * @param cocktail Cocktail to update
	 * @return If the cocktail was updated
	 */
	public long update(Cocktail cocktail) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NAME_COLUMN, cocktail.getName());
		values.put(DataBaseHelper.COCKTAIL_RAITING, cocktail.getRating());
		values.put(DataBaseHelper.COCKTAIL_RECIPIE, cocktail.getRecipie());
		values.put(DataBaseHelper.COCKTAIL_NOTE, cocktail.getNote());
		values.put(DataBaseHelper.COCKTAIL_ALCOHOL, cocktail.getAlcohol());
		values.put(DataBaseHelper.COCKTAIL_CATEGORY_ID, cocktail.getCategory()
				.getId());

		long result = database.update(DataBaseHelper.COCKTAIL_TABLE, values,
				WHERE_ID_EQUALS,
				new String[] { String.valueOf(cocktail.getId()) });
		Log.d("Update Result:", "=" + result);
		return result;
	}

	/**
	 * To delete a cocktail from the table
	 * @param cocktail Cocktail to delete
	 * @return If the cocktail was deleted
	 */
	public int deleteCocktail(Cocktail cocktail) {
		return database.delete(DataBaseHelper.COCKTAIL_TABLE, WHERE_ID_EQUALS,
				new String[] { cocktail.getId() + "" });
	}

	/**
	 * To get a list of all cocktail which have the filter input in their name.
	 * The list gets sorted based on the name or the rating of the cocktails
	 * @param filterInput Name of the cocktail you want to find
	 * @param nameSort If nameSort is true the list gets sorted on the names of the cocktails and if it is false the list gets sortet on the rating
	 * @return Array list of the found cocktails
	 */
	public ArrayList<Cocktail> getCocktails(String filterInput, Boolean nameSort) {
		String query;
		ArrayList<Cocktail> cocktails = new ArrayList<Cocktail>();

		query = "SELECT distinct " + COCKTAIL_ID_WITH_PREFIX + ","
				+ COCKTAIL_NAME_WITH_PREFIX + ","
				+ DataBaseHelper.COCKTAIL_RAITING + ","
				+ DataBaseHelper.COCKTAIL_RECIPIE + ","
				+ DataBaseHelper.COCKTAIL_NOTE + ","
				+ DataBaseHelper.COCKTAIL_ALCOHOL + ","
				+ DataBaseHelper.COCKTAIL_CATEGORY_ID + ","
				+ CATEGORY_NAME_WITH_PREFIX + " FROM "
				+ DataBaseHelper.COCKTAIL_TABLE + " cocktail, "
				+ DataBaseHelper.CATEGORY_TABLE + " category WHERE cocktail."
				+ DataBaseHelper.COCKTAIL_CATEGORY_ID + " = category."
				+ DataBaseHelper.ID_COLUMN + " AND ("
				+ COCKTAIL_NAME_WITH_PREFIX + " LIKE '%" + filterInput
				+ "%' OR category." + DataBaseHelper.NAME_COLUMN + " LIKE '%";

		if (nameSort) {
			query += filterInput + "%') order by " + COCKTAIL_NAME_WITH_PREFIX
					+ " asc ;";
		} else {
			query += filterInput + "%') order by "
					+ DataBaseHelper.COCKTAIL_RAITING + " desc, cocktail."
					+ DataBaseHelper.NAME_COLUMN + " asc ;";
		}

		Log.d("query", query);
		Cursor cursor = database.rawQuery(query, null);
		while (cursor.moveToNext()) {
			Cocktail cocktail = new Cocktail();
			cocktail.setId(cursor.getInt(0));
			cocktail.setName(cursor.getString(1));
			cocktail.setRaiting(cursor.getInt(2));
			cocktail.setRecipie(cursor.getString(3));
			cocktail.setNote(cursor.getString(4));
			cocktail.setAlcohol(cursor.getInt(5));

			Category cateory = new Category();
			cateory.setId(cursor.getInt(6));
			cateory.setName(cursor.getString(7));

			cocktail.setCategory(cateory);

			cocktails.add(cocktail);
		}
		cursor.close();
		return cocktails;
	}

	/**
	 * To get a list of all cocktail which have the filter input in their name, in the name of the
	 * resources of the cocktail or in the name of the category.
	 * The list gets sorted based on the name or the rating of the cocktails
	 * @param filterInput Name of the cocktail, resource or category you want to find
	 * @param nameSort If nameSort is true the list gets sorted on the names of the cocktails and if it is false the list gets sortet on the rating
	 * @return Array list of the found cocktails
	 */
	public ArrayList<Cocktail> getFilteredCocktails(String filterInput,
			Boolean nameSort) {
		ArrayList<Cocktail> cocktails = new ArrayList<Cocktail>();
		String query = "SELECT distinct " + COCKTAIL_ID_WITH_PREFIX + ","
				+ COCKTAIL_NAME_WITH_PREFIX + ","
				+ DataBaseHelper.COCKTAIL_RAITING + ","
				+ DataBaseHelper.COCKTAIL_RECIPIE + ","
				+ DataBaseHelper.COCKTAIL_NOTE + ","
				+ DataBaseHelper.COCKTAIL_ALCOHOL + ","
				+ DataBaseHelper.COCKTAIL_CATEGORY_ID + ","
				+ CATEGORY_NAME_WITH_PREFIX + " FROM "
				+ DataBaseHelper.COCKTAIL_TABLE + " cocktail INNER JOIN "
				+ DataBaseHelper.CATEGORY_TABLE + " category ON cocktail."
				+ DataBaseHelper.COCKTAIL_CATEGORY_ID + " = category."
				+ DataBaseHelper.ID_COLUMN + " INNER JOIN "
				+ DataBaseHelper.INGREDIENT_TABLE + " ingredient ON "
				+ COCKTAIL_ID_WITH_PREFIX + " = ingredient."
				+ DataBaseHelper.INGREDIENT_COCKTAIL_ID + " INNER JOIN "
				+ DataBaseHelper.RESSOURCE_TABLE + " ressource ON ingredient."
				+ DataBaseHelper.INGREDIENT_RESSOURCE_ID + " = ressource."
				+ DataBaseHelper.ID_COLUMN + " WHERE "
				+ COCKTAIL_NAME_WITH_PREFIX + " LIKE '%" + filterInput
				+ "%' OR ressource." + DataBaseHelper.NAME_COLUMN + " LIKE '%"
				+ filterInput + "%' OR category." + DataBaseHelper.NAME_COLUMN
				+ " LIKE '%";

		if (nameSort) {
			query += filterInput + "%' order by " + COCKTAIL_NAME_WITH_PREFIX
					+ " asc ;";
		} else {
			query += filterInput + "%' order by "
					+ DataBaseHelper.COCKTAIL_RAITING + " desc, cocktail."
					+ DataBaseHelper.NAME_COLUMN + " asc ;";
		}

		Log.d("query", query);
		Cursor cursor = database.rawQuery(query, null);
		while (cursor.moveToNext()) {
			Cocktail cocktail = new Cocktail();
			cocktail.setId(cursor.getInt(0));
			cocktail.setName(cursor.getString(1));
			cocktail.setRaiting(cursor.getInt(2));
			cocktail.setRecipie(cursor.getString(3));
			cocktail.setNote(cursor.getString(4));
			cocktail.setAlcohol(cursor.getInt(5));

			Category cateory = new Category();
			cateory.setId(cursor.getInt(6));
			cateory.setName(cursor.getString(7));

			cocktail.setCategory(cateory);

			cocktails.add(cocktail);
		}
		cursor.close();
		return cocktails;
	}

	/**
	 * To get all values of the cocktail if you only know the id of it
	 * @param id Id of the cocktail you will find
	 * @return Cocktail with the entered id or null if no cocktail was found
	 */
	public Cocktail getCocktailyById(int id) {
		Cursor cursor = database.query(DataBaseHelper.COCKTAIL_TABLE,
				new String[] { DataBaseHelper.ID_COLUMN,
						DataBaseHelper.NAME_COLUMN,
						DataBaseHelper.COCKTAIL_RAITING,
						DataBaseHelper.COCKTAIL_RECIPIE,
						DataBaseHelper.COCKTAIL_CATEGORY_ID,
						DataBaseHelper.COCKTAIL_NOTE,
						DataBaseHelper.COCKTAIL_ALCOHOL },
				DataBaseHelper.ID_COLUMN + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null) {
			cursor.moveToNext();
		} else {
			Log.e("Get Cocktail By Id", "Cursor = null");
		}

		try {
			Cocktail cocktail = new Cocktail();
			cocktail.setId(cursor.getInt(0));
			cocktail.setName(cursor.getString(1));
			cocktail.setRaiting(cursor.getInt(2));
			cocktail.setRecipie(cursor.getString(3));

			Category cateory = new Category();
			CategoryDAO categoryDOA = new CategoryDAO(getmContext());
			cateory.setId(cursor.getInt(4));
			cateory.setName(categoryDOA.getCategoryById(cateory.getId())
					.getName());
			cocktail.setCategory(cateory);

			cocktail.setNote(cursor.getString(5));
			cocktail.setAlcohol(cursor.getInt(6));

			cursor.close();
			return cocktail;
		} catch (Exception e) {
			Log.e("Get Cocktail By Id", "Wrong Cocktail Name\n" + e);
			return null;
		}
	}

	/**
	 * Sets the flag inshoppinglist of the cocktail to 1 or 0
	 * @param cocktail Cocktail where the inshoppinglist flag should be set 
	 * @param value 1 if the shoppinglist flag should be set or 0 if it should be reset
	 */
	public void setInShoppingList(Cocktail cocktail, int value) {
		ContentValues cv = new ContentValues();
		cv.put("inshoppinglist", value);

		int affectedRows = database.update(DataBaseHelper.COCKTAIL_TABLE, cv,
				"id" + " = " + cocktail.getId(), null);

		if (affectedRows == 0) {
			Log.d("Set In Shopping List", "affectedRows = 0");
		} else if (affectedRows > 1) {
			Log.d("Get Cocktail By Id", "affectedRows > 1");
		}
	}

	/**
	 * Sets the rating of the cocktail
	 * @param cocktail Cocktail where the rating should be set 
	 * @param value Between 0 and 5 depends on how good was the cocktail
	 */
	public void setRating(Cocktail cocktail, int value) {
		ContentValues cv = new ContentValues();
		cv.put("raiting", value);

		int affectedRows = database.update(DataBaseHelper.COCKTAIL_TABLE, cv,
				"id" + " = " + cocktail.getId(), null);

		if (affectedRows == 0) {
			Log.e("CocktailDAO: Set Rating", "affectedRows = 0");
		} else if (affectedRows > 1) {
			Log.e("CocktailDAO: Set Rating", "affectedRows > 1");
		}
	}

	/**
	 * Sets the note of the cocktail
	 * @param cocktail Cocktail where the note should be set 
	 * @param note String which should be set as note
	 */
	public void setNote(Cocktail cocktail, String note) {
		ContentValues cv = new ContentValues();
		cv.put("note", note);

		int affectedRows = database.update(DataBaseHelper.COCKTAIL_TABLE, cv,
				"id" + " = " + cocktail.getId(), null);

		if (affectedRows == 0) {
			Log.e("CocktailDAO: Set Note", "affectedRows = 0");
		} else if (affectedRows > 1) {
			Log.e("CocktailDAO: Set Note", "affectedRows > 1");
		}
	}

	/**
	 * Loads all cocktails in the cocktail table
	 */
	public void loadCocktails() {

		CategoryDAO categorieDAO = new CategoryDAO(getmContext());

		Cocktail ginFizz = new Cocktail(
				"Gin Fizz",
				0,
				"Alles mit viel Eis in den Shaker. In ein eisgefülltes Glas abseihen.",
				"", 1, categorieDAO.getCategories("Sauer").get(0));
		Cocktail cubaLibre = new Cocktail(
				"Cuba Libre",
				0,
				"Die Limette vierteln und in ein Glas ausdrücken und hineingeben. Anschließend den Rum und Eis dazugeben und mit Cola auffüllen.",
				"", 1, categorieDAO.getCategories("Sauer").get(0));
		Cocktail singaporeSling = new Cocktail(
				"Singapore Sling",
				0,
				"Alles, mit Ausnahme des Angostura, mit viel Eis in den Shaker und zum Schluss die Fruchtsäfte hinzugeben und alles kurz shaken. Dann in ein Longdrinkglas abseihen und den Angostura hinzugeben.",
				"", 1, categorieDAO.getCategories("Fruchtig").get(0));
		Cocktail saturn = new Cocktail(
				"Saturn",
				0,
				"Crushed Ice in einen Shaker, dann alle Zutaten hinzugeben und alles kurz shaken. In ein Glas mit frischem Crushed Ice geben.",
				"", 1, categorieDAO.getCategories("Einzigartig").get(0));
		Cocktail whiskeySour = new Cocktail(
				"Whiskey Sour",
				0,
				"Alles mit viel Eis in den Shaker. In ein eisgefülltes Glas abseihen.",
				"", 1, categorieDAO.getCategories("Sauer").get(0));
		Cocktail negroni = new Cocktail(
				"Negroni",
				0,
				"Alles mit viel Eis in den Shaker. In ein eisgefülltes Glas abseihen. Mit einer Orangenscheibe garnieren.",
				"", 1, categorieDAO.getCategories("Bitter").get(0));
		Cocktail maiTai = new Cocktail(
				"Mai Tai",
				0,
				"Alles mit viel Crushed Eis in den Shaker.Alles in ein Glas geben und mit Crushed Ice auffüllen.",
				"", 1, categorieDAO.getCategories("Süß").get(0));
		Cocktail ginBasil = new Cocktail(
				"Gin-Basil Smash",
				0,
				"Alle Zutaten in den Shaker. Basilikumblätter mit einem Stößel zerdrücken. Mit viel Eis shaken. Double-Strain in ein Glas.",
				"", 1, categorieDAO.getCategories("Einzigartig").get(0));
		Cocktail martini = new Cocktail(
				"Martini",
				0,
				"Alle Zutaten mit Eis in ein Mixglas und rühren. Dann in ein gekühltes Cocktailglas (ohne Eis) und mit Zitronenzeste garnieren.",
				"", 1, categorieDAO.getCategories("Stark").get(0));
		Cocktail martinez = new Cocktail(
				"Martinez",
				0,
				"Alle Zutaten mit Eis in ein Mixglas und rühren. Dann in ein gekühltes Cocktailglas (ohne Eis) und mit Zitronenzeste garnieren.",
				"", 1, categorieDAO.getCategories("Stark").get(0));
		Cocktail tequilaSunrise = new Cocktail(
				"Tequila Sunrise",
				0,
				"Alle Zutaten außer der Grenadine in einem Longdrinkglas mit Eiswürfel zusammenrühren. Dann die Grenadine vorsichtig über die Rückseite eines Barlöffels in das Glas gießen.",
				"", 1, categorieDAO.getCategories("Süß").get(0));
		Cocktail barracudaBite = new Cocktail(
				"Barracuda Bite",
				0,
				"Alles mit viel Eis in den Shaker. In ein eisgefülltes Glas abseihen.",
				"", 1, categorieDAO.getCategories("Süß").get(0));
		Cocktail bigBen = new Cocktail(
				"Big Ben",
				0,
				"Alles, bis auf Bitter Lemon, mit viel Eis in den Shaker. In ein eisgefülltes Glas abseihen und mit Bitter Lemon auffüllen.",
				"", 1, categorieDAO.getCategories("Süß").get(0));
		Cocktail bologna = new Cocktail(
				"Bologna",
				0,
				"Alle Zutaten mit Eis in ein Mixglas und rühren. Dann in ein gekühltes Glas (ohne Eis) geben.",
				"", 1, categorieDAO.getCategories("Bitter").get(0));
		Cocktail elPresidente = new Cocktail(
				"El Presidente",
				0,
				"Alles mit viel Eis in den Shaker. In ein eisgefülltes Glas abseihen.",
				"", 1, categorieDAO.getCategories("Stark").get(0));
		Cocktail eastSour = new Cocktail(
				"Eastern Sour",
				0,
				"Alles mit viel Eis in den Shaker. In ein crushedeisgefülltes Glas abseihen.",
				"", 1, categorieDAO.getCategories("Süß").get(0));
		Cocktail derby = new Cocktail(
				"Derby",
				0,
				"Alle Zutaten mit Eis in einem Glas verrühren. Dann in ein gekühltes Glas (ohne Eis) geben.",
				"", 1, categorieDAO.getCategories("Stark").get(0));
		Cocktail margarita = new Cocktail(
				"Margarita",
				0,
				"Alles mit viel Eis in den Shaker. Dann in ein Margaritaglas mit Salzrand geben.",
				"", 1, categorieDAO.getCategories("Sauer").get(0));
		Cocktail caipirinha = new Cocktail(
				"Caipirinha",
				0,
				"Die Limette achteln, dann in ein Caipirinhaglas geben, dazu den Rohrzucker und mit einem Holzstößel zerdrücken. Das Glas bis fast oben hin mit crushed Eis füllen, danach den Cachaca dazugeben, dann kräftig umrühren.",
				"", 1, categorieDAO.getCategories("Sauer").get(0));
		Cocktail ipanema = new Cocktail(
				"Ipanema",
				0,
				"Die Limette achteln, dann in ein Caipirinhaglas geben, dazu den Rohrzucker und mit einem Holzstößel zerdrücken. Das Glas bis fast oben hin mit crushed Eis füllen, danach den Ginger Ale dazugeben, dann kräftig umrühren",
				"", 0, categorieDAO.getCategories("Sauer").get(0));
		Cocktail florida = new Cocktail(
				"Florida",
				0,
				"Alles mit viel Eis in den Shaker. In ein eisgefülltes Glas abseihen.",
				"", 0, categorieDAO.getCategories("Süß").get(0));

		List<Cocktail> cocktails = new ArrayList<Cocktail>();
		cocktails.add(caipirinha);
		cocktails.add(ipanema);
		cocktails.add(florida);
		cocktails.add(margarita);
		cocktails.add(derby);
		cocktails.add(eastSour);
		cocktails.add(elPresidente);
		cocktails.add(negroni);
		cocktails.add(bologna);
		cocktails.add(bigBen);
		cocktails.add(barracudaBite);
		cocktails.add(tequilaSunrise);
		cocktails.add(martinez);
		cocktails.add(martini);
		cocktails.add(ginBasil);
		cocktails.add(maiTai);
		cocktails.add(ginFizz);
		cocktails.add(cubaLibre);
		cocktails.add(singaporeSling);
		cocktails.add(saturn);
		cocktails.add(whiskeySour);
		for (Cocktail cocktail : cocktails) {
			save(cocktail);
		}
	}
}
