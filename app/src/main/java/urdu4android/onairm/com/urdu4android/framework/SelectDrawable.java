package urdu4android.onairm.com.urdu4android.framework;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;


public class SelectDrawable extends StateListDrawable {

    public SelectDrawable(Drawable selectDrawable, Drawable normalDrawable) {
        addState(new int[]{android.R.attr.state_selected}, selectDrawable);
        addState(new int[]{}, normalDrawable);
    }

    public SelectDrawable(Resources resources, int tab_activity_on, int tab_activity_off) {
        this(resources.getDrawable(tab_activity_on), resources.getDrawable(tab_activity_off));
    }
}
