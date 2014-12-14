package jeese.helpme.home;

import java.util.ArrayList;

import com.haarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

import jeese.helpme.R;
import jeese.helpme.view.swiperefreshandload.SwipeRefreshLayout;
import jeese.helpme.view.swiperefreshandload.SwipeRefreshLayout.OnLoadListener;
import jeese.helpme.view.swiperefreshandload.SwipeRefreshLayout.OnRefreshListener;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Home_Fragment extends Fragment implements OnRefreshListener,
		OnLoadListener {
	private View mView;
	private ListView mListView;
	private Home_ListView_Adapter mHomeListViewAdapter;
	private SwipeRefreshLayout mSwipeLayout;

	@SuppressLint("ResourceAsColor")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mView = View.inflate(getActivity(), R.layout.home_fragment, null);

		mSwipeLayout = (SwipeRefreshLayout) mView
				.findViewById(R.id.swipe_container);
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setOnLoadListener(this);
		mSwipeLayout.setColor(R.color.light_blue, R.color.red, R.color.green,
				R.color.orange);
		mSwipeLayout.setMode(SwipeRefreshLayout.Mode.BOTH);
		mSwipeLayout.setLoadNoFull(false);

		mListView = (ListView) mView.findViewById(R.id.home_fragment_listview);

		mHomeListViewAdapter = new Home_ListView_Adapter(getActivity(), null);

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

	public ArrayList<Integer> getItems() {
		ArrayList<Integer> items = new ArrayList<Integer>();
		for (int i = 0; i < 30; i++) {
			items.add(i);
		}
		return items;
	}
	
	public ArrayList<Integer> getnewItems() {
		ArrayList<Integer> items = new ArrayList<Integer>();
		for (int i = 0; i < 3; i++) {
			items.add(i);
		}
		return items;
	}

	@Override
	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mHomeListViewAdapter.addAll(0, getnewItems());
				mSwipeLayout.setRefreshing(false);
				mHomeListViewAdapter.notifyDataSetChanged();
			}
		}, 2000);

	}

	@Override
	public void onLoad() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mHomeListViewAdapter.addAll(getnewItems());
				mSwipeLayout.setLoading(false);
				mHomeListViewAdapter.notifyDataSetChanged();
			}
		}, 1000);

	}

}
