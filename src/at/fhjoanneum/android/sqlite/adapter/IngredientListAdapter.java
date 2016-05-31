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
import at.fhjoanneum.android.sqlite.data.Cocktail;
import at.fhjoanneum.android.sqlite.data.Ingredient;

/**
 * This is the adapter class for the ingredient list. Here it's defined which
 * layout will be used for the fragment and it change the layout if the device
 * is a phone or if it is a tablet. It creates a list of ingredients and
 * connects the pictures and text fields with the ingredients.
 * @author Manuela Mayer
 * 
 */
public class IngredientListAdapter extends ArrayAdapter<Ingredient> {

	private Context context;
	private List<Ingredient> ingredients;
	/**
	 * IngredientListAdapter constructor
	 * 
	 * @param context type Context (current state of the application)
	 * @param ingredients type List ( list of all ingredients)
	 * @param cocktail type Cocktail (instance of Cocktail class)
	 */
	public IngredientListAdapter(Context context, List<Ingredient> ingredients,
			Cocktail cocktail) {
		super(context, R.layout.list_item, ingredients);
		this.context = context;
		this.ingredients = ingredients;
	}

	private class ViewHolder {
		TextView detailIngredientNameTxt;
		TextView detailIngredientAmountTxt;
		ImageView detailIngredientAvalabilityIv;
		ImageView detailIngredientAvalabilityIv2;

	}

	@Override
	public int getCount() {
		return ingredients.size();
	}

	@Override
	public Ingredient getItem(int position) {
		return ingredients.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	/**
	 * Connects the text fields and the pictures with the layouts. If the
	 * variable is_smartphone is true than it choose the phone layout otherwise
	 * it choose the tablet layout. The icons "inBar" or "inShoppinglist" are
	 * set if the ingredient is already in the bar or in the shopping list
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			if (context.getResources().getBoolean(R.bool.is_smartphone)) {
				convertView = inflater.inflate(
						R.layout.detail_ingredient_item_handy, null);
			} else {
				convertView = inflater.inflate(R.layout.detail_ingredient_item,
						null);
			}

			holder = new ViewHolder();

			holder.detailIngredientNameTxt = (TextView) convertView
					.findViewById(R.id.txt_detail_ingredient_name);
			holder.detailIngredientAmountTxt = (TextView) convertView
					.findViewById(R.id.txt_detail_ingredient_amount);
			holder.detailIngredientAvalabilityIv = (ImageView) convertView
					.findViewById(R.id.iv_detail_ingredient_image);
			holder.detailIngredientAvalabilityIv2 = (ImageView) convertView
					.findViewById(R.id.iv_detail_ingredient_image2);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Ingredient ingredient = (Ingredient) getItem(position);
		holder.detailIngredientNameTxt.setText(ingredient.getRessource()
				.getName());
		holder.detailIngredientAmountTxt.setText(String.valueOf(ingredient
				.getAmount()) + ingredient.getUnit());

		holder.detailIngredientAvalabilityIv2.setAlpha(0f);
		holder.detailIngredientAvalabilityIv.setAlpha(0f);

		if (ingredient.getRessource().getBar() == 1) {
			holder.detailIngredientAvalabilityIv.setAlpha(255f);
			holder.detailIngredientAvalabilityIv
					.setImageResource(R.drawable.ic_bar32);
		}
		if (ingredient.getRessource().getInShoppingList() == 1) {
			holder.detailIngredientAvalabilityIv2.setAlpha(255f);
			holder.detailIngredientAvalabilityIv2
					.setImageResource(R.drawable.ic_shoppingcart32);
		}

		return convertView;
	}

	/**
	 * Creates one instance of the ingredient
	 */
	@Override
	public void add(Ingredient ingredient) {
		ingredients.add(ingredient);
		notifyDataSetChanged();
		super.add(ingredient);
	}

	/**
	 * Removes one instance of the ingredient
	 */
	@Override
	public void remove(Ingredient ingredient) {
		ingredients.remove(ingredient);
		notifyDataSetChanged();
		super.remove(ingredient);
	}

}
