package com.ww.skin

import android.app.Application
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable

/**
 * @Author weiwei
 * @Date 2024/10/8 23:24
 */

class SkinResources private constructor(application: Application) {
    private var isDefaultSkin = false
    private val mAppResources: Resources = application.resources
    private var mSkinResources: Resources? = null

    private var mSkinPkgName: String? = null

    /**
     * 通过原始app中的resid获取其对应的resources中的名字，类型
     * 从而根据名字和类型获取到对应的皮肤包中的信息
     *
     * @param resId
     * @return
     */
    private fun getIdentifier(resId: Int): Int {
        if (isDefaultSkin) return resId
        //在皮肤包中不一定就是当前程序的   id
        //获取对应id在当前的名称 colorPrimary
        val resName = mAppResources.getResourceEntryName(resId)
        val resType = mAppResources.getResourceTypeName(resId)
        return mSkinResources!!.getIdentifier(resName, resType, mSkinPkgName)
    }

    fun getColor(resId: Int): Int {
        if (isDefaultSkin) {
            return mAppResources.getColor(resId)
        }
        val skinId = getIdentifier(resId)
        if (skinId == 0) {
            return mAppResources.getColor(resId)
        }
        return mSkinResources!!.getColor(skinId)
    }

    fun getColorStateList(resId: Int): ColorStateList {
        if (isDefaultSkin) {
            return mAppResources.getColorStateList(resId)
        }
        val skinId = getIdentifier(resId)
        if (skinId == 0) {
            return mAppResources.getColorStateList(resId)
        }
        return mSkinResources!!.getColorStateList(skinId)
    }

    fun getDrawable(resId: Int): Drawable {
        if (isDefaultSkin) {
            return mAppResources.getDrawable(resId)
        }
        val skinId = getIdentifier(resId)
        if (skinId == 0) {
            return mAppResources.getDrawable(resId)
        }
        return mSkinResources!!.getDrawable(skinId)
    }

    fun applySkin(skinResource: Resources?, packageName: String?) {
        this.mSkinResources = skinResource
        this.mSkinPkgName = packageName
        isDefaultSkin = false
    }

    fun reset() {
        isDefaultSkin = true
    }

    companion object {
        var instance: SkinResources? = null

        fun init(application: Application) {
            if (instance == null) {
                synchronized(SkinResources::class.java) {
                    if (instance == null) {
                        instance = SkinResources(application)
                    }
                }
            }
        }
    }
}