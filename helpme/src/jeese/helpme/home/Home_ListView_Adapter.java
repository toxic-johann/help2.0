package jeese.helpme.home;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;

import jeese.helpme.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haarman.listviewanimations.ArrayAdapter;

public class Home_ListView_Adapter extends ArrayAdapter<Integer> {

	private Context mContext;
	private FinalBitmap fb;

	public Home_ListView_Adapter(Context context, ArrayList<Integer> items) {
		super(items);
		mContext = context;
		fb = FinalBitmap.create(context);//��ʼ��FinalBitmapģ��
        fb.configLoadingImage(R.drawable.user_head);
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
			viewHolder.headimage = (ImageView) view.findViewById(R.id.home_fragment_listview_item_headimage);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		viewHolder.textView.setText("��֪�����������������ӵĶ������Ǿ������Լ����Ը�Ҳ�������Լ�˼������ɡ�������������Ҷ����µ�ǹ�ˡ���Ҳ����˵��������ĸ����Ӱ�졣��Ϊʲô��Ҫ�ƽ���һ�������ĵĹ�ʧ������˺��أ���ˡ�ɣ�ԭ�°ɣ����Ǵ��ĵ��ˣ�����Ҽƽϡ�" + (getItem(position) + 1));
		fb.display(viewHolder.headimage,"http://imgsrc.baidu.com/forum/w%3D580/sign=12dad1c8b899a9013b355b3e2d970a58/91039313b07eca8068d2343b912397dda044832c.jpg");
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
		ImageView headimage;
	}

}
