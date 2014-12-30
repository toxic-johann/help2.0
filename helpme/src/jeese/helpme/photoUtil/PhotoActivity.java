/*package jeese.helpme.photoUtil;

import java.util.ArrayList;
import java.util.List;
import jeese.helpme.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class PhotoActivity extends Activity {

	private ArrayList<View> listViews = null;
	private ViewPager pager;
	private MyPageAdapter adapter;

	public static List<Bitmap> bitmap = new ArrayList<Bitmap>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		
		//ViewPager的功能就是可以使视图滑动，就像Lanucher左右滑动那样。
		pager = (ViewPager) findViewById(R.id.viewpager);
		//设置viewpager的监听事件
		pager.setOnPageChangeListener(pageChangeListener);
		
		for (int i = 0; i < bitmap.size(); i++) {
			initListViews(bitmap.get(i));
		}
		
		adapter = new MyPageAdapter(listViews);// 构造adapter
		//设置viewpager的适配器和监听事件
		pager.setAdapter(adapter);
	
		Intent intent = getIntent();
		int id = intent.getIntExtra("ID", 0);
		pager.setCurrentItem(id);
	}

	*//**
	 * 在listview中添加图片，初始化listview
	 *//*
	private void initListViews(Bitmap bm) {
		if (listViews == null){
			listViews = new ArrayList<View>();
		}
		ImageView img = new ImageView(this);
		img.setBackgroundColor(0xff000000);
		img.setImageBitmap(bm);
		img.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		listViews.add(img);// 添加view
	}

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		public void onPageSelected(int position) {// 页面选择响应函数
		这个方法有一个参数position，代表哪个页面被选中。当用手指滑动翻页的时候，如果翻动成功了（滑动的距离够长），
		 * 手指抬起来就会立即执行这个方法，position就是当前滑动到的页面。
		 * 如果直接setCurrentItem翻页，那position就和setCurrentItem的参数一致，
		 * 这种情况在onPageScrolled执行方法前就会立即执行。
		 
		}
		public void onPageScrolled(int arg0, float arg1, int arg2) {// 滑动中。。。
		}
		public void onPageScrollStateChanged(int arg0) {// 滑动状态改变
		}
	};
	
	*//**
	 * 在这里一般需要重写PagerAdapter。
	 * ViewPager的适配器是PagerAdapter，它是基类提供适配器来填充页面ViewPager内部
	 *//*
	class MyPageAdapter extends PagerAdapter {
		private ArrayList<View> listViews;// 将要分页显示的View装入数组中  
		private int size;//大小
		
		*//**
		 * 构造函数：将要分页显示的View装入数组中  。参数是我们的页卡，这样比较方便。
		 * @param listViews
		 *//*
		public MyPageAdapter(ArrayList<View> listViews) {	// 初始化viewpager的时候给的一个页面												
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		*//**
		 * 自己写的一个方法用来添加数据
		 * @param listViews
		 *//*
		public void setListViews(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}
		*//**
		 * 返回页卡的数量 
		 *//*
		public int getCount() {
			return size;
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		*//**
		 * 销毁view对象
		 *//*
		public void destroyItem(View arg0, int position, Object object) {
			((ViewPager) arg0).removeView(listViews.get(position % size));//删除页卡
		}

		public void finishUpdate(View arg0) {
		}
		*//**
		 * 这个方法用来实例化页卡,返回view对象
		 *//*
		public Object instantiateItem(View arg0, int position) {
			try {
				//添加页卡 
				((ViewPager) arg0).addView(listViews.get(position % size), 0);
			} catch (Exception e) {
			}
			return listViews.get(position % size);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;//官方提示这样写 
		}
	}
}
*/


package jeese.helpme.photoUtil;

import java.util.ArrayList;
import java.util.List;

import jeese.helpme.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PhotoActivity extends Activity {

	private ArrayList<View> listViews = null;
	private ViewPager pager;
	private MyPageAdapter adapter;
	private int count;

	public List<Bitmap> bmp = new ArrayList<Bitmap>();
	public List<String> drr = new ArrayList<String>();
	public List<String> del = new ArrayList<String>();
	public int max;

	RelativeLayout photo_relativeLayout;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);

		photo_relativeLayout = (RelativeLayout) findViewById(R.id.photo_relativeLayout);
		photo_relativeLayout.setBackgroundColor(0x70000000);

		for (int i = 0; i < Bimp.bmp.size(); i++) {
			bmp.add(Bimp.bmp.get(i));
		}
		for (int i = 0; i < Bimp.drr.size(); i++) {
			drr.add(Bimp.drr.get(i));
		}
		max = Bimp.max;

		Button photo_bt_exit = (Button) findViewById(R.id.photo_bt_exit);
		photo_bt_exit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				finish();
			}
		});
		Button photo_bt_del = (Button) findViewById(R.id.photo_bt_del);
		photo_bt_del.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (listViews.size() == 1) {
					Bimp.bmp.clear();
					Bimp.drr.clear();
					Bimp.max = 0;
					FileUtils.deleteDir();
					finish();
				} else {
					String newStr = drr.get(count).substring( 
							drr.get(count).lastIndexOf("/") + 1,
							drr.get(count).lastIndexOf("."));
					bmp.remove(count);
					drr.remove(count);
					del.add(newStr);
					max--;
					pager.removeAllViews();
					listViews.remove(count);
					adapter.setListViews(listViews);
					adapter.notifyDataSetChanged();
				}
			}
		});
		Button photo_bt_enter = (Button) findViewById(R.id.photo_bt_enter);
		photo_bt_enter.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				Bimp.bmp = bmp;
				Bimp.drr = drr;
				Bimp.max = max;
				for(int i=0;i<del.size();i++){				
					FileUtils.delFile(del.get(i)+".JPEG"); 
				}
				finish();
			}
		});

		pager = (ViewPager) findViewById(R.id.viewpager);
		pager.setOnPageChangeListener(pageChangeListener);
		for (int i = 0; i < bmp.size(); i++) {
			initListViews(bmp.get(i));//
		}

		adapter = new MyPageAdapter(listViews);// 构造adapter
		pager.setAdapter(adapter);// 设置适配器
		Intent intent = getIntent();
		int id = intent.getIntExtra("ID", 0);
		pager.setCurrentItem(id);
	}

	private void initListViews(Bitmap bm) {
		if (listViews == null)
			listViews = new ArrayList<View>();
		ImageView img = new ImageView(this);// 构造textView对象
		img.setBackgroundColor(0xff000000);
		img.setImageBitmap(bm);
		img.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		listViews.add(img);// 添加view
	}

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		public void onPageSelected(int arg0) {// 页面选择响应函数
			count = arg0;
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {// 滑动中。。。

		}

		public void onPageScrollStateChanged(int arg0) {// 滑动状态改变

		}
	};

	class MyPageAdapter extends PagerAdapter {

		private ArrayList<View> listViews;// content

		private int size;// 页数

		public MyPageAdapter(ArrayList<View> listViews) {// 构造函数
															// 初始化viewpager的时候给的一个页面
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public void setListViews(ArrayList<View> listViews) {// 自己写的一个方法用来添加数据
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public int getCount() {// 返回数量
			return size;
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {// 销毁view对象
			((ViewPager) arg0).removeView(listViews.get(arg1 % size));
		}

		public void finishUpdate(View arg0) {
		}

		public Object instantiateItem(View arg0, int arg1) {// 返回view对象
			try {
				((ViewPager) arg0).addView(listViews.get(arg1 % size), 0);

			} catch (Exception e) {
			}
			return listViews.get(arg1 % size);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}
}
