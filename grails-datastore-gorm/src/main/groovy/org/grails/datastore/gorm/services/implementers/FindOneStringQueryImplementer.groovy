package org.grails.datastore.gorm.services.implementers

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.stmt.Statement
import org.grails.datastore.gorm.GormEntity
import org.grails.datastore.mapping.reflect.AstUtils

import static org.codehaus.groovy.ast.tools.GeneralUtils.callX
import static org.codehaus.groovy.ast.tools.GeneralUtils.returnS

/**
 * Implements support for String-based queries that return a domain class
 *
 * @author Graeme Rocher
 * @since 6.1
 */
@CompileStatic
class FindOneStringQueryImplementer extends AbstractStringQueryImplementer implements SingleResultServiceImplementer<GormEntity> {
    @Override
    protected Statement buildQueryReturnStatement(ClassNode domainClassNode, MethodNode abstractMethodNode, MethodNode newMethodNode, Expression args) {
        returnS(
                callX(  findDomainClassForConnectionId(domainClassNode, newMethodNode),
                        getFindMethodToInvoke(domainClassNode, newMethodNode),
                        args)
        )
    }

    protected String getFindMethodToInvoke(ClassNode classNode, MethodNode methodNode) {
        return "find"
    }

    @Override
    protected boolean isCompatibleReturnType(ClassNode domainClass, MethodNode methodNode, ClassNode returnType, String prefix) {
        return AstUtils.isDomainClass(returnType)
    }
}
