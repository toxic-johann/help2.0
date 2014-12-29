package jeese.helpme.activity;

import jeese.helpme.R;
import jeese.helpme.discover.Discover_Fragment;
import jeese.helpme.help.Help_Fragment;
import jeese.helpme.home.Home_Fragment;
import jeese.helpme.me.Me_Fragment;
import jeese.helpme.people.People_Fragment;
import jeese.helpme.service.LocationService;
import jeese.helpme.service.MainService;
import jeese.helpme.util.DummyTabContent;
import jeese.helpme.view.SystemBarTintManager;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	TabHost tabHost;
	TabWidget tabWidget;
	LinearLayout bottom_layout;
	int CURRENT_TAB = 3;
	/**
	 * 自定义初始界面 0->home 1->discover 2->help 3->people 4->me
	 */
	int CUSTOM_TAB = 2;
	int firstenter = 0;
	Home_Fragment homeFragment;
	Discover_Fragment discoverFragment;
	Help_Fragment helpFragment;
	People_Fragment peopleFragment;
	Me_Fragment meFragment;
	android.support.v4.app.FragmentTransaction ft;
	RelativeLayout tabIndicator1, tabIndicator2, tabIndicator3, tabIndicator4,
			tabIndicator5;

	TextView tvTab1;
	ImageView ivTab1;
	TextView tvTab2;
	ImageView ivTab2;
	ImageView ivTab3;
	TextView tvTab4;
	ImageView ivTab4;
	TextView tvTab5;
	ImageView ivTab5;

	@TargetApi(19) 
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}

		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.tabtext2);
		
		// 左上角图标是否显示，如果设成false，则没有程序图标，仅仅就个标题
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);	

		// 开启后台服务
		Intent intent = new Intent(this, MainService.class);
		startService(intent);

		// 定位一次
		Intent intent_1 = new Intent(this, LocationService.class);
		startService(intent_1);

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
				helpFragment = (Help_Fragment) fm.findFragmentByTag("help");
				peopleFragment = (People_Fragment) fm
						.findFragmentByTag("people");
				meFragment = (Me_Fragment) fm.findFragmentByTag("me");
				ft = fm.beginTransaction();

				/** 如果存在hide */
				if (homeFragment != null)
					ft.hide(homeFragment);

				if (discoverFragment != null)
					ft.hide(discoverFragment);

				if (helpFragment != null)
					ft.hide(helpFragment);

				if (peopleFragment != null)
					ft.hide(peopleFragment);

				if (meFragment != null)
					ft.hide(meFragment);

				/** 如果当前选项卡是home */
				if (tabId.equalsIgnoreCase("home")) {
					if (firstenter < 1) {
						//ft.add(R.id.realtabcontent, new Discover_Fragment(), "discover");
						firstenter++;
					} else {
						isTabHome();
						CURRENT_TAB = 1;
					}

					/** 如果当前选项卡是discover */
				} else if (tabId.equalsIgnoreCase("discover")) {
					isTabDiscover();
					CURRENT_TAB = 2;

					/** 如果当前选项卡是help */
				} else if (tabId.equalsIgnoreCase("help")) {
					isTabHelp();
					CURRENT_TAB = 3;

					/** 如果当前选项卡是people */
				} else if (tabId.equalsIgnoreCase("people")) {
					isTabPeople();
					CURRENT_TAB = 4;

					/** 如果当前选项卡是me */
				} else if (tabId.equalsIgnoreCase("me")) {
					isTabMe();
					CURRENT_TAB = 5;
				} else {
					switch (CURRENT_TAB) {
					case 1:
						isTabHome();
						break;
					case 2:
						isTabDiscover();
						break;
					case 3:
						isTabHelp();
						break;
					case 4:
						isTabPeople();
						break;
					case 5:
						isTabMe();
						break;
					}

				}
				ft.commit();
			}

		};
		// 设置初始选项卡
		tabHost.setOnTabChangedListener(tabChangeListener);
		initTab();
		tabHost.setCurrentTab(CUSTOM_TAB);
	}

	// 判断当前
	public void isTabHome() {

		if (homeFragment == null) {
			ft.add(R.id.realtabcontent, new Home_Fragment(), "home");
		} else {
			ft.show(homeFragment);
		}
		tvTab1.setTextColor(getResources().getColor(R.color.tabtext2));
		tvTab2.setTextColor(getResources().getColor(R.color.tabtext1));
		tvTab4.setTextColor(getResources().getColor(R.color.tabtext1));
		tvTab5.setTextColor(getResources().getColor(R.color.tabtext1));
	}

	public void isTabDiscover() {

		if (discoverFragment == null) {
			ft.add(R.id.realtabcontent, new Discover_Fragment(), "discover");
		} else {
			ft.show(discoverFragment);
		}
		tvTab1.setTextColor(getResources().getColor(R.color.tabtext1));
		tvTab2.setTextColor(getResources().getColor(R.color.tabtext2));
		tvTab4.setTextColor(getResources().getColor(R.color.tabtext1));
		tvTab5.setTextColor(getResources().getColor(R.color.tabtext1));
	}

	public void isTabHelp() {

		if (helpFragment == null) {
			ft.add(R.id.realtabcontent, new Help_Fragment(), "help");
		} else {
			ft.show(helpFragment);
		}
		tvTab1.setTextColor(getResources().getColor(R.color.tabtext1));
		tvTab2.setTextColor(getResources().getColor(R.color.tabtext1));
		tvTab4.setTextColor(getResources().getColor(R.color.tabtext1));
		tvTab5.setTextColor(getResources().getColor(R.color.tabtext1));
	}

	public void isTabPeople() {

		if (peopleFragment == null) {
			ft.add(R.id.realtabcontent, new People_Fragment(), "people");
		} else {
			ft.show(peopleFragment);
		}
		tvTab1.setTextColor(getResources().getColor(R.color.tabtext1));
		tvTab2.setTextColor(getResources().getColor(R.color.tabtext1));
		tvTab4.setTextColor(getResources().getColor(R.color.tabtext2));
		tvTab5.setTextColor(getResources().getColor(R.color.tabtext1));
	}

	public void isTabMe() {

		if (meFragment == null) {
			ft.add(R.id.realtabcontent, new Me_Fragment(), "me");
		} else {
			ft.show(meFragment);
		}
		tvTab1.setTextColor(getResources().getColor(R.color.tabtext1));
		tvTab2.setTextColor(getResources().getColor(R.color.tabtext1));
		tvTab4.setTextColor(getResources().getColor(R.color.tabtext1));
		tvTab5.setTextColor(getResources().getColor(R.color.tabtext2));
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
		tvTab1 = (TextView) tabIndicator1.getChildAt(2);
		ivTab1 = (ImageView) tabIndicator1.getChildAt(1);
		ivTab1.setBackgroundResource(R.drawable.selector_mood_home);
		tvTab1.setText(R.string.buttom_home);

		tabIndicator2 = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, tw, false);
		tvTab2 = (TextView) tabIndicator2.getChildAt(2);
		ivTab2 = (ImageView) tabIndicator2.getChildAt(1);
		ivTab2.setBackgroundResource(R.drawable.selector_mood_discover);
		tvTab2.setText(R.string.buttom_discover);

		tabIndicator3 = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator_help, tw, false);
		ivTab3 = (ImageView) tabIndicator3.getChildAt(1);
		ivTab3.setBackgroundResource(R.drawable.selector_mood_help);

		tabIndicator4 = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, tw, false);
		tvTab4 = (TextView) tabIndicator4.getChildAt(2);
		ivTab4 = (ImageView) tabIndicator4.getChildAt(1);
		ivTab4.setBackgroundResource(R.drawable.selector_mood_people);
		tvTab4.setText(R.string.buttom_people);

		tabIndicator5 = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, tw, false);
		tvTab5 = (TextView) tabIndicator5.getChildAt(2);
		ivTab5 = (ImageView) tabIndicator5.getChildAt(1);
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

		TabHost.TabSpec tSpecDiscover = tabHost.newTabSpec("discover");
		tSpecDiscover.setIndicator(tabIndicator2);
		tSpecDiscover.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tSpecDiscover);

		TabHost.TabSpec tSpecHelp = tabHost.newTabSpec("help");
		tSpecHelp.setIndicator(tabIndicator3);
		tSpecHelp.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tSpecHelp);

		TabHost.TabSpec tSpecPeople = tabHost.newTabSpec("people");
		tSpecPeople.setIndicator(tabIndicator4);
		tSpecPeople.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tSpecPeople);

		TabHost.TabSpec tSpecMe = tabHost.newTabSpec("me");
		tSpecMe.setIndicator(tabIndicator5);
		tSpecMe.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tSpecMe);

	}

}
