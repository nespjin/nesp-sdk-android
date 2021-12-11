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

package com.nesp.sdk.android.os.shell;



import java.io.IOException;
import java.io.OutputStream;
@Deprecated
public class RootShell {
	// private static final String TAG = RootShell.class.getSimpleName();
	private static final String HOOK_ROOT_CMD = "echo \"rootOK\"\n";
	private static RootShell mInstance;
	private OutputStream mOutput;
	private Process mProcess;
	private boolean mRooted;

	private RootShell() {
		this.mRooted = false;
		this.mOutput = null;
		this.mProcess = null;
		requestRoot();
	}

	/**
	 * get a instance
	 */
	public static RootShell open() {
		if (mInstance == null) {
			mInstance = new RootShell();
		}
		return mInstance;
	}

	public void close() {
		if (this.mRooted) {
			execute("exit");
			try {
				if (this.mOutput != null) {
					this.mOutput.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		mInstance = null;
	}

	/**
	 * execute shell cmd
	 *
	 * @param command
	 *            shell command
	 */
	public void execute(String command) {
		try {
			if (this.mOutput == null) {
				return;
			}
			this.mOutput.write(command.getBytes());
			if (!command.trim().endsWith("\n")) {
				this.mOutput.write("\n".getBytes());
			}
			this.mOutput.flush();
		} catch (IOException e) {
		}
	}

	/**
	 * request root permission
	 */
	private void requestRoot() {
		try {
			// this.mProcess = Runtime.getRuntime().exec("sh \n");
			this.mProcess = Runtime.getRuntime().exec("su \n");
			this.mOutput = this.mProcess.getOutputStream();
			this.mOutput.write(HOOK_ROOT_CMD.getBytes());
			this.mOutput.flush();
			mRooted = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isRooted() {
		return mRooted;
	}
}
