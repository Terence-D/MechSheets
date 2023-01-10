package ca.coffeeshopstudio.meksheets.utils;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Downloader {
    OkHttpClient client;

    private DownloadProgressCallback progressCallback;

    public Downloader(DownloadProgressCallback progressCallback) {
        this.progressCallback = progressCallback;
        client = new OkHttpClient();
    }

    public void Start(String url, File fileToWrite) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                // handle failure
                progressCallback.onFail();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        progressCallback.onFail();
                        //throw new IOException("Unexpected code " + response);
                    }

                    assert responseBody != null;
                    try (InputStream inputStream = responseBody.byteStream();
                         FileOutputStream outputStream = new FileOutputStream(fileToWrite)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        long totalBytesRead = 0;
                        long totalBytes = Objects.requireNonNull(response.body()).contentLength();
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                            totalBytesRead += bytesRead;
                            progressCallback.onProgressChanged(totalBytesRead, (int) ((totalBytesRead * 100) / totalBytes));
                        }
                        progressCallback.onComplete(fileToWrite);
                        outputStream.flush();
                    }
                }
            }
        });
    }

    public void setProgressCallback(DownloadProgressCallback callback) {
        progressCallback = callback;
    }
}

