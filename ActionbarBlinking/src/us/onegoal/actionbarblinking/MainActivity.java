package us.onegoal.actionbarblinking;

import java.util.Locale;

import us.onegoal.actionbarblinking.R.color;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	PagerTitleStrip pagerTitleStrip;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	ActionbarBlinker blinker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//initialize actionbar blinking
		
		blinker = new ActionbarBlinker(this, getResources().getColor(R.color.color_section1), blinker.BLINK_SLOW);
		getActionBar().setBackgroundDrawable(blinker);
		blinker.start();
		
		
		pagerTitleStrip = (PagerTitleStrip) findViewById(R.id.pager_title_strip);
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		//Set on page changed listener
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				
				//BACKGROUND color of the fragment
				String pagerColorName = "color_section" + (position + 2);
				String blinkerColorName = "color_section" + (position + 1);
				Log.i("COLOR_NAME", pagerColorName);
				try {
					Class<color> res = R.color.class;
					
					if (position < 4) {
						//Shows details of next page
						java.lang.reflect.Field pagerField = res.getField(pagerColorName);
						int pagerColorId = pagerField.getInt(null);
						pagerTitleStrip.setBackgroundColor(getResources().getColor(pagerColorId));
					} else {
						//Shows end of pager
						pagerTitleStrip.setBackgroundColor(Color.WHITE);
					}
					
					//Changes color of blinker base on page
					java.lang.reflect.Field blinkerField = res.getField(blinkerColorName);
					int blinkerColorId = blinkerField.getInt(null);
					blinker.setActionBarColor(getResources().getColor(blinkerColorId));
				
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
					Log.e("COLOR_ID_MAINACTIVITY", "Failure to get color id.", e);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					Log.e("COLOR_ID_MAINACTIVITY", "Failure to get color id.", e);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					Log.e("COLOR_ID_MAINACTIVITY", "Failure to get color id.", e);
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 5 total pages.
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);
			case 4:
				return getString(R.string.title_section5).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));

			//BACKGROUND color of the fragment
			String colorName = "color_section" + getArguments().getInt(ARG_SECTION_NUMBER);
			Log.i("COLOR_NAME", colorName);
			
			try {
				Class<color> res = R.color.class;
				java.lang.reflect.Field field = res.getField(colorName);
				int colorId = field.getInt(null);
				rootView.setBackgroundColor(this.getResources().getColor(colorId));
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
				Log.e("COLOR_ID_FRAGMENT", "Failure to get color id.", e);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				Log.e("COLOR_ID_FRAGMENT", "Failure to get color id.", e);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				Log.e("COLOR_ID_FRAGMENT", "Failure to get color id.", e);
			}
			return rootView;
		}
	}

}
