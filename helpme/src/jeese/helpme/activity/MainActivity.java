package jeese.helpme.activity;

import home.Home_Fragment;
import jeese.helpme.R;
import jeese.helpme.fragment.Discover_Fragment;
import jeese.helpme.fragment.Me_Fragment;
import jeese.helpme.fragment.People_Fragment;
import jeese.helpme.util.DummyTabContent;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	TabHost tabHost;
	TabWidget tabWidget;
	LinearLayout bottom_layout;
	int CURRENT_TAB = 0; // 设置常量
	Home_Fragment homeFragment;
	Discover_Fragment discoverFragment;
	People_Fragment peopleFragment;
	Me_Fragment meFragment;
	android.support.v4.app.FragmentTransaction ft;
	RelativeLayout tabIndicator1, tabIndicator2, tabIndicator3, tabIndicator4,
			tabIndicator5;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 //使左上角图标是否显示，如果设成false，则没有程序图标，仅仅就个标题
		ActionBar actionBar = getActionBar();  
		actionBar.setDisplayShowHomeEnabled(false);  
		
		findTabView();
		tabHost.setup();

		/** 监听 */
		TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {

				/** 碎片管理 */
				android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
				homeFragment = (Home_Fragment) fm.findFragmentByTag("home");
				discoverFragment = (Discover_Fragment) fm
						.findFragmentByTag("discover");
				peopleFragment = (People_Fragment) fm
						.findFragmentByTag("people");
				meFragment = (Me_Fragment) fm.findFragmentByTag("me");
				ft = fm.beginTransaction();

				/** 如果存在Detaches掉 */
				if (homeFragment != null)
					ft.detach(homeFragment);

				/** 如果存在Detaches掉 */
				if (discoverFragment != null)
					ft.detach(discoverFragment);

				/** 如果存在Detaches掉 */
				if (peopleFragment != null)
					ft.detach(peopleFragment);

				/** 如果存在Detaches掉 */
				if (meFragment != null)
					ft.detach(meFragment);

				/** 如果当前选项卡是home */
				if (tabId.equalsIgnoreCase("home")) {
					isTabHome();
					CURRENT_TAB = 1;

					/** 如果当前选项卡是discover */
				} else if (tabId.equalsIgnoreCase("discover")) {
					isTabDiscover();
					CURRENT_TAB = 2;

					/** 如果当前选项卡是people */
				} else if (tabId.equalsIgnoreCase("people")) {
					isTabPeople();
					CURRENT_TAB = 3;

					/** 如果当前选项卡是me */
				} else if (tabId.equalsIgnoreCase("me")) {
					isTabMe();
					CURRENT_TAB = 4;
				} else {
					switch (CURRENT_TAB) {
					case 1:
						isTabHome();
						break;
					case 2:
						isTabDiscover();
						break;
					case 3:
						isTabPeople();
						break;
					case 4:
						isTabMe();
						break;
					default:
						isTabHome();
						break;
					}

				}
				ft.commit();
			}

		};
		// 设置初始选项卡
		tabHost.setCurrentTab(0);
		tabHost.setOnTabChangedListener(tabChangeListener);
		initTab();
		/** 设置初始化界面 */
		tabHost.setCurrentTab(0);

	}

	// 判断当前
	public void isTabHome() {

		if (homeFragment == null) {
			ft.add(R.id.realtabcontent, new Home_Fragment(), "home");
		} else {
			ft.attach(homeFragment);
		}
	}

	public void isTabDiscover() {

		if (discoverFragment == null) {
			ft.add(R.id.realtabcontent, new Discover_Fragment(), "discover");
		} else {
			ft.attach(discoverFragment);
		}
	}

	public void isTabPeople() {

		if (peopleFragment == null) {
			ft.add(R.id.realtabcontent, new People_Fragment(), "people");
		} else {
			ft.attach(peopleFragment);
		}
	}

	public void isTabMe() {

		if (meFragment == null) {
			ft.add(R.id.realtabcontent, new Me_Fragment(), "me");
		} else {
			ft.attach(meFragment);
		}
	}

	/**
	 * 找到Tabhost布局
	 */
	public void findTabView() {

		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabWidget = (TabWidget) findViewById(android.R.id.tabs);
		LinearLayout layout = (LinearLayout) tabHost.getChildAt(0);
		TabWidget tw = (TabWidget) layout.getChildAt(1);

		tabIndicator1 = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, tw, false);
		TextView tvTab1 = (TextView) tabIndicator1.getChildAt(1);
		ImageView ivTab1 = (ImageView) tabIndicator1.getChildAt(0);
		ivTab1.setBackgroundResource(R.drawable.selector_mood_home);
		tvTab1.setText(R.string.buttom_home);

		tabIndicator2 = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, tw, false);
		TextView tvTab2 = (TextView) tabIndicator2.getChildAt(1);
		ImageView ivTab2 = (ImageView) tabIndicator2.getChildAt(0);
		ivTab2.setBackgroundResource(R.drawable.selector_mood_discover);
		tvTab2.setText(R.string.buttom_discover);

		tabIndicator3 = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator_help, tw, false);
		ImageView ivTab3 = (ImageView) tabIndicator3.getChildAt(0);
		ivTab3.setBackgroundResource(R.drawable.selector_mood_help);

		tabIndicator4 = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, tw, false);
		TextView tvTab4 = (TextView) tabIndicator4.getChildAt(1);
		ImageView ivTab4 = (ImageView) tabIndicator4.getChildAt(0);
		ivTab4.setBackgroundResource(R.drawable.selector_mood_people);
		tvTab4.setText(R.string.buttom_people);

		tabIndicator5 = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, tw, false);
		TextView tvTab5 = (TextView) tabIndicator5.getChildAt(1);
		ImageView ivTab5 = (ImageView) tabIndicator5.getChildAt(0);
		ivTab5.setBackgroundResource(R.drawable.selector_mood_me);
		tvTab5.setText(R.string.buttom_me);
	}

	/**
	 * 初始化选项卡
	 * 
	 * */
	public void initTab() {

		TabHost.TabSpec tSpecHome = tabHost.newTabSpec("home");
		tSpecHome.setIndicator(tabIndicator1);
		tSpecHome.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tSpecHome);

		TabHost.TabSpec tSpecWall = tabHost.newTabSpec("discover");
		tSpecWall.setIndicator(tabIndicator2);
		tSpecWall.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tSpecWall);

		TabHost.TabSpec tSpecCamera = tabHost.newTabSpec("camera");
		tSpecCamera.setIndicator(tabIndicator3);
		tSpecCamera.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tSpecCamera);

		// 拍照按钮监听事件，弹出dialog
		tabIndicator3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Dialog choose = new Dialog(MainActivity.this,
						R.style.draw_dialog);
				choose.setContentView(R.layout.help_dialog);

				// 设置背景模糊参数
				WindowManager.LayoutParams winlp = choose.getWindow()
						.getAttributes();
				winlp.alpha = 0.9f; // 0.0-1.0
				choose.getWindow().setAttributes(winlp);
				choose.show();// 显示弹出框
			}
		});

		TabHost.TabSpec tSpecPeople = tabHost.newTabSpec("people");
		tSpecPeople.setIndicator(tabIndicator4);
		tSpecPeople.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tSpecPeople);

		TabHost.TabSpec tSpecMe = tabHost.newTabSpec("me");
		tSpecMe.setIndicator(tabIndicator5);
		tSpecMe.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tSpecMe);

	}

	DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case 1:
				Toast.makeText(MainActivity.this, "点击了——1", Toast.LENGTH_LONG)
						.show();
				break;
			case 2:
				Toast.makeText(MainActivity.this, "点击了——2", Toast.LENGTH_LONG)
						.show();
				break;
			}

		}
	};

}
