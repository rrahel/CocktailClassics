package at.fhjoanneum.android.sqlite.adapter;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import at.fhjoanneum.android.sqlite.R;
import at.fhjoanneum.android.sqlite.data.Cocktail;

/**
 * This is the adapter class for the cocktail list. Here it's defined which
 * layout will be used for the fragment and it change the layout if the device
 * is a phone or if it is a tablet. It creates a list of cocktails and connects
 * the pictures and text fields with the cocktails.
 * @author Manuela Mayer
 */
public class CocktailListAdapter extends ArrayAdapter<Cocktail> {

	private Context context;
	List<Cocktail> cocktails;

	private static final String JPEG_FILE_PREFIX = "co_";
	private static final String JPEG_FILE_SUFFIX = "2.jpg";

	/**
	 * CocktailListAdapter constructor
	 * 
	 * @param context type Context (current state of the application)
	 * @param cocktails type List ( list of all cocktails)
	 */
	public CocktailListAdapter(Context context, List<Cocktail> cocktails) {
		super(context, R.layout.list_item, cocktails);
		this.context = context;
		this.cocktails = cocktails;
	}

	private class ViewHolder {
		TextView cocktailNameTxt;
		RatingBar cocktailRating;
		TextView cocktailCategoryTxt;
		ImageView cocktailPicture;
	}

	@Override
	public int getCount() {
		return cocktails.size();
	}

	@Override
	public Cocktail getItem(int position) {
		return cocktails.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	/**
	 * Connects the text fields and the pictures with the layouts. If the
	 * variable is_smartphone is true than it choose the phone layout otherwise
	 * it choose the tablet layout. If the user manually changes the picture
	 * than it automatically picks the user's picture otherwise it picks the
	 * pictures from the res folder.
	 * 
	 */

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			if (context.getResources().getBoolean(R.bool.is_smartphone)) {
				convertView = inflater.inflate(R.layout.list_item_handy, null);
			} else {
				convertView = inflater.inflate(R.layout.list_item, null);
			}

			holder = new ViewHolder();

			holder.cocktailNameTxt = (TextView) convertView
					.findViewById(R.id.txt_cocktail_list_name);
			holder.cocktailRating = (RatingBar) convertView
					.findViewById(R.id.ratingBar1);
			holder.cocktailCategoryTxt = (TextView) convertView
					.findViewById(R.id.txt_cocktail_list_category);
			holder.cocktailPicture = (ImageView) convertView
					.findViewById(R.id.cocktail_list_picture);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Cocktail cocktail = (Cocktail) getItem(position);
		holder.cocktailNameTxt.setText(cocktail.getName());
		holder.cocktailRating.setRating(cocktail.getRating());
		holder.cocktailCategoryTxt.setText(cocktail.getCategory().getName());
		if (context.getResources().getBoolean(R.bool.is_smartphone)) {
			holder.cocktailPicture.setVisibility(0);
			holder.cocktailPicture.getLayoutParams().width = 0;
			holder.cocktailPicture.getLayoutParams().height = 0;
		} else {
			String picName = JPEG_FILE_PREFIX
					+ cocktail.getName().toLowerCase().replace(" ", "")
							.replace("-", "");

			String root = Environment.getExternalStorageDirectory().toString();
			File imageFile = new File(root + "/DCIM/CocktailClassics/"
					+ picName + JPEG_FILE_SUFFIX);
			if (imageFile.exists()) {
				Bitmap myBitmap = BitmapFactory.decodeFile(imageFile
						.getAbsolutePath());
				holder.cocktailPicture.setImageBitmap(myBitmap);

			} else {

				int imageRessourceID = context.getResources().getIdentifier(
						picName, "drawable", context.getPackageName());
				holder.cocktailPicture.setImageResource(imageRessourceID);
			}
		}

		return convertView;
	}

	/**
	 * Creates one instance of the cocktail
	 */
	@Override
	public void add(Cocktail cocktail) {
		cocktails.add(cocktail);
		notifyDataSetChanged();
		super.add(cocktail);
	}

	/**
	 * Removes one instance of the cocktail
	 */
	@Override
	public void remove(Cocktail cocktail) {
		cocktails.remove(cocktail);
		notifyDataSetChanged();
		super.remove(cocktail);
	}
}
