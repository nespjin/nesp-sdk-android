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

package com.nesp.sdk.android.media

import java.util.*
import kotlin.collections.ArrayList

/**
 *
 *
 * @team NESP Technology
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @time: Created 2020/2/29 9:32 AM
 * @project FishMovie
 **/
@Deprecated("")
class FFmpeg {

    class Commands {
        companion object {
            fun getDownloadM3U8ToMP4(url: String, savePath: String, outFileName: String): Array<String?> {
                val cmdlist = RxFFmpegCommandList()
                cmdlist.append("-i")
                cmdlist.append(url)
                cmdlist.append("-c")
                cmdlist.append("copy")
                cmdlist.append("$savePath/$outFileName")
                return cmdlist.build()
            }

            fun getDownloadM3U8ToMP4(url: String, savePath: String): Array<String?> {
                val cmdlist = RxFFmpegCommandList()
                cmdlist.append("-i")
                cmdlist.append(url)
                cmdlist.append("-c")
                cmdlist.append("copy")
                cmdlist.append(savePath)
                return cmdlist.build()
            }
        }
    }

    class RxFFmpegCommandList : ArrayList<String?>() {
        /**
         * 清除命令集合
         */
        fun clearCommands() {
            this.clear()
        }

        fun append(s: String?): RxFFmpegCommandList {
            this.add(s)
            return this
        }

        fun build(): Array<String?> {
            val command = arrayOfNulls<String>(size)
            for (i in this.indices) {
                command[i] = this[i]
            }
            return command
        }

        init {
            this.add("ffmpeg")
            this.add("-y")
        }
    }

}