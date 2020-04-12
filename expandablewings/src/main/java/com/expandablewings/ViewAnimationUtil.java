package com.expandablewings;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

class ViewAnimationUtil {

    static void expandOrCollapse(final View v, boolean exp_or_collapse, boolean right) {
        TranslateAnimation anim;
        if (v != null) {
            if (exp_or_collapse) {
                if (right) {
                    anim = new TranslateAnimation(-v.getWidth(), 0.0f, 0.0f, 0.0f);
                } else {
                    anim = new TranslateAnimation(v.getWidth(), 0.0f, 0.0f, 0.0f);
                }
                v.setVisibility(View.VISIBLE);
            } else {
                if (right) {
                    anim = new TranslateAnimation(0.0f, -v.getWidth(), 0.0f, 0.0f);
                } else {
                    anim = new TranslateAnimation(0.0f, v.getWidth(), 0.0f, 0.0f);
                }

                Animation.AnimationListener collapselistener = new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        v.setVisibility(View.GONE);
                    }
                };

                anim.setAnimationListener(collapselistener);
            }

            // To Collapse
            //

            anim.setDuration(300);
            anim.setInterpolator(new AccelerateInterpolator(0.5f));
            v.startAnimation(anim);
        }
    }
}