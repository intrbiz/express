package com.intrbiz.express.script.permissions;

import java.lang.reflect.ReflectPermission;
import java.security.Permissions;
import java.util.PropertyPermission;
import java.util.logging.LoggingPermission;

/**
 * <p>
 * Default permissions which a script is permitted in a restricted context
 * </p>
 * <p>
 * These set of permissions don't allow:
 * </p>
 * <ul>
 *     <li>System.exit() to be called</li>
 *     <li>Getting or setting of system properties</li>
 *     <li>Any file access</li>
 *     <li>Opening network sockets</li>
 *     <li>Setting methods and fields accessible via reflection</li>
 * </ul>
 */
public final class RestrictedScriptPermissions
{
    public static Permissions defaultRestrictedScriptPermissions()
    {
        Permissions permissions = new Permissions();
        // reflect permissions needed by express
        permissions.add(new ReflectPermission("suppressAccessChecks"));
        permissions.add(new ReflectPermission("newProxyInPackage.*"));
        // restrictive set of runtime permissions
        permissions.add(new RuntimePermission("getClassLoader"));
        permissions.add(new RuntimePermission("getProtectionDomain"));
        permissions.add(new RuntimePermission("getFileSystemAttributes"));
        permissions.add(new RuntimePermission("readFileDescriptor"));
        permissions.add(new RuntimePermission("accessClassInPackage.*"));
        permissions.add(new RuntimePermission("defineClassInPackage.*"));
        permissions.add(new RuntimePermission("accessDeclaredMembers"));
        permissions.add(new RuntimePermission("queuePrintJob"));
        permissions.add(new RuntimePermission("getStackTrace"));
        permissions.add(new RuntimePermission("preferences"));
        // allow read properties
        permissions.add(new PropertyPermission("*", "read"));
        // allow init of java.util logging
        permissions.add(new LoggingPermission("control", null));
        permissions.setReadOnly();
        return permissions;
    }

}
