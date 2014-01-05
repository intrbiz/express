package com.intrbiz.express.operator;

import com.intrbiz.converter.Converter;
import com.intrbiz.converter.converters.StringConverter;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.validator.Validator;
import com.intrbiz.validator.validators.ValidatorText;

public class StringLiteral extends Literal
{

    private String value;

    public StringLiteral(String val, boolean decode)
    {
        super("StringLiteral");
        if (decode) 
            this.value = decode(val);
        else
            this.value = val;
    }

    public StringLiteral()
    {
        super("StringLiteral");
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String toString()
    {
        return "'" + encode(this.getValue()) + "'";
    }

    public static String encode(String s)
    {
        StringBuffer r = new StringBuffer();
        if (s != null)
        {
            for (char c : s.toCharArray())
            {
                switch (c)
                {
                    case '\\':
                        r.append('\\').append('\\');
                        break;
                    case '"':
                        r.append('\\').append('"');
                        break;
                    case '\'':
                        r.append('\\').append('\'');
                        break;
                    case '\b':
                        r.append('\\').append('b');
                        break;
                    case '\f':
                        r.append('\\').append('f');
                        break;
                    case '\n':
                        r.append('\\').append('n');
                        break;
                    case '\r':
                        r.append('\\').append('r');
                        break;
                    case '\t':
                        r.append('\\').append('t');
                        break;
                    case '\0':
                        break;
                    default:
                        r.append(c);
                        break;
                }
            }
        }
        return r.toString();
    }

    public static String decode(String s)
    {
        StringBuffer sb = new StringBuffer();
        char[] cs = s.toCharArray();
        int i = 0, l = cs.length;
        char c;
        while (i < l)
        {
            c = cs[i];
            i++;
            if (c == '\\')
            {
                c = cs[i];
                i++;
                if (c == 'u')
                {
                    sb.appendCodePoint(Integer.parseInt(new String(new char[] {
                            cs[i], cs[++i], cs[++i], cs[++i]
                    }), 16));
                    i++;
                }
                else
                {
                    switch (c)
                    {
                        case 'n':
                            sb.append('\n');
                            break;
                        case 'b':
                            sb.append('\b');
                            break;
                        case 'f':
                            sb.append('\f');
                            break;
                        case 't':
                            sb.append('\t');
                            break;
                        case 'r':
                            sb.append('\r');
                            break;
                        case '\"':
                            sb.append('\"');
                            break;
                        case '\'':
                            sb.append('\'');
                            break;
                        case '\\':
                            sb.append('\\');
                            break;
                        case '/':
                            sb.append('/');
                            break;
                    }
                }
            }
            else
            {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @Override
    public Converter getConverter(ExpressContext context, Object source) throws ExpressException
    {
        return new StringConverter();
    }

    @Override
    public Validator getValidator(ExpressContext context, Object source) throws ExpressException
    {
        return new ValidatorText();
    }
}
