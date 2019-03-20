package com.burlaka.vmpusherannotation

/**
 * Note!
 * If you not specify [listenerId] all annotated methods must has unique names.
 *
 * @param actionId specify id for action
 * @param listenerId specify listener for id when need time compile optimization. When annotated methods to much and compile speed to slow.
 *
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class BindUiAction(val actionId: Int, val listenerId: Int = -1)

/**
 * @param id specify listener for id when annotated methods to much and need time compile optimization.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class BindUiListener(val id: Int = -1)