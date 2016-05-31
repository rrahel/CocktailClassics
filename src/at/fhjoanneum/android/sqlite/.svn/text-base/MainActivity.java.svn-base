package at.fhjoanneum.android.sqlite;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import at.fhjoanneum.android.sqlite.db.CategoryDAO;
import at.fhjoanneum.android.sqlite.db.CocktailDAO;
import at.fhjoanneum.android.sqlite.db.IngredientDAO;
import at.fhjoanneum.android.sqlite.db.RessourceDAO;
import at.fhjoanneum.android.sqlite.fragment.CocktailAlcoholLevelCalculator;
import at.fhjoanneum.android.sqlite.fragment.CocktailBarFragment;
import at.fhjoanneum.android.sqlite.fragment.CocktailListFragment;
import at.fhjoanneum.android.sqlite.fragment.CocktailShoppingFragment;
import at.fhjoanneum.android.sqlite.fragment.ImpressumFragment;
import at.fhjoanneum.android.sqlite.fragment.QuitDialogFragment;

/**
 * <h1>Cocktail Classics</h1>
 * 
 * <h2>Cocktail Classics is an app which shows a big selection of classical
 * cocktails. Every cocktail is something special and can be mixed very easily
 * because the recipe, the ingredients and a picture is shown for every
 * cocktail. You can rate your favorite cocktails and sort them like you want.
 * If there is something to add in the recipe you can easily take a note for
 * every cocktail and you can also change the picture if you prefer your own
 * pictures.</h2>
 * 
 * This is our main activity. Here the whole database will be set up if
 * necessary and the data gets loaded into the database. Also the first Fragment
 * gets shown, which is the cocktail list fragment where the whole list of
 * cocktails gets shown. The adapter will create the navigation drawer which is
 * needed to change the content of the fragments.
 * 
 * @author Manuela Mayer, Kahmann Sebastian, Egger Andreas, Cemi Rrahel
 * @version 1.0
 *
 */
public class MainActivity extends FragmentActivity implements
		OnItemClickListener {

	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private Fragment contentFragment;
	private CocktailListFragment cocktailListFragment;
	private ActionBarDrawerToggle drawerListener;
	private DrawerAdapter adapter;

	/**
	 * This method locks the orientation of the screen to portrait mode for
	 * better readability. A boolean value will be set to know if the current
	 * used device is a smart phone or a tablet. It also connects the
	 * activity_main.xml with the Activity If everything went wrong with the
	 * database, the first fragment, CocktailListFragment gets shown.
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		adapter = new DrawerAdapter(this);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		drawerList = (ListView) findViewById(R.id.drawerList);
		drawerList.setAdapter(adapter);
		drawerList.setOnItemClickListener(this);
		drawerListener = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close);
		drawerLayout.setDrawerListener(drawerListener);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		FragmentManager fragmentManager = getSupportFragmentManager();

		CategoryDAO categoryDAO = new CategoryDAO(this);
		RessourceDAO ressourceDAO = new RessourceDAO(this);
		CocktailDAO cocktailDAO = new CocktailDAO(this);
		IngredientDAO ingredientDAO = new IngredientDAO(this);

		// Initially loads database content
		if (categoryDAO.getCategories("").size() <= 0)
			categoryDAO.loadCategories();
		if (ressourceDAO.getRessources("").size() <= 0)
			ressourceDAO.loadRessources();
		if (cocktailDAO.getCocktails("", true).size() <= 0)
			cocktailDAO.loadCocktails();
		if (ingredientDAO.getIngredients("").size() <= 0)
			ingredientDAO.loadIngredients();

		if (savedInstanceState != null) {
			if (fragmentManager
					.findFragmentByTag(CocktailListFragment.ARG_ITEM_ID) != null) {
				cocktailListFragment = (CocktailListFragment) fragmentManager
						.findFragmentByTag(CocktailListFragment.ARG_ITEM_ID);
				contentFragment = cocktailListFragment;
			}
		} else {
			cocktailListFragment = new CocktailListFragment();
			setFragmentTitle(R.string.app_name);
			switchContent(cocktailListFragment,
					CocktailListFragment.ARG_ITEM_ID);

		}
	}

	public void onCheckboxClick(View view) {
		if (((CheckBox) view).isChecked()) {
			Log.d("Bar", String.valueOf(view.getParent()));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerListener.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("content", CocktailListFragment.ARG_ITEM_ID);
		super.onSaveInstanceState(outState);
	}

	/**
	 * This procedure is used to switch the content to another fragment
	 * 
	 * @param fragment
	 *            The new fragment
	 * @param tag
	 *            The tag of the current fragment
	 */
	public void switchContent(Fragment fragment, String tag) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.content_frame, fragment, tag);
		transaction.commit();
		contentFragment = fragment;
	}

	/**
	 * Set the title of the fragment
	 * 
	 * @param resourseId
	 *            The id from the at.fhjoanneum.android.sqlite.R file which
	 *            stands for a name in the strings.xml file
	 */
	protected void setFragmentTitle(int resourseId) {
		setTitle(resourseId);
		getActionBar().setTitle(resourseId);

	}

	/**
	 * We change to the CocktailListFragment from the CocktailDetailFragment. If
	 * we are in one of the main fragments which are the CocktailListFragment,
	 * CocktailBarFragment, CocktailShoppinglistFragment, ImpressumFragment or
	 * CocktailAlcoholLevelCalculator, we show the quit dialog.
	 */
	@Override
	public void onBackPressed() {
		FragmentManager fm = getSupportFragmentManager();
		if (fm.getBackStackEntryCount() > 0) {
			for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
				fm.popBackStack();
			}
			CocktailListFragment cockFrag = new CocktailListFragment();
			fm.beginTransaction().replace(R.id.content_frame, cockFrag)
					.commit();
		} else if (fm.getBackStackEntryCount() == 0) {
			// Shows an alert dialog on quit
			onQuitDialog();
		}

	}

	/**
	 * Changes to the QuitDialogFragment which shows us the quit dialog. We do
	 * not use an predefined dialog because the layout would not fit to our app
	 * layout
	 */

	public void onQuitDialog() {
		QuitDialogFragment quitDialogFragment = new QuitDialogFragment();
		quitDialogFragment.show(getSupportFragmentManager(),
				QuitDialogFragment.ARG_ITEM_ID);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerListener.syncState();
	}

	/**
	 * If we click on one item of the navigation drawer this procedure will call
	 * the correct fragment.
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		selectItem(position);
		FragmentManager fragmentManager = getSupportFragmentManager();
		// FragmentSwitch
		switch (position) {
		// cocktail list
		case 0:
			CocktailListFragment cocktailListFragment = new CocktailListFragment();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, cocktailListFragment).commit();
				fragmentManager.popBackStack();
			break;
		// bar
		case 1:
			CocktailBarFragment cocktailBarFragment = new CocktailBarFragment();
			if(fragmentManager.getBackStackEntryCount()==0){
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, cocktailBarFragment).addToBackStack(null).commit();}
			else{fragmentManager.beginTransaction()
				.replace(R.id.content_frame, cocktailBarFragment).commit();}
			break;
		// shopping list
		case 2:
			CocktailShoppingFragment cocktailShoppingListFragment = new CocktailShoppingFragment();
			if(fragmentManager.getBackStackEntryCount()==0){
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, cocktailShoppingListFragment).addToBackStack(null)
					.commit();}
			else{fragmentManager.beginTransaction()
				.replace(R.id.content_frame, cocktailShoppingListFragment)
				.commit();}
			break;
		// Alccalc
		case 3:
			CocktailAlcoholLevelCalculator cocktailAlcCalc = new CocktailAlcoholLevelCalculator();
			if(fragmentManager.getBackStackEntryCount()==0){
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, cocktailAlcCalc).addToBackStack(null).commit();}
			else{fragmentManager.beginTransaction()
				.replace(R.id.content_frame, cocktailAlcCalc).commit();}
			break;
		// impressum
		case 4:
			ImpressumFragment impressum = new ImpressumFragment();
			if(fragmentManager.getBackStackEntryCount()==0){
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, impressum).addToBackStack(null).commit();}
			else{fragmentManager.beginTransaction()
				.replace(R.id.content_frame, impressum).commit();}
			break;
		}
		drawerLayout.closeDrawers();

	}

	private void popBackStack(FragmentManager fragmentManager) {
		for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
			fragmentManager.popBackStack();
		}
	}

	private void selectItem(int position) {
		drawerList.setItemChecked(position, true);
	}

	public void setTitle(String title) {
		getActionBar().setTitle(title);
	}
}

// class is in here, because it will only be used in this class for the nav
// drawer
/**
 * This class is needed to use our navigation drawer. It sets the number of
 * items in it and also the names of the items. Every item has also an icon in
 * our app which gets als set in this adapter.
 * 
 * @author Cemi Rrahel, Egger Andreas, Kahmann Sebastian, Mayer Manuela
 * @version 1.0
 *
 */
class DrawerAdapter extends BaseAdapter {
	private Context mContext;
	// array with the item names for the list in the drawer
	private String[] menue;

	// images for the items
	private int[] images = { R.drawable.ic_cocktail32, R.drawable.ic_bar32,
			R.drawable.ic_shoppingcart32, R.drawable.ic_calculator32,
			R.drawable.ic_impressum32 };

	public DrawerAdapter(Context context) {
		menue = context.getResources().getStringArray(R.array.menue);
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return menue.length;
	}

	@Override
	public Object getItem(int position) {
		return menue[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			item = inflater.inflate(R.layout.drawer_item, parent, false);
		} else {
			item = convertView;
		}
		TextView titleTextView = (TextView) item
				.findViewById(R.id.drawer_item_text);
		ImageView titleImageView = (ImageView) item
				.findViewById(R.id.drawer_item_image);
		titleTextView.setText(menue[position]);
		titleImageView.setImageResource(images[position]);
		return item;
	}

}
