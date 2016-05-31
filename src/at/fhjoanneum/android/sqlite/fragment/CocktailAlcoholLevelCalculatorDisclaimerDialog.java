package at.fhjoanneum.android.sqlite.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import at.fhjoanneum.android.sqlite.R;

/**
 * 
 * In this fragment we show a disclaimer for the alcohol level calculator. There is a "finish" button to close the dialog.
 * 
 * @author Andreas Egger
 *
 */
public class CocktailAlcoholLevelCalculatorDisclaimerDialog extends DialogFragment {

	public static final String ARG_ITEM_ID = "alcohol_level_calculator_disclaimer";
	private DialogFragment dialog = this;
	Activity activity;
	
	private Button btFinish;
	
	public interface CocktailAlcoholLevelCalculatorDisclaimerDialogListener {
		void onFinishDialog();
	}

	public CocktailAlcoholLevelCalculatorDisclaimerDialog() {

	}
	
	/**
	 * In the following two methods the layout is attached to the fragment, variables get attached to the views and the button and the button gets a click Listener assigned to it.
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		activity = getActivity();

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View customDialogView = inflater.inflate(R.layout.dialog_alcohol_calculator_disclaimer, null);
		builder.setView(customDialogView);
		
		btFinish = (Button) customDialogView.findViewById(R.id.bt_finish);
		
		setListeners();

		
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

}
