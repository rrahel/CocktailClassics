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
import at.fhjoanneum.android.sqlite.adapter.BarListAdapter;
import at.fhjoanneum.android.sqlite.data.Ressource;
import at.fhjoanneum.android.sqlite.db.RessourceDAO;

/**
 * 
 * The bar Fragment is responsible for displaying the bar overview list. Via the
 * task this Fragment gets its list items filled by the BarListAdapter.
 * 
 * @author Andreas Egger
 *
 */
public class CocktailBarFragment extends Fragment {

	Activity activity;
	ListView barListView;
	private ArrayList<Ressource> ressources;

	private BarListAdapter barListAdapter;
	private RessourceDAO ressourceDAO;

	private GetRessourceTask task;

	private Button btAddRessource;
	private Button btDeleteRessource;

	/**
	 * Here a ressourceDAO Object is created, so that when can later access the
	 * Database
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		ressourceDAO = new RessourceDAO(activity);

	}

	/**
	 * In the following three methods the layout is attached to the fragment,
	 * variables get attached to the views and Listeners get set to the buttons
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_bar, container, false);
		findViewsById(view);
		task = new GetRessourceTask(activity);
		task.execute((Void) null);

		return view;

	}

	private void findViewsById(View rootView) {
		barListView = (ListView) rootView.findViewById(R.id.list_ingredients);
		btDeleteRessource = (Button) rootView
				.findViewById(R.id.bt_delete_ressource);
		btAddRessource = (Button) rootView.findViewById(R.id.bt_add_ressource);
		setListeners();
	}

	private void setListeners() {
		btAddRessource.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				onAddRessource(view);
			}
		});

		btDeleteRessource.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				onDeleteRessource(view);
			}
		});
	}

	/**
	 * In these two methods we set what happens when you tap on one of the
	 * buttons (new Fragments get called, either the Add or Delete Fragment)
	 * 
	 */
	public void onDeleteRessource(View view) {
		CocktailBarDeleteFragment cocktailBarDeleteFragment = new CocktailBarDeleteFragment();
		cocktailBarDeleteFragment.show(getFragmentManager(),
				CocktailBarDeleteFragment.ARG_ITEM_ID);
	}

	public void onAddRessource(View view) {
		CocktailBarAddFragment cocktailBarAddFragment = new CocktailBarAddFragment();
		cocktailBarAddFragment.show(getFragmentManager(),
				CocktailBarAddFragment.ARG_ITEM_ID);
	}

	/**
	 * In this class we define the task that is responsible for getting the list
	 * of resources out of the Database (via ressourceDAO.getInBarRessources())
	 * 
	 */
	public class GetRessourceTask extends
			AsyncTask<Void, Void, ArrayList<Ressource>> {

		private final WeakReference<Activity> activityWeakRef;

		public GetRessourceTask(Activity context) {
			this.activityWeakRef = new WeakReference<Activity>(context);
		}

		@Override
		protected ArrayList<Ressource> doInBackground(Void... arg0) {

			ArrayList<Ressource> ressourceList = ressourceDAO
					.getInBarRessources();
			return ressourceList;
		}

		/**
		 * In this method we add the list of resources, that we received from
		 * the ressourceDAO, to the barListAdapter, which is than inserting it
		 * into the ListView items
		 */
		@Override
		protected void onPostExecute(ArrayList<Ressource> ressourceList) {
			if (activityWeakRef.get() != null
					&& !activityWeakRef.get().isFinishing()) {
				ressources = ressourceList;
				if (ressourceList != null) {
					if (ressourceList.size() != 0) {
						barListAdapter = new BarListAdapter(activity,
								ressourceList, 0);
						barListView.setAdapter(barListAdapter);
					} else {
						Toast.makeText(activity, "Keine Zutaten gefunden",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		}
	}

	public void updateView() {
		task = new GetRessourceTask(activity);
		task.execute((Void) null);
	}

	/**
	 * Here we set the titles that are shown in the Actionbar
	 */
	@Override
	public void onResume() {
		getActivity().setTitle(R.string.bar_frag_name);
		getActivity().getActionBar().setTitle(R.string.bar_frag_name);
		super.onResume();
	}

}
