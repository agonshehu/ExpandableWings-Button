package com.expandablewings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ExpandableWings extends RelativeLayout implements View.OnClickListener {

    private TextView rightWingText, leftWingText;
    private FloatingActionButton floatingActionButton;
    private Context context;
    private OnWingClick mListener;
    private boolean iconRotate = false;
    private int rotationDegree = 0;
    private RelativeLayout rightWing, leftWing;

    public enum Wings {
        LEFT, RIGHT, CENTER
    }

    public ExpandableWings(Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    public ExpandableWings(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }



    private void init(AttributeSet attrs){
        try {
            mListener = (OnWingClick) context;
        } catch (Exception ignored) {

        }

        LayoutInflater.from(context).inflate(R.layout.expandable_layout, this, true);
        RelativeLayout rootView = findViewById(R.id.mRootView);
        rightWing = rootView.findViewById(R.id.expandable_right);
        leftWing = rootView.findViewById(R.id.expandable_left);
        rightWingText = rootView.findViewById(R.id.rightwingtext);
        leftWingText = rootView.findViewById(R.id.leftwingtext);
        floatingActionButton = rootView.findViewById(R.id.addbtn);

        @SuppressLint("Recycle") TypedArray typeArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.ExpandableWings);

        int mInitRightWingColor = typeArray.getColor(R.styleable.ExpandableWings_ew_right_backgroundcolor,
                ContextCompat.getColor(getContext(), R.color.transparent));
        int mInitLeftWingColor = typeArray.getColor(R.styleable.ExpandableWings_ew_left_backgroundcolor,
                ContextCompat.getColor(getContext(), R.color.transparent));

        int mInitRightWingHeight = (int) typeArray.getDimension(R.styleable.ExpandableWings_ew_right_height,
                getResources().getDimension(R.dimen.wingheight));
        int mInitRightWingWidth = (int) typeArray.getDimension(R.styleable.ExpandableWings_ew_right_width,
                getResources().getDimension(R.dimen.wingwidth));

        int mInitLeftWingHeight = (int) typeArray.getDimension(R.styleable.ExpandableWings_ew_left_height,
                getResources().getDimension(R.dimen.wingheight));
        int mInitLeftWingWidth = (int) typeArray.getDimension(R.styleable.ExpandableWings_ew_left_width,
                getResources().getDimension(R.dimen.wingwidth));

        int mInitRightWingTextColor = typeArray.getColor(R.styleable.ExpandableWings_ew_right_textcolor,
                ContextCompat.getColor(getContext(), R.color.colorPrimary));
        int mInitLeftWingTextColor = typeArray.getColor(R.styleable.ExpandableWings_ew_left_textcolor,
                ContextCompat.getColor(getContext(), R.color.colorPrimary));

        int mInitRightWingTextSize = (int) typeArray.getDimension(R.styleable.ExpandableWings_ew_right_textsize,
                getResources().getDimension(R.dimen.text_size_18));
        int mInitLeftWingTextSize = (int) typeArray.getDimension(R.styleable.ExpandableWings_ew_left_textsize,
                getResources().getDimension(R.dimen.text_size_18));

        int mInitIconSize = (int) typeArray.getDimension(R.styleable.ExpandableWings_ew_fabsize,
                getResources().getDimension(R.dimen.fab_size));

        boolean mInitState = typeArray.getBoolean(R.styleable.ExpandableWings_ew_visibility_state,
                false);

        String mInitRightWingText = typeArray.getString(R.styleable.ExpandableWings_ew_right_text);
        String mInitLeftWingText = typeArray.getString(R.styleable.ExpandableWings_ew_left_text);
        if (mInitRightWingText != null) {
            rightWingText.setText(mInitRightWingText);
        }
        if (mInitLeftWingText != null){
            leftWingText.setText(mInitLeftWingText);
        }

        floatingActionButton.setCustomSize(mInitIconSize);

        rightWingText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mInitRightWingTextSize);
        leftWingText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mInitLeftWingTextSize);

        leftWingText.setTextColor(mInitLeftWingTextColor);
        rightWingText.setTextColor(mInitRightWingTextColor);

        setDimensions(leftWing, mInitLeftWingHeight, mInitLeftWingWidth);
        setDimensions(rightWing, mInitRightWingHeight, mInitRightWingWidth);

        changeIconColor(leftWing, mInitLeftWingColor);
        changeIconColor(rightWing, mInitRightWingColor);

        if (mInitState){
            leftWing.setVisibility(VISIBLE);
            rightWing.setVisibility(VISIBLE);
        }

        floatingActionButton.setOnClickListener(this);
        rightWing.setOnClickListener(this);
        leftWing.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.expandable_right) {
            if (mListener != null) mListener.onClick(Wings.RIGHT);
        } else if (id == R.id.expandable_left) {
            if (mListener != null) mListener.onClick(Wings.LEFT);
        } else if (id == R.id.addbtn) {
            if (mListener != null) mListener.onClick(Wings.CENTER);
        }
    }


    public void toggleFab(){
        if (isExpanded()) {
            collapseView();
        } else {
            expandView();
        }
    }

    public void collapseView() {
        ViewAnimationUtil.expandOrCollapse(rightWing, false, true);
        ViewAnimationUtil.expandOrCollapse(leftWing, false, false);
        if (iconRotate) {
            floatingActionButton.animate().rotation(0);
        }
    }

    public void expandView(){
        ViewAnimationUtil.expandOrCollapse(rightWing, true, true);
        ViewAnimationUtil.expandOrCollapse(leftWing, true, false);
        if (iconRotate) {
            floatingActionButton.animate().rotation(rotationDegree);
        }
    }


    public boolean isExpanded(){
        return rightWing.getVisibility() == VISIBLE && leftWing.getVisibility() == VISIBLE;
    }

    public interface OnWingClick{
        void onClick(Wings wings);
    }


    private void changeIconColor(@NonNull RelativeLayout drawable, int color){
        drawable.getBackground().setColorFilter(
                color,
                PorterDuff.Mode.SRC_ATOP
        );
    }

    public void setOnWingClickListener(OnWingClick listener) {
        mListener = listener;
    }

    public void setWingColors(int leftWingColor, int rightWingColor){
        changeIconColor(leftWing, leftWingColor);
        changeIconColor(rightWing, rightWingColor);
    }

    public void setTextColor(int leftColor, int rightColor){
        leftWingText.setTextColor(leftColor);
        rightWingText.setTextColor(rightColor);

    }

    public void setTextSize(int leftSize, int rightSize){
        leftWingText.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftSize);
        rightWingText.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightSize);

    }

    public void setText(String leftString, String rightString){
        leftWingText.setText(leftString);
        rightWingText.setText(rightString);

    }

    public void setWingDimensions(int leftWingHeight, int leftWingWidth, int rightWingHeight, int rightWingWidth){
        setDimensions(leftWing, leftWingHeight, leftWingWidth);
        setDimensions(rightWing, rightWingHeight, rightWingWidth);

    }

    public void setFloatingActionButtonCustomSize(int size){
        floatingActionButton.setCustomSize(size);

    }

    public void setFloatingActionButtonIcon(Drawable icon){
        floatingActionButton.setImageDrawable(icon);

    }

    public void setFloatingActionButtonIconRotation(boolean iconRotation, int degrees){
        iconRotate = iconRotation;
        rotationDegree = degrees;

    }

    public void setFloatingActionButtonSize(String size){
        if (size.equals("Mini")){
            floatingActionButton.setSize(com.google.android.material.floatingactionbutton.FloatingActionButton.SIZE_MINI);
        } else if (size.equals("Normal")) {
            floatingActionButton.setSize(FloatingActionButton.SIZE_NORMAL);
        }
    }


    private void setDimensions(@NonNull RelativeLayout linearLayout, int wingHeight, int wingWidth){
        LayoutParams params = (LayoutParams) linearLayout.getLayoutParams();
// Changes the height and width to the specified *pixels*
        if (wingHeight != 0) {
            params.height = wingHeight;
        }
        if (wingWidth != 0) {
            params.width = wingWidth;
        }
        linearLayout.setLayoutParams(params);
    }

}
