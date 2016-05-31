package at.fhjoanneum.android.sqlite.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import at.fhjoanneum.android.sqlite.R;

/**
 * Simple fragment that loads and manages the impressum.
 * 
 * @author Sebastian Kahmann
 */

public class ImpressumFragment extends Fragment {
	private String fhLink = "http://www.fh-joanneum.at";
	private ImageView fhLogo;

	/**
	 * In this method is checked whether the user is using a smartphone or tablet.
	 * The correct layout will load.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (getActivity().getResources().getBoolean(R.bool.is_smartphone)){
			return inflater.inflate(R.layout.fragment_impressum, container, false);
		}
		else{
			return inflater.inflate(R.layout.fragment_impressum_tablet, container, false);
		}
		
	}
	
	/**
	 * In this method the onClick Listener for the FH-banner is set.
	 * it starts an intent and uses the URL to find the FH homepage. 
	 */
	public void onViewCreated(View view, Bundle savedInstanceState) {
		fhLogo = (ImageView) getActivity().findViewById(R.id.impressum_fhlogo);
		fhLogo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(fhLink));
				startActivity(browserIntent);
			}
		});
	};
	
	/**
	 * this method uses the method setTitle from the activity and set the title of the actionbar.
	 */
	@Override
	public void onResume() {
		getActivity().setTitle(R.string.impressum_frag_name);
		getActivity().getActionBar().setTitle(R.string.impressum_frag_name);
		super.onResume();
	}

}
