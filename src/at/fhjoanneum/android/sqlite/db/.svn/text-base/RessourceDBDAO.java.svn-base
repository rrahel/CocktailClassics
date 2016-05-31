package at.fhjoanneum.android.sqlite.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * To interact with the database.
 * In this class the database will be prepared for changing the values.
 * @author Manuela Mayer
 *
 */
public class RessourceDBDAO {

	protected SQLiteDatabase database;
	private DataBaseHelper dbHelper;
	private Context mContext;

	/**
	 * Constructor which also opens the data base helper
	 * @param context current state of the application
	 */
	public RessourceDBDAO(Context context) {
		this.mContext = context;
		dbHelper = DataBaseHelper.getHelper(mContext);
		open();
	}

	/**
	 * Makes the data base writable to change values of the columns
	 * @throws SQLException If something goes wrong during making the database writable
	 */
	public void open() throws SQLException {
		if (dbHelper == null)
			dbHelper = DataBaseHelper.getHelper(mContext);
		database = dbHelper.getWritableDatabase();
	}

	/**
	 * Closes the database
	 */
	public void close() {
		dbHelper.close();
		database = null;
	}
	
	/**
	 * To get the state of the application
	 * @return State of the application
	 */
	protected Context getmContext() {
		return mContext;
	}

}
