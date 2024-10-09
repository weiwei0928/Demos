package com.ww.skin

import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat

/**
 * @Author weiwei   thsai
 * @Date 2024/10/8 23:23
 */


class SkinAttribute {
    //记录换肤需要操作的view与属性信息
    private val mSkinViews: MutableList<SkinView> = ArrayList()

    fun look(view: View, attrs: AttributeSet) {
        val mSkinPairs: MutableList<SkinPair> = ArrayList()

        for (i in 0 until attrs.attributeCount) {
            //获得属性名
            val attributeName = attrs.getAttributeName(i)
            if (mAttributes.contains(attributeName)) {
                //#
                //8537874835
                //@8537874835
                val attributeValue = attrs.getAttributeValue(i)
                Log.d(
                    TAG,
                    "look attributeValue: $attributeValue"
                )
                //比如color以#开头表示写死的颜色，不可用与换肤
                if (attributeValue.startsWith("#")) {
                    continue
                }
                var resId: Int
                //以？开头的表示使用属性
                if (attributeValue.startsWith("?")) {
                    val attrId = attributeValue.substring(1).toInt()
                    //                    resId = SkinThemeUtils.getResId(view.getContext(), new int[]{attrId})[0];
                    resId = attributeValue.substring(1).toInt()
                } else {
                    //正常以@开头
                    resId = attributeValue.substring(1).toInt()
                }
                Log.e("skin", "  $attributeName = $attributeValue")
                val skinPair = SkinPair(attributeName, resId)
                mSkinPairs.add(skinPair)
            }
        }

        if (!mSkinPairs.isEmpty()) {
            val skinView = SkinView(view, mSkinPairs)
            //如果选择过皮肤，调用一次applySkin加载皮肤的资源
            skinView.applySkin()
            mSkinViews.add(skinView)
        }
    }

    //对view中的所有属性进行修改
    fun applySkin() {
        for (mSkinView in mSkinViews) {
            mSkinView.applySkin()
        }
    }


    internal class SkinView(
        var view: View, //这个view的能被换肤的属性与它对应的id集合
        var skinPairs: List<SkinPair>
    ) {
        //对view中的所有属性进行修改
        fun applySkin() {
            for (skinPair in skinPairs) {
                var left: Drawable? = null
                var top: Drawable? = null
                var right: Drawable? = null
                var bottom: Drawable? = null
                when (skinPair.attributeName) {
                    "background" -> //                        Object background = SkinResources.getInstance().getBackground(skinPair.resId);
//                        //背景可能是@color也可能是@drawable
//                        if (background instanceof Integer) {
//                            view.setBackgroundColor((int) background);
//                        } else {
//                            ViewCompat.setBackground(view, (Drawable) background);
//                        }
                        ViewCompat.setBackground(
                            view,
                            SkinResources.instance?.getDrawable(skinPair.resId)
                        )

                    "src" -> //                        background = SkinResources.getInstance().getBackground(skinPair.resId);
//                        if (background instanceof Integer) {
//                            ((ImageView) view).setImageDrawable(new ColorDrawable((Integer) background));
//                        } else {
//                            ((ImageView) view).setImageDrawable((Drawable) background);
//                        }
                        (view as ImageView).setImageDrawable(
                            SkinResources.instance?.getDrawable(skinPair.resId)
                        )

                    "textColor" -> (view as TextView).setTextColor(
                        SkinResources.instance?.getColorStateList(skinPair.resId)
                    )

                    "drawableLeft" -> left = SkinResources.instance?.getDrawable(skinPair.resId)
                    "drawableTop" -> top = SkinResources.instance?.getDrawable(skinPair.resId)
                    "drawableRight" -> right =
                        SkinResources.instance?.getDrawable(skinPair.resId)

                    "drawableBottom" -> bottom =
                        SkinResources.instance?.getDrawable(skinPair.resId)

                    else -> {}
                }
                if (null != left || null != right || null != top || null != bottom) {
                    (view as TextView).setCompoundDrawablesWithIntrinsicBounds(
                        left,
                        top,
                        right,
                        bottom
                    )
                }
            }
        }
    }

    internal class SkinPair(//属性名
        var attributeName: String, //对应的资源ID
        var resId: Int
    )

    companion object {
        private const val TAG = "SkinAttribute"
        private val mAttributes: MutableList<String> = ArrayList()

        init {
            mAttributes.add("background")
            mAttributes.add("src")
            mAttributes.add("textColor")
            mAttributes.add("drawableLeft")
            mAttributes.add("drawableTop")
            mAttributes.add("drawableRight")
            mAttributes.add("drawableBottom")
        }
    }
}