package com.example.aksaedittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Vibrator;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import java.util.Objects;

public class AksaTextView extends RelativeLayout {
    LayoutInflater mInflater;
    TextView hintLbl;
    AppCompatTextView aksaCalendartext;
    View divder;
    TextView tvErrorMessage;
    TypedArray typedArray;
    Resources resources;
    TextView tvMandatory;
    int actionX, actionY;
    boolean mDownTouch = false;
    private int hintLabelColor,mandatoryTextColor, hintLableSize, edTextColor, edTextSize, edHintColor, hintDrawbleLeftInt,
            edDrawbleleftInt, edDrawableRightInt, hintDrawablePadding, edDrawablePadding, hintlblStyle, edTextStye, edMaxLenght,
            edInputType, edImoOption;
    private Drawable hintDrawableLeft, edDrawableLeft, edDrawableRight, drawableBottom, drawableTop;
    private String hintTxt = "", edHintTxt = "", edTxt = "", edFont, hintFont, edDigitsOnly;
    private boolean toggleDrawableRight, ed_Enabled = true, ed_IsMandatory = false;
    //    boolean  ed_Enabled = true;
    private DrawableClickListener clickListener;


    public AksaTextView(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        initViews();

    }

    public AksaTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        parseAttributes(attrs);
        initViews();

    }

    public AksaTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        parseAttributes(attrs);
        initViews();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AksaTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mInflater = LayoutInflater.from(context);
        parseAttributes(attrs);
        initViews();

    }

    @InverseBindingAdapter(attribute = "ed_txt")
    public static String getEdTxt(AksaTextView view) {
        return view.edTxt;
    }

    @BindingAdapter("ed_txt")
    public static void setEdTxt(AksaTextView view, String edTxt) {
        view.edTxt = edTxt;
    }

    @BindingAdapter(value = "ed_txtAttrChanged")
    public static void setListener(AksaTextView editText, final InverseBindingListener listener) {
        if (listener != null) {
            editText.aksaCalendartext.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    editText.edTxt = charSequence.toString();
                    if (editText.tvErrorMessage.getVisibility() == VISIBLE) {
                        editText.tvErrorMessage.setText(null);
                        editText.tvErrorMessage.setVisibility(GONE);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    listener.onChange();
                }
            });
        }
    }

    public void setEdDrawableLeft(int id) {
        this.hintLbl.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0);
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
            ed_IsMandatory = typedArray.getBoolean(R.styleable.AksaEditText_ed_is_mandatory, false);
            mandatoryTextColor = typedArray.getColor(R.styleable.AksaEditText_ed_mandatory_text_color, -1);
//            ed_Enabled = typedArray.getBoolean(R.styleable.AksaEditText_ed_enable, true);
            edImoOption = typedArray.getInt(R.styleable.AksaEditText_android_imeOptions, 0);
        } catch (Exception e) {
            Log.e("ex", "Exception in custom editTxt");

        } finally {
            typedArray.recycle();
        }
    }

    private void initViews() {
        View v = mInflater.inflate(R.layout.custom_akas_calendar_textview, this, true);
        hintLbl = v.findViewById(R.id.hint_lbl);
        aksaCalendartext = v.findViewById(R.id.custom_tv);
        tvErrorMessage = v.findViewById(R.id.tv_error_message);
        divder = v.findViewById(R.id.div);
        tvMandatory = v.findViewById(R.id.mandatory_textView);
        bindViews();
    }

    //    @SuppressLint("ClickableViewAccessibility")
    @SuppressLint("ClickableViewAccessibility")
    private void bindViews() {
        try {
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
                hintLbl.setCompoundDrawablesRelativeWithIntrinsicBounds(hintDrawableLeft, null, null, null);
            }

            if (hintDrawablePadding != 0)
                hintLbl.setCompoundDrawablePadding(hintDrawablePadding);
            if (edHintTxt != null && !edHintTxt.isEmpty())
                aksaCalendartext.setHint(edHintTxt);
            if (edTxt != null && !edTxt.isEmpty())
                aksaCalendartext.setText(edTxt);
            if (edHintColor != 0)
                aksaCalendartext.setHintTextColor(edHintColor);
            else
                aksaCalendartext.setHintTextColor(getContext().getResources().getColor(R.color.secondaryText));
            if (edTextColor != 0)
                aksaCalendartext.setTextColor(getContext().getResources().getColor(R.color.black));
            else
                aksaCalendartext.setTextColor(edTextColor);
            if (edTextSize != 0)
                aksaCalendartext.setTextSize(TypedValue.COMPLEX_UNIT_PX, edTextSize);
            if (edFont != null)
                aksaCalendartext.setTypeface(edTextStye == 0 ? Typeface.createFromAsset(getContext().getAssets(), "font/" + edFont) :
                        Typeface.createFromAsset(getContext().getAssets(), "font/" + edFont), edTextStye);
            if (edDrawbleleftInt != 0) {
                edDrawableLeft = AppCompatResources.getDrawable(getContext(), edDrawbleleftInt);
            }
            if (edDrawableRightInt != 0) {
                edDrawableRight = AppCompatResources.getDrawable(getContext(), edDrawableRightInt);
            }

            aksaCalendartext.setCompoundDrawablesRelativeWithIntrinsicBounds(edDrawbleleftInt == 0 ? null : edDrawableLeft, null, edDrawableRightInt == 0 ? null : edDrawableRight, null);

            if (edDrawablePadding != 0)
                aksaCalendartext.setCompoundDrawablePadding(edDrawablePadding);

            if (edMaxLenght != 0)
                aksaCalendartext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(edMaxLenght)});
            if (edInputType != EditorInfo.TYPE_NULL) {
                aksaCalendartext.setInputType(edInputType);
            } else {
                aksaCalendartext.setInputType(InputType.TYPE_CLASS_TEXT);
            }

            if (edImoOption != 0) {
                aksaCalendartext.setImeOptions(edImoOption);
            } else {
                aksaCalendartext.setImeOptions(EditorInfo.IME_ACTION_DONE);
            }

            if (edDigitsOnly != null) {
                aksaCalendartext.setKeyListener(new CustomDigitsKeyListener(edDigitsOnly.toCharArray(), edInputType));
            }

            if (ed_IsMandatory) {
                tvMandatory.setVisibility(VISIBLE);
                if (mandatoryTextColor != -1) {
                    tvMandatory.setTextColor(mandatoryTextColor);
                }
            } else {
                tvMandatory.setVisibility(GONE);
            }
            if (!ed_Enabled) {
                // if false comes from xml then disable edittext
                aksaCalendartext.setEnabled(!ed_Enabled);
                aksaCalendartext.setTextColor(resources.getColor(R.color.disableTextColor));
                aksaCalendartext.setClickable(ed_Enabled);
                aksaCalendartext.setAlpha(0.5f);
                aksaCalendartext.setFocusable(ed_Enabled);
                aksaCalendartext.setOnTouchListener((view, motionEvent) -> {
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP && clickListener != null)
                        clickListener.onClick(this);
                    return false;
                });
            }
            aksaCalendartext.setEnabled(true);
            aksaCalendartext.setClickable(true);
            aksaCalendartext.setOnTouchListener((view, motionEvent) -> {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    clickListener.onClick(this);
                return false;
            });

        } catch (Exception e) {
            Log.e("ex", "customView : Binding exception");
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getText() {

        return Objects.requireNonNull(aksaCalendartext.getText()).toString();
    }

    //    public boolean isEd_Enabled() {
//        return aksaCalendartext.isEnabled();
//    }


    public void setText(String text) {
        aksaCalendartext.setText(text);
        aksaCalendartext.invalidate();
        aksaCalendartext.requestLayout();
    }

    public int getLenght() {
        return aksaCalendartext.length();
    }

    public void setError(String error) {
        if (error != null) {
            aksaCalendartext.requestFocus();
            getFocusOnView(this.aksaCalendartext);
            tvErrorMessage.setVisibility(VISIBLE);
            tvErrorMessage.setText(error);
          /*  Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
            aksaCalendartext.startAnimation(shake);
            Vibrator vb = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
            vb.vibrate(400);*/
        } else {
            aksaCalendartext.clearFocus();
            aksaCalendartext.setError(null);
        }

    }

    public void setEdEnabled(boolean isEnabled) {
        aksaCalendartext.setEnabled(isEnabled);
    }

    public boolean isEmpty() {
        return aksaCalendartext.toString().isEmpty();
    }

    public boolean isEnable() {
        return aksaCalendartext.isEnabled();
    }

    public void setEnable(boolean enable) {
        aksaCalendartext.setEnabled(enable);
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

        void onClick(AksaTextView target);
    }

    private void getFocusOnView(View view) {
        int margintop = -40;
        Rect rect = new Rect(0, margintop, view.getWidth(), view.getHeight());
        view.requestRectangleOnScreen(rect, false);
    }
}

