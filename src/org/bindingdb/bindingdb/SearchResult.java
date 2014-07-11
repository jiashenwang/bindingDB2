package org.bindingdb.bindingdb;

import java.util.ArrayList;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.text.ClipboardManager;


public class SearchResult extends Activity{


	static ListView listview;
	static String Uni_id;
	static String potain;
	private ProgressBar spinner;
	
	static TextView text;
	
	private Button pre;
	private Button next;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_result);
			
//		spinner = (ProgressBar)findViewById(R.id.progressBar1);
		
		
		Intent i=getIntent();
		Uni_id=i.getStringExtra("Uni_id");
		potain = i.getStringExtra("potain");
		
		if(!Uni_id.equals("") && !potain.equals(""))
			Toast.makeText(this,"Searching-> ID: "+Uni_id+", Protein: "+potain,
				Toast.LENGTH_SHORT).show();
		else if(!Uni_id.equals("") && potain.equals(""))
			Toast.makeText(this,"Searching-> ID: "+Uni_id,
					Toast.LENGTH_SHORT).show();
		else if(Uni_id.equals("") && !potain.equals(""))
			Toast.makeText(this,"Searching-> Protein: "+potain,
					Toast.LENGTH_SHORT).show();

		
		listview = (ListView)findViewById(R.id.result_list);
		
		MyAsyncTaskHelper asyncHelper = new MyAsyncTaskHelper();
		asyncHelper.execute();		
		
		  
		  getActionBar().setDisplayHomeAsUpEnabled(true);

		  
		
		  
		/*
		  if(asyncHelper.getStatus() == AsyncTask.Status.FINISHED)
		{
			pre.setVisibility(View.VISIBLE);
			next.setVisibility(View.VISIBLE);
		}
		*/

	}
	
	private class MyAsyncTaskHelper extends AsyncTask<Void, Void, BarAdapter> {
		private Button pre, next;

		@Override
		protected BarAdapter doInBackground(Void... params) {
			spinner = (ProgressBar)findViewById(R.id.progressBar1);
			pre = (Button)findViewById(R.id.pre);
			next = (Button)findViewById(R.id.next);
			text = (TextView)findViewById(R.id.no_result);
					
			final BarAdapter items = new BarAdapter(SearchResult.this, potain, Uni_id);
			
			
			pre.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	                 // Perform action on click
	            	 
	            	 if(items.moveToPre())
	            	 {
		            	 items.notifyDataSetChanged();
		            	 listview.smoothScrollToPosition(0);
	            	 }
	            	 else
	            	 {
	            		 items.notifyDataSetChanged();
	         			Toast.makeText(getApplicationContext(),"Sorry, No more previous search result",
	        					Toast.LENGTH_SHORT).show();
	         			listview.smoothScrollToPosition(0);
	            	 }
	            	 
	             }
	         });
			next.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	                 // Perform action on click
	            	 
	            	 if(items.moveToNext())
	            	 {
		            	 items.notifyDataSetChanged();
		            	 listview.smoothScrollToPosition(0);
	            	 }
	            	 else
	            	 {
	            		 items.notifyDataSetChanged();
	         			Toast.makeText(getApplicationContext(),"Sorry, No more search result",
	        					Toast.LENGTH_SHORT).show();
	         			listview.smoothScrollToPosition(0);
	            	 }
	            	 
	             }
	         });

			return items;

		}

		@Override
		protected void onPostExecute(BarAdapter items) {
			
			pre.setEnabled(true);
			next.setEnabled(true);
			spinner.setVisibility(View.GONE);
			listview.setAdapter(items);	
			
			if(items.Largelist.isEmpty())
				text.setVisibility(View.VISIBLE);
		}
	}	
	
}	

class Entry {
    public String Ligand_SMILES;
    public String BindingDB_MonomerID;
    public String BindingDB_Ligand_Name;
    public String Ki;
    public String IC50;
    public String Link;
    public String UniProt_Recommended_Name_of_Target_Chain;
    public String UniProt_Primary_ID_of_Target_Chain;
    public int img;
    Button SmilesB;
    ImageButton LinkB;
    
    Entry()
    {
        Ligand_SMILES = "";
        BindingDB_MonomerID = "";
        BindingDB_Ligand_Name = "";
        Ki = "";
        IC50 = "";
        Link = "";
        UniProt_Recommended_Name_of_Target_Chain = "";
        UniProt_Primary_ID_of_Target_Chain = "";
        img = R.drawable.caf;
    }
    Entry(String Ligand_SMILES, String BindingDB_MonomerID, String BindingDB_Ligand_Name,
    				String Ki, String IC50, String Link, String UniProt_Recommended_Name_of_Target_Chain,
    				String UniProt_Primary_ID_of_Target_Chain, int img) 
    {
        this.Ligand_SMILES = Ligand_SMILES;
        this.BindingDB_MonomerID = BindingDB_MonomerID;
        this.BindingDB_Ligand_Name = BindingDB_Ligand_Name;
        this.Ki = Ki;
        this.IC50 = IC50;
        this.Link = Link;
        this.UniProt_Recommended_Name_of_Target_Chain = UniProt_Recommended_Name_of_Target_Chain;
        this.UniProt_Primary_ID_of_Target_Chain = UniProt_Primary_ID_of_Target_Chain;
        this.img = img;
    }
}
class BarAdapter extends BaseAdapter
{
	public Cursor proteins;
	public MyDatabase db;
	private Context context;

	int totalPage = 0;
	int currentPage = 0;
	
	int count=0;
	
	String id = "";
	String pot = "";
	
	ArrayList<Entry> list = new ArrayList();
	ArrayList<Entry> Largelist = new ArrayList();
	
	BarAdapter(Context c, String potain,String Uni_id)
	{
		id = Uni_id;
		pot = potain;
		
		context = c;
		db = new MyDatabase(c);
		proteins = db.getProtein();
		
		if(!Uni_id.isEmpty() && !pot.isEmpty())
		{			
			pot = Character.toString(potain.charAt(0)).toUpperCase()+potain.substring(1);
			id = Character.toString(Uni_id.charAt(0)).toUpperCase()+Uni_id.substring(1);
			
			Largelist = db.getAllConpoundsByProtainAndId(pot, id);
		}
		else if(Uni_id.isEmpty() && !pot.isEmpty())
		{
			pot = Character.toString(potain.charAt(0)).toUpperCase()+potain.substring(1);
			Largelist = db.getAllProtain(pot);
		}
		else
		{
			id = Character.toString(Uni_id.charAt(0)).toUpperCase()+Uni_id.substring(1);
			Largelist = db.getAllId(id);
		}
		
		totalPage = Largelist.size()/20;
		
		if(Largelist.size()%20 != 0)
			totalPage++;
			
		
		list.clear();
		if(totalPage <= 1)
		{
			for(int j=0; j<Largelist.size(); j++)
				list.add(Largelist.get(j));
		}
		else
		{
			for(int j=0; j<20; j++)
				list.add(Largelist.get(j));
		}
		
		currentPage++;

		//spinner.setVisibility(View.GONE);
	}
	
	public boolean moveToNext()
	{
		list.clear();
		/*
		if(currentPage >= totalPage)
		{	
			currentPage--;
			
			for(int j=currentPage*20; j<Largelist.size(); j++)
				list.add(Largelist.get(j));
			
			return false;
		}
		else
		{*/
			if(Largelist.size()==0)
				return false;
			if(20*(currentPage+1)>Largelist.size())
			{
				for(int j=currentPage*20; j<Largelist.size(); j++)
					list.add(Largelist.get(j));
			}
			else
			{
				for(int j=currentPage*20; j<currentPage*20+20; j++)
					list.add(Largelist.get(j));
			}
			currentPage++;
			
			if(currentPage >= totalPage)
			{
				currentPage = totalPage-1;
				return false;
			}
			return true;
		//}
	}
	
	public boolean moveToPre()
	{
		list.clear();
		
		if(Largelist.size()==0)
			return false;
	/*	
		if(currentPage <= 0)
		{
			currentPage=1;
			
			if(totalPage == 1)
			{
				for(int j=0; j<Largelist.size(); j++)
					list.add(Largelist.get(j));
			}
			else
			{
				for(int j=0; j<20; j++)
					list.add(Largelist.get(j));
			}
			
			return false;
		}
		else
		{*/
			
			for(int j=currentPage*20-20; j<currentPage*20; j++)
				list.add(Largelist.get(j));
			
			currentPage--;
			
			if(currentPage<=0)
			{
				currentPage = 1;
				return false;
			}
			
			return true;
		//}
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row=inflater.inflate(R.layout.single_row, parent, false);
		TextView title = (TextView)row.findViewById(R.id.title);
		TextView description = (TextView)row.findViewById(R.id.description);
		ImageView image = (ImageView)row.findViewById(R.id.imageView);
		TextView ic = (TextView)row.findViewById(R.id.IC50);
		TextView smile = (TextView)row.findViewById(R.id.SMILES);



		final Entry temp = list.get(position);
		
		temp.SmilesB = (Button) row.findViewById(R.id.button1);
		temp.LinkB = (ImageButton) row.findViewById(R.id.button2);
		
		temp.SmilesB.setOnClickListener(new OnClickListener() {

			 @Override
			 public void onClick(View v) {
			  // TODO Auto-generated method stub
			  ((ClipboardManager)context.getSystemService(context.CLIPBOARD_SERVICE))
			  	.setText(temp.Ligand_SMILES);
			  
			  Toast.makeText(context, "SMILES string copied",
			    Toast.LENGTH_LONG).show();
			 }
		});
		
		
		temp.LinkB.setOnClickListener(new OnClickListener() {

			 @Override
			 public void onClick(View v) {
			  // TODO Auto-generated method stub
			  Log.i("Edit Button Clicked", "**********");
			  Toast.makeText(context, "Opening the Webpage of the selected compound",
			    Toast.LENGTH_LONG).show();
			  
			  Intent web = new Intent(Intent.ACTION_VIEW);
			  web.setData(Uri.parse(temp.Link));
			  context.startActivity(web);
			 }
		});


		title.setText(temp.UniProt_Recommended_Name_of_Target_Chain);
		description.setText(temp.Ki);
		image.setImageResource(temp.img);
		ic.setText(temp.IC50);
		smile.setText(temp.UniProt_Primary_ID_of_Target_Chain);



		return row;
	}
	
}
