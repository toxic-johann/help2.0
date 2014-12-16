package jeese.helpme.home;

import java.util.ArrayList;

import com.haarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

import jeese.helpme.R;
import jeese.helpme.location.MapActivity;
import jeese.helpme.view.SwipeRefreshLayout;
import jeese.helpme.view.SwipeRefreshLayout.OnLoadListener;
import jeese.helpme.view.SwipeRefreshLayout.OnRefreshListener;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Home_Fragment extends Fragment implements OnRefreshListener,
		OnLoadListener {
	private View mView;
	private ListView mListView;
	private Home_ListView_Adapter mHomeListViewAdapter;
	private SwipeRefreshLayout mSwipeLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mView = View.inflate(getActivity(), R.layout.home_fragment, null);
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
	
	@SuppressLint("ResourceAsColor")
	private void init(){
		mSwipeLayout = (SwipeRefreshLayout) mView
				.findViewById(R.id.swipe_container);
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setOnLoadListener(this);
		mSwipeLayout.setColor(R.color.light_blue, R.color.red, R.color.green,
				R.color.orange);
		mSwipeLayout.setMode(SwipeRefreshLayout.Mode.BOTH);
		mSwipeLayout.setLoadNoFull(false);

		mListView = (ListView) mView.findViewById(R.id.home_fragment_listview);

		mHomeListViewAdapter = new Home_ListView_Adapter(getActivity(), null,
				new ListItemButtonClickListener());

		AnimationAdapter animAdapter = new SwingBottomInAnimationAdapter(
				mHomeListViewAdapter);
		animAdapter.setAbsListView(mListView);
		mListView.setAdapter(animAdapter);

		mListView.setOnItemClickListener(new ListItemClickListener());
		mListView.setOnItemLongClickListener(new ListItemLongClickListener());

		mHomeListViewAdapter.addAll(getItems());

		View listitemview = View.inflate(getActivity(),
				R.layout.home_fragment_listview_item, null);
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

	private final class ListItemButtonClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			for (int i = mListView.getFirstVisiblePosition(); i <= mListView
					.getLastVisiblePosition(); i++) {
				if (v == mListView.getChildAt(
						i - mListView.getFirstVisiblePosition()).findViewById(
						R.id.home_fragment_listview_item_markbutton)) {
					Toast.makeText(getActivity(),
							"Clicked on Mark Action Button of List Item " + i,
							Toast.LENGTH_SHORT).show();
				} else if (v == mListView.getChildAt(
						i - mListView.getFirstVisiblePosition()).findViewById(
						R.id.home_fragment_listview_item_helpbutton)) {
					Toast.makeText(getActivity(),
							"Clicked on Help Action Button of List Item " + i,
							Toast.LENGTH_SHORT).show();
					Intent Intent = new Intent(getActivity(), MapActivity.class);
					startActivity(Intent);
				}
			}
		}
	}

	private final class ListItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent Intent = new Intent(getActivity(), HelpDetailsVisitorActivity.class);
			startActivity(Intent);
		}
	}

	private final class ListItemLongClickListener implements
			OnItemLongClickListener {
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			Toast.makeText(getActivity(),
					"LongClicked on List Item " + position, Toast.LENGTH_SHORT)
					.show();
			return true;
		}

	}

}
