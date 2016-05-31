package at.fhjoanneum.android.sqlite.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import at.fhjoanneum.android.sqlite.R;
/**
 * Simple fragment that loads and manages the QuitDialog of the app.
 * 
 * @author Sebastian Kahmann
 */
public class QuitDialogFragment extends DialogFragment {
	
	private Activity activity;
	private Button btnJa;
	private Button btnNein;
	private DialogFragment dialog = this;
	public static final String ARG_ITEM_ID = "quit_dialog_fragment";
	
	
	
	
	public interface QuitDialogFragmentListener {
		void onFinishDialog();
	}

	public QuitDialogFragment() {

	}
	
	/**
	 * In this method all views will be loaded and listeners will be set.
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		activity = getActivity();
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View customDialogView = inflater.inflate(R.layout.quit_dialog_layout, null);
		builder.setView(customDialogView);
		AlertDialog alertDialog = builder.create();
		alertDialog.setCanceledOnTouchOutside(false);
		
		btnJa = (Button)	customDialogView.findViewById(R.id.quit_button_ja);
		btnNein = (Button) customDialogView.findViewById(R.id.quit_button_nein);
		
		btnJa.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.finish();
			}
		});
		btnNein.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		

		return alertDialog;
	}

}
