package ca.dal.bartertrader;

import javax.annotation.Nullable;

public class LogInResult {
    @Nullable
    private String error;

    private boolean success;

    LogInResult(@Nullable String error) {
        this.error = error;
    }

    LogInResult(boolean success) {
        this.success = success;
    }

    boolean getSuccess() {
        return success;
    }

    @Nullable
    String getError() {
        return error;
    }
}
