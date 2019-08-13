package edu.luc.etl.cs313.android.shapes.model;

import java.util.List;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

	// TODO entirely your job (except onCircle)

	@Override
	public Location onCircle(final Circle c) {
		final int radius = c.getRadius();
		return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
	}

	@Override
	public Location onFill(final Fill f) {
		return f.getShape().accept(this);
	}

	@Override
	public Location onGroup(final Group g) {
		final List<? extends Shape> l = g.getShapes();
		Location b = l.get(0).accept(this);

		int minX = b.getX();
		int minY = b.getY();
		int maxX = b.getX()+((Rectangle)b.getShape()).getWidth();
		int maxY = b.getY()+((Rectangle) b.getShape()).getHeight();

		for (int i= 1; i < l.size(); i++) {
			Location b1 = l.get(i).accept(this);
			int minX1 = b1.getX();
			int minY1 = b1.getY();
			int maxX1 = b1.getX() + ((Rectangle) b1.getShape()).getWidth();
			int maxY1 = b1.getY() + ((Rectangle) b1.getShape()).getHeight();
			minX = Math.min(minX, minX1);
			minY = Math.min(minY, minY1);
			maxX = Math.max(maxX, maxX1);
			maxY = Math.max(maxY, maxY1);

		}

			return  new Location(minX, minY, new Rectangle( maxX-minX, maxY-minY));
	}

	@Override
	public Location onLocation(final Location l) {
		final Location b = l.getShape().accept(this);
		final int x = l.getX();
		final int y = l.getY();
		final Shape shape = b.getShape();
		return new Location(b.getX() + x,b.getY()+y,shape);

	}

	@Override
	public Location onRectangle(final Rectangle r) {
		return new Location(0,0, r);
	}

	@Override
	public Location onStroke(final Stroke c) {
		return c.getShape().accept(this);
	}

	@Override
	public Location onOutline(final Outline o) {
		return o.getShape().accept(this);
	}


	@Override
	public Location onPolygon(final Polygon s) {
		return onGroup(s);
	}
}
