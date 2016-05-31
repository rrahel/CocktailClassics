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
import at.fhjoanneum.android.sqlite.data.Ressource;

/**
 * This is the adapter class for the bar list. Here it's defined which layout
 * will be used for the fragment and which will be used for the dialogs. It
 * creates a list of resources and connects the pictures and text fields with
 * the resources
 * 
 * @author Andreas Egger
 * 
 */
public class BarListAdapter extends ArrayAdapter<Ressource> {

	private Context context;
	List<Ressource> ressources;
	int site;

	/**
	 * BarListAdapter constructor
	 * 
	 * @param context type Context (current state of the application)
	 * @param ressources type List ( list of all ressources)
	 * @param site type int (0 = tablet; 1=smartphone)
	 */
	public BarListAdapter(Context context, List<Ressource> ressources, int site) {
		super(context, R.layout.bar_item, ressources);
		this.context = context;
		this.ressources = ressources;
		this.site = site;
	}

	private class ViewHolder {
		TextView detailRessourceNameTxt;
		ImageView ressourcePicture;

	}

	@Override
	public int getCount() {
		return ressources.size();
	}

	@Override
	public Ressource getItem(int position) {
		return ressources.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	/**
	 * Connects the text fields and the pictures with the layouts and it change
	 * the layout when the dialog is open through the variable "site" site = 0
	 * means that it's the fragment, site = 1 means that it's the dialog
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (site == 0) {
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.bar_item, null);
				holder = new ViewHolder();

				holder.detailRessourceNameTxt = (TextView) convertView
						.findViewById(R.id.resource_name);
				holder.ressourcePicture = (ImageView) convertView
						.findViewById(R.id.resource_picture);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Ressource ressource = (Ressource) getItem(position);
			holder.detailRessourceNameTxt.setText(ressource.getName().trim());
			int imageRessourceID = context.getResources().getIdentifier(
					"res_"
							+ ressource.getName().toLowerCase()
									.replace(" ", "").replace("-", "")
									.replace("(", "").replace(")", ""),
					"drawable", context.getPackageName());
			holder.ressourcePicture.setImageResource(imageRessourceID);

		}
		if (site == 1) {

			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.bar_change_item, null);
				holder = new ViewHolder();

				holder.detailRessourceNameTxt = (TextView) convertView
						.findViewById(R.id.resource_name_change_bar);
				holder.ressourcePicture = (ImageView) convertView
						.findViewById(R.id.resource_picture_dialog);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Ressource ressource = (Ressource) getItem(position);
			holder.detailRessourceNameTxt.setText(ressource.getName());

			int imageRessourceID = context.getResources().getIdentifier(
					"res_"
							+ ressource.getName().toLowerCase()
									.replace(" ", "").replace("-", "")
									.replace("(", "").replace(")", ""),
					"drawable", context.getPackageName());
			holder.ressourcePicture.setImageResource(imageRessourceID);

		}

		return convertView;
	}

	/**
	 * Creates one instance of the ressource
	 */
	@Override
	public void add(Ressource ressource) {
		ressources.add(ressource);
		notifyDataSetChanged();
		super.add(ressource);
	}

	/**
	 * Removes one instance of the ressource
	 */
	@Override
	public void remove(Ressource ressource) {
		ressources.remove(ressource);
		notifyDataSetChanged();
		super.remove(ressource);
	}

}
