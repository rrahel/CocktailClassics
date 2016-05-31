package at.fhjoanneum.android.sqlite.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import at.fhjoanneum.android.sqlite.data.Category;

/**
 * Class to interact with the database.
 * Here you find all the queries for the category table of the data base
 * @author Manuela Mayer, Kahmann Sebastian
 *
 */
public class CategoryDAO extends CocktailDBDAO {

	private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
			+ " =?";

	/**
	 * Constructor which calls the super constructor
	 * @param context Current state of the application
	 */
	public CategoryDAO(Context context) {
		super(context);
	}

	/**
	 * Save a new category in the category table
	 * @param category Category to save
	 * @return If the new category was inserted
	 */
	public long save(Category category) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NAME_COLUMN, category.getName());

		return database.insert(DataBaseHelper.CATEGORY_TABLE, null, values);
	}

	/**
	 * To update a category
	 * @param category Category to update
	 * @return If the category was updated
	 */
	public long update(Category category) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NAME_COLUMN, category.getName());

		long result = database.update(DataBaseHelper.CATEGORY_TABLE, values,
				WHERE_ID_EQUALS,
				new String[] { String.valueOf(category.getId()) });
		Log.d("Update Result:", "=" + result);
		return result;

	}

	/**
	 * To delete a category from the table
	 * @param category Category to delete
	 * @return If the category was deleted
	 */
	public int deleteCategory(Category category) {
		return database.delete(DataBaseHelper.CATEGORY_TABLE, WHERE_ID_EQUALS,
				new String[] { category.getId() + "" });
	}

	/**
	 * To get a list of all categories which have the filter input in their name 
	 * @param filterInput Name of the category you want to find
	 * @return Array list of the found categories
	 */
	public List<Category> getCategories(String filterInput) {
		List<Category> categories = new ArrayList<Category>();
		String query = "SELECT distinct " + DataBaseHelper.ID_COLUMN + ","
				+ DataBaseHelper.NAME_COLUMN + " FROM "
				+ DataBaseHelper.CATEGORY_TABLE + " WHERE ("
				+ DataBaseHelper.NAME_COLUMN + " LIKE '%" + filterInput
				+ "%') order by " + DataBaseHelper.NAME_COLUMN + " asc ;";

		Log.d("query", query);
		Cursor cursor = database.rawQuery(query, null);
		while (cursor.moveToNext()) {
			Category category = new Category();
			category.setId(cursor.getInt(0));
			category.setName(cursor.getString(1));
			categories.add(category);
		}
		cursor.close();
		return categories;
	}

	/**
	 * To get all values of the category if you only know the id of it
	 * @param id Id of the category you will find
	 * @return Category with the entered id or null if no category was found
	 */
	public Category getCategoryById(int id) {
		Cursor cursor = database.query(DataBaseHelper.CATEGORY_TABLE,
				new String[] { DataBaseHelper.ID_COLUMN,
						DataBaseHelper.NAME_COLUMN }, DataBaseHelper.ID_COLUMN
						+ "=?", new String[] { String.valueOf(id) }, null,
				null, null, null);

		if (cursor != null) {
			cursor.moveToNext();
		} else {
			Log.e("Get Category By Id", "Cursor = null");
		}

		try {
			Category category = new Category();
			category.setId(cursor.getInt(0));
			category.setName(cursor.getString(1));
			
			cursor.close();
			return category;
		} catch (Exception e) {
			Log.e("Get Category By Id", "Wrong Category Name\n" + e);
			return null;
		}
		
	}

	/**
	 * Loads all categories in the category table
	 */
	public void loadCategories() {

		List<Category> categories = new ArrayList<Category>();
		categories.add(new Category("Fruchtig"));
		categories.add(new Category("Süß"));
		categories.add(new Category("Sauer"));
		categories.add(new Category("Bitter"));
		categories.add(new Category("Stark"));
		categories.add( new Category("Einzigartig"));
		for (Category categorie : categories) {
			save(categorie);
		}
	}

}
