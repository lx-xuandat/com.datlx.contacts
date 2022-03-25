package com.datlx.contacts.utilities;

import android.util.Log;

public class GoogleDriveAPI {
    public static boolean UploadFileData(String pathFile) {
        if (true) {
            Log.d("UploadFileData", "Success");
            return true;
        }
        Log.d("UploadFileData", "Fail");
        return false;
    }

    public static String DownloadFile() {
        if (true) {
            Log.d("DownloadFile", "Success");
            return "path-file-down-load";
        }
        Log.d("DownloadFile", "Fail");
        return null;
    }
}
