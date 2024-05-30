package com.ww.demos

import android.app.Activity
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs


abstract class BaseAppLayoutActivity(private val isAddInNestedScrollView: Boolean = true) :
    Activity() {

//    var viewInToolbarLayout: View? = null
//    var viewInExpendLayout: View? = null
//
//    var mEmptyView: EmptyView? = null
//    var mNestScrollView: NestedScrollView? = null
//    var mNewToolbar: Toolbar? = null
//    var mBaseAppBarLayout: AppBarLayout? = null
//
//    // 客服按钮
//    var mMenuSecondIv: ImageView? = null
//
//    // 帮助按钮
//    var mMenuFirstIv: ImageView? = null
//
//    // 返回按钮
//    var mMenuBackIv: ImageView? = null
//
//    // 标题
//    var mMenuTitleView: TextView? = null
//    var mMenuTitleLayout: View? = null
//
//    //loading时间
//    var startTime: Long = 0
//    var showTime: Long = 0
//    var hideTime: Long = 0
//
//    /**
//     * 统计页面加载耗时
//     */
//    private var viewStartTime: Long = 0
//
//    /**
//     * 页面加载dialog
//     */
//    private var loadingDialog: LoadingDialog? = null
//
//    /**
//     * loading是否加载过
//     */
//    private var loadingIsTracker: Long = 0
//
//    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
//        loadingIsTracker = System.currentTimeMillis()
//        super.onCreate(savedInstanceState)
//    }
//
//    companion object {
//        // 有menu情况下 返回键点击类型 第一个菜单点击类型 第二个菜单点击类型
//        const val MENU_TITLE_BACK_TYPE = 0
//        const val MENU_TITLE_FIRST_TYPE = 1
//        const val MENU_TITLE_SECOND_TYPE = 2
//    }
//
//    private val matchLayoutParams = ViewGroup.LayoutParams(
//        ViewGroup.LayoutParams.MATCH_PARENT,
//        ViewGroup.LayoutParams.MATCH_PARENT
//    )
//
//    override fun getLayout(): Int {
//        return R.layout.common_ktx_activity_applayout_base_layout
//    }
//
//    override fun initEventAndData() {
//        setHasToolBar(false)
//        baseToolBar.title = ""
//        mNewToolbar = baseToolBar
//        setSupportActionBar(baseToolBar)
//        mEmptyView = emptyView
//        mNestScrollView = baseNestedScrollView
//        mBaseAppBarLayout = baseAppBarLayout
//        initData()
//
//        setAppbarLayout()
//        addExpendLayout()
//        addToolbarLayout()
//        addFlowUiView()
//        addUiView()
//
//        initView()
//    }
//
//    private fun setAppbarLayout() {
//        mBaseAppBarLayout?.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
//            val maxScroll = appBarLayout.totalScrollRange
//            val percentage = abs(verticalOffset).toFloat() / maxScroll.toFloat()
//            handleOnTitle(percentage)
//        })
//    }
//
//    // 控制Title的显示
//    open fun handleOnTitle(percentage: Float) {
//        if (percentage == 0F) {
//            if (isShowMenu()) {
//                mMenuTitleView?.visibility = View.INVISIBLE
//            } else {
//                viewInToolbarLayout?.visibility = View.INVISIBLE
//            }
//            viewInExpendLayout?.visibility = View.VISIBLE
//        } else if (percentage == 1F) {
//            if (isShowMenu()) {
//                mMenuTitleView?.visibility = View.VISIBLE
//            } else {
//                viewInToolbarLayout?.visibility = View.VISIBLE
//            }
//            viewInExpendLayout?.visibility = View.INVISIBLE
//        }
//    }
//
//    private fun addExpendLayout() {
//        getExpendLayout()?.let {
//            viewInExpendLayout = it
//            baseExpendLayout?.addView(it)
//        }
//    }
//
//    private fun addToolbarLayout() {
//        if (isShowMenu()) {
//            mMenuTitleLayout =
//                LayoutInflater.from(this).inflate(R.layout.common_ktx_menu_title_layout, null)
//            mMenuTitleView = mMenuTitleLayout?.findViewById(R.id.transf_class_menu_title_tv)
//            mMenuFirstIv = mMenuTitleLayout?.findViewById(R.id.transf_classes_menu_ic_help_iv)
//            mMenuSecondIv = mMenuTitleLayout?.findViewById(R.id.transf_class_menu_customer_iv)
//            mMenuBackIv = mMenuTitleLayout?.findViewById(R.id.transf_classes_menu_back_iv)
//            baseToolBar?.addView(mMenuTitleLayout, matchLayoutParams)
//        } else {
//            getToolbarLayout()?.let {
//                viewInToolbarLayout = it
//                if (it is ToolbarTextView) {
//                    baseToolBar?.addView(it, it.layoutParams)
//                } else {
//                    baseToolBar?.addView(it, matchLayoutParams)
//                }
//            }
//        }
//
//    }
//
//
//    private fun addFlowUiView() {
//        getFlowUIView()?.let {
//            baseAppBarLayout?.addView(it, matchLayoutParams)
//        }
//    }
//
//    private fun addUiView() {
//        val parentViewGroup = if (isAddInNestedScrollView) baseNestedScrollView else baseCoordinator
//        mNestScrollView?.visibility = if (isAddInNestedScrollView) View.VISIBLE else View.GONE
//        getUIView(parentViewGroup)?.let {
//            if (it.parent == null) {
//                parentViewGroup?.addView(it, matchLayoutParams)
//            }
//        }
//    }
//
//    // 获取menuTitle根布局
//    fun getMeunTitleLayout() = mMenuTitleLayout
//
//    // 是否显示右上角menu 如果显示 则需要自定义
//    open fun isShowMenu() = false
//
//    /**
//     * 自定义自己的展开view
//     * 父类:RelativeLayout
//     */
//    abstract fun getExpendLayout(): View?
//
//    /**
//     * 自定义自己的ToolBar里的内容
//     * 父类:Toolbar
//     */
//    abstract fun getToolbarLayout(): View?
//
//    /**
//     * 自定义自己的悬浮的内容
//     * 父类:CollapsingToolbarLayout
//     */
//    abstract fun getFlowUIView(): View?
//
//    /**
//     * 自定义自己的内容
//     * 父类:CoordinatorLayout
//     */
//    abstract fun getUIView(parentView: ViewGroup?): View?
//
//    open fun initData() {}
//
//    open fun initView() {}
//
//    override fun setListener() {
//
//        mMenuBackIv?.setOnClickListener {
//            menuTitleListener?.invoke(MENU_TITLE_BACK_TYPE)
//        }
//        mMenuFirstIv?.setOnClickListener {
//            menuTitleListener?.invoke(MENU_TITLE_FIRST_TYPE)
//        }
//        mMenuSecondIv?.setOnClickListener {
//            menuTitleListener?.invoke(MENU_TITLE_SECOND_TYPE)
//        }
//
//    }
//
//    var menuTitleListener: ((type: Int) -> Unit)? = null
//
//    override fun onDestroy() {
//        super.onDestroy()
//        dismissLoading()
//        loadingDialog = null
//    }
//
//    //isNeedLoadingTrack 是否需要做loading显示的埋点
//    open fun showLoading(
//        msg: String = resources?.getString(R.string.common_ktx_loading),
//        isNeedLoadingTrack: Boolean = false,
//    ) {
//        if (isNeedLoadingTrack) startTime = System.currentTimeMillis()
//        if (loadingDialog == null)
//            loadingDialog = LoadingDialog(mContext, R.style.loadingStyle)
//        showLoadingExt(loadingDialog, msg)
//        if (isNeedLoadingTrack) showTime = System.currentTimeMillis()
//    }
//
//    open fun dismissLoading() {
//        dismissLoadingExt(loadingDialog)
//    }
//
//    /**
//     * loading显示时间上报
//     */
//    fun apmLoadingTracker(
//        pageType: ApmViewCountUtils.PageType,
//        isNetSuccess: Boolean,
//    ) {
//
//        if (loadingIsTracker == 0L) {
//            return
//        }
//        // 调用 - 显示
//        var callDuration = if (startTime != 0L && showTime != 0L) showTime - startTime else 0
//        // 显示 - 消失
//        var appearDuration = if (showTime != 0L && hideTime != 0L) hideTime - showTime else 0
//        // 调用 - 消失
//        var disappearDuration = if (startTime != 0L && hideTime != 0L) hideTime - startTime else 0
//        ApmViewCountUtils.apmLoading(pageType,
//            this.javaClass.name,
//            callDuration,
//            appearDuration,
//            disappearDuration,
//            isNetSuccess)
//        loadingIsTracker = 0L
//    }
//
//    /**
//     * apm 统计页面核心接口请求时常 埋点
//     *
//     * @param pageType  ACTIVITY FRAGMENT DIALOG
//     * @param isNetSuccess 是否是请求接口成功
//     * @param isCache 是否是缓存，是缓存直接return 。默认false
//     */
//    protected fun apmPageLoadTimeTracker(
//        pageType: ApmViewCountUtils.PageType,
//        isNetSuccess: Boolean,
//        isCache: Boolean = false,
//    ) {
//        if (isCache) {
//            return
//        }
//        if (viewStartTime == 0L) {
//            return
//        }
//        LogUtil.e("viewStartTime : $viewStartTime")
//        ApmViewCountUtils.uploadViewCount(
//            pageType,
//            this.javaClass.name,
//            System.currentTimeMillis() - viewStartTime,
//            isNetSuccess
//        )
//        viewStartTime = 0
//    }
//
//    private var xesResources: Resources? = null
//    override fun getResources(): Resources {
//        if (xesResources == null) {
//            xesResources = ProxyResources(super.getResources())
//        }
//        return xesResources!!
//    }
}