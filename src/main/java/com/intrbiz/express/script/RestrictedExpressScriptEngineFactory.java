package com.intrbiz.express.script;

import java.security.AccessControlContext;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.ProtectionDomain;

import com.intrbiz.express.DefaultScriptContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.ExpressExtensionRegistry;
import com.intrbiz.express.script.permissions.RestrictedScriptPermissions;

public class RestrictedExpressScriptEngineFactory extends ExpressScriptEngineFactory
{
    private final CodeSource source;
    
    private final PermissionCollection permissions;
    
    private final ProtectionDomain domain;
    
    private final AccessControlContext context;
    
    public RestrictedExpressScriptEngineFactory(CodeSource source, PermissionCollection permissions)
    {
        super();
        this.source = source;
        this.permissions = permissions;
        // setup access control context
        this.domain = new ProtectionDomain(this.source, this.permissions);
        this.context = new AccessControlContext(new ProtectionDomain[] { this.domain });
    }
    
    public RestrictedExpressScriptEngineFactory(PermissionCollection permissions)
    {
        this(new CodeSource(null, (CodeSigner[]) null), permissions);
    }
    
    public RestrictedExpressScriptEngineFactory()
    {
        this(RestrictedScriptPermissions.defaultRestrictedScriptPermissions());
    }

    @Override
    public ExpressScriptEngine parse(String script) throws ExpressException
    {
        ExpressExtensionRegistry registry = new ExpressExtensionRegistry("script", this.globalRegistry);
        return new ExpressScriptEngine(registry, this::checkJavaAccess, new RestrictedValueScript(new DefaultScriptContext(registry), script, this.context));
    }
}
