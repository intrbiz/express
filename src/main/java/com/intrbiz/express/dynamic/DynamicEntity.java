package com.intrbiz.express.dynamic;

import com.intrbiz.converter.Converter;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.validator.Validator;

public interface DynamicEntity
{
	Object get(String name, ExpressContext context, Object source) throws ExpressException;
	void set(String name, Object value, ExpressContext context, Object source) throws ExpressException;
	
	Converter getConverter(String name, ExpressContext context, Object source) throws ExpressException;
	Validator getValidator(String name, ExpressContext context, Object source) throws ExpressException;
}
