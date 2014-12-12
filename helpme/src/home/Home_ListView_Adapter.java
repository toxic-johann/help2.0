package home;

import java.util.ArrayList;

import jeese.helpme.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haarman.listviewanimations.ArrayAdapter;

public class Home_ListView_Adapter extends ArrayAdapter<Integer> {

	private Context mContext;

	public Home_ListView_Adapter(Context context, ArrayList<Integer> items) {
		super(items);
		mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		View view = convertView;
		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(
					R.layout.home_fragment_listview_item, parent, false);

			viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) view
					.findViewById(R.id.home_fragment_listview_item_textview);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		viewHolder.textView.setText("你知道我在世界上最珍视的东西吗？那就是我自己的性格，也就是我自己思想的自由。在这个问题上我都放下刀枪了——也就是说，听任你的改造和影响。你为什么还要计较我一两次无心的过失对你的伤害呢？宽恕吧！原谅吧！我是粗心的人，别和我计较。" + (getItem(position) + 1));

		return view;
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).hashCode();
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	private static class ViewHolder {
		TextView textView;
	}

}
