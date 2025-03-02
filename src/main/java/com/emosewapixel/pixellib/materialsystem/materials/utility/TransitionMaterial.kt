package com.emosewapixel.pixellib.materialsystem.materials.utility

import com.emosewapixel.pixellib.materialsystem.materials.Material

/*
Transition Materials are materials that can't exist in a regular state and when used in recipes turn in another Material and as such they can't be
used for generating items/blocks/fluids.
For example, if you're generating decomposition recipes and a material contains Hydrogen (H), you'd want to make a recipe that gives Hydrogen Gas(H2)
and as such you'd have the Hydrogen replaced with Hydrogen Gas and every other material stack in the recipe would be multiplied by the needed amount for that conversion
 */

open class TransitionMaterial(name: String, private val endMaterialFun: () -> Material, val neededAmount: Int) : Material(name, "", -1, -1) {
    val endMaterial: Material
        get() = endMaterialFun.invoke()

    @JvmName("invokeTransition")
    operator fun invoke(builder: TransitionMaterial.() -> Unit) = builder(this)
}