package at.fhjoanneum.android.sqlite.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import at.fhjoanneum.android.sqlite.R;
import at.fhjoanneum.android.sqlite.adapter.CocktailListAdapter;
import at.fhjoanneum.android.sqlite.data.Cocktail;
import at.fhjoanneum.android.sqlite.db.CocktailDAO;
import at.fhjoanneum.android.sqlite.db.IngredientDAO;

/**
 * This fragment shows a list of all cocktail from our database. Through
 * GetCocktailTask class this Fragment gets its list items filled by the
 * CocktailListAdaptor. This fragment has a search field where users can search
 * for cocktail names, category and ingredients Users can sort the list by the
 * name or by the rating.
 * 
 * @author Manuela Mayer, Rrahel Cemi
 * 
 */
public class CocktailListFragment extends Fragment implements
		OnItemClickListener {

	public static final String ARG_ITEM_ID = "cocktail_list";

	private Activity activity;
	private ListView cocktailListView;
	private ArrayList<Cocktail> cocktails;
	private TextView cockName;
	private TextView cockRating;
	private ImageView sortByRating;
	private ImageView sortByName;

	private Boolean nameSort = true;

	private CocktailListAdapter cocktailListAdapter;
	private CocktailDAO cocktailDAO;
	private IngredientDAO ingredientDAO;

	private EditText searchText;
	private String search;

	private GetCocktailTask task;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		cocktailDAO = new CocktailDAO(activity);
		ingredientDAO = new IngredientDAO(activity);
	}

	/**
	 * The layout is attached to the fragment and the task methods are called
	 * and the listeners for sorting the list
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_cocktail_list,
				container, false);
		findViewsById(view);

		task = new GetCocktailTask(activity);
		task.execute((Void) null);

		cocktailListView.setOnItemClickListener(this);

		cockName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!nameSort) {

					sortByName.setVisibility(0);
					sortByRating.setVisibility(255);
					if (search == null) {
						search = "";
					}
					nameSort = true;
					task.onPostExecute(task.filterList());

				}
			}
		});
		cockRating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (nameSort) {

					sortByName.setVisibility(255);
					sortByRating.setVisibility(0);
					if (search == null) {
						search = "";
					}
					nameSort = false;
					task.onPostExecute(task.filterList());
				}
			}
		});

		searchText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				search = searchText.getText().toString();
				task.onPostExecute(task.filterList());

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		return view;
	}

	/**
	 * variables get attached to the views
	 */
	private void findViewsById(View view) {
		cocktailListView = (ListView) view.findViewById(R.id.list_cocktails);
		searchText = (EditText) view.findViewById(R.id.myFilter);
		cockName = (TextView) view.findViewById(R.id.cocktailListNameTextView);
		cockRating = (TextView) view
				.findViewById(R.id.cocktailListRatingTextView);
		sortByName = (ImageView) view.findViewById(R.id.sort_by_name);
		sortByRating = (ImageView) view.findViewById(R.id.sort_by_rating);
	}

	/**
	 * Clicking on a cocktail will open the detail fragment for that cocktail
	 */
	@Override
	public void onItemClick(AdapterView<?> list, View view, int position,
			long id) {
		Cocktail cocktail = (Cocktail) list.getItemAtPosition(position);

		if (cocktail != null) {
			Bundle arguments = new Bundle();
			arguments.putParcelable("selectedCocktail", cocktail);

			CocktailDetailFragment cocktailDetailFragment = new CocktailDetailFragment();
			cocktailDetailFragment.setArguments(arguments);
			FragmentManager frm = getActivity().getSupportFragmentManager();
			frm.beginTransaction()
					.replace(R.id.content_frame, cocktailDetailFragment)
					.addToBackStack(null).commit();
		}
	}

	/**
	 * This class is responsible for getting the list of Cocktails out of the
	 * Database
	 * 
	 */
	public class GetCocktailTask extends
			AsyncTask<Void, Void, ArrayList<Cocktail>> {

		private final WeakReference<Activity> activityWeakRef;

		public GetCocktailTask(Activity context) {
			this.activityWeakRef = new WeakReference<Activity>(context);
		}

		/**
		 * Gets a list of all cocktails from a select statement in the class
		 * CocktailDAO
		 */
		@Override
		protected ArrayList<Cocktail> doInBackground(Void... arg0) {

			ArrayList<Cocktail> cocktailList = cocktailDAO.getCocktails("",
					nameSort);

			return cocktailList;
		}

		/**
		 * Gets a list of filtered cocktails from a select statement in the
		 * class CocktailDAO
		 */
		protected ArrayList<Cocktail> filterList() {

			ArrayList<Cocktail> cocktailList = cocktailDAO
					.getFilteredCocktails(search, nameSort);
			return cocktailList;
		}

		/**
		 * The cocktail list added to the CocktalListAdapter which inserts them
		 * into List view items
		 */
		@Override
		protected void onPostExecute(ArrayList<Cocktail> cocktailList) {
			if (activityWeakRef.get() != null
					&& !activityWeakRef.get().isFinishing()) {
				cocktails = cocktailList;
				if (cocktailList != null) {
					if (cocktailList.size() != 0) {
						cocktailListAdapter = new CocktailListAdapter(activity,
								cocktailList);
						cocktailListView.setAdapter(cocktailListAdapter);
					} else {
						Toast.makeText(activity, "Keine Cocktails gefunden",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		}
	}


	/**
	 * If the application was in the background and we use it again this method
	 * saves the states of the application
	 */
	@Override
	public void onResume() {
		getActivity().setTitle(R.string.app_name);
		getActivity().getActionBar().setTitle(R.string.app_name);
		super.onResume();
	}
}
