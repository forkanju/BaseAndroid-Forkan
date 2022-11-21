package com.learning.baseprojectforkan.common.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.learning.baseprojectforkan.R;

/**
 * name : forkan
 * date : 15/11/16 4:23pm
 * email: forkanju44rth@gmail.com
 *
 * @ : pran-rfl group, dhaka
 */

public class ChooseAlertDialog extends Dialog {

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int DEFAULT_RADIUS = 6;
    public static final int DIALOG_TYPE_INFO = 0;
    public static final int DIALOG_TYPE_HELP = 1;
    public static final int DIALOG_TYPE_WRONG = 2;
    public static final int DIALOG_TYPE_SUCCESS = 3;
    public static final int DIALOG_TYPE_WARNING = 4;
    public static final int DIALOG_TYPE_DEFAULT = DIALOG_TYPE_INFO;

    private AnimationSet mAnimIn, mAnimOut;
    private View mDialogView;
    private TextView mTitleTv, mContentTv, mPositiveBtn, mNegativeBtn;
    private OnPositiveListener mOnPositiveListener;
    private OnNegativeListener mOnNegativeListener;

    private int mDialogType;
    private boolean mIsShowAnim;
    private CharSequence mTitle, mContent, mBtnText, mNegText;

    public ChooseAlertDialog(Context context) {
        this(context, 0);
    }

    public ChooseAlertDialog(Context context, int theme) {
        super(context, R.style.color_dialog);
        init();
    }

    private void init() {
        mAnimIn = AnimationLoader.getInAnimation(getContext());
        mAnimOut = AnimationLoader.getOutAnimation(getContext());
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initListener();
        initNegativeListener();
    }

    private void initListener() {
        mPositiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnPositiveListener != null) {
                    mOnPositiveListener.onClick(ChooseAlertDialog.this);
                }
            }
        });

        initAnimListener();

    }



    private void dismissWithAnimation(boolean showOutAnimation) {
        if (showOutAnimation) {
            mDialogView.startAnimation(mAnimOut);
        } else {
            super.dismiss();
        }
    }

    private void initAnimListener() {
        mAnimOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDialogView.post(new Runnable() {
                    @Override
                    public void run() {
                        callDismiss();
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void initNegativeListener() {
        mNegativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnNegativeListener != null) {
                    mOnNegativeListener.onClick(ChooseAlertDialog.this);
                }
            }
        });

        initAnimListener();
    }

    private void callDismiss() {
        super.dismiss();
    }

    private void initView() {
        View contentView = View.inflate(getContext(), R.layout.layout_choose_dialog, null);
        setContentView(contentView);
        resizeDialog();

        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mTitleTv = (TextView) contentView.findViewById(R.id.tvTitle);
        mContentTv = (TextView) contentView.findViewById(R.id.tvContent);
        mPositiveBtn = (TextView) contentView.findViewById(R.id.btnPositive);
        mNegativeBtn = (TextView) contentView.findViewById(R.id.btnNegative);

        View llBtnGroup = findViewById(R.id.llBtnGroup);
        ImageView logoIv = (ImageView) contentView.findViewById(R.id.logoIv);
        logoIv.setBackgroundResource(getLogoResId(mDialogType));

        LinearLayout topLayout = (LinearLayout) contentView.findViewById(R.id.topLayout);
        ImageView triangleIv = new ImageView(getContext());
        triangleIv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dp2px(getContext(), 10)));
        triangleIv.setImageBitmap(createTriangle((int) (DisplayUtil.getScreenSize(getContext()).x * 0.7), DisplayUtil.dp2px(getContext(), 10)));
        topLayout.addView(triangleIv);

        setBtnDefaultBackground(mPositiveBtn);
        setBtnActiveBackground(mNegativeBtn);
        setBottomCorners(llBtnGroup);

        int radius = DisplayUtil.dp2px(getContext(), DEFAULT_RADIUS);
        float[] outerRadii = new float[]{radius, radius, radius, radius, 0, 0, 0, 0};
        RoundRectShape roundRectShape = new RoundRectShape(outerRadii, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.getPaint().setColor(getContext().getResources().getColor(getColorResId(mDialogType)));
        LinearLayout llTop = (LinearLayout) findViewById(R.id.llTop);
        llTop.setBackground(shapeDrawable);

        mTitleTv.setText(mTitle);
        mContentTv.setText(mContent);
        mPositiveBtn.setText(mBtnText);
        mNegativeBtn.setText(mNegText);
    }

    private Bitmap createTriangle(int width, int height) {
        if (width <= 0 || height <= 0) {
            return null;
        }
        return getBitmap(width, height, getContext().getResources().getColor(getColorResId(mDialogType)));
    }

    private Bitmap getBitmap(int width, int height, int backgroundColor) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, BITMAP_CONFIG);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(backgroundColor);
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(width, 0);
        path.lineTo(width / 2, height);
        path.close();

        canvas.drawPath(path, paint);
        return bitmap;

    }

    private int getColorResId() {
        return R.color.light_gray;
    }

    private int getColorResId(int mDialogType) {
        if (DIALOG_TYPE_DEFAULT == mDialogType) {
            return R.color.color_type_info;
        }
        if (DIALOG_TYPE_INFO == mDialogType) {
            return R.color.color_type_info;
        }
        if (DIALOG_TYPE_HELP == mDialogType) {
            return R.color.color_type_help;
        }
        if (DIALOG_TYPE_WRONG == mDialogType) {
            return R.color.color_type_wrong;
        }
        if (DIALOG_TYPE_SUCCESS == mDialogType) {
            return R.color.color_type_success;
        }
        if (DIALOG_TYPE_WARNING == mDialogType) {
            return R.color.color_type_warning;
        }
        return R.color.color_type_info;
    }

    private int getLogoResId(int mDialogType) {
        if (DIALOG_TYPE_DEFAULT == mDialogType) {
            return R.mipmap.ic_info;
        }
        if (DIALOG_TYPE_INFO == mDialogType) {
            return R.mipmap.ic_info;
        }
        if (DIALOG_TYPE_HELP == mDialogType) {
            return R.mipmap.ic_help;
        }
        if (DIALOG_TYPE_WRONG == mDialogType) {
            return R.mipmap.ic_wrong;
        }
        if (DIALOG_TYPE_SUCCESS == mDialogType) {
            return R.mipmap.ic_success;
        }
        if (DIALOG_TYPE_WARNING == mDialogType) {
            return R.mipmap.icon_warning;
        }
        return R.mipmap.ic_info;

    }

    private void resizeDialog() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) (DisplayUtil.getScreenSize(getContext()).x * 0.7);
        getWindow().setAttributes(params);
    }

    private void setBtnActiveBackground(final TextView btnCancel) {
        btnCancel.setTextColor(createColorStateList(getContext().getResources().getColor(getColorResId(mDialogType)),
                getContext().getResources().getColor(R.color.color_dialog_gray)));
        btnCancel.setBackground(getContext().getResources().getDrawable(getSelBtn(mDialogType)));
    }

    private void setBtnDefaultBackground(final TextView btnOk) {
        btnOk.setTextColor(createColorStateList(getContext().getResources().getColor(getColorResId()),
                getContext().getResources().getColor(R.color.color_dialog_gray)));
        btnOk.setBackground(getContext().getResources().getDrawable(getSelBtn()));
    }

    private ColorStateList createColorStateList(int normal, int pressed) {
        return createColorStateList(normal, pressed, Color.BLACK, Color.BLACK);
    }

    private ColorStateList createColorStateList(int normal, int pressed, int focused, int unable) {
        int[] colors = new int[]{pressed, focused, normal, focused, unable, normal};
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_focused};
        states[4] = new int[]{android.R.attr.state_window_focused};
        states[5] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    private int getSelBtn() {
        return R.drawable.sel_btn_disable;
    }

    private int getSelBtn(int mDialogType) {
        if (DIALOG_TYPE_DEFAULT == mDialogType) {
            return R.drawable.sel_btn;
        }
        if (DIALOG_TYPE_INFO == mDialogType) {
            return R.drawable.sel_btn_info;
        }
        if (DIALOG_TYPE_HELP == mDialogType) {
            return R.drawable.sel_btn_help;
        }
        if (DIALOG_TYPE_WRONG == mDialogType) {
            return R.drawable.sel_btn_wrong;
        }
        if (DIALOG_TYPE_SUCCESS == mDialogType) {
            return R.drawable.sel_btn_success;
        }
        if (DIALOG_TYPE_WARNING == mDialogType) {
            return R.drawable.sel_btn_warning;
        }
        return R.drawable.sel_btn;
    }

    private void setBottomCorners(View llBtnGroup) {
        int radius = DisplayUtil.dp2px(getContext(), DEFAULT_RADIUS);
        float[] outerRadii = new float[]{0, 0, 0, 0, radius, radius, radius, radius};
        RoundRectShape roundRectShape = new RoundRectShape(outerRadii, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
        shapeDrawable.getPaint().setColor(Color.WHITE);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        llBtnGroup.setBackgroundDrawable(shapeDrawable);
    }

    private void startWithAnimation(boolean showInAnimation) {
        if (showInAnimation) {
            mDialogView.startAnimation(mAnimIn);
        }
    }

    public ChooseAlertDialog setAnimationEnable(boolean enable) {
        mIsShowAnim = enable;
        return this;
    }

    public ChooseAlertDialog setTitleText(CharSequence title) {
        mTitle = title;
        return this;
    }

    public ChooseAlertDialog setTitleText(int resId) {
        return setTitleText(getContext().getString(resId));
    }

    public ChooseAlertDialog setContentText(CharSequence content) {
        mContent = content;
        return this;
    }

    public ChooseAlertDialog setContentText(int resId) {
        return setContentText(getContext().getString(resId));
    }

    public TextView getTitleTextView() {
        return mTitleTv;
    }

    public TextView getContentTextView() {
        return mContentTv;
    }

    public ChooseAlertDialog setDialogType(int type) {
        mDialogType = type;
        return this;
    }

    public int getDialogType() {
        return mDialogType;
    }


    public ChooseAlertDialog setPositiveListener(CharSequence btnText, OnPositiveListener l) {
        mBtnText = btnText;
        return setPositiveListener(l);
    }

    public ChooseAlertDialog setPositiveListener(int stringResId, OnPositiveListener l) {
        return setPositiveListener(getContext().getString(stringResId), l);
    }

    public ChooseAlertDialog setPositiveListener(OnPositiveListener l) {
        mOnPositiveListener = l;
        return this;
    }

    public ChooseAlertDialog setNegativeListener(CharSequence btnText, OnNegativeListener l) {
        mNegText = btnText;
        return setNegativeListener(l);
    }

    public ChooseAlertDialog set(int stringResId, OnNegativeListener l) {
        return setNegativeListener(getContext().getString(stringResId), l);
    }

    public ChooseAlertDialog setNegativeListener(OnNegativeListener l) {
        mOnNegativeListener = l;
        return this;
    }

    public ChooseAlertDialog setAnimationIn(AnimationSet animIn) {
        mAnimIn = animIn;
        return this;
    }

    public ChooseAlertDialog setAnimationOut(AnimationSet animOut) {
        mAnimOut = animOut;
        initAnimListener();
        return this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        startWithAnimation(mIsShowAnim);
    }

    @Override
    public void dismiss() {
        dismissWithAnimation(mIsShowAnim);
    }

    public interface OnPositiveListener {
        void onClick(ChooseAlertDialog dialog);
    }

    public interface OnNegativeListener {
        void onClick(ChooseAlertDialog dialog);
    }
}
