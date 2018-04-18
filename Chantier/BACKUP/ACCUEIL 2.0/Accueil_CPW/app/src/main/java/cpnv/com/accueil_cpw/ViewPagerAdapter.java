package cpnv.com.accueil_cpw;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by tiffany.di-domenico on 06.03.2018.
 * Accueil_CPW
 * Source : https://stackoverflow.com/questions/30245993/how-to-display-images-in-android-and-change-it-by-swipe-right-left?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
 *
 */

public class ViewPagerAdapter extends PagerAdapter {
    DatabaseManager cosWorldDB;
    Cursor data = cosWorldDB.showData();

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer [] images = {R.drawable.img1,R.drawable.img2,R.drawable.img3};

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);

        ViewPager vp =(ViewPager) container;
        vp.addView(view, 0);
        return  view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
