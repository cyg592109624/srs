package com.sunrise.srs.utils;

import android.media.AudioManager;
import android.support.annotation.NonNull;

/**
 * Created by ChuHui on 2017/9/15.
 */

public class SoundsUtils {
    private static AudioManager mAudioManager;
    private static SoundsUtils soundsUtils;

    private SoundsUtils() {

    }

    public static void setAudioManager(@NonNull AudioManager manager) {
        mAudioManager = manager;
    }

    public static int getRingMode() {
        int mode = mAudioManager.getRingerMode();
        switch (mode) {
            default:
                break;
            case AudioManager.RINGER_MODE_NORMAL:
                //普通模式 2
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                //振动模式 1
                break;
            case AudioManager.RINGER_MODE_SILENT:
                //静音模式 0
                break;
        }
        return mode;
    }

    public static int getVoice(@NonNull int audioFlag) {
        int result = 0;
        switch (audioFlag) {
            default:
                result = -1;
                break;
            case AudioManager.STREAM_VOICE_CALL:
                result = getVoiceCall();
                break;
            case AudioManager.STREAM_SYSTEM:
                result = getVoiceSystem();
                break;
            case AudioManager.STREAM_RING:
                result = getVoiceRing();
                break;
            case AudioManager.STREAM_MUSIC:
                result = getVoiceMusic();
                break;
            case AudioManager.STREAM_ALARM:
                result = getVoiceAlarm();
                break;
        }
        return result;
    }

    public static int getVoiceMax(@NonNull int audioFlag) {
        int result = 0;
        switch (audioFlag) {
            default:
                result = -1;
                break;
            case AudioManager.STREAM_VOICE_CALL:
                result = getVoiceCallMax();
                break;
            case AudioManager.STREAM_SYSTEM:
                result = getVoiceSystemMax();
                break;
            case AudioManager.STREAM_RING:
                result = getVoiceRingMax();
                break;
            case AudioManager.STREAM_MUSIC:
                result = getVoiceMusicMax();
                break;
            case AudioManager.STREAM_ALARM:
                result = getVoiceAlarmMax();
                break;
        }
        return result;
    }

    public static void setVoiceValue(int audioFlag, @NonNull int value) {
        switch (audioFlag) {
            default:
                break;
            case AudioManager.STREAM_VOICE_CALL:
                setVoiceCall(value);
                break;
            case AudioManager.STREAM_SYSTEM:
                setVoiceSystem(value);
                break;
            case AudioManager.STREAM_RING:
                setVoiceRing(value);
                break;
            case AudioManager.STREAM_MUSIC:
                setVoiceMusic(value);
                break;
            case AudioManager.STREAM_ALARM:
                setVoiceAlarm(value);
                break;
        }
    }


    /**
     * 获取当前通话音量
     *
     * @return
     */
    public static int getVoiceCall() {
        return mAudioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
    }

    public static void setVoiceCall(@NonNull int value) {
        mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, value, AudioManager.FLAG_PLAY_SOUND);
    }

    /**
     * 获取最大通话音量
     *
     * @return
     */
    public static int getVoiceCallMax() {
        return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
    }


    /**
     * 获取当前系统音量
     *
     * @return
     */
    public static int getVoiceSystem() {
        return mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
    }


    public static void setVoiceSystem(@NonNull int value) {
        mAudioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, value, AudioManager.FLAG_PLAY_SOUND);
    }

    /**
     * 获取最大系统音量
     *
     * @return
     */
    public static int getVoiceSystemMax() {
        return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
    }


    /**
     * 获取当前铃声音量
     *
     * @return
     */
    public static int getVoiceRing() {
        return mAudioManager.getStreamVolume(AudioManager.STREAM_RING);
    }


    public static void setVoiceRing(@NonNull int value) {
        mAudioManager.setStreamVolume(AudioManager.STREAM_RING, value, AudioManager.FLAG_PLAY_SOUND);
    }

    /**
     * 获取最大铃声音量
     *
     * @return
     */
    public static int getVoiceRingMax() {
        return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
    }

    /**
     * 获取当前音乐音量
     *
     * @return
     */
    public static int getVoiceMusic() {
        return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    public static void setVoiceMusic(@NonNull int value) {
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, value, AudioManager.FLAG_PLAY_SOUND);
    }

    /**
     * 获取最大音乐音量
     *
     * @return
     */
    public static int getVoiceMusicMax() {
        return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    /**
     * 获取当前提示音量
     *
     * @return
     */
    public static int getVoiceAlarm() {
        return mAudioManager.getStreamVolume(AudioManager.STREAM_ALARM);
    }

    public static void setVoiceAlarm(@NonNull int value) {
        mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, value, AudioManager.FLAG_PLAY_SOUND);
    }


    /**
     * 获取最大提示音量
     *
     * @return
     */
    public static int getVoiceAlarmMax() {
        return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
    }
}

