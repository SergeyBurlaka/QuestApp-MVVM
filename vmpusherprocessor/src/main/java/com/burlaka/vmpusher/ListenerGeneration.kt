package com.burlaka.vmpusher


import com.burlaka.vmpusherannotation.BindUiAction
import com.burlaka.vmpusherannotation.BindUiListener
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.Name
import javax.lang.model.element.TypeElement

@AutoService(Processor::class) // For registering the service
@SupportedSourceVersion(SourceVersion.RELEASE_8) // to support Java 8
@SupportedOptions(ListenerGeneration.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class ListenerGeneration : AbstractProcessor() {

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
        const val LISTENER = "listenerId"
        const val ACTION = "actionId"
        //todo hard core package name!
        const val PACKAGE_NAME = "com.jellyworkz.processor"
    }

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {

        val generatedSourcesRoot: String =
            processingEnv.options[ListenerGeneration.KAPT_KOTLIN_GENERATED_OPTION_NAME].orEmpty()

        if (generatedSourcesRoot.isEmpty()) {
            processingEnv.messager.errormessage { "Can't find the target directory for generated Kotlin files." }
            return false
        }

        val annotatedMethods = ArrayList<ExecutableElement>()

        roundEnv.getElementsAnnotatedWith(BindUiAction::class.java)
            .forEach { methodElement ->
                if (methodElement.kind != ElementKind.METHOD) {
                    processingEnv.messager.errormessage { "Can only be applied to functions,  element: $methodElement " }
                    return false
                }
                (methodElement as ExecutableElement).apply {
                    annotatedMethods.add(this)
                }
            }

        val annotatedListeners = HashSet<TypeElement>()

        roundEnv.getElementsAnnotatedWith(BindUiListener::class.java)
            .forEach { methodElement ->
                if (methodElement.kind != ElementKind.INTERFACE) {
                    processingEnv.messager.errormessage { "Can only be applied to functions,  element: $methodElement " }
                    return false
                }
                (methodElement as TypeElement).apply {
                    annotatedListeners.add(this)
                }
            }

        annotatedListeners.forEach { typeElement ->

            val funBuildsCash = ArrayList<FunSpec.Builder>()

            val listenerLastName = typeElement.simpleName

            //build generating method signature
            val bindFuncBuilder =

                FunSpec.builder(name = "performTaskFor$listenerLastName")
                    .addModifiers(KModifier.PUBLIC)
                    .addParameter(
                        name = LISTENER,
                        type = typeElement.asType().asTypeName()
                    )
                    .addParameter(
                        name = ACTION,
                        type = ClassName("kotlin", "Int")
                    )

            //build generating method body
            bindFuncBuilder.addStatement("when{")

            val listenerId = typeElement.getAnnotation(BindUiListener::class.java).id


            if (listenerId != -1) {

                annotatedMethods.forEach { annotatedMethod ->
                    if (annotatedMethod.getAnnotation(BindUiAction::class.java).listenerId == listenerId) {
                        funBuildsCash.addAll(getFunBuilders(bindFuncBuilder, annotatedMethod, listenerLastName))
                    }
                }

            } else {

                typeElement.enclosedElements.forEach { innerMethods ->

                    val methodsCash = HashSet<String>()
                    annotatedMethods.forEach { annotatedMethod ->
                        annotatedMethod.simpleName.apply {

                            methodsCash.also {
                                val oldSize = it.size
                                it.add(toString())
                                if (it.size == oldSize) {
                                    processingEnv.messager.errormessage { "If you not specify listener id, all with @BindUiAction annotated methods must have unique names!" }
                                    throw RuntimeException("methods must have unique names!")
                                }
                            }

                            if (this == innerMethods.simpleName) {
                                funBuildsCash.addAll(getFunBuilders(bindFuncBuilder, annotatedMethod, listenerLastName))
                            }
                        }

                    }
                }
            }

            bindFuncBuilder.addStatement("}")

            funBuildsCash.add(bindFuncBuilder)

            //write method
            val file = File(generatedSourcesRoot)
            file.mkdir()

            FileSpec.builder(
                "$PACKAGE_NAME.$listenerLastName",
                "BindActionsGeneratedFor${typeElement.simpleName}"
            ).apply {
                funBuildsCash.forEach { funBuilder ->
                    this.addFunction(funBuilder.build())
                }
                this.build()
                    .writeTo(file)
            }
        }
        return false
    }

    private fun getFunBuilders(
        bindFuncBuilder: FunSpec.Builder,
        annotatedMethod: ExecutableElement,
        listenerLastName: Name
    ) = ArrayList<FunSpec.Builder>().apply {

        bindFuncBuilder.addStatement(
            "$ACTION == ${annotatedMethod.getAnnotation(BindUiAction::class.java).actionId} -> {" + "\n"
                    + " $LISTENER.${annotatedMethod.simpleName}()" + "\n"
                    + "}"
        )

        FunSpec
            .builder(name = "${annotatedMethod.simpleName}For$listenerLastName")
            .addModifiers(KModifier.PUBLIC)
            .returns(Int::class)
            .addCode(
                """
                                        | return ${annotatedMethod.getAnnotation(BindUiAction::class.java).actionId}
                                        |""".trimMargin()
            ).apply {
                add(this)
            }
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> =
        mutableSetOf(BindUiAction::class.java.canonicalName).apply {
            add(BindUiListener::class.java.canonicalName)
        }

}

