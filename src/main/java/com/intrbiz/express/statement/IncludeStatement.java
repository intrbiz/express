package com.intrbiz.express.statement;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.operator.Operator;
import com.intrbiz.express.template.ExpressTemplate;
import com.intrbiz.express.template.loader.TemplateLoader;

public class IncludeStatement extends Statement
{
    private Operator operator;
    
    public IncludeStatement()
    {
        super();
    }

    public IncludeStatement(Operator operator)
    {
        super();
        this.operator = operator;
    }

    public Operator getOperator()
    {
        return operator;
    }

    public void setOperator(Operator operator)
    {
        this.operator = operator;
    }

    @Override
    public void execute(ExpressContext ctx, Object source) throws ExpressException
    {
        TemplateLoader loader = ctx.getTemplateLoader();
        if (loader != null)
        {
            // evaluate the template name
            Object evaledTemplateName = this.operator.get(ctx, source);
            if (evaledTemplateName == null) return;
            String templateName = String.valueOf(evaledTemplateName);
            // load the template
            ExpressTemplate template = loader.load(ctx, templateName);
            // encode it
            if (template != null) template.encode(ctx, source);
        }
    }

    public String toString(String p)
    {
        return p + "include " + this.getOperator() + ";\r\n";
    }
}
