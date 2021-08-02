package com.zzt.popupwindows;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zzt.popupwindows.dialog.MyTestDialog;
import com.zzt.popupwindows.dialog.MyTestPopupWindows;
import com.zzt.popupwindows.guide.ZGuidePopupV;
import com.zzt.popupwindows.library.ZNormalPopup;

public class PopupSampleActivity extends AppCompatActivity {

    TextView tv_text1, tv_text2, tv_text3, tv_text4, tv_text5, tv_text6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_sample);
        initView();
    }

    private void setTextViewGradientDown(TextView textView) {
        int[] colors = {Color.RED, Color.TRANSPARENT};
        float[] position = {0.7f, 1.0f};
        LinearGradient mLinearGradient = new LinearGradient(
                0, 0,
                0, textView.getPaint().getTextSize(),
                colors, position, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(mLinearGradient);
        textView.invalidate();
    }

    private void setTextViewGradientUP(TextView textView) {
        int[] colors = {Color.TRANSPARENT, Color.RED};
        float[] position = {0.3f, 1f};
        LinearGradient mLinearGradient = new LinearGradient(
                0, 0,
                0, textView.getPaint().getTextSize(),
                colors, position, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(mLinearGradient);
        textView.invalidate();
    }

    int padding = 50;
    ZNormalPopup zNormalPopup;

    public void initView() {

        zNormalPopup = new ZNormalPopup(
                this,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        tv_text1 = findViewById(R.id.tv_text1);
        tv_text2 = findViewById(R.id.tv_text2);
        tv_text3 = findViewById(R.id.tv_text3);
        tv_text4 = findViewById(R.id.tv_text4);
        tv_text5 = findViewById(R.id.tv_text5);
        tv_text6 = findViewById(R.id.tv_text6);


        tv_text6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTestDialog myTextDialog = new MyTestDialog(PopupSampleActivity.this);
                myTextDialog.setButtonClick1(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyTestPopupWindows popupWindows = new MyTestPopupWindows(PopupSampleActivity.this);
                        popupWindows.showAtLocation(myTextDialog.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                    }
                });
                myTextDialog.setButtonClick2(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView textView = new TextView(PopupSampleActivity.this);
                        textView.setPadding(padding, padding, padding, padding);
                        textView.setBackgroundColor(getResources().getColor(R.color.color_FFD333));
                        textView.setText("弹框的这个图片");
                        textView.setTextColor(
                                getResources().getColor(R.color.salmon)
                        );
                        new ZGuidePopupV(
                                v.getContext(),
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        ).preferredDirection(ZGuidePopupV.DIRECTION_BOTTOM)
                                .offsetYIfBottom(dp2px(v.getContext(), 10))
                                .view(textView)
                                .show(v);
                    }
                });
                myTextDialog.show();
            }
        });


        setTextViewGradientDown(tv_text4);
        setTextViewGradientUP(tv_text5);


        tv_text1.setBackgroundColor(getResources().getColor(R.color.color_77FF89));
        tv_text4.setBackgroundColor(getResources().getColor(R.color.color_77FF89));
        tv_text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = new TextView(PopupSampleActivity.this);
                textView.setPadding(padding, padding, padding, padding);
                textView.setBackgroundColor(getResources().getColor(R.color.color_FFD333));
                textView.setText("通过 dimAmount() 设置背景遮罩");
                textView.setTextColor(
                        getResources().getColor(R.color.salmon)
                );
                new ZGuidePopupV(
                        v.getContext(),
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ).preferredDirection(ZGuidePopupV.DIRECTION_BOTTOM)
                        .offsetYIfBottom(dp2px(v.getContext(), 10))
                        .view(textView)
                        .show(v);
            }
        });
        tv_text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = new TextView(PopupSampleActivity.this);
                textView.setPadding(padding, padding, padding, padding);
                textView.setBackgroundColor(getResources().getColor(R.color.color_FFD333));
                textView.setText("通过 dimAmount() 设置背景遮罩  这个显示在上面");
                textView.setTextColor(
                        getResources().getColor(R.color.salmon)
                );
                new ZGuidePopupV(
                        v.getContext(),
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ).preferredDirection(ZGuidePopupV.DIRECTION_TOP)
                        .offsetYIfTop(dp2px(v.getContext(), 10))
                        .view(textView)
                        .show(v);
            }
        });
        tv_text1.setOnClickListener(v -> {

            //region 这个可以的
            TextView textView = new TextView(PopupSampleActivity.this);


            textView.setPadding(padding, padding, padding, padding);
            textView.setText("通过 dimAmount() 设置背景遮罩");
            textView.setTextColor(
                    getResources().getColor(R.color.salmon)
            );

            zNormalPopup.preferredDirection(ZNormalPopup.DIRECTION_BOTTOM)
                    .view(textView)
                    .bgColor(getResources().getColor(R.color.qmui_config_color_white))
                    .bgColorGradient(new int[]{ContextCompat.getColor(v.getContext(), R.color.color_FFD333), ContextCompat.getColor(v.getContext(), R.color.color_FF8702)})
                    .borderColor(getResources().getColor(R.color.magenta))
                    .borderWidth(0)
                    .radius(10)
                    .arrow(true)
                    .arrowSize(dp2px(v.getContext(), 30), dp2px(v.getContext(), 30))
                    .dimAmount(0.6f)
                    .show(v);
            //endregion

        });
        tv_text2.setOnClickListener(v -> {
            //region 这个可以的
            TextView textView = new TextView(PopupSampleActivity.this);


            textView.setPadding(padding, padding, padding, padding);
            textView.setText("通过 dimAmount() 设置背景遮罩");
            textView.setTextColor(
                    getResources().getColor(R.color.salmon)
            );

            zNormalPopup.preferredDirection(ZNormalPopup.DIRECTION_TOP)
                    .view(textView)
                    .bgColor(getResources().getColor(R.color.qmui_config_color_white))
                    .bgColorGradient(new int[]{ContextCompat.getColor(v.getContext(), R.color.color_FFD333), ContextCompat.getColor(v.getContext(), R.color.color_FF8702)})
                    .borderColor(getResources().getColor(R.color.magenta))
                    .borderWidth(0)
                    .radius(dp2px(v.getContext(), 10))
                    .arrow(true)
                    .arrowSize(dp2px(v.getContext(), 20), dp2px(v.getContext(), 20))
                    .offsetYIfBottom(dp2px(v.getContext(), 0))
                    .dimAmount(0.6f)
                    .show(v);
            //endregion
        });
        tv_text3.setOnClickListener(v -> {

            //region 这个可以的
            TextView textView = new TextView(PopupSampleActivity.this);


            textView.setPadding(padding, padding, padding, padding);
            textView.setText("通过 dimAmount() 设置背景遮罩");
            textView.setTextColor(
                    getResources().getColor(R.color.salmon)
            );

            zNormalPopup.preferredDirection(ZNormalPopup.DIRECTION_CENTER_IN_SCREEN)
                    .view(textView)
                    .bgColor(getResources().getColor(R.color.qmui_config_color_white))
                    .borderColor(getResources().getColor(R.color.magenta))
                    .borderWidth(5)
                    .radius(10)
                    .dimAmount(0.6f)
                    .onDismiss(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {

                        }
                    })
                    .show(v);
            //endregion
        });
    }

    public int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }
}