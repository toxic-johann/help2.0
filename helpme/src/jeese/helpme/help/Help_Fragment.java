package jeese.helpme.help;

import jeese.helpme.R;
import jeese.helpme.location.MapPage;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Help_Fragment extends Fragment implements OnClickListener {
	private View mView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mView = View.inflate(getActivity(), R.layout.help_fragment, null);
		init();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup p = (ViewGroup) mView.getParent();
		if (p != null) {
			p.removeAllViewsInLayout();
		}
		return mView;
	}
	
	private void init() {

		ImageButton help_1 = (ImageButton) mView.findViewById(R.id.help_fragment_helpbutton_1);
		help_1.setOnClickListener(this);
		ImageButton help_2 = (ImageButton) mView.findViewById(R.id.help_fragment_helpbutton_2);
		help_2.setOnClickListener(this);
		ImageButton help_3 = (ImageButton) mView.findViewById(R.id.help_fragment_helpbutton_3);
		help_3.setOnClickListener(this);
		ImageButton help_4 = (ImageButton) mView.findViewById(R.id.help_fragment_helpbutton_4);
		help_4.setOnClickListener(this);
		ImageButton help_5 = (ImageButton) mView.findViewById(R.id.help_fragment_helpbutton_5);
		help_5.setOnClickListener(this);
		ImageButton help_6 = (ImageButton) mView.findViewById(R.id.help_fragment_helpbutton_6);
		help_6.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.help_fragment_helpbutton_1:
			Intent gpsIntent = new Intent(getActivity(), SendLifeHelpActivity.class);
			startActivity(gpsIntent);
			break;

		case R.id.help_fragment_helpbutton_2:

			break;

		case R.id.help_fragment_helpbutton_3:

			break;
			
		case R.id.help_fragment_helpbutton_4:

			break;
			
		case R.id.help_fragment_helpbutton_5:

			break;
			
		case R.id.help_fragment_helpbutton_6:

			break;
		}
	}
}
