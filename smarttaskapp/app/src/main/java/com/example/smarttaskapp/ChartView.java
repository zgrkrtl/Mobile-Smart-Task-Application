package com.example.smarttaskapp;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChartView extends View {

    private Map<String, Integer> data;
    private List<Integer> barColors;

    private Paint barPaint;
    private Paint textPaint;

    public ChartView(Context context) {
        super(context);
        init();
    }

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        barPaint = new Paint();
        textPaint = new Paint();

        barColors = new ArrayList<>();
        barColors.add(Color.BLUE);
        barColors.add(Color.RED);
        barColors.add(Color.GREEN);
        barColors.add(Color.YELLOW);
        barColors.add(Color.MAGENTA);
        barColors.add(Color.CYAN);
    }

    public void setData(Map<String, Integer> data) {
        this.data = data;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (data == null || data.isEmpty()) {
            return;
        }

        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        int barWidth = canvasWidth / (data.size() * 2);
        int maxData = getMax(data);
        float dataScale = (float) canvasHeight / maxData;

        int i = 0;
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            int startX = i * barWidth * 2;
            int endX = startX + barWidth * 2;
            int startY = canvasHeight - (int) (entry.getValue() * dataScale);
            int endY = canvasHeight;


            barPaint.setColor(barColors.get(i % barColors.size()));
            canvas.drawRect(startX, startY, endX, endY, barPaint);


            textPaint.setColor(Color.BLACK);
            canvas.drawText(String.valueOf(entry.getValue()), startX + barWidth, startY - 10, textPaint);

            i++;
        }
    }

    private int getMax(Map<String, Integer> data) {
        int max = 0;
        for (int value : data.values()) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
