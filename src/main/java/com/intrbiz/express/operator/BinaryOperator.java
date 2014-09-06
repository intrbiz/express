package com.intrbiz.express.operator;

public abstract class BinaryOperator extends Operator
{

	private Operator left;
	private Operator right;

	protected BinaryOperator(String name)
	{
		super(name);
	}

	/**
	 * @return the left
	 */
	public Operator getLeft()
	{
		return left;
	}

	/**
	 * @param left
	 *            the left to set
	 */
	public void setLeft(Operator left)
	{
		this.left = left;
	}

	/**
	 * @return the right
	 */
	public Operator getRight()
	{
		return right;
	}

	/**
	 * @param right
	 *            the right to set
	 */
	public void setRight(Operator right)
	{
		this.right = right;
	}

	@Override
	public String toString()
	{
		return  (this.getLeft() == null ? "" : this.getLeft() + " ") + this.getName() + " " + this.getRight();
	}
	
	@Override
    public boolean isConstant()
    {
        return this.isIdempotent() && this.getLeft().isConstant() && this.getRight().isConstant();
    }
	
	/**
     * Does this operation return the same result given the same arguments
     */
    public boolean isIdempotent()
    {
        return false;
    }
}
