package jeese.helpme.photoUtil;

import java.io.Serializable;
import java.util.List;

import jeese.helpme.R;
import jeese.helpme.help.SendLifeHelpActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

public class ChoosePicActivity extends Activity {
	// ArrayList<Entity> dataList;//����װ������Դ���б�
	List<ImageBucket> dataList;
	GridView gridView;
	ImageBucketAdapter adapter;// �Զ����������
	AlbumHelper helper;
	public static final String EXTRA_IMAGE_LIST = "imagelist";
	public static Bitmap bimap;
	private Button cancelChooseBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_bucket);

		/**
		 * ȡ���x�����ԣ����ذl������������
		 */
		cancelChooseBtn=(Button) findViewById(R.id.cancel_choose_album);
		cancelChooseBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ChoosePicActivity.this, SendLifeHelpActivity.class);
				startActivity(intent);
			}
		});

		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());
		initData();
		initView();
	}

	/**
	 * ��ʼ������
	 */
	private void initData() {
		dataList = helper.getImagesBucketList(false);	
		bimap=BitmapFactory.decodeResource(
				getResources(),
				R.drawable.icon_addpic_unfocused);
	}

	/**
	 * ��ʼ��view��ͼ
	 */
	private void initView() {
		gridView = (GridView) findViewById(R.id.pic_bucket_gridview);
		adapter = new ImageBucketAdapter(ChoosePicActivity.this, dataList);
		gridView.setAdapter(adapter);//�����m����

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				/**
				 * ����position���������Ի�ø�GridView����View��󶨵�ʵ���࣬Ȼ���������isSelected״̬��
				 * ���ж��Ƿ���ʾѡ��Ч���� ����ѡ��Ч���Ĺ��������������Ĵ����л���˵��
				 */
				// if(dataList.get(position).isSelected()){
				// dataList.get(position).setSelected(false);
				// }else{
				// dataList.get(position).setSelected(true);
				// }
				/**
				 * ֪ͨ���������󶨵����ݷ����˸ı䣬Ӧ��ˢ����ͼ
				 */
				// adapter.notifyDataSetChanged();
				Intent intent = new Intent(ChoosePicActivity.this,
						ImageGridActivity.class);//���һ��album����ת������������ͼƬ�б�
				intent.putExtra(ChoosePicActivity.EXTRA_IMAGE_LIST,
						(Serializable) dataList.get(position).imageList);
				startActivity(intent);
				finish();
			}
		});
	}
}
