package at.fhjoanneum.android.sqlite.fragment;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import at.fhjoanneum.android.sqlite.PhotoCameraGalleryActivity;
import at.fhjoanneum.android.sqlite.R;
import at.fhjoanneum.android.sqlite.adapter.IngredientListAdapter;
import at.fhjoanneum.android.sqlite.data.Cocktail;
import at.fhjoanneum.android.sqlite.data.Ingredient;
import at.fhjoanneum.android.sqlite.db.CocktailDAO;
import at.fhjoanneum.android.sqlite.db.IngredientDAO;

/**
 * This fragment shows the details of a cocktail. It shows a picture of the
 * cocktail, name, rating, all the ingredient, recipe and the notes.
 * 
 * @author Manuela Mayer
 * 
 */
public class CocktailDetailFragment extends Fragment implements
		OnRatingBarChangeListener {

	public static final String ARG_ITEM_ID = "cocktail_detail_fragment";

	private TextView cocktailNameTxt;
	private TextView cocktailRecipieTxt;
	private TextView cocktailNoteTxt;
	private CheckBox cocktailAlcoholCb;
	private CheckBox cocktailMixableCb;
	private RatingBar cocktailRating;
	private Button btShoppingList;
	private Button btChangeNote;
	private Button btChangePic;
	private ListView ingredientListView;
	private ImageView cocktailPicture;

	private Activity activity;
	private Cocktail cocktail = null;
	private IngredientListAdapter ingredientListAdapter;
	private IngredientDAO ingredientDAO;
	private ArrayList<Ingredient> ingredients;
	private GetIngredientTask task;
	private String search;

	private static final String JPEG_FILE_PREFIX = "co_";
	private static final String JPEG_FILE_SUFFIX = "2.jpg";

	private CocktailDAO cocktailDAO = new CocktailDAO(getActivity());

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		ingredientDAO = new IngredientDAO(activity);
	}

	/**
	 * First it checks the device if it is a smart phone or a table, then the
	 * layout is attached to the fragment and the task methods are called
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View page;
		if (getResources().getBoolean(R.bool.is_smartphone)) {
			page = inflater.inflate(R.layout.fragment_detail_cocktail_handy,
					container, false);
		} else {
			page = inflater.inflate(R.layout.fragment_detail_cocktail,
					container, false);
		}

		findViewsById(page);

		setListeners();

		Bundle bundle = this.getArguments();
		cocktail = bundle.getParcelable("selectedCocktail");
		setCocktail();

		task = new GetIngredientTask(activity);
		task.execute((Void) null);

		return page;
	}

	/**
	 * Listeners for the buttons(addToShoppingList, changeNote and changePic)
	 */
	private void setListeners() {
		btShoppingList.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onAddToShoppingList(v);
			}
		});

		btChangeNote.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onChangeNote(v);
			}
		});

		cocktailRating.setOnRatingBarChangeListener(this);

		btChangePic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onChangePicClicked(v);
			}
		});
	}

	public void onChangePicClicked(View view) {
		Intent intent = new Intent(getActivity(),
				PhotoCameraGalleryActivity.class);
		PhotoCameraGalleryActivity.setCocktail(cocktail);
		startActivity(intent);
	}

	/**
	 * All the variables get checked and attached to the views
	 */
	private void setCocktail() {

		if (cocktail != null) {
			setPic();

			cocktailNameTxt.setText(cocktail.getName());
			search = cocktail.getName();
			cocktailRecipieTxt.setText(cocktail.getRecipie());
			if (!getResources().getBoolean(R.bool.is_smartphone)) {
				cocktailNoteTxt.setText(cocktail.getNote());
			}

			boolean[] checked = { false, false };
			if (cocktail.getAlcohol() > 0) {
				checked[0] = true;
			}

			boolean check = true;
			ArrayList<Ingredient> ingredients = ingredientDAO
					.getIngredients(cocktail.getName());
			for (Ingredient ingredient : ingredients) {
				if (ingredient.getRessource().getBar() == 0) {
					check = false;
					break;
				}
			}
			if (check) {
				checked[1] = true;
			}

			cocktailAlcoholCb.setEnabled(false);
			cocktailAlcoholCb.setChecked(checked[0]);
			cocktailMixableCb.setEnabled(false);
			cocktailMixableCb.setChecked(checked[1]);

			cocktailRating.setRating(cocktail.getRating());
			cocktailRating.setRating(cocktailDAO.getCocktailyById(
					cocktail.getId()).getRating());

		}
	}

	/**
	 * Sets the cocktail picture. If the user changed the picture manually than
	 * it will show the picture from the user else it will take the picture from
	 * the res folder
	 */
	private void setPic() {
		String picName = JPEG_FILE_PREFIX
				+ cocktail.getName().toLowerCase().replace(" ", "")
						.replace("-", "");

		String root = Environment.getExternalStorageDirectory().toString();
		File imageFile = new File(root + "/DCIM/CocktailClassics/" + picName
				+ JPEG_FILE_SUFFIX);

		if (imageFile.exists()) {
			Bitmap myBitmap = BitmapFactory.decodeFile(imageFile
					.getAbsolutePath());
			cocktailPicture.setImageBitmap(myBitmap);

		} else {
			int imageRessourceID = getActivity().getResources().getIdentifier(
					picName, "drawable", getActivity().getPackageName());
			cocktailPicture.setImageResource(imageRessourceID);
		}
	}

	/**
	 * variables get attached to the views
	 */
	private void findViewsById(View rootView) {
		cocktailNameTxt = (TextView) rootView
				.findViewById(R.id.txt_detail_name);
		cocktailRecipieTxt = (TextView) rootView
				.findViewById(R.id.txt_detail_recipie);
		cocktailNoteTxt = (TextView) rootView
				.findViewById(R.id.txt_detail_note);

		btShoppingList = (Button) rootView
				.findViewById(R.id.bt_addToShoppingList);
		btChangeNote = (Button) rootView.findViewById(R.id.bt_changeCocktail);
		btChangePic = (Button) rootView.findViewById(R.id.bt_changePic);

		cocktailAlcoholCb = (CheckBox) rootView
				.findViewById(R.id.cb_detail_alcohol);
		cocktailMixableCb = (CheckBox) rootView
				.findViewById(R.id.cb_detail_mixable);

		ingredientListView = (ListView) rootView
				.findViewById(R.id.detail_Ingredient);
		cocktailPicture = (ImageView) rootView.findViewById(R.id.cocktailPic);
		cocktailRating = (RatingBar) rootView.findViewById(R.id.ratingBar1);
	}

	/**
	 * Saves the current value of the rating
	 */
	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromTouch) {
		final float currentRating = ratingBar.getRating();
		cocktailDAO.setRating(cocktail, (int) currentRating);
	}

	/**
	 * open the dialog if the button is clicked
	 * 
	 * @param view
	 *            Current view
	 */
	public void onAddToShoppingList(View view) {
		Bundle arguments = new Bundle();
		arguments.putParcelable("selectedCocktail", cocktail);
		CocktailInShoppingListFragment cocktailInShoppingListFragment = new CocktailInShoppingListFragment();
		cocktailInShoppingListFragment.setArguments(arguments);
		cocktailInShoppingListFragment.show(getFragmentManager(),
				CocktailInShoppingListFragment.ARG_ITEM_ID);
	}

	/**
	 * Open the dialog if the button is clicked
	 * 
	 * @param view
	 *            Current view
	 */
	public void onChangeNote(View view) {
		Bundle arguments = new Bundle();
		arguments.putParcelable("selectedCocktail", cocktail);
		CocktailChangeNoteFragment cocktailChangeNoteFragment = new CocktailChangeNoteFragment();
		cocktailChangeNoteFragment.setArguments(arguments);
		cocktailChangeNoteFragment.show(getFragmentManager(),
				CocktailInShoppingListFragment.ARG_ITEM_ID);
	}

	/**
	 * This class is responsible for getting the list of ingredients out of the
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
		 * Gets a list ingredients for a cocktail from a select statement in the
		 * class ingredientDAO
		 */
		@Override
		protected ArrayList<Ingredient> doInBackground(Void... arg0) {

			ArrayList<Ingredient> ingredientList = ingredientDAO
					.getIngredients(search);
			return ingredientList;
		}

		/**
		 * The ingredient list added to the ingredientListAdapter which inserts
		 * them into List view items
		 */
		@Override
		protected void onPostExecute(ArrayList<Ingredient> ingredientList) {
			if (activityWeakRef.get() != null
					&& !activityWeakRef.get().isFinishing()) {
				ingredients = ingredientList;
				if (ingredientList != null) {
					if (ingredientList.size() != 0) {
						ingredientListAdapter = new IngredientListAdapter(
								activity, ingredientList, cocktail);
						ingredientListView.setAdapter(ingredientListAdapter);
					} else {
						Toast.makeText(activity, "Keine Zutaten gefunden",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		}
	}

	/*
	 * This method is invoked from MainActivity onFinishDialog() method. It is
	 * called from CustomCocktailDialogFragment when an cocktail record is
	 * updated. This is used for communicating between fragments.
	 */
	public void updateView() {
		task = new GetIngredientTask(activity);
		task.execute((Void) null);
	}

	@Override
	public void onResume() {
		getActivity().setTitle(R.string.app_name);
		getActivity().getActionBar().setTitle(R.string.detail_view_header);
		setPic();
		super.onResume();
	}

	private void onBackPressed() {
		Log.d("-----", "onBackstack");

	}
}
