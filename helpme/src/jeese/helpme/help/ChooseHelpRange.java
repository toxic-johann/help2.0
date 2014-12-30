package jeese.helpme.help;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jeese.helpme.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.ListView;

public class ChooseHelpRange extends Activity {
	private ListView chooseList;
	private List<Map<String, Object>> data=new ArrayList<Map<String, Object>>();
	private ImageView chooseImg;
	private Button cancelChooseBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share_range);
		cancelChooseBtn = (Button) findViewById(R.id.cancel_send_btn);
		chooseList=(ListView) findViewById(R.id.choose_friend_list);
		
		getData();
		SimpleAdapter listAdapter = new SimpleAdapter(this, data, R.layout.item_share_range, 
				new String[]{"name", "img"}, new int[]{R.id.choose_text,R.id.choose_image});
		
		//设置listview的适配器和监听函数
		chooseList.setAdapter(listAdapter);
		chooseList.setOnItemClickListener(listListener);
		
		/**
		 * 取消选择
		 */
		cancelChooseBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	/**
	 * 获得数据
	 */
	public void getData(){
		Map<String,Object> map=new HashMap<String, Object>();
		
		map.put("img", R.drawable.orange);
		map.put("name", "公开");
		data.add(map);
		
		map = new HashMap<String, Object>();
		map.put("img", R.drawable.orange);
		map.put("name", "亲友");
		data.add(map);
		
		map = new HashMap<String, Object>();
		map.put("img", R.drawable.orange);
		map.put("name","好友圈");
		data.add(map);
	}
	
	
	/**
	 * 设置listview中item的点击事件监听
	 */
	OnItemClickListener listListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			chooseImg=(ImageView) arg1.findViewById(R.id.choose_image);
			chooseImg.setImageDrawable(getResources().getDrawable(R.drawable.deep_orange));
			//调用setResult函数，回传数据
			Bundle bundle = new Bundle();
			bundle.putString("group", data.get(position).get("name").toString());
			Intent intent = new Intent(ChooseHelpRange.this, SendLifeHelpActivity.class);
			intent.putExtras(bundle);
			setResult(android.app.Activity.RESULT_OK, intent);
			finish();
		}
	};
}
