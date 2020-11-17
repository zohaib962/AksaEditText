package com.example.aksaedittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Vibrator;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import java.util.Objects;

public class AksaEditTextPassword extends RelativeLayout {


    public static final int DRAWABLE_LEFT = 0;
    public static final int DRAWABLE_TOP = 1;
    public static final int DRAWABLE_RIGHT = 2;
    public static final int DRAWABLE_BOTTOM = 3;


    LayoutInflater mInflater;
    TextView hintLbl;
    AppCompatEditText aksaEdtext;
    TextView tvErrorMessage;
    View divder;
    TypedArray typedArray;
    Resources resources;
    int actionX, actionY;
    boolean mDownTouch = false, ed_Enabled = true;
    private int hintLabelColor, hintLableSize, edTextColor, edTextSize, edHintColor, hintDrawbleLeftInt,
            edDrawbleleftInt, edDrawableRightInt, hintDrawablePadding, edDrawablePadding, hintlblStyle, edTextStye, edMaxLenght,
            edInputType, edImoOption;
    private Drawable hintDrawableLeft, edDrawableLeft, edDrawableRight, drawableBottom, drawableTop;
    private String hintTxt = "", edHintTxt = "", edTxt = "", edFont, hintFont, edDigitsOnly;
    private boolean toggleDrawableRight;
    private DrawableClickListener clickListener;


    public AksaEditTextPassword(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        initViews();

    }

    public AksaEditTextPassword(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        parseAttributes(attrs);
        initViews();

    }

    public AksaEditTextPassword(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        parseAttributes(attrs);
        initViews();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AksaEditTextPassword(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mInflater = LayoutInflater.from(context);
        parseAttributes(attrs);
        initViews();

    }

    @InverseBindingAdapter(attribute = "ed_txt")
    public static String getEdTxt(AksaEditTextPassword view) {
        return view.edTxt;
    }

    @BindingAdapter("ed_txt")
    public static void setEdTxt(AksaEditTextPassword view, String edTxt) {
        view.edTxt = edTxt;
    }

    @BindingAdapter(value = "ed_txtAttrChanged")
    public static void setListener(AksaEditTextPassword editText, final InverseBindingListener listener) {
        if (listener != null) {
            editText.aksaEdtext.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    if (editText.tvErrorMessage.getVisibility() == VISIBLE) {
                        editText.tvErrorMessage.setText(null);
                        editText.tvErrorMessage.setVisibility(GONE);
                    }

                    editText.edTxt = charSequence.toString().replace("-", "");
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    listener.onChange();
                }
            });
        }
    }

    private void parseAttributes(AttributeSet attrs) {
        typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.AksaEditText, 0, 0);
        resources = getContext().getResources();
        try {
            hintTxt = typedArray.getString(R.styleable.AksaEditText_hint_label_txt);
            edHintTxt = typedArray.getString(R.styleable.AksaEditText_ed_hint_txt);
            edTxt = typedArray.getString(R.styleable.AksaEditText_ed_txt);
            hintLabelColor = typedArray.getColor(R.styleable.AksaEditText_hint_lable_color, resources.getColor(R.color.zong_green));
            edHintColor = typedArray.getColor(R.styleable.AksaEditText_ed_hint_color, resources.getColor(R.color.primaryText));
            edTextColor = typedArray.getColor(R.styleable.AksaEditText_ed_color, resources.getColor(R.color.primaryText));
            hintLableSize = typedArray.getDimensionPixelSize(R.styleable.AksaEditText_hint_label_size, 14);
            edTextSize = typedArray.getDimensionPixelSize(R.styleable.AksaEditText_ed_text_size, 14);
            edFont = typedArray.getString(R.styleable.AksaEditText_ed_font_family);
            hintFont = typedArray.getString(R.styleable.AksaEditText_hint_label_font_faimly);
            hintDrawbleLeftInt = typedArray.getResourceId(R.styleable.AksaEditText_hint_label_drawable_left, 0);
            edDrawbleleftInt = typedArray.getResourceId(R.styleable.AksaEditText_ed_drawable_start, 0);
            edDrawableRightInt = typedArray.getResourceId(R.styleable.AksaEditText_ed_drawable_end, 0);
            hintDrawablePadding = typedArray.getDimensionPixelSize(R.styleable.AksaEditText_hint_label_drawable_padding, 0);
            edDrawablePadding = typedArray.getDimensionPixelSize(R.styleable.AksaEditText_ed_drawable_padding, 0);
            hintlblStyle = typedArray.getInteger(R.styleable.AksaEditText_hint_label_style, 0);
            edTextStye = typedArray.getInteger(R.styleable.AksaEditText_ed_txt_style, 25);
            edMaxLenght = typedArray.getInteger(R.styleable.AksaEditText_ed_maxLenght, 0);
            edDigitsOnly = typedArray.getString(R.styleable.AksaEditText_ed_digits);
            edInputType = typedArray.getInt(R.styleable.AksaEditText_android_inputType, EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
            ed_Enabled = typedArray.getBoolean(R.styleable.AksaEditText_ed_enable, true);
            edImoOption = typedArray.getInt(R.styleable.AksaEditText_android_imeOptions, 0);
        } catch (Exception e) {
            Log.e("ex", "Exception in custom editTxt");

        } finally {
            typedArray.recycle();
        }
    }

    private void initViews() {
        View v = mInflater.inflate(R.layout.custom_akas_textview, this, true);
        hintLbl = v.findViewById(R.id.hint_lbl);
        aksaEdtext = v.findViewById(R.id.custom_ed);
        tvErrorMessage = v.findViewById(R.id.tv_error_message);
        divder = v.findViewById(R.id.div);
        bindViews();


        aksaEdtext.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode actionMode, MenuItem item) {
                return false;
            }

            public void onDestroyActionMode(ActionMode actionMode) {
            }
        });

        aksaEdtext.setLongClickable(false);
        aksaEdtext.setTextIsSelectable(false);

    }

    //    @SuppressLint("ClickableViewAccessibility")
    @SuppressLint("ClickableViewAccessibility")
    private void bindViews() {
        try {
            aksaEdtext.setBackgroundResource(android.R.color.transparent);
            hintLbl.setText(hintTxt);
            if (hintLabelColor != 0)
                hintLbl.setTextColor(hintLabelColor);
            else
                hintLbl.setTextColor(getContext().getResources().getColor(R.color.secondaryText));
            if (hintLableSize != 0)
                hintLbl.setTextSize(TypedValue.COMPLEX_UNIT_PX, hintLableSize);
            if (hintFont != null)
                hintLbl.setTypeface(hintlblStyle == 0 ? Typeface.createFromAsset(getContext().getAssets(), "font/" + hintFont) :
                        Typeface.createFromAsset(getContext().getAssets(), "font/" + hintFont), hintlblStyle);

            if (hintDrawbleLeftInt != 0) {
                hintDrawableLeft = ContextCompat.getDrawable(getContext(), hintDrawbleLeftInt);
                hintLbl.setCompoundDrawablesRelativeWithIntrinsicBounds(hintDrawableLeft, null, null, null);
            }

            if (hintDrawablePadding != 0)
                hintLbl.setCompoundDrawablePadding(hintDrawablePadding);
            if (edHintTxt != null && !edHintTxt.isEmpty())
                aksaEdtext.setHint(edHintTxt);
            if (edTxt != null && !edTxt.isEmpty())
                aksaEdtext.setText(edTxt);
            if (edHintColor != 0)
                aksaEdtext.setHintTextColor(edHintColor);
            else
                aksaEdtext.setHintTextColor(getContext().getResources().getColor(R.color.secondaryText));
            if (edTextColor != 0)
                aksaEdtext.setTextColor(getContext().getResources().getColor(R.color.black));
            else
                aksaEdtext.setTextColor(edTextColor);
            if (edTextSize != 0)
                aksaEdtext.setTextSize(TypedValue.COMPLEX_UNIT_PX, edTextSize);
            if (edFont != null)
                aksaEdtext.setTypeface(edTextStye == 0 ? Typeface.createFromAsset(getContext().getAssets(), "font/" + edFont) :
                        Typeface.createFromAsset(getContext().getAssets(), "font/" + edFont), edTextStye);
            if (edDrawbleleftInt != 0) {
                edDrawableLeft = AppCompatResources.getDrawable(getContext(), edDrawbleleftInt);
            }
            if (edDrawableRightInt != 0) {
                edDrawableRight = AppCompatResources.getDrawable(getContext(), edDrawableRightInt);
            }

            aksaEdtext.setCompoundDrawablesRelativeWithIntrinsicBounds(edDrawbleleftInt == 0 ? null : edDrawableLeft, null, edDrawableRightInt == 0 ? null : edDrawableRight, null);

            if (edDrawablePadding != 0)
                aksaEdtext.setCompoundDrawablePadding(edDrawablePadding);

            if (edMaxLenght != 0)
                aksaEdtext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(edMaxLenght)});
            if (edInputType != EditorInfo.TYPE_NULL) {
                aksaEdtext.setInputType(edInputType);
            } else {
                aksaEdtext.setInputType(InputType.TYPE_CLASS_TEXT);
            }

            if (edImoOption != 0) {
                aksaEdtext.setImeOptions(edImoOption);
            } else {
//                aksaEdtext.setImeOptions(EditorInfo.IME_ACTION_DONE);
            }


            if (edDigitsOnly != null) {
                aksaEdtext.setKeyListener(new CustomDigitsKeyListener(edDigitsOnly.toCharArray(), edInputType));
            }

            if (!ed_Enabled) {
                // if false comes from xml then disable edittext
                aksaEdtext.setEnabled(!ed_Enabled);
                aksaEdtext.setTextColor(resources.getColor(R.color.disableTextColor));
                aksaEdtext.setClickable(ed_Enabled);
                aksaEdtext.setAlpha(0.5f);
                aksaEdtext.setFocusable(ed_Enabled);
                aksaEdtext.setOnTouchListener((view, motionEvent) -> {
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP && clickListener != null)
                        clickListener.onClick(this);
                    return false;
                });


            }
        } catch (Exception e) {
            Log.e("ex", "customView : Binding exception");
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    public void addDrawableClickListener(int drawable, DrawableClickListener listener) {
        this.clickListener = listener;
        aksaEdtext.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (aksaEdtext.getRight() - aksaEdtext.getCompoundDrawables()[drawable].getBounds().width())) {
                    clickListener.onClick(this);
                    return true;
                }
            }
            return false;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getText() {

        return Objects.requireNonNull(aksaEdtext.getText()).toString();
    }

    public void setText(String text) {


        aksaEdtext.setText(text);
        aksaEdtext.invalidate();
        aksaEdtext.requestLayout();
    }

    public int getLenght() {
        return aksaEdtext.length();
    }


    public void setError(String error) {
        if (error != null) {
            aksaEdtext.requestFocus();
            tvErrorMessage.setVisibility(VISIBLE);
            tvErrorMessage.setText(error);

        } else {
            aksaEdtext.clearFocus();
            tvErrorMessage.setVisibility(GONE);
            tvErrorMessage.setText(null);
        }

    }

    public boolean isEd_Enabled() {
        return aksaEdtext.isEnabled();
    }

    public void setEdEnabled(boolean isEnabled) {
        aksaEdtext.setEnabled(isEnabled);
    }

    public void setLabel(String labelText) {
        this.hintTxt = labelText;
        hintLbl.setText(hintTxt);
    }

    public void setEdText(String text, int color) {
        this.edTxt = text;
        aksaEdtext.setText(text);
        aksaEdtext.setTextColor(color);
    }

    public boolean isEmpty() {
        return aksaEdtext.toString().isEmpty();
    }

    public boolean isEnable() {
        return aksaEdtext.isEnabled();
    }

    public void setEnable(boolean enable) {
        aksaEdtext.setEnabled(enable);
    }

    public void toggleDrawableRight() {
        if (!toggleDrawableRight) {
            toggleDrawableRight = true;
            edDrawableRightInt = R.drawable.ic_hide;
            aksaEdtext.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            aksaEdtext.setSelection(Objects.requireNonNull(aksaEdtext.getText()).length());
            edDrawableRight = AppCompatResources.getDrawable(getContext(), edDrawableRightInt);
        } else {
            toggleDrawableRight = false;
            edDrawableRightInt = R.drawable.ic_eye;
            aksaEdtext.setTransformationMethod(new PasswordTransformationMethod());
            aksaEdtext.setSelection(Objects.requireNonNull(aksaEdtext.getText()).length());
            edDrawableRight = AppCompatResources.getDrawable(getContext(), edDrawableRightInt);
        }
        aksaEdtext.setCompoundDrawablesRelativeWithIntrinsicBounds(edDrawbleleftInt == 0 ? null : edDrawableLeft, null, edDrawableRightInt == 0 ? null : edDrawableRight, null);

    }

    public void setDrawableClickListener(DrawableClickListener listener) {
        this.clickListener = listener;
    }

    @Override
    protected void finalize() throws Throwable {
        edDrawableRight = null;
        drawableBottom = null;
        edDrawableLeft = null;
        drawableTop = null;
        super.finalize();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        invalidate();
        requestLayout();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public interface DrawableClickListener {

        void onClick(AksaEditTextPassword target);

        enum DrawablePosition {TOP, BOTTOM, LEFT, RIGHT}
    }
}