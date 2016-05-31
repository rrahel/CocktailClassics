 package at.fhjoanneum.android.sqlite.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import at.fhjoanneum.android.sqlite.R;
import at.fhjoanneum.android.sqlite.adapter.ShoppingListAdapter;
import at.fhjoanneum.android.sqlite.data.Ingredient;
import at.fhjoanneum.android.sqlite.db.IngredientDAO;
import at.fhjoanneum.android.sqlite.db.RessourceDAO;
/**
 * This dialog fragment displays a list of all ingredients that are in the shopping list 
 * and by clicking at a ingredient the user can add it in the bar. After adding the ingredient to the bar 
 * the shopping list will refresh automatically.
 * @author Rrahel Cemi
 *
 */
public class CocktailShoppingAddInBarFragment extends DialogFragment implements
		OnItemClickListener {
	public static final String ARG_ITEM_ID = "Zutaten_In_Bar_Einfugen";

	Activity activity;
	ListView shoppingListView;
	private ArrayList<Ingredient> ingredientlist;

	private DialogFragment dialog = this;

	private ShoppingListAdapter shoppingListAdapter;
	private IngredientDAO ingredientDAO;
	private GetIngredientTask task;
	private RessourceDAO resDAO;

	private Button btnFinish;
	private ImageView ivIcon;
	private TextView tvHeader;
	private CocktailShoppingFragment cockFrag;
	
	
	public interface CocktaiShoppinglZutatenLoschenListener {
		void onFinishDialog();
	}

	public CocktailShoppingAddInBarFragment() {

	}
	/**
	 * Attach the layout to the class and the variables are attached to the views
	 */
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		activity = getActivity();
		ingredientDAO = new IngredientDAO(activity);
		resDAO = new RessourceDAO(activity);
		cockFrag = new CocktailShoppingFragment();

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View customDialogView = inflater.inflate(
				R.layout.dialog_shopping_options, null);
		builder.setView(customDialogView);

		shoppingListView = (ListView) customDialogView
				.findViewById(R.id.list_change_ressources);
		
		tvHeader = (TextView) customDialogView.findViewById(R.id.shopping_change_name);
		tvHeader.setText("Habe ich eingekauft:");
		
		ivIcon = (ImageView) customDialogView.findViewById(R.id.iv_shopping_icon);
		ivIcon.setImageResource(R.drawable.ic_bar32);
		
		btnFinish = (Button) customDialogView.findViewById(R.id.bt_finish);

		setListeners();

		task = new GetIngredientTask(activity);
		task.execute((Void) null);

		shoppingListView.setOnItemClickListener(this);

		AlertDialog alertDialog = builder.create();
		alertDialog.setCanceledOnTouchOutside(false);
		return alertDialog;
	}
	/**
	 * Listener for the finish button to close the dialog after it's pressed
	 */
	private void setListeners() {
		btnFinish.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

			
				
				dialog.dismiss();
				
			}
		});
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
		 * them into List view items
		 */
		@Override
		protected void onPostExecute(ArrayList<Ingredient> ingredientList) {
			if (activityWeakRef.get() != null
					&& !activityWeakRef.get().isFinishing()) {
				ingredientlist = ingredientList;
				if (ingredientlist != null) {
					if (ingredientlist.size() != 0) {
						shoppingListAdapter = new ShoppingListAdapter(activity,
								ingredientlist,1);
						shoppingListView.setAdapter(shoppingListAdapter);
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
	/**
	 * Listener for clicking on an item.
	 * It adds the item to the bar from the shopping list 
	 * and delete it from the current list.
	 * The shoppingfragment will refresh after each action.
	 * 
	 */
	@Override
	public void onItemClick(AdapterView<?> list, View view, int position,
			long id) {

		Ingredient ingredient = (Ingredient) list.getItemAtPosition(position);

		if (ingredient != null) {
			
			resDAO.setInBar(ingredient.getRessource(), 1);
			resDAO.setInShoppinglist(ingredient.getRessource(), 0);
			shoppingListAdapter.remove(ingredient);
			
			CocktailShoppingFragment cockfrag = new CocktailShoppingFragment();
			
			FragmentManager fm = getActivity().getSupportFragmentManager();
			fm.beginTransaction().replace(R.id.content_frame, cockfrag).commit();
		}

	}

}
