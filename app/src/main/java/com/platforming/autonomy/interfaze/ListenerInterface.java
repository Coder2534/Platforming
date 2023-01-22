package com.platforming.autonomy.interfaze;

public interface ListenerInterface {
    default void onSuccess() {}

    default void onSuccess(String msg) {}

    default void onSuccess(int msg) {}

    default void onSuccess(long msg) {}

    default void onFail() {}
}
