package com.intrbiz.express.dynamic;

import com.intrbiz.converter.Converter;
import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;
import com.intrbiz.validator.Validator;

public interface DynamicEntity
{
	Object get(String name, ELContext context, Object source) throws ELException;
	void set(String name, Object value, ELContext context, Object source) throws ELException;
	
	Converter getConverter(String name, ELContext context, Object source) throws ELException;
	Validator getValidator(String name, ELContext context, Object source) throws ELException;
}
