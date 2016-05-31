package at.fhjoanneum.android.sqlite.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import at.fhjoanneum.android.sqlite.R;
import at.fhjoanneum.android.sqlite.adapter.ShoppingListAdapter;
import at.fhjoanneum.android.sqlite.data.Ingredient;
import at.fhjoanneum.android.sqlite.db.IngredientDAO;

/**
 * 
 * This fragment shows a list of resources that are not in bar and the user
 * wants to buy. Through the "GetIngredientTask" class this Fragment gets its
 * list items filled by the shoppingListAdapter. The list is automatically
 * refreshed after any change happens.
 * 
 * @author Rrahel Cemi
 * 
 */
public class CocktailShoppingFragment extends Fragment {

	Activity activity;
	ListView shoppingListView;
	private ShoppingListAdapter shopListAdapter;
	private ArrayList<Ingredient> ingredients;

	private Button btAddInBar;
	private Button btDeleteZutaten;

	private GetIngredientTask task;

	private IngredientDAO ingredientDAO;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		ingredientDAO = new IngredientDAO(activity);

	}

	/**
	 * The layout is attached to the fragment and the task methods are called
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_shopping_list,
				container, false);
		findViewsById(view);

		task = new GetIngredientTask(activity);
		task.execute((Void) null);

		return view;

	}

	/**
	 * variables get attached to the views and the listener is called
	 */
	private void findViewsById(View rootView) {
		shoppingListView = (ListView) rootView
				.findViewById(R.id.list_ressouces);
		btAddInBar = (Button) rootView.findViewById(R.id.bt_add_toBar);
		btDeleteZutaten = (Button) rootView
				.findViewById(R.id.bt_delete_ressource_shop);
		setListeners();
	}

	/**
	 * Defines the methods that the listener will use
	 */
	private void setListeners() {
		btAddInBar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				onAddInBar(v);

			}
		});

		btDeleteZutaten.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				onDeleteZutaten(v);
			}
		});

	}

	/**
	 * after pressing the button a new dialog is attached
	 * 
	 * @param view
	 */
	public void onAddInBar(View view) {
		CocktailShoppingAddInBarFragment inBar = new CocktailShoppingAddInBarFragment();
		inBar.show(getFragmentManager(),
				CocktailShoppingAddInBarFragment.ARG_ITEM_ID);
	}

	/**
	 * after pressing the button a new dialog is attached
	 * 
	 * @param view
	 */
	public void onDeleteZutaten(View view) {

		CocktailShoppingDeleteZutaten delZutaten = new CocktailShoppingDeleteZutaten();
		delZutaten.show(getFragmentManager(),
				CocktailShoppingDeleteZutaten.ARG_ITEM_ID);
	}

	/**
	 * This class is responsible for getting the list of Ingredient out of the
	 * Database
	 * 
	 */
	public class GetIngredientTask extends
			AsyncTask<Void, Void, ArrayList<Ingredient>> {

		private final WeakReference<Activity> activityWeakRef;

		public GetIngredientTask(Activity context) {
			this.activityWeakRef = new WeakReference<Activity>(context);
		}

		/**
		 * Gets a list of all ingredients in the shopping list from a select
		 * statement in the class ingredientDAO
		 */
		@Override
		protected ArrayList<Ingredient> doInBackground(Void... arg0) {

			ArrayList<Ingredient> ingredientList = ingredientDAO
					.getAllIngredientsForShopping();

			return ingredientList;
		}

		/**
		 * The ingredient list added to the ShoppingListAdapter which inserts
		 * them into Listview items
		 */
		protected void onPostExecute(ArrayList<Ingredient> ingredientList) {
			if (activityWeakRef.get() != null
					&& !activityWeakRef.get().isFinishing()) {
				ingredients = ingredientList;
				if (ingredientList != null) {
					if (ingredientList.size() != 0) {
						shopListAdapter = new ShoppingListAdapter(activity,
								ingredientList, 0);
						shoppingListView.setAdapter(shopListAdapter);
					} else {
						Toast.makeText(activity, "Keine Zutaten gefunden",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		}
	}

	public void updateView() {
		task = new GetIngredientTask(activity);
		task.execute((Void) null);
	}

	@Override
	public void onResume() {
		getActivity().setTitle(R.string.shopping_frag_name);
		getActivity().getActionBar().setTitle(R.string.shopping_frag_name);
		super.onResume();
	}

}
