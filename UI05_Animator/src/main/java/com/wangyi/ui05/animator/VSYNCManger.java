package com.wangyi.ui05.animator;

import java.util.ArrayList;
import java.util.List;

public class VSYNCManger {
    private static final VSYNCManger ourInstance = new VSYNCManger();

    public static VSYNCManger getInstance() {
        return ourInstance;
    }

    private VSYNCManger() {
        new Thread(runnable).start();
    }
    private List<AnimationFrameCallback>list=new ArrayList<>();

    public void add(AnimationFrameCallback animationFrameCallback){
        list.add(animationFrameCallback);
    }


    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
          while (true){
              try {
                  Thread.sleep(16);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              for (AnimationFrameCallback animationFrameCallback : list) {
                  animationFrameCallback.doAnimationFrame(System.currentTimeMillis());
              }
          }
        }
    };
    interface AnimationFrameCallback{
        boolean doAnimationFrame(long currentTime);
    }
}
