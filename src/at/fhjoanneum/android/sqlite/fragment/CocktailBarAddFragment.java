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
import at.fhjoanneum.android.sqlite.adapter.BarListAdapter;
import at.fhjoanneum.android.sqlite.data.Ressource;
import at.fhjoanneum.android.sqlite.db.RessourceDAO;

/**
 * 
 * This fragment is responsible for displaying the resources that are currently
 * not in your bar. By tapping at one of the resources it gets added
 * automatically to the bar and the bar is automatically refreshed
 * 
 * @author Andreas Egger
 *
 */
public class CocktailBarAddFragment extends DialogFragment implements
		OnItemClickListener {

	public static final String ARG_ITEM_ID = "cocktail_bar_add_fragment";

	Activity activity;
	ListView barListView;
	private ArrayList<Ressource> ressources;

	private DialogFragment dialog = this;

	private BarListAdapter barListAdapter;
	private RessourceDAO ressourceDAO;

	private GetRessourceTask task;

	private Button btFinish;
	private ImageView ivIcon;
	private TextView tvHeader;

	public interface CocktailBarDeleteFragmentListener {
		void onFinishDialog();
	}

	public CocktailBarAddFragment() {

	}

	/**
	 * In the following two methods the layout is attached to the fragment,
	 * variables get attached to the views and Listeners get set to the buttons
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		activity = getActivity();
		ressourceDAO = new RessourceDAO(getActivity());

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View customDialogView = inflater.inflate(R.layout.dialog_change_bar,
				null);
		builder.setView(customDialogView);

		barListView = (ListView) customDialogView
				.findViewById(R.id.list_change_ressources);

		ivIcon = (ImageView) customDialogView.findViewById(R.id.iv_bar_icon);
		ivIcon.setImageResource(R.drawable.ic_add32);

		tvHeader = (TextView) customDialogView
				.findViewById(R.id.bar_change_name);
		tvHeader.setText("In meine Bar soll:");

		btFinish = (Button) customDialogView.findViewById(R.id.bt_finish);

		setListeners();

		task = new GetRessourceTask(activity);
		task.execute((Void) null);

		barListView.setOnItemClickListener(this);

		AlertDialog alertDialog = builder.create();
		alertDialog.setCanceledOnTouchOutside(false);

		return alertDialog;
	}

	private void setListeners() {
		btFinish.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				dialog.dismiss();
			}
		});
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
					.getNotInBarRessources();
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
								ressourceList, 1);
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

	/**
	 * Here we set what happens, when an item is tapped. It gets added to the
	 * bar and is removed from the current list.
	 */
	@Override
	public void onItemClick(AdapterView<?> list, View view, int position,
			long id) {

		Ressource ressource = (Ressource) list.getItemAtPosition(position);

		if (ressource != null) {
			ressourceDAO.setInBar(ressource, 1);
			barListAdapter.remove(ressource);

			CocktailBarFragment barFrag = new CocktailBarFragment();

			FragmentManager fm = getActivity().getSupportFragmentManager();
			fm.beginTransaction().replace(R.id.content_frame, barFrag).commit();
		}

	}

}
