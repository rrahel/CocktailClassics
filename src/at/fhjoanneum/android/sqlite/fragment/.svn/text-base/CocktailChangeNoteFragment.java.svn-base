package at.fhjoanneum.android.sqlite.fragment;

import java.io.File;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import at.fhjoanneum.android.sqlite.R;
import at.fhjoanneum.android.sqlite.data.Cocktail;
import at.fhjoanneum.android.sqlite.db.CocktailDAO;

/**
 * This is a dialog fragment where the user can add or change notes about the cocktails
 * 
 * @author Manuela Mayer
 * 
 */
public class CocktailChangeNoteFragment extends DialogFragment {

	private ImageView ivCocktailImage;
	private EditText etNote;
	private Button btOk;
	private Button btCancel;

	private Cocktail cocktail;
	private CocktailDAO cocktailDAO;
	private DialogFragment dialog = this;

	public static final String ARG_ITEM_ID = "cocktail_change_note_fragment";

	private static final String JPEG_FILE_PREFIX = "co_";
	private static final String JPEG_FILE_SUFFIX = "2.jpg";

	public interface CocktailChangeNoteFragmentListener {
		void onFinishDialog();
	}

	public CocktailChangeNoteFragment() {

	}

	/**
	 * Attach the layout to the class and the variables are attached to the
	 * views. Sets the listeners for the buttons.
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		cocktailDAO = new CocktailDAO(getActivity());

		Bundle bundle = this.getArguments();
		cocktail = bundle.getParcelable("selectedCocktail");

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View customDialogView = inflater.inflate(R.layout.dialog_change_note,
				null);
		builder.setView(customDialogView);

		findViewsById(customDialogView);

		etNote.setText(cocktail.getNote());

		setPic();

		btOk = (Button) customDialogView.findViewById(R.id.button_ok);

		btOk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onOkClicked();
			}
		});

		btCancel = (Button) customDialogView.findViewById(R.id.button_cancel);

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
			ivCocktailImage.setImageBitmap(myBitmap);

		} else {
			int imageRessourceID = getActivity().getResources().getIdentifier(
					picName, "drawable", getActivity().getPackageName());
			ivCocktailImage.setImageResource(imageRessourceID);
		}
	}

	private void findViewsById(View v) {
		ivCocktailImage = (ImageView) v.findViewById(R.id.cocktailPic);
		etNote = (EditText) v.findViewById(R.id.etxt_name);
	}

	/**
	 * Listener for the save button. It writes the the note to the detail
	 * fragment
	 */
	private void onOkClicked() {
		cocktailDAO.setNote(cocktail, etNote.getText().toString());
		cocktail.setNote(etNote.getText().toString());

		Bundle arguments = new Bundle();
		arguments.putParcelable("selectedCocktail", cocktail);
		CocktailDetailFragment cocktailDetailFragment = new CocktailDetailFragment();
		cocktailDetailFragment.setArguments(arguments);
		getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, cocktailDetailFragment).commit();

		dialog.dismiss();
	}

}
