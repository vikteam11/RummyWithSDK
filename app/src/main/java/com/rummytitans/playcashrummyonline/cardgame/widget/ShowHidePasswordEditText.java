package com.rummytitans.playcashrummyonline.cardgame.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.rummytitans.playcashrummyonline.cardgame.R;


/**
 * Custom version of EditText that shows and hides password onClick of the visibility icon
 */
public class ShowHidePasswordEditText extends EditText {

    private static final String TAG = ShowHidePasswordEditText.class.getSimpleName();
    private final static String IS_SHOWING_PASSWORD_STATE_KEY = "IS_SHOWING_PASSWORD_STATE_KEY";
    private final static String SUPER_STATE_KEY = "SUPER_STATE_KEY";
    private final int DEFAULT_ADDITIONAL_TOUCH_TARGET_SIZE = 40;
    private boolean isShowingPassword = false;
    private Drawable drawableEnd;
    private boolean leftToRight = true;
    private int tintColor = 0;
    @DrawableRes
    private int visibilityIndicatorShow;
    @DrawableRes
    private int visibilityIndicatorHide;
    private int additionalTouchTargetSize = DEFAULT_ADDITIONAL_TOUCH_TARGET_SIZE;

    public ShowHidePasswordEditText(Context context) {
        super(context);
        init(null);
    }

    public ShowHidePasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ShowHidePasswordEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.ShowHidePasswordEditText);
            visibilityIndicatorShow = attrsArray.getResourceId(R.styleable.ShowHidePasswordEditText_drawable_show, R.drawable.ic_lockclosed);
            visibilityIndicatorHide = attrsArray.getResourceId(R.styleable.ShowHidePasswordEditText_drawable_hide, R.drawable.ic_lockopen);
            tintColor = attrsArray.getColor(R.styleable.ShowHidePasswordEditText_tint_color,getResources().getColor( R.color.silver));
            additionalTouchTargetSize = attrsArray.getDimensionPixelSize(R.styleable.ShowHidePasswordEditText_additionalTouchTargetSize, DEFAULT_ADDITIONAL_TOUCH_TARGET_SIZE);
            attrsArray.recycle();
        } else {
            visibilityIndicatorShow = R.drawable.ic_lockclosed;
            visibilityIndicatorHide = R.drawable.ic_lockopen;

        }

        leftToRight = isLeftToRight();

        //ensures by default this view is only line only
        setMaxLines(1);

        //note this must be set before maskPassword() otherwise it was undeo the passwordTransformation
        setSingleLine(true);


        //initial state is hiding
        isShowingPassword = false;
        maskPassword();

        //save the state of whether the password is being shown
        setSaveEnabled(true);
        showPasswordVisibilityIndicator(true);

    }

    private boolean isLeftToRight() {
        // If we are pre JB assume always LTR
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return true;
        }

        // Other methods, seemingly broken when testing though.
        // return ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL
        // return !ViewUtils.isLayoutRtl(this);

        Configuration config = getResources().getConfiguration();
        return !(config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL);
    }

    @Override
    public void setCompoundDrawables(Drawable left, Drawable top,
                                     Drawable right, Drawable bottom) {

        //keep a reference to the right drawable so later on touch we can check if touch is on the drawable
        if (leftToRight && right != null) {
            drawableEnd = right;
        } else if (!leftToRight && left != null) {
            drawableEnd = left;
        }

        super.setCompoundDrawables(left, top, right, bottom);
    }

    public void setTintColor(@ColorInt int tintColor) {
        this.tintColor = tintColor;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP && drawableEnd != null) {
            Rect bounds = drawableEnd.getBounds();

            int x = (int) event.getX();

            //take into account the padding and additionalTouchTargetSize
            int drawableWidthWithPadding = bounds.width() + (leftToRight ? getPaddingRight() : getPaddingLeft()) + additionalTouchTargetSize;

            //check if the touch is within bounds of drawableEnd icon
            if ((leftToRight && (x >= (this.getRight() - (drawableWidthWithPadding)))) ||
                    (!leftToRight && (x <= (this.getLeft() + (drawableWidthWithPadding))))) {
                togglePasswordVisibility();
                //use this to prevent the keyboard from coming up
                event.setAction(MotionEvent.ACTION_CANCEL);
            }
        }

        return super.onTouchEvent(event);
    }

    public void showPasswordVisibilityIndicator(boolean show) {
        //Log.d(TAG, "showPasswordVisibilityIndicator() called with: " + "show = [" + show + "]");
        //preserve and existing CompoundDrawables
        Drawable[] existingDrawables = getCompoundDrawables();
        Drawable left = existingDrawables[0];
        Drawable top = existingDrawables[1];
        Drawable right = existingDrawables[2];
        Drawable bottom = existingDrawables[3];
        Drawable leftside = VectorDrawableCompat.create(getResources(), R.drawable.ic_password, getContext().getTheme());
        if (show) {
            Drawable original = isShowingPassword ?
                    VectorDrawableCompat.create(getResources(), visibilityIndicatorHide, getContext().getTheme()) :
                    VectorDrawableCompat.create(getResources(), visibilityIndicatorShow, getContext().getTheme());
            original.mutate();
            if (tintColor == 0) {
                setCompoundDrawablesWithIntrinsicBounds(leftToRight ? left : left, top, leftToRight ? original : right, bottom);
            } else {
                Drawable wrapper = DrawableCompat.wrap(original);
                DrawableCompat.setTint(wrapper, tintColor);
                setCompoundDrawablesWithIntrinsicBounds(leftToRight ? left : left, top, leftToRight ? wrapper : right, bottom);
            }
        } else {
            setCompoundDrawablesWithIntrinsicBounds(leftToRight ? left : left, top, leftToRight ? null : right, bottom);
        }
    }

    //make it visible
    private void unmaskPassword() {
        setTransformationMethod(null);
    }

    //hide it
    private void maskPassword() {
        setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    public void togglePasswordVisibility() {
        // Store the selection
        int selectionStart = this.getSelectionStart();
        int selectionEnd = this.getSelectionEnd();

        // Set transformation method to show/hide password
        if (isShowingPassword) {
            maskPassword();
        } else {
            unmaskPassword();
        }

        // Restore selection
        this.setSelection(selectionStart, selectionEnd);

        // Toggle flag and show indicator
        isShowingPassword = !isShowingPassword;
        showPasswordVisibilityIndicator(true);
    }

    @Override
    protected void finalize() throws Throwable {
        drawableEnd = null;
        super.finalize();
    }

    public
    @DrawableRes
    int getVisibilityIndicatorShow() {
        return visibilityIndicatorShow;
    }

    public void setVisibilityIndicatorShow(@DrawableRes int visibilityIndicatorShow) {
        this.visibilityIndicatorShow = visibilityIndicatorShow;
    }

    public
    @DrawableRes
    int getVisibilityIndicatorHide() {
        return visibilityIndicatorHide;
    }

    public void setVisibilityIndicatorHide(@DrawableRes int visibilityIndicatorHide) {
        this.visibilityIndicatorHide = visibilityIndicatorHide;
    }

    /**
     * @return true if the password is visible | false if hidden
     */
    public boolean isShowingPassword() {
        return isShowingPassword;
    }

    public int getAdditionalTouchTargetSizePixels() {
        return additionalTouchTargetSize;
    }

    /**
     * @param additionalTouchTargetSize inPixels
     */
    public void setAdditionalTouchTargetSizePixels(int additionalTouchTargetSize) {
        this.additionalTouchTargetSize = additionalTouchTargetSize;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SUPER_STATE_KEY, super.onSaveInstanceState());
        bundle.putBoolean(IS_SHOWING_PASSWORD_STATE_KEY, this.isShowingPassword);
        return bundle;
    }


    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.isShowingPassword = bundle.getBoolean(IS_SHOWING_PASSWORD_STATE_KEY, false);

            if (isShowingPassword) {
                unmaskPassword();
            }
            state = bundle.getParcelable(SUPER_STATE_KEY);
        }
        super.onRestoreInstanceState(state);
    }
}