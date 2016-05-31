package at.fhjoanneum.android.sqlite.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import at.fhjoanneum.android.sqlite.R;
import at.fhjoanneum.android.sqlite.data.Ingredient;

/**
 * This is the adapter class for the shopping list. Here it's defined which
 * layout will be used for the fragment and which will be used for the dialogs.
 * It creates a list of ingredients and connects the pictures and text fields
 * with the ingredients
 * 
 * @author Rrahel Cemi
 */

public class ShoppingListAdapter extends ArrayAdapter<Ingredient> {
	private Context context;
	List<Ingredient> ingredients;
	int site;

	/**
	 * ShoppingListAdapter constructor
	 * 
	 * @param context Type Context (current state of the application)
	 * @param ingredients Type List ( list of all ingredients)
	 * @param site Type int (0 = tablet; 1=smartphone)
	 */
	public ShoppingListAdapter(Context context, List<Ingredient> ingredients,
			int site) {
		super(context, R.layout.shopping_item, ingredients);
		this.context = context;
		this.ingredients = ingredients;
		this.site = site;
	}

	private class ViewHolder {
		TextView shoppingRessource;
		ImageView ressourcePicture;
	}

	/**
	 * Connects the text fields and the pictures with the layouts and it change
	 * the layout when the dialog is open through the variable "site" site = 0
	 * means that it's the fragment, site = 1 means that it's the dialog
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (site == 0) {
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.shopping_item, null);
				holder = new ViewHolder();

				holder.shoppingRessource = (TextView) convertView
						.findViewById(R.id.shopping_item_name);
				holder.ressourcePicture = (ImageView) convertView
						.findViewById(R.id.resource_picture);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Ingredient ingredient = getItem(position);
			holder.shoppingRessource.setText(ingredient.getRessource()
					.getName());
			int imageRessourceID = context.getResources().getIdentifier(
					"res_"
							+ ingredient.getRessource().getName().toLowerCase()
									.replace(" ", "").replace("-", "")
									.replace("(", "").replace(")", ""),
					"drawable", context.getPackageName());
			holder.ressourcePicture.setImageResource(imageRessourceID);
		}

		if (site == 1) {
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.shopping_change_item,
						null);
				holder = new ViewHolder();

				holder.shoppingRessource = (TextView) convertView
						.findViewById(R.id.shopping_item_name_change);
				holder.ressourcePicture = (ImageView) convertView
						.findViewById(R.id.resource_picture);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Ingredient ingredient = getItem(position);
			holder.shoppingRessource.setText(ingredient.getRessource()
					.getName());
			int imageRessourceID = context.getResources().getIdentifier(
					"res_"
							+ ingredient.getRessource().getName().toLowerCase()
									.replace(" ", "").replace("-", "")
									.replace("(", "").replace(")", ""),
					"drawable", context.getPackageName());
			holder.ressourcePicture.setImageResource(imageRessourceID);

		}
		return convertView;
	}

	/**
	 * Removes one instance of the ingredient
	 */

	public void remove(Ingredient ingredient) {
		ingredients.remove(ingredient);
		notifyDataSetChanged();
		super.remove(ingredient);
	}

}
