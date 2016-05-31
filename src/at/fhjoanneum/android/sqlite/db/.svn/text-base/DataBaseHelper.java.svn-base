package at.fhjoanneum.android.sqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Data base helper for creating the database.
 * The names and columns of the tables are set in this class.
 * Also the data type for every column is set and the actions in case of
 * creating the data base or an upgrade are taken.
 * @author Manuela Mayer
 *
 */
public class DataBaseHelper extends SQLiteOpenHelper {

	// Database information
	private static final String DATABASE_NAME = "cocktailclassics";
	private static final int DATABASE_VERSION = 43;
	
	// Table names
	public static final String COCKTAIL_TABLE = "cocktail";
	public static final String CATEGORY_TABLE = "category";
	public static final String RESSOURCE_TABLE = "ressource";
	public static final String INGREDIENT_TABLE = "ingredient";

	// Shared columns
	public static final String ID_COLUMN = "id";
	public static final String NAME_COLUMN = "name";
	// Cocktail table
	public static final String COCKTAIL_RAITING = "raiting";
	public static final String COCKTAIL_RECIPIE = "recipie";
	public static final String COCKTAIL_NOTE = "note";
	public static final String COCKTAIL_ALCOHOL = "alcohol";
	public static final String COCKTAIL_CATEGORY_ID = "category_id";
	// Ingredient table
	public static final String INGREDIENT_COCKTAIL_ID = "cocktail_id";
	public static final String INGREDIENT_RESSOURCE_ID = "ressource_id";
	public static final String INGREDIENT_AMOUNT = "amount";
	public static final String INGREDIENT_UNIT = "unit";
	// Ressource table
	public static final String RESSOURCE_INBAR = "inbar";
	public static final String RESSOURCE_INSHOPPINGLIST = "inshoppinglist";
	
	/**
	 * To create the cocktail table with all the necessary columns and the datatypes.
	 */
	public static final String CREATE_COCKTAIL_TABLE = "CREATE TABLE "
			+ COCKTAIL_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
			+ NAME_COLUMN + " TEXT, " + COCKTAIL_RAITING + " INTEGER, "
			+ COCKTAIL_RECIPIE + " TEXT, " + COCKTAIL_NOTE + " TEXT, "
			+ COCKTAIL_ALCOHOL + " INTEGER, " 
			+ COCKTAIL_CATEGORY_ID + " INTEGER, " 
			+ "FOREIGN KEY(" + COCKTAIL_CATEGORY_ID
			+ ") REFERENCES " + CATEGORY_TABLE + "(id) " + ")";

	/**
	 * To drop the cocktail table
	 */
	private static final String DROP_COCKTAIL_TABLE = "DROP TABLE "
			+ COCKTAIL_TABLE;

	/**
	 * To create the category table with all the necessary columns and the datatypes.
	 */
	public static final String CREATE_CATEGORY_TABLE = "CREATE TABLE "
			+ CATEGORY_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY,"
			+ NAME_COLUMN + ")";

	/**
	 * To drop the category table
	 */
	private static final String DROP_CATEGORY_TABLE = "DROP TABLE "
			+ CATEGORY_TABLE;

	/**
	 * To create the resource table with all the necessary columns and the datatypes.
	 */
	public static final String CREATE_RESSOURCE_TABLE = "CREATE TABLE "
			+ RESSOURCE_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY,"
			+ NAME_COLUMN + " TEXT, "
			+ RESSOURCE_INBAR + " INTEGER, "
			+ RESSOURCE_INSHOPPINGLIST + " INTEGER )";

	/**
	 * To drop the resource table
	 */
	private static final String DROP_RESSOURCE_TABLE = "DROP TABLE "
			+ RESSOURCE_TABLE;
	
	/**
	 * To create the ingredient table with all the necessary columns and the datatypes.
	 */
	public static final String CREATE_INGREDIENT_TABLE = "CREATE TABLE "
			+ INGREDIENT_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY,"
			+ INGREDIENT_COCKTAIL_ID + " INTEGER, " 
			+ INGREDIENT_RESSOURCE_ID + " INTEGER, "
			+ INGREDIENT_AMOUNT + " INTEGER, "
			+ INGREDIENT_UNIT + " TEXT, "
			+ "FOREIGN KEY(" + INGREDIENT_COCKTAIL_ID
			+ ") REFERENCES " + COCKTAIL_TABLE + "(id) " 
			+ "FOREIGN KEY(" + INGREDIENT_RESSOURCE_ID
			+ ") REFERENCES " + RESSOURCE_TABLE + "(id) "
			+ ")";

	/**
	 * To drop the ingredient table
	 */
	private static final String DROP_INGREDIENT_TABLE = "DROP TABLE "
			+ INGREDIENT_TABLE;
	
	private static DataBaseHelper instance;

	/**
	 * Constructor to create a new data base
	 * @param context context of the application
	 * @return instance of the data base helper
	 */
	public static synchronized DataBaseHelper getHelper(Context context) {
		if (instance == null)
			instance = new DataBaseHelper(context);
		return instance;
	}

	/**
	 * Gets called in the constructor.
	 * Gives the context, name of the database and the veraion to the
	 * super constructor.
	 * @param context current state of the application
	 */
	private DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * To enable foreign key constraints
	 */
	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			// Enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

	/**
	 * To create the data base if you install the app
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL("PRAGMA encoding = \"UTF-8\""); 
			db.execSQL(CREATE_CATEGORY_TABLE);
			db.execSQL(CREATE_COCKTAIL_TABLE);
			db.execSQL(CREATE_RESSOURCE_TABLE);
			db.execSQL(CREATE_INGREDIENT_TABLE);
			Log.d("Create Tables", "Tables correct created");
		} catch (Exception e) {
			Log.e("Create Tables", "Failure in creating tables=" + e);
		}

	}

	/**
	 * To upgrade the database if the version number is increased.
	 * At first all tables gets deleted after this all tables gets created again.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			db.execSQL(DROP_INGREDIENT_TABLE);
			db.execSQL(DROP_RESSOURCE_TABLE);
			db.execSQL(DROP_COCKTAIL_TABLE);
			db.execSQL(DROP_CATEGORY_TABLE);
			onCreate(db);
			Log.d("Update Tables", "Tables correct updated");
		} catch (Exception e) {
			Log.e("Update Tables", "Failure in updating tables=" + e);
		}
	}
}
