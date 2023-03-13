package planet.com.helokings.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import planet.com.helokings.Pojo.HomeData.BannerItem;
import planet.com.helokings.R;

public class PopulorViewpadapter extends PagerAdapter {
    Context context;
    List<BannerItem> data;
    LayoutInflater layoutInflater;


    public PopulorViewpadapter(Context context, List<BannerItem> data) {
        this.context = context;
        this.data = data;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = layoutInflater.inflate(R.layout.pvpadapetr, container, false);

        ImageView imageView = view.findViewById(R.id.viewimg);
        LinearLayout llImageClick = (LinearLayout) view.findViewById(R.id.llImageClick);

        Picasso.get().load(data.get(position).getImage()).into(imageView);
        // Glide.with(context).load(data.get(position).getImage()).into(imageView);


        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public int getCount() {
        return data.size();
    }
}