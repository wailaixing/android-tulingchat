package com.example.tulingchat;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements HttpGetDataListener, OnClickListener {

	private HttpData httpData;
	private List<ListData> lists;
	private ListView lv;
	private EditText sendText;
	private Button send_btn;
	private String content_str;
	private TextAdapter adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	@Override
	public void getDataUrl(String data) {
		// System.out.println(data);
		parseText(data);
	}

	private void initView() {
		sendText = (EditText) findViewById(R.id.sendText);
		send_btn = (Button) findViewById(R.id.send_btn);
		lv = (ListView) findViewById(R.id.lv);
		lists = new ArrayList<ListData>();
		send_btn.setOnClickListener(this);
		adapter=new TextAdapter(lists, this);
		lv.setAdapter(adapter);
	}

	public void parseText(String str) {
		try {
			JSONObject jb = new JSONObject(str);
			ListData listData;
			listData = new ListData(jb.getString("text"),ListData.RECEIVE);
			lists.add(listData);
			adapter.notifyDataSetChanged();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		content_str=sendText.getText().toString();
		sendText.setText("");
		String drop=content_str.replace(" ", "");
		String dropn=drop.replace("\n", "");
		
		ListData listData;
		listData=new ListData(content_str, ListData.SEND);
		lists.add(listData);
		adapter.notifyDataSetChanged();
		httpData = (HttpData) new HttpData(
				"http://www.tuling123.com/openapi/api?key=2ca7c0bae5f9ee2b3be807acf4a82c8a&info=" + dropn, this).execute();

	}
}
