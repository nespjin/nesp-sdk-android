/*
 *
 *   Copyright (c) 2020  NESP Technology Corporation. All rights reserved.
 *
 *   This program is not free software; you can't redistribute it and/or modify it
 *   without the permit of team manager.
 *
 *   Unless required by applicable law or agreed to in writing.
 *
 *   If you have any questions or if you find a bug,
 *   please contact the author by email or ask for Issues.
 *
 *   Author:JinZhaolu <1756404649@qq.com>
 */

package com.nesp.sdk.android.page

import kotlin.math.max
import kotlin.math.min

/**
 *
 * Author: <a href="mailto:jinzhaolu@numob.com">Jack Email:jinzhaolu@numob.com</a>
 * Time: Created 2020/7/30 4:57 PM
 * Project:
 * Description:
 **/
class PageInfo<T> {

    /**
     * 是否有上一页
     */
    val haveLastPage
        get() = currentPage > 0

    /**
     * 是否有下一页
     */
    val haveNextPage
        get() = currentPage < pageCount - 1

    /**
     * 总页数
     */
    var pageCount = 0L

    val isSinglePage: Boolean
        get() {
            return pageCount == 0L || pageCount == 1L
        }

    /**
     * 当前页
     */
    var currentPage = 0L
        private set

    /**
     * 第一页
     */
    fun firstPage() {
        setCurrentPage(0)
    }

    /**
     * 下一页
     */
    fun nextPage(): Boolean {
        if (!haveNextPage) return false
        setCurrentPage(currentPage + 1)
        return true
    }

    /**
     * 上一页
     */
    fun prePage(): Boolean {
        if (!haveLastPage) return false
        setCurrentPage(currentPage - 1)
        return true
    }

    /**
     * 最后一页
     */
    fun lastPage() {
        setCurrentPage(pageCount - 1)
    }

    fun refreshCurrentPage() {
        setCurrentPage(currentPage)
    }

    fun setCurrentPage(currentPage: Long) {
        val curPage = max(min(max(currentPage, 0L), pageCount - 1), 0)
        this.currentPage = curPage

        onPageChangeListener?.onPageChanged(curPage, data, pageCount)
    }

    /**
     * 当前页的数据
     */
    var data: ArrayList<T> = arrayListOf()

    /**
     * 各页面选择的数据
     */
    var selectedData: ArrayList<T> = arrayListOf()

    var onPageChangeListener: OnPageChangeListener<T>? = null

    interface OnPageChangeListener<T> {

        fun onPageChanged(currentPage: Long, data: ArrayList<T>, pageCount: Long)
    }
}