package home;

import java.util.ArrayList;

import com.haarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

import jeese.helpme.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Home_Fragment extends Fragment {
	private View mView;
	private ListView mListView;
	private Home_ListView_Adapter mHomeListViewAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mView = View.inflate(getActivity(), R.layout.home_fragment, null);

		mListView = (ListView) mView.findViewById(R.id.home_fragment_listview);

		mHomeListViewAdapter = new Home_ListView_Adapter(getActivity(),
				null);

		AnimationAdapter animAdapter = new SwingBottomInAnimationAdapter(
				mHomeListViewAdapter);
		animAdapter.setAbsListView(mListView);
		mListView.setAdapter(animAdapter);

		mHomeListViewAdapter.addAll(getItems());
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

	public static ArrayList<Integer> getItems() {
		ArrayList<Integer> items = new ArrayList<Integer>();
		for (int i = 0; i < 100; i++) {
			items.add(i);
		}
		return items;
	}

}
