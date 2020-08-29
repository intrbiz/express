package com.intrbiz.express.operator;

import java.util.ArrayList;
import java.util.List;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class ListLiteral extends Operator
{	
    protected List<Operator> elements = new ArrayList<Operator>();
    
	public ListLiteral()
	{
	    super("[]");
	}

	public List<Operator> getElements()
    {
        return this.elements;
    }
	
	public void addElement(Operator element)
	{
	    this.elements.add(element);
	}

    public void setElements(List<Operator> elements)
    {
        this.elements = elements;
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        List<Object> list = new ArrayList<>(this.elements.size());
        for (Operator element : this.elements)
        {
            list.add(element.get(context, source));
        }
        return list;
    }

    @Override
    public boolean isConstant()
    {
        for (Operator element : this.elements)
        {
            if (! element.isConstant())
                return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        StringBuffer ret = new StringBuffer();
        ret.append("[");
        boolean npa = false;
        for (Operator op : this.elements)
        {
            if (npa) ret.append(", ");
            ret.append(op.toString());
            npa = true;
        }
        ret.append("]");
        return ret.toString();
    }
}
