package com.vladimir.pinterestlistview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.accential.trueone.bean.Offer;
import com.example.slide.R;
import com.vladimir.pinterestlistview.adapters.ItemsAdapter;

public class ItemsActivity extends Activity {

	private ListView listViewLeft;
	private ListView listViewRight;
	private ItemsAdapter leftAdapter;
	private ItemsAdapter rightAdapter;

	int[] leftViewsHeights;
	int[] rightViewsHeights;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.items_list);

		// listViewLeft = (ListView) findViewById(R.id.list_view_left);
		// listViewRight = (ListView) findViewById(R.id.list_view_right);

		loadItems();

		listViewLeft.setOnTouchListener(touchListener);
		listViewRight.setOnTouchListener(touchListener);
		listViewLeft.setOnScrollListener(scrollListener);
		listViewRight.setOnScrollListener(scrollListener);
	}

	// Passing the touch event to the opposite list
	OnTouchListener touchListener = new OnTouchListener() {
		boolean dispatched = false;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (v.equals(listViewLeft) && !dispatched) {
				dispatched = true;
				listViewRight.dispatchTouchEvent(event);
			} else if (v.equals(listViewRight) && !dispatched) {
				dispatched = true;
				listViewLeft.dispatchTouchEvent(event);
			}

			dispatched = false;
			return false;
		}
	};

	/**
	 * Synchronizing scrolling Distance from the top of the first visible
	 * element opposite list: sum_heights(opposite invisible screens) -
	 * sum_heights(invisible screens) + distance from top of the first visible
	 * child
	 */
	OnScrollListener scrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView v, int scrollState) {
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {

			if (view.getChildAt(0) != null) {
				if (view.equals(listViewLeft)) {
					leftViewsHeights[view.getFirstVisiblePosition()] = view
							.getChildAt(0).getHeight();

					int h = 0;
					for (int i = 0; i < listViewRight.getFirstVisiblePosition(); i++) {
						h += rightViewsHeights[i];
					}

					int hi = 0;
					for (int i = 0; i < listViewLeft.getFirstVisiblePosition(); i++) {
						hi += leftViewsHeights[i];
					}

					int top = h - hi + view.getChildAt(0).getTop();
					listViewRight.setSelectionFromTop(
							listViewRight.getFirstVisiblePosition(), top);
				} else if (view.equals(listViewRight)) {
					rightViewsHeights[view.getFirstVisiblePosition()] = view
							.getChildAt(0).getHeight();

					int h = 0;
					for (int i = 0; i < listViewLeft.getFirstVisiblePosition(); i++) {
						h += leftViewsHeights[i];
					}

					int hi = 0;
					for (int i = 0; i < listViewRight.getFirstVisiblePosition(); i++) {
						hi += rightViewsHeights[i];
					}

					int top = h - hi + view.getChildAt(0).getTop();
					listViewLeft.setSelectionFromTop(
							listViewLeft.getFirstVisiblePosition(), top);
				}

			}

		}
	};

	private void loadItems() {
		Integer[] leftItems = new Integer[] { R.drawable.ic_launcher,
				R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher, R.drawable.ic_launcher };
		Integer[] rightItems = new Integer[] { R.drawable.ic_launcher,
				R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher, R.drawable.ic_launcher };

		Offer offer = new Offer();
		offer.setPhoto("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQvdJvRmUipR5QlqgiUD-9Rnl4Vbu5nSqWrVdupGZ0zuf-P1NBFNg");

		Offer offer1 = new Offer();
		offer1.setPhoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRF7Kvcg8hVKoSWB6n9-a31ov93UB-d6vw8vYpVA0u8UtZ5vRsXkA");

		Offer offer2 = new Offer();
		offer2.setPhoto("http://d1jqu7g1y74ds1.cloudfront.net/wp-content/uploads/2009/01/falcon9_vertical002.jpg");

		Offer offer3 = new Offer();
		offer3.setPhoto("http://static.wmobjects.com.br/imgres/arquivos/ids/2513801-344-344/freezer-vertical-consul-142-litros-cvu20gb-slim-branco.jpg");

		Offer offer4 = new Offer();
		offer4.setPhoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRF7Kvcg8hVKoSWB6n9-a31ov93UB-d6vw8vYpVA0u8UtZ5vRsXkA");

		List<Offer> offers1 = new ArrayList<Offer>();
		offers1.add(offer4);
		offers1.add(offer3);

		List<Offer> offers2 = new ArrayList<Offer>();
		offers2.add(offer);
		offers2.add(offer1);
		offers2.add(offer2);

		leftAdapter = new ItemsAdapter(this, R.layout.item, offers1);
		rightAdapter = new ItemsAdapter(this, R.layout.item, offers2);
		listViewLeft.setAdapter(leftAdapter);
		listViewRight.setAdapter(rightAdapter);

		leftViewsHeights = new int[leftItems.length];
		rightViewsHeights = new int[rightItems.length];
	}

}