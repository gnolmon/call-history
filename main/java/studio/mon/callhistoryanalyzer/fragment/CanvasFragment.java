package studio.mon.callhistoryanalyzer.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import studio.mon.callhistoryanalyzer.R;
import studio.mon.callhistoryanalyzer.core.Common;
import studio.mon.callhistoryanalyzer.core.Constants;
import studio.mon.callhistoryanalyzer.core.CoreActivity;
import studio.mon.callhistoryanalyzer.core.CoreFragment;
import studio.mon.callhistoryanalyzer.core.DBHelper;
import studio.mon.callhistoryanalyzer.model.CallAnalyzer;

public class CanvasFragment extends CoreFragment implements View.OnClickListener{

	private LinearLayout tabMissed, tabReceived, tabDialed, tabTotal;
	private LinearLayout mSearchBlock,/* mTitleBlock,*/ idSort;
	private ImageView imGroup, imMatch ,imStadium;
	private TextView mTitle, mTitleBlock;
	private ImageView imMissed, imReceived ,imDialed, imTotal;
	private Spinner spinner;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_canvas, container, false);
		initViews(view);
		initListener();
		initModels();
		initAnimations();

		return view;
	}



	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	}

	public void changeColor(int imgId) {
		if (imgId == imMissed.getId()) {
			imMissed.setImageAlpha(255);
//			imMissed.setBackgroundColor(Color.rgb(105, 166, 160));
		} else {
			imMissed.setImageAlpha(150);
//			imMissed.setBackgroundColor(Color.rgb(255, 255, 255));
		}
		if (imgId == imDialed.getId()) {
			imDialed.setImageAlpha(255);
//			imDialed.setBackgroundColor(Color.rgb(105, 166, 160));
		} else {
			imDialed.setImageAlpha(150);
//			imDialed.setBackgroundColor(Color.rgb(255, 255, 255));
		}
		if (imgId == imReceived.getId()) {
			imReceived.setImageAlpha(255);
//			imReceived.setBackgroundColor(Color.rgb(105, 166, 160));
		} else {
			imReceived.setImageAlpha(150);
//			imReceived.setBackgroundColor(Color.rgb(255, 255, 255));
		}
		if (imgId == imTotal.getId()) {
			imTotal.setImageAlpha(255);
//			imTotal.setBackgroundColor(Color.rgb(105, 166, 160));
		} else {
			//imTotal.setImageAlpha(150);
//			imTotal.setBackgroundColor(Color.rgb(255, 255, 255));
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.tab_missed:
				mPager.setCurrentItem(0);
				changeColor(imMissed.getId());
//				imGroup.setBackgroundResource(R.drawable.team_click);
//				imMatch.setBackgroundResource(R.drawable.match);
//				imStadium.setBackgroundResource(R.drawable.stadium);
				break;
			case R.id.tab_received:
				mPager.setCurrentItem(1);
				changeColor(imReceived.getId());
//				imMatch.setBackgroundResource(R.drawable.match_click);
//				imGroup.setBackgroundResource(R.drawable.team);
//				imStadium.setBackgroundResource(R.drawable.stadium);
				break;
			case R.id.tab_dialed:
				mPager.setCurrentItem(2);
				changeColor(imDialed.getId());
//				imStadium.setBackgroundResource(R.drawable.stadium_click);
//				imMatch.setBackgroundResource(R.drawable.match);
//				imGroup.setBackgroundResource(R.drawable.team);
				break;
			case R.id.tab_total:
				mPager.setCurrentItem(3);
				changeColor(imTotal.getId());
//				imStadium.setBackgroundResource(R.drawable.stadium_click);
//				imMatch.setBackgroundResource(R.drawable.match);
//				imGroup.setBackgroundResource(R.drawable.team);
				break;
		default:
			break;
		}
	}

	public static final int NUM_PAGES = 4;
	public static ViewPager mPager;
	public PagerAdapter mPagerAdapter;

	public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
		}

		@Override
		public CoreFragment getItem(int position) {
			switch (position) {
				case 0:
					return MissedFragment.getInstance(mActivity);
				case 1:
					return ReceivedFragment.getInstance(mActivity);
				case 2:
					return DialedFragment.getInstance(mActivity);
				case 3:
					return TotalFragment.getInstance(mActivity);
			default:
				break;
			}
			return null;
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}

	public void changeTabState(boolean missed, boolean received, boolean dial, boolean total) {
		if(missed){
			changeColor(imMissed.getId());
			mTitle.setText("MISSED CALL");
//			imGroup.setBackgroundResource(R.drawable.team_click);
//			imMatch.setBackgroundResource(R.drawable.match);
//			imStadium.setBackgroundResource(R.drawable.stadium);
		}
		if(received){
			changeColor(imReceived.getId());
			mTitle.setText("RECEIVED CALL");
//			imMatch.setBackgroundResource(R.drawable.match_click);
//			imGroup.setBackgroundResource(R.drawable.team);
//			imStadium.setBackgroundResource(R.drawable.stadium);
		}
		if(dial){
			changeColor(imDialed.getId());
			mTitle.setText("DIAL CALL");
//			imStadium.setBackgroundResource(R.drawable.stadium_click);
//			imGroup.setBackgroundResource(R.drawable.team);
//			imMatch.setBackgroundResource(R.drawable.match);
		}
		if(total){
			changeColor(imTotal.getId());
			mTitle.setText("TOTAL CALL");
//			imStadium.setBackgroundResource(R.drawable.stadium_click);
//			imGroup.setBackgroundResource(R.drawable.team);
//			imMatch.setBackgroundResource(R.drawable.match);
		}
	}

	public void initModels() {
		mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
		mPager.setOffscreenPageLimit(NUM_PAGES);
		mPager.setAdapter(mPagerAdapter);
		mPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				switch (mPager.getCurrentItem()) {
					case 0:
						changeTabState(true, false, false, false);
						break;
					case 1:
						changeTabState(false, true, false, false);
						break;
					case 2:
						changeTabState(false, false, true , false);
						break;
					case 3:
						changeTabState(false, false, false , true);
						break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		
		mPager.setCurrentItem(firstTab);
		changeTabState(true, false, false, false);

	}

	@Override
	protected void initViews(View v) {
		mPager = (ViewPager) v.findViewById(R.id.viewpager);
		tabMissed = (LinearLayout) v.findViewById(R.id.tab_missed);
		tabReceived = (LinearLayout) v.findViewById(R.id.tab_received);
		tabDialed = (LinearLayout) v.findViewById(R.id.tab_dialed);
		tabTotal = (LinearLayout) v.findViewById(R.id.tab_total);
		mTitle = (TextView) v.findViewById(R.id.fragment_canvas_title);
		//mTitleBlock = (LinearLayout) v.findViewById(R.id.fragment_canvas_title_block);
		imMissed = (ImageView) v.findViewById(R.id.imMissed);
		imReceived = (ImageView) v.findViewById(R.id.imReceived);
		imDialed = (ImageView) v.findViewById(R.id.imDialed);
		imTotal = (ImageView) v.findViewById(R.id.imTotal);

		spinner = (Spinner) v.findViewById(R.id.spinner);

	}


	@Override
	protected void initListener() {
		tabMissed.setOnClickListener(this);
		tabReceived.setOnClickListener(this);
		tabDialed.setOnClickListener(this);
		tabTotal.setOnClickListener(this);

		List<String> spinnerList = Arrays.asList("Refresh", "View date", "View month", "Clear", "");
		ArrayAdapter<String> spinnerAdapter =
				new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, spinnerList){
					@Override
					public int getCount() {
						return(4);
					}
				};
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(spinnerAdapter);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (view != null) ((TextView) view).setText(null);
				switch (mPager.getCurrentItem()) {
					case 0:
						Log.i("Count1:",  "" + MissedFragment.missedCallList.size());
						Common.spinnerClicked(position, mContext, MissedFragment.missedCallList, MissedFragment.adapter, Constants.MISSED_CALL);
						spinner.setSelection(4);
						break;
					case 1:
						Common.spinnerClicked(position, mContext, ReceivedFragment.receivedCallList, ReceivedFragment.adapter, Constants.RECEIVED_CALL);
						spinner.setSelection(4);
						break;
					case 2:
						Common.spinnerClicked(position, mContext, DialedFragment.dialedCallList, DialedFragment.adapter, Constants.DIALED_CALL);
						spinner.setSelection(4);
						break;
					case 3:
						switch (position){
							case 0:
								refreshTotal();
								break;
							case 1:
								showTotalDialog(Constants.DATE_TYPE, mContext);
								break;
							case 2:
								showTotalDialog(Constants.MONTH_TYPE, mContext);
								break;
							case 3:
								clear();
								break;
							}
							spinner.setSelection(4);
						break;
					default:
						break;
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	private List<CallAnalyzer> listMissedCall, listReceivedCall, listDialedCall;

	public void refreshTotal(){
		listMissedCall = Common.refresh(mContext, listMissedCall, Constants.MISSED_CALL);
		listDialedCall = Common.refresh(mContext, listDialedCall, Constants.DIALED_CALL);
		listReceivedCall = Common.refresh(mContext, listReceivedCall, Constants.RECEIVED_CALL);
		setTotalContent();
	}

	public void showTotalDialog(final String timeType, final Context context) {
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog dpd = new DatePickerDialog(context,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						Date date = new Date(year-1900, monthOfYear, dayOfMonth);
						SimpleDateFormat sdf = null;
						if (timeType.equals(Constants.DATE_TYPE)) {
							sdf = new SimpleDateFormat("yyyy-MM-dd");
						} else {
							sdf = new SimpleDateFormat("yyyy-MM");
						}
						String strDate = sdf.format(date);
						listMissedCall.clear();
						listDialedCall.clear();
						listReceivedCall.clear();
						DBHelper db = new DBHelper(context);
						for (CallAnalyzer callAnalyzer : db.getAll()) {
							if (callAnalyzer.getType().equals(Constants.MISSED_CALL)
									&& callAnalyzer.getTime().startsWith(strDate) ) {
								listMissedCall.add(callAnalyzer);
							}
						}
						for (CallAnalyzer callAnalyzer : db.getAll()) {
							if (callAnalyzer.getType().equals(Constants.DIALED_CALL)
									&& callAnalyzer.getTime().startsWith(strDate) ) {
								listDialedCall.add(callAnalyzer);
							}
						}
						for (CallAnalyzer callAnalyzer : db.getAll()) {
							if (callAnalyzer.getType().equals(Constants.RECEIVED_CALL)
									&& callAnalyzer.getTime().startsWith(strDate) ) {
								listReceivedCall.add(callAnalyzer);
							}
						}
						setTotalContent();
					}
				},
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH)
		);
		dpd.show();
	}

	private void setTotalContent(){
		int totalDialed = 0, totalReceived = 0;
		if (listDialedCall.size() > 0){
			for(CallAnalyzer c : listDialedCall){
				totalDialed += Integer.parseInt(c.getDuration().substring(0, (c.getDuration().length() - 1)));
			}
		}
		if (listReceivedCall.size() > 0){
			for(CallAnalyzer c : listReceivedCall){
				totalReceived += Integer.parseInt(c.getDuration().substring(0, (c.getDuration().length() - 1)));
			}
		}
		TotalFragment.getInstance().tvMissed.setText(listMissedCall.size() + " call");
		TotalFragment.getInstance().tvDialed.setText(totalDialed + "s");
		TotalFragment.getInstance().tvReceived.setText(totalReceived + "s");
	}
	public void clear(){
		TotalFragment.getInstance().tvMissed.setText("");
		TotalFragment.getInstance().tvDialed.setText("");
		TotalFragment.getInstance().tvReceived.setText("");
	}



	@Override
	protected void initAnimations() {

	}

	public static int firstTab = 0;
	public static CoreActivity mActivity;
	public static CanvasFragment mInstance;

	public static CanvasFragment getInstance(CoreActivity activity) {
		if (mInstance == null) {
			mInstance = new CanvasFragment();
		}
		mActivity = activity;
		return mInstance;
	}

	public static CanvasFragment getInstance() {
		if (mInstance == null) {
			mInstance = new CanvasFragment();
		}
		return mInstance;
	}

	@Override
	public void onResume(){
	    super.onResume();
	}
}
