package at.fhjoanneum.android.sqlite.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import at.fhjoanneum.android.sqlite.R;

/**
 * 
 * This fragment is for the alcohol level calculator. You can add your stats
 * (like height, weight and age) in the EditText Fields and you can choose what
 * you drank with the spinners.
 * 
 * @author Andreas Egger
 *
 */
public class CocktailAlcoholLevelCalculator extends Fragment {

	private Spinner drinkSpinner1, drinkSpinner2, drinkSpinner3, drinkSpinner4,
			sexSpinner;
	EditText inputHeight;
	EditText inputWeight;
	EditText inputAge;
	EditText inputAmount1;
	EditText inputAmount2;
	EditText inputAmount3;
	EditText inputAmount4;
	TextView alcoholLevelText;

	private Button calculateBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	/**
	 * In the following two methods the layout is attached to the fragment,
	 * variables get attached to the views and buttons
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_alcohol_calculator,
				container, false);
		findViewsById(view);

		return view;

	}

	private void findViewsById(View view) {
		sexSpinner = (Spinner) view.findViewById(R.id.sex_spinner);
		drinkSpinner1 = (Spinner) view.findViewById(R.id.drink_spinner_1);
		drinkSpinner2 = (Spinner) view.findViewById(R.id.drink_spinner_2);
		drinkSpinner3 = (Spinner) view.findViewById(R.id.drink_spinner_3);
		drinkSpinner4 = (Spinner) view.findViewById(R.id.drink_spinner_4);
		inputWeight = (EditText) view.findViewById(R.id.input_weight);
		inputHeight = (EditText) view.findViewById(R.id.input_height);
		inputAge = (EditText) view.findViewById(R.id.input_age);
		inputAmount1 = (EditText) view.findViewById(R.id.input_amount_1);
		inputAmount2 = (EditText) view.findViewById(R.id.input_amount_2);
		inputAmount3 = (EditText) view.findViewById(R.id.input_amount_3);
		inputAmount4 = (EditText) view.findViewById(R.id.input_amount_4);
		alcoholLevelText = (TextView) view
				.findViewById(R.id.label_alcohol_level);
		calculateBtn = (Button) view.findViewById(R.id.bt_calculate);

		setListeners();
	}

	/**
	 * Here we check if the input of age, height and weight is valid
	 * 
	 */
	private boolean inputValid() {
		// check whether the input is valid
		String patternHeight = "^((\\d\\.\\d+)|\\d)$";
		String patternWeight = "^((\\d+\\.\\d+)|\\d+)$";
		String patternAge = "\\d{2}";

		if (inputHeight.getText().toString().matches(patternWeight)
				&& inputAge.getText().toString().matches(patternAge)
				&& inputWeight.getText().toString().matches(patternWeight)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 
	 * Here we check if the input of the amounts is valid
	 */
	private boolean amountsValid(String matchString) {
		String patternAmount = "\\d+";

		if (matchString.matches(patternAmount)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Here we set the listener for the button
	 */
	public void setListeners() {

		calculateBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				calculateAlcoholLevel(v);

			}

		});

	}

	/**
	 * 
	 * This method is the main method of the fragment. The inputs get inserted
	 * into variables and checked if they are correct. Here also the alcohol
	 * level is calculated with two different formulas (one for women, one for
	 * men). The formulas used are the Watson alcohol level calculation
	 * formulas. Before the alcohol level is finally displayed a disclaimer
	 * dialog fragment is called.
	 * 
	 */
	private void calculateAlcoholLevel(View v) {
		
		Boolean check;
		

		if (inputValid() == true) {
			check = true;
			double alcoholLevel;
			double height = Double
					.parseDouble(inputHeight.getText().toString());
			double weight = Double
					.parseDouble(inputWeight.getText().toString());
			double age = Double.parseDouble(inputAge.getText().toString());
			int amount1 = 0;
			int amount2 = 0;
			int amount3 = 0;
			int amount4 = 0;
			if (!inputAmount1.getText().toString().matches("")) {
				if (amountsValid(inputAmount1.getText().toString())) {
					amount1 = Integer.parseInt(inputAmount1.getText()
							.toString());
				}
			}
			if (!inputAmount2.getText().toString().matches("")) {
				if (amountsValid(inputAmount2.getText().toString())) {
					amount2 = Integer.parseInt(inputAmount2.getText()
							.toString());
				}
			}
			if (!inputAmount3.getText().toString().matches("")) {
				if (amountsValid(inputAmount3.getText().toString())) {
					amount3 = Integer.parseInt(inputAmount3.getText()
							.toString());
				}
			}
			if (!inputAmount4.getText().toString().matches("")) {
				if (amountsValid(inputAmount4.getText().toString())) {
					amount4 = Integer.parseInt(inputAmount4.getText()
							.toString());
				}
			}

			double alcoholAmount1 = amount1
					* getAlcoholAmount(String.valueOf(drinkSpinner1
							.getSelectedItem()));
			double alcoholAmount2 = amount2
					* getAlcoholAmount(String.valueOf(drinkSpinner2
							.getSelectedItem()));
			double alcoholAmount3 = amount3
					* getAlcoholAmount(String.valueOf(drinkSpinner3
							.getSelectedItem()));
			double alcoholAmount4 = amount4
					* getAlcoholAmount(String.valueOf(drinkSpinner4
							.getSelectedItem()));
			double totalAlcoholAmount = alcoholAmount1 + alcoholAmount2
					+ alcoholAmount3 + alcoholAmount4;

			if (String.valueOf(sexSpinner.getSelectedItem()).equals("männlich")) {
				alcoholLevel = ((0.8 * totalAlcoholAmount) / (1.055 * (2.447
						- 0.09516 * age + 0.1074 * height + 0.3362 * weight)));
				alcoholLevelText.setText(String.valueOf(Math
						.round(100.0 * alcoholLevel) / 100.0));

			} else {

				alcoholLevel = ((0.8 * totalAlcoholAmount) / (1.055 * (-2.097
						+ 0.1069 * height + 0.2466 * weight)));
				alcoholLevelText.setText(String.valueOf(Math
						.round(100.0 * alcoholLevel) / 100.0));
			}

		} else {
			Log.e("Alcohol level calculator", "Input is not valid");
			alcoholLevelText.setText("Die Eingaben sind nicht gültig");
			check = false;
		}
		
		if(check==true){
			CocktailAlcoholLevelCalculatorDisclaimerDialog cocktailAlcoholLevelCalculatorDisclaimerDialog = new CocktailAlcoholLevelCalculatorDisclaimerDialog();
			cocktailAlcoholLevelCalculatorDisclaimerDialog.show(
					getFragmentManager(),
					CocktailAlcoholLevelCalculatorDisclaimerDialog.ARG_ITEM_ID);
		}
		

	}

	/**
	 * 
	 * In this method we have a switch case to get the right alcohol amounts
	 * depending on the type of drink chosen from the spinners.
	 */
	private double getAlcoholAmount(String element) {

		double alcoholAmount;
		switch (element) {
		case "0,2l Bier":
			alcoholAmount = 200 * 0.055 * 0.8;
			break;
		case "0,33l Bier":
			alcoholAmount = 330 * 0.055 * 0.8;
			break;
		case "0,5l Bier":
			alcoholAmount = 500 * 0.055 * 0.8;
			break;
		case "0,125l Wein":
			alcoholAmount = 125 * 0.12 * 0.8;
			break;
		case "0,25l Wein":
			alcoholAmount = 250 * 0.12 * 0.8;
			break;
		case "0,02l Likör 20%":
			alcoholAmount = 20 * 0.20 * 0.8;
			break;
		case "0,02l Schnaps 40%":
			alcoholAmount = 20 * 0.2 * 0.8;
			break;
		default:
			alcoholAmount = 0;
			break;
		}
		return alcoholAmount;
	}

	/**
	 * Here we set the titles that are shown in the Actionbar
	 */
	@Override
	public void onResume() {
		getActivity().setTitle(R.string.alc_calc_frag_name);
		getActivity().getActionBar().setTitle(R.string.alc_calc_frag_name);
		super.onResume();
	}

}
