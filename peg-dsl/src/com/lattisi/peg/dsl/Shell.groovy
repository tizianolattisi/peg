package com.lattisi.peg.dsl

import groovy.util.logging.Log
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer
import org.codehaus.groovy.control.customizers.ImportCustomizer
import org.codehaus.groovy.control.customizers.SecureASTCustomizer

/**
 * Created with IntelliJ IDEA.
 * User: tiziano
 * Date: 05/10/13
 * Time: 10:40
 * To change this template use File | Settings | File Templates.
 */
class Shell extends GroovyShell{

    private Language language

    Shell(Binding binding, CompilerConfiguration config) {
        super(binding, config)
    }

    public static GroovyShell build(){
        def conf = new CompilerConfiguration()
        conf.scriptBaseClass = LanguageBaseScriptClass.class.name

        def imports = new ImportCustomizer()
        imports.addStaticStars(com.lattisi.peg.dsl.EntityType.name)

        def secure = new SecureASTCustomizer()
        secure.with {
            closuresAllowed = false
            methodDefinitionAllowed = false

            starImportsWhitelist = []
            importsWhitelist = []
            staticImportsWhitelist = []

            staticStarImportsWhitelist = [com.lattisi.peg.dsl.EntityType.name]

            tokensWhitelist = [com.lattisi.peg.dsl.EntityType.triangle,
                    com.lattisi.peg.dsl.EntityType.segment]

            // to secure...
            //constantTypesClassesWhiteList = []
            //receiversClassesWhiteList = []
        }

        def transf = new ASTTransformationCustomizer(Log)

        conf.addCompilationCustomizers(imports, transf, secure)

        def language = new Language()
        def binding = new Binding([language: language])

        //GroovyShell shell = new GroovyShell(binding, conf)
        Shell shell = new Shell(binding, conf)
        shell.setLanguage(language)

        return shell
    }

    void setLanguage(Language language) {
        this.language = language
    }

    Language getLanguage() {
        return language
    }
}
