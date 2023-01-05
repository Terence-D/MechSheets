package ca.coffeeshopstudio.meksheets;

import java.io.File;

public interface DownloadProgressCallback {
    void onProgressChanged(long rawProgressAmount, int percent);

    void onFail();

    void onComplete(File downloadedFile);
}
