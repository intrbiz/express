Intrbiz Express
===============
An expression and micro scripting language.  Initially designed for data binding 
in JSC, now Balsa.


License
-------
Intrbiz Express
Copyright (c) 2005, Chris Ellis
All rights reserved.

Intrbiz Express is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Intrbiz Express is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with Intrbiz Express.  If not, see <http://www.gnu.org/licenses/>.


Usage
-----

	// Create a context, which is used to resolve entities
	ELContext context = new AbstractELContext() {
		@Override
		public Object getEntityInner(String name, Object source)
		{
			// We have no entities to offer up :(
			return null;
		}
	};

	// Parse an expression
	ValueExpression expression = new ValueExpression(context, "#{'Hello' + ' ' + 'World'}");

	// Evaluate the expression
	System.out.println(expression.get(context, null));


Author
------
Chris Ellis

Twitter: @intrbiz

Web: balsa-framework.org or intrbiz.com

Copyright (c) Chris Ellis 2008