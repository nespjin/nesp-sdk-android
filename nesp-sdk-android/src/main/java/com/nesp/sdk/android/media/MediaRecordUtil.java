package com.nesp.sdk.android.media;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import androidx.annotation.RequiresPermission;

import java.io.File;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/9/6 9:19
 **/
public class MediaRecordUtil {
    private static final String TAG = "MediaRecordUtils";

    //returns whether the microphone is available
    public static boolean isMicrophoneAvailable(Context context) {
        MediaRecorder recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        recorder.setOutputFile(new File(context.getCacheDir(), "MediaUtil#micAvailTestFile").getAbsolutePath());
        boolean available = true;
        try {
            recorder.prepare();
            recorder.start();
        } catch (Exception exception) {
            available = false;
        }
        recorder.release();
        return available;
    }

    @RequiresPermission(android.Manifest.permission.RECORD_AUDIO)
    public static boolean isMicAvailability() {
        boolean available = true;
        AudioRecord recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, 44100,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_DEFAULT, 44100);
        try {
            if (recorder.getRecordingState() != AudioRecord.RECORDSTATE_STOPPED) {
                available = false;

            }

            recorder.startRecording();
            if (recorder.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
                recorder.stop();
                available = false;

            }
            recorder.stop();
        } finally {
            recorder.release();
            recorder = null;
        }

        return available;
    }

}
