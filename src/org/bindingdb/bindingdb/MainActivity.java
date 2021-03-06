package org.bindingdb.bindingdb;

import java.util.Locale;


import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements ActionBar.TabListener {


	SectionsPagerAdapter mSectionsPagerAdapter;


	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
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
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			
			switch (position) {
            case 0:

                return new ProteinSearchFragment();

            default:
                // The other sections of the app are dummy placeholders.
                return new PlaceholderFragment();
			}
			 
			
			
//			return PlaceholderFragment.newInstance(position + 1);
		}			

		@Override
		public int getCount() {
			// Show 2 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
	}

	
	public static class ProteinSearchFragment extends Fragment {
		
		EditText text1;
		EditText text2;
		
		//RadioGroup searchMethod;
		//RadioButton searchByName;
		//RadioButton searchByID;
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
			
            final View rootView = inflater.inflate(R.layout.fragment_protein_search, container, false);
            
            
            Button button = (Button)rootView.findViewById(R.id.protein_search_btn);
            Button button2 = (Button)rootView.findViewById(R.id.id_search_btn);
    		text1 = (EditText)rootView.findViewById(R.id.protein_name_search);
    		text2 = (EditText)rootView.findViewById(R.id.uniprot_search);
    		
    		
    		text1.setOnTouchListener(new View.OnTouchListener()
            {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					text2.setText("");
					return false;
				}
            });
    		
    		text2.setOnTouchListener(new View.OnTouchListener()
            {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					text1.setText("");
					return false;
				}
            });
    		
        	//searchByName = (RadioButton) rootView.findViewById(R.id.firstB);
        	//searchByID = (RadioButton) rootView.findViewById(R.id.secondB);
    		
        	//searchMethod = (RadioGroup) rootView.findViewById(R.id.radio_group);

        	
        	
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                	

                	if(!text1.getText().toString().isEmpty())
                	{
                    	Intent intent = new Intent(getActivity(), SearchResult.class);
                    	intent.putExtra("potain",text1.getText().toString());
                    	intent.putExtra("Uni_id","");
                    	startActivity(intent);
                	}
                	else
                	{
                		Toast.makeText(v.getContext(),"Please enter Protein Name!",
	        					Toast.LENGTH_SHORT).show();
                	}

                }
            });
            
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                	

                	if(!text2.getText().toString().isEmpty())
                	{
                    	Intent intent = new Intent(getActivity(), SearchResult.class);
                    	intent.putExtra("potain","");
                    	intent.putExtra("Uni_id",text2.getText().toString());
                    	startActivity(intent);
                	}
                	else
                	{
                		Toast.makeText(v.getContext(),"Please enter UNIPORT ID!",
	        					Toast.LENGTH_SHORT).show();
                	}

                }
            });

//        	searchMethod.setOnCheckedChangeListener(new OnCheckedChangeListener(){  		
//        		@Override
//        		public void onCheckedChanged(RadioGroup group, int checkedId){
//        			
//        			if(checkedId == R.id.firstB)
//        			{
//        				Toast.makeText(rootView.getContext(), "Searching by name selected", Toast.LENGTH_SHORT).show();
//        				text1.setEnabled(true);
//        				text2.setEnabled(false);
//        			}
//        			else if(checkedId == R.id.secondB)
//        			{
//        				Toast.makeText(rootView.getContext(), "Searching by ID selected", Toast.LENGTH_SHORT).show();
//        				text1.setEnabled(false);
//        				text2.setEnabled(true);
//        			}
//        		}
//        	});
            
            return rootView;
		}
	}
	

	public static class PlaceholderFragment extends Fragment {

		EditText text;
		Button but;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_compound_search, container,
					false);
            
			text = (EditText)rootView.findViewById(R.id.compound_name_search);
            but = (Button)rootView.findViewById(R.id.compound_search_btn);
            
            but.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                	

                	if(!text.getText().toString().isEmpty())
                	{
                    	Intent intent = new Intent(getActivity(), CompSearchResult.class);
                    	intent.putExtra("compound",text.getText().toString());
                    	startActivity(intent);
                	}
                	else
                	{
                		Toast.makeText(v.getContext(),"Please enter Compound Name!",
	        					Toast.LENGTH_SHORT).show();
                	}

                }
            });
            
			return rootView;
		}
	}

}
