package jeese.helpme.headset;

import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;

public class HeadSetHelper {

	private static HeadSetHelper headSetHelper;
	
	private OnHeadSetListener headSetListener = null;
	
	public static HeadSetHelper getInstance(){
		if(headSetHelper == null){
			headSetHelper = new HeadSetHelper();
		}
		return headSetHelper;
	}
	
	/**
	 * ���ö�������˫�������ӿ�
	 * ������openǰ���ô˽ӿڣ�����������Ч
	 * @param headSetListener
	 */
	public void setOnHeadSetListener(OnHeadSetListener headSetListener){
		this.headSetListener = headSetListener;
	}
	
	/**
	 * ���������߿ؼ���,
	 * ����������ýӿڼ���֮���ٵ��ô˷���������ӿ���Ч
	 * @param context
	 */
	public void open(Context context){
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		ComponentName name = new ComponentName(context.getPackageName(),
				HeadSetReceiver.class.getName());
		audioManager.registerMediaButtonEventReceiver(name);
	}
	/**
	 * �رն����߿ؼ���
	 * @param context
	 */
	public void close(Context context){
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		ComponentName name = new ComponentName(context.getPackageName(),
				HeadSetReceiver.class.getName());
		audioManager.unregisterMediaButtonEventReceiver(name);
	}
	/**
	 * ɾ����������˫�������ӿ�
	 */
	public void delHeadSetListener()
	{
		this.headSetListener=null;
	}
	
	/**
	 * ��ȡ��������˫���ӿ�
	 * @return
	 */
	protected OnHeadSetListener getOnHeadSetListener() {
		return headSetListener;
	}
	
	/**
	 * ������ť��˫������
	 * @author 
	 *
	 */
	public interface OnHeadSetListener{

		/**
		 * �����ڰ���δ������˽ӿ������̣߳����Է���ʹ��
		 */
		public void onFiveClick();
	}
}
