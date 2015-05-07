package com.thilo.android.creepysounds;

import java.util.Locale;

import com.thilo.soundbored.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TabHost;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	private InterstitialAd interstitial;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));	
		}
		
		// Prepare the Interstitial Ad
				interstitial = new InterstitialAd(MainActivity.this);
				// Insert the Ad Unit ID
				interstitial.setAdUnitId("ca-app-pub-123456789/123456789");
		 
				//Locate the Banner Ad in activity_main.xml
				AdView adView = (AdView) this.findViewById(R.id.adView);
		 
				// Request for Ads
				AdRequest adRequest = new AdRequest.Builder()
		 
				// Add a test device to show Test Ads
				 .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				 .addTestDevice("abc")
						.build();
		 
				// Load ads into Banner Ads
				adView.loadAd(adRequest);
		 
				// Load ads into Interstitial Ads
				interstitial.loadAd(adRequest);
		 
				// Prepare an Interstitial Ad Listener
				interstitial.setAdListener(new AdListener() {
					public void onAdLoaded() {
						// Call displayInterstitial() function
						displayInterstitial();
					}
				});

	}
	
	public void displayInterstitial() {
		// If Ads are loaded, show Interstitial else show nothing.
		if (interstitial.isLoaded()) {
			interstitial.show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new SectionFragment();
			Bundle args = new Bundle();
			args.putInt(SectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return 6;
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
			case 5:
				return getString(R.string.title_section6).toUpperCase(l);
			}
			return null;
		}
	}

	public static class SectionFragment extends Fragment {
		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main,
					container, false);
			ImageButton ibtn = (ImageButton) rootView
					.findViewById(R.id.imgButton);
			ibtn.setSoundEffectsEnabled(false);
			
			switch(getArguments().getInt(ARG_SECTION_NUMBER)) {
			case 1:
				ibtn.setImageResource(R.drawable.catscream);
				break;
			case 2:
				ibtn.setImageResource(R.drawable.ghost);
				break;
			case 3:
				ibtn.setImageResource(R.drawable.iwillkillyou);
				break;
			case 4:
				ibtn.setImageResource(R.drawable.monster);
				break;
			case 5:
				ibtn.setImageResource(R.drawable.sqeakingdoor);
				break;
			case 6:
				ibtn.setImageResource(R.drawable.zombie);
				break;
			}
			
			ibtn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					MediaPlayer mp;
					switch(getArguments().getInt(ARG_SECTION_NUMBER)) {
					case 1:
						mp = MediaPlayer.create(getActivity(), R.raw.catscream);
						break;
					case 2:
						mp = MediaPlayer.create(getActivity(), R.raw.ghost);
						break;
					case 3:
						mp = MediaPlayer.create(getActivity(), R.raw.iwillkillyou);
						break;
					case 4:
						mp = MediaPlayer.create(getActivity(), R.raw.monster);
						break;
					case 5:
						mp = MediaPlayer.create(getActivity(), R.raw.sqeakingdoor);
						break;
					default:
						mp = MediaPlayer.create(getActivity(), R.raw.zombie);
						break;
					}
					mp.start();
				}
			});
			
			return rootView;
		}
	}

}
