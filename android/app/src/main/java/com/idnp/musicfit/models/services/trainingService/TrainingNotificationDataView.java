package com.idnp.musicfit.models.services.trainingService;

import com.idnp.musicfit.R;

public class TrainingNotificationDataView {


    public static final String STATE_PLAY_TRAINING_LABEL="Training";
    public static final String STATE_PAUSE_TRAINING_LABEL="Resting";
    private String statusTitle;
    private int backgroundStatus;

    public TrainingNotificationDataView(String statusTitle, int backgroundStatus) {
        this.statusTitle = statusTitle;
        this.backgroundStatus = backgroundStatus;
    }

    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    public int getBackgroundStatus() {
        return backgroundStatus;
    }

    public void setBackgroundStatus(int backgroundStatus) {
        this.backgroundStatus = backgroundStatus;
    }
}
