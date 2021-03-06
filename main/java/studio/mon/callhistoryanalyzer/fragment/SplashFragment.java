package studio.mon.callhistoryanalyzer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import studio.mon.callhistoryanalyzer.core.CoreActivity;
import studio.mon.callhistoryanalyzer.core.CoreFragment;
import studio.mon.callhistoryanalyzer.R;

public class SplashFragment extends CoreFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_splash, container, false);
		initViews(view);
		initModels();
		initAnimations();
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1500);
					mFragmentManager.beginTransaction().replace(R.id.canvas, CanvasFragment.getInstance(mActivity)).commit();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		return view;
	}
	@Override
	public void onClick(View view) {
		switch(view.getId()) {

		}
	}
	
	protected void initModels() {

	}

	@Override
	protected void initViews(View v) {

	}

	@Override
	protected void initAnimations() {
		
	}
	
	public static final long serialVersionUID = 6036846677812555352L;
	
	public static CoreActivity mActivity;
	public static SplashFragment mInstance;
	public static SplashFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new SplashFragment();
		}
		mActivity = activity;
		return mInstance;
	}
	public static SplashFragment getInstance() {
		if (mInstance == null) {
			mInstance = new SplashFragment();
		}
		return mInstance;
	}
	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		
	}
}
