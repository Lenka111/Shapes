package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import edu.luc.etl.cs313.android.shapes.model.*;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {

	// TODO entirely your job (except onCircle)

	private final Canvas canvas;

	private final Paint paint;

	public Draw(final Canvas canvas, final Paint paint) {
		this.canvas = canvas; // FIXME fixed
		this.paint =  paint; // FIXME  fixed
		paint.setStyle(Style.STROKE);
	}

	@Override
	public Void onCircle(final Circle c) {
		canvas.drawCircle(0, 0, c.getRadius(), paint);
		return null;
	}

	@Override
	public Void onStroke(final Stroke c) {
		final int original = paint.getColor();
		//setting stroke color to draw the shape
		paint.setColor(c.getColor());
		//visit through the decorators
		c.getShape().accept(this);
		//restoring the original paint value
		paint.setColor(original);
		return null;
	}

	@Override
	public Void onFill(final Fill f) {
		//set style of paint to fill the shape
		paint.setStyle(Style.FILL_AND_STROKE);
		f.getShape().accept(this);
		//restoring the original style
		paint.setStyle(Style.FILL);
		return null;
	}

	@Override
	public Void onGroup(final Group g) {

		for(Shape s: g.getShapes()){
			s.accept(this);
		}
		return null;
	}

	@Override
	public Void onLocation(final Location l) {
		canvas.translate(l.getX(),l.getY());
		l.getShape().accept(this);
		canvas.translate(-l.getX(),-l.getY());
		return null;
	}

	@Override
	public Void onRectangle(final Rectangle r) {
		canvas.drawRect(0, 0, r.getWidth(), r.getHeight(), paint);
		return null;
	}

	@Override
	public Void onOutline(Outline o) {
		final Style original = paint.getStyle();
		paint.setStyle(Style.STROKE);
		o.getShape().accept(this);
		//resetting to original style
		paint.setStyle(original);

		return null;
	}

	@Override
	public Void onPolygon(final Polygon s) {
		final int n = s.getPoints().size();
		final float[] pts = new float [4*n];

		pts[0]=s.getPoints().get(0).getX();
		pts[4*n-1]=s.getPoints().get(0).getY();
		pts[1]=s.getPoints().get(0).getY();
		pts[4*n-2]=s.getPoints().get(0).getX();
		for (int i = 1; i<n; i++) {
			Point p = s.getPoints().get(i);
			pts[i * 4 - 2] = p.getX();
			pts[i * 4 - 1] = p.getY();
			pts[i * 4] = p.getX();
			pts[i * 4 + 1] = p.getY();
		}

		canvas.drawLines(pts, paint);
		return null;
	}
}
