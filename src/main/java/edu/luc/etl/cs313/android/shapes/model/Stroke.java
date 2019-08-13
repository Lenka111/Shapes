package edu.luc.etl.cs313.android.shapes.model;

/**
 * A decorator for specifying the stroke (foreground) color for drawing the
 * shape.
 */
public class Stroke implements Shape {

	// TODO entirely your job
	protected final int color; // defined instance variables color and shape
	protected final Shape shape;

	public Stroke(final int color, final Shape shape) {
		this.color = color; //fixed
		this.shape = shape; //fixed
	}

	//defined the getters for the instance variables
	public int getColor() {
		return this.color;
	}  // fixed the return type

	public Shape getShape() {
		return this.shape;
	} // fixed the return type

	@Override
	public <Result> Result accept(Visitor<Result> v) {
		return v.onStroke(this);
	} // fixed the accept() method
}
