package ume.oite.jp.calendarapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import ume.oite.jp.calendarapp.R;
import ume.oite.jp.calendarapp.View.Live2DSurfaceView;

/**
 * Created by Ume on 2015/07/17.
 */
public class Live2DFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.fragment_live2d, container, false);

        layout.addView(new Live2DSurfaceView(this.getActivity()));

        return layout;

    }

}
