package at.fhjoanneum.android.sqlite.fragment;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import at.fhjoanneum.android.sqlite.R;
import at.fhjoanneum.android.sqlite.data.Cocktail;
import at.fhjoanneum.android.sqlite.data.Ingredient;
import at.fhjoanneum.android.sqlite.data.Ressource;
import at.fhjoanneum.android.sqlite.db.CocktailDAO;
import at.fhjoanneum.android.sqlite.db.IngredientDAO;
import at.fhjoanneum.android.sqlite.db.RessourceDAO;

/**
 * This class is a dialog fragment which is used to ask the user if he wants add
 * all the resources to the shop or just the ones that are not in the bar.
 * 
 * @author Manuela Mayer
 * 
 */
public class CocktailInShoppingListFragment extends DialogFragment {

	private Button btEverything;
	private Button btCompareWithBar;
	private Button btCancel;

	private Cocktail cocktail;
	private DialogFragment dialog = this;

	private CocktailDAO cocktailDAO;
	private RessourceDAO ressourceDAO;
	private IngredientDAO ingredientDAO;

	public static final String ARG_ITEM_ID = "cocktail_addtoshoppinglist_fragment";

	public interface CocktailInShoppingListFragmentListener {
		void onFinishDialog();
	}

	public CocktailInShoppingListFragment() {

	}

	/**
	 * Attach the layout to the class and the variables are attached to the
	 * views also the listeners for the buttons
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		ingredientDAO = new IngredientDAO(getActivity());
		ressourceDAO = new RessourceDAO(getActivity());
		cocktailDAO = new CocktailDAO(getActivity());
		Bundle bundle = this.getArguments();
		cocktail = bundle.getParcelable("selectedCocktail");

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View customDialogView = inflater.inflate(
				R.layout.dialog_add_inshoppinglist, null);
		builder.setView(customDialogView);

		btEverything = (Button) customDialogView
				.findViewById(R.id.bt_everything);

		btEverything.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onEverythingClicked();
			}
		});

		btCompareWithBar = (Button) customDialogView
				.findViewById(R.id.bt_compare);

		btCompareWithBar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onCompareWithBarClicked();
			}
		});

		btCancel = (Button) customDialogView.findViewById(R.id.bt_cancel);

		btCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		AlertDialog alertDialog = builder.create();
		alertDialog.setCanceledOnTouchOutside(false);
		return alertDialog;
	}

	/**
	 * Puts all the ingredients of a cocktail to the shopping list
	 */
	private void onEverythingClicked() {
		ArrayList<Ingredient> ingredients = ingredientDAO
				.getIngredients(cocktail.getName());

		for (Ingredient ingredient : ingredients) {
			Ressource ressource = ingredient.getRessource();
			ressourceDAO.setInShoppinglist(ressource, 1);
		}

		Bundle arguments = new Bundle();
		arguments.putParcelable("selectedCocktail", cocktail);
		CocktailDetailFragment cocktailDetailFragment = new CocktailDetailFragment();
		cocktailDetailFragment.setArguments(arguments);
		getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, cocktailDetailFragment).commit();

		dialog.dismiss();
	}

	/**
	 * Puts just the ingredients that are not in the bar to the shoppinglist
	 */
	private void onCompareWithBarClicked() {
		ArrayList<Ingredient> ingredients = ingredientDAO
				.getIngredients(cocktail.getName());

		for (Ingredient ingredient : ingredients) {
			Ressource ressource = ingredient.getRessource();
			if (ressource.getBar() == 0) {
				ressourceDAO.setInShoppinglist(ressource, 1);
			}
		}

		Bundle arguments = new Bundle();
		arguments.putParcelable("selectedCocktail", cocktail);
		CocktailDetailFragment cocktailDetailFragment = new CocktailDetailFragment();
		cocktailDetailFragment.setArguments(arguments);
		getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, cocktailDetailFragment).commit();

		dialog.dismiss();
	}
}
