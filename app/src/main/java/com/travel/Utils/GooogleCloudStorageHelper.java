package com.travel.Utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class GooogleCloudStorageHelper {
    private static final String PROJECT_ID = "androidapp-418407";
    private static final String BUCKET_NAME = "app-bucket1";
    private static final String FOLDER_PATH = "Profile";

    private static final String JSON_KEY_PATH = "cloud-storage.json";

    public static void uploadImageToStorage(Context context, byte[] imageData, UploadImageListener listener) {
        new UploadImageTask(context, imageData, listener).execute();
    }

    private static class UploadImageTask extends AsyncTask<Void, Void, String> {

        private Context context;
        private byte[] imageData;
        private UploadImageListener listener;

        public UploadImageTask(Context context, byte[] imageData, UploadImageListener listener) {
            this.context = context;
            this.imageData = imageData;
            this.listener = listener;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                AssetManager assetManager = context.getAssets();
                InputStream inputStream = assetManager.open(JSON_KEY_PATH);

                Storage storage = StorageOptions.newBuilder()
                        .setProjectId(PROJECT_ID)
                        .setCredentials(GoogleCredentials.fromStream(inputStream))
                        .build()
                        .getService();

                String fileName = "image_" + System.currentTimeMillis() + ".jpg";
                String objectName = FOLDER_PATH + "/" + fileName;

                BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
                Blob blob = storage.create(BlobInfo.newBuilder(blobId).build(), imageData);
                return blob.getMediaLink();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String imageUrl) {
            if (listener != null) {
                if (imageUrl != null) {
                    listener.onUploadSuccess(imageUrl);
                } else {
                    listener.onUploadFailed();
                }
            }
        }
    }

    public interface UploadImageListener {
        void onUploadSuccess(String imageUrl);
        void onUploadFailed();
    }
}
