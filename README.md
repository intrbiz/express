# Intrbiz Express

Express is an expression, micro scripting and templating language.  It was Initially built as an expression language for a web framework, but over the years has evolved to meet other usecases.

It's primary usage is within the Balsa web framework as an expression language to bind data into views.  It's also used as a templating and scripting engine within Bergamot Monitoring.

The language syntax is pretty similar to Java and Javascript, with many constructs being the same.  However the primary aim is to be able to interop with Java data structures rather than being 
a full on scripting language.

At runtime syntax is parsed into and abstract syntax tree which is then directly interpreted.  This is not designed for speed, or to support JIT.  It's designed for simple usecases and where 
other factors are a more important tradeoff.

## Synopsis

Express is very easy to integrate into your application:

	// Create a context, which is used to resolve entities and custom functions
	ExpressContext context = new DefaultContext();

	// Parse an expression
	ValueExpression expression = new ValueExpression(context, "Hello #{ 'World ' + join(', ', [1, 2, 3]) }");

	// Evaluate the expression
	System.out.println(expression.get(context, null));

## License

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

## Author

Chris Ellis

Twitter: @intrbiz

Web: balsa-framework.org or intrbiz.com

Copyright (c) Chris Ellis 2020
