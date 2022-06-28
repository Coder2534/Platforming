package com.android.platforming.interfaze;

public interface ListenerInterface {
    default void onSuccess() {

    }

    default void onSuccess(String msg) {

    }

    default void onFail() {

    }
}
