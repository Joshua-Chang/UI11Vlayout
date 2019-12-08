package com.wangyi.ui05.splash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ParallaxFragment extends Fragment {
    private List<View>parallaxView=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();
        int layoutId = args.getInt("layoutId");
        ParallaxLayoutInflater parallaxLayoutInflater = new ParallaxLayoutInflater(inflater,getActivity(),this);
        return parallaxLayoutInflater.inflate(layoutId,null);
    }

    public List<View> getParallaxViews() {
        return parallaxView;
    }
}
