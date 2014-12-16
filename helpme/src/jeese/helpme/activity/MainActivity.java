package jeese.helpme.activity;

import jeese.helpme.R;
import jeese.helpme.fragment.Discover_Fragment;
import jeese.helpme.fragment.Me_Fragment;
import jeese.helpme.fragment.People_Fragment;
import jeese.helpme.home.Home_Fragment;
import jeese.helpme.service.MainService;
import jeese.helpme.util.DummyTabContent;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
	int CURRENT_TAB = 0; // ���ó���
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

		// ���Ͻ�ͼ���Ƿ���ʾ��������false����û�г���ͼ�꣬�����͸�����
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);

		// ������̨����
		Intent intent = new Intent(this, MainService.class);
		startService(intent);

		findTabView();
		tabHost.setup();

		/** ���� */
		TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {

				/** ��Ƭ���� */
				android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
				homeFragment = (Home_Fragment) fm.findFragmentByTag("home");
				discoverFragment = (Discover_Fragment) fm
						.findFragmentByTag("discover");
				peopleFragment = (People_Fragment) fm
						.findFragmentByTag("people");
				meFragment = (Me_Fragment) fm.findFragmentByTag("me");
				ft = fm.beginTransaction();

				/** �������Detaches�� */
				if (homeFragment != null)
					ft.detach(homeFragment);

				/** �������Detaches�� */
				if (discoverFragment != null)
					ft.detach(discoverFragment);

				/** �������Detaches�� */
				if (peopleFragment != null)
					ft.detach(peopleFragment);

				/** �������Detaches�� */
				if (meFragment != null)
					ft.detach(meFragment);

				/** �����ǰѡ���home */
				if (tabId.equalsIgnoreCase("home")) {
					isTabHome();
					CURRENT_TAB = 1;

					/** �����ǰѡ���discover */
				} else if (tabId.equalsIgnoreCase("discover")) {
					isTabDiscover();
					CURRENT_TAB = 2;

					/** �����ǰѡ���people */
				} else if (tabId.equalsIgnoreCase("people")) {
					isTabPeople();
					CURRENT_TAB = 3;

					/** �����ǰѡ���me */
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
		// ���ó�ʼѡ�
		tabHost.setCurrentTab(0);
		tabHost.setOnTabChangedListener(tabChangeListener);
		initTab();
		/** ���ó�ʼ������ */
		tabHost.setCurrentTab(0);

	}

	// �жϵ�ǰ
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
	 * �ҵ�Tabhost����
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
	 * ��ʼ��ѡ�
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

		// ���հ�ť�����¼�������dialog
		tabIndicator3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Dialog choose = new Dialog(MainActivity.this,
						R.style.draw_dialog);
				choose.setContentView(R.layout.help_dialog);

				// ���ñ���ģ������
				WindowManager.LayoutParams winlp = choose.getWindow()
						.getAttributes();
				winlp.alpha = 0.9f; // 0.0-1.0
				choose.getWindow().setAttributes(winlp);
				choose.show();// ��ʾ������
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
				Toast.makeText(MainActivity.this, "����ˡ���1", Toast.LENGTH_LONG)
						.show();
				break;
			case 2:
				Toast.makeText(MainActivity.this, "����ˡ���2", Toast.LENGTH_LONG)
						.show();
				break;
			}

		}
	};

}
