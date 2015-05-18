package com.vladimir.pinterestlistview.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.accential.trueone.bean.Offer;
import com.example.slide.R;
import com.loopj.android.image.SmartImageView;

public class ItemsAdapter extends ArrayAdapter<Integer> {

	Context context;
	LayoutInflater inflater;
	int layoutResourceId;
	float imageWidth;
	List<Offer> offers;

	public ItemsAdapter(Context context, int layoutResourceId,
			List<Offer> offers) {
		super(context, layoutResourceId);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.offers = offers;

		float width = ((Activity) context).getWindowManager()
				.getDefaultDisplay().getWidth();
		float margin = (int) convertDpToPixel(10f, (Activity) context);
		// two images, three margins of 10dips
		imageWidth = ((width - (3 * margin)) / 2);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		FrameLayout row = (FrameLayout) convertView;
		ItemHolder holder;
		// Integer item = getItem(position);
		Offer offer = offers.get(position);
		SmartImageView itemImage = null;
		if (row == null) {
			holder = new ItemHolder();
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = (FrameLayout) inflater.inflate(layoutResourceId, parent,
					false);

			// smart
			itemImage = (SmartImageView) row.findViewById(R.id.item_image);

			// ImageView itemImage = (ImageView)
			// row.findViewById(R.id.item_image);
			holder.itemImage = itemImage;
		} else {
			holder = (ItemHolder) row.getTag();
		}

		row.setTag(holder);
		// setImageBitmap(item, holder.itemImage);
		Log.e("", offer.getPhoto());
		itemImage.setImageUrl(offer.getPhoto());
		return row;
	}

	public static class ItemHolder {
		SmartImageView itemImage;
	}

	// resize the image proportionately so it fits the entire space
	private void setImageBitmap(Integer item, ImageView imageView) {
		Bitmap bitmap = BitmapFactory.decodeResource(getContext()
				.getResources(), item);
		float i = ((float) imageWidth) / ((float) bitmap.getWidth());
		float imageHeight = i * (bitmap.getHeight());
		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) imageView
				.getLayoutParams();
		params.height = (int) imageHeight;
		params.width = (int) imageWidth;
		imageView.setLayoutParams(params);
		imageView.setImageResource(item);
	}

	public static float convertDpToPixel(float dp, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return px;
	}

}