package com.intrbiz.express.operator;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class MapLiteral extends Operator
{	
    protected LinkedHashMap<String, Operator> elements = new LinkedHashMap<String, Operator>();
    
	public MapLiteral()
	{
	    super("{}");
	}

	public LinkedHashMap<String, Operator> getElements()
    {
        return this.elements;
    }
	
	public void putElement(String name, Operator element)
	{
	    this.elements.put(name, element);
	}

    public void setElements(LinkedHashMap<String, Operator> elements)
    {
        this.elements = elements;
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        for (Entry<String, Operator> element : this.elements.entrySet())
        {
            map.put(element.getKey(), element.getValue().get(context, source));
        }
        return map;
    }

    @Override
    public boolean isConstant()
    {
        for (Operator element : this.elements.values())
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
        ret.append("{");
        boolean npa = false;
        for (Entry<String, Operator> element : this.elements.entrySet())
        {
            if (npa) ret.append(", ");
            ret.append("'").append(StringLiteral.encode(element.getKey())).append("'");
            ret.append(": ");
            ret.append(element.getValue().toString());
            npa = true;
        }
        ret.append("}");
        return ret.toString();
    }
}
