package com.intrbiz.express.operator;


public abstract class UnaryOperator extends Operator
{
	private Operator value;

	protected UnaryOperator(String name)
	{
		super(name);
	}

	public Operator getOperator()
	{
		return value;
	}

	public void setOperator(Operator value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return this.getName() + " " + this.getOperator();
	}

	@Override
    public boolean isConstant()
    {
        return this.getOperator().isConstant();
    }
}
