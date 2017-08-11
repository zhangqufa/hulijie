package com.ssj.hulijie.pro.firstpage.adapter;

import java.util.List;

import com.bumptech.glide.Glide;
import com.ssj.hulijie.R;
import com.ssj.hulijie.pro.firstpage.bean.CatetoryItem;
import com.ssj.hulijie.pro.firstpage.bean.ItemFirstPageMainHeaderList;
import com.ssj.hulijie.pro.firstpage.view.AllCatetoryActivity;
import com.ssj.hulijie.pro.firstpage.view.SearchResultActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MygridviewAdapter extends BaseAdapter{

	public List<ItemFirstPageMainHeaderList> data;
	private LayoutInflater _inflater;
	private Context context;

	public MygridviewAdapter(Context context, List<ItemFirstPageMainHeaderList> data) {
		this.data = data;
		this.context = context;
		_inflater = LayoutInflater.from(context);
	}

	public void updateList(List<ItemFirstPageMainHeaderList> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ItemFirstPageMainHeaderList item = data.get(position);
		ViewHolder holder;
		if (convertView == null) {
			convertView = _inflater.inflate(R.layout.item_first_page_header, null);
			holder = new ViewHolder();
			holder.tv_menu = (TextView) convertView.findViewById(R.id.rv_header_rv_tv);
			holder.iv_menu = (ImageView) convertView.findViewById(R.id.rv_header_rv_iv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_menu.setText(item.getName());
		Glide.with(context).load(item.getPic()).animate(R.anim.scale_pic).into(holder.iv_menu);

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				String id = item.getId();

				Intent intent = null;
				if ("0".equals(id)) {
					intent = new Intent(context, AllCatetoryActivity.class);

				} else {
					intent = new Intent(context, SearchResultActivity.class);
					intent.putExtra("item", item);
				}

				if (intent != null) {
					context.startActivity(intent);
				}
			}
		});
		return convertView;
	}

	private class ViewHolder {
		private TextView tv_menu;
		private ImageView iv_menu;

	}
}
