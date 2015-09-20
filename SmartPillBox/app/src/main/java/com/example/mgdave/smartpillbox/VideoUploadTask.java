package com.example.mgdave.smartpillbox;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class VideoUploadTask extends AsyncTask<String,Void,String> {

    private String fileUri, serverUrl, mediaType, mimeType;
    private Context context;

    public VideoUploadTask(Context context, String fileUri, String serverUrl, String mimeType) {
        this.context = context;
        this.fileUri = fileUri;
        this.serverUrl = serverUrl;
        this.mimeType = mimeType;
        if (mimeType.startsWith("image")){
            this.mediaType = "image";
        } else if (mimeType.startsWith("video")){
            this.mediaType = "video";
        }
    }

    @Override
    protected String doInBackground(String... params) {
        if(!checkInternetConnection()){
            return null;
        }
        else if (params==null){
           return null;
        }
        try {
            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1*1024*1024;
            File sourceFile = new File(fileUri);

            if (!sourceFile.isFile()) {
                return null;
            } else {
                FileInputStream fileInputStream = new FileInputStream(fileUri);
                URL url  = new URL(serverUrl);

                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileUri);
                conn.setRequestProperty("Media-Type",mediaType);
                conn.setRequestProperty("Mime-Type",mimeType);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                                + fileUri + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                bytesRead =  fileInputStream.read(buffer, 0, bufferSize);

                while(bytesRead > 0){
                    dos.write(buffer,0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer,0,bufferSize);
                }

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                int serverResponseCode = conn.getResponseCode();
                String serverResonseMessage = conn.getResponseMessage();

                fileInputStream.close();
                dos.flush();
                dos.close();
                return serverResonseMessage;
            }
        } catch (IOException e){
                return null;
        }
    }

    private boolean checkInternetConnection(){
        ConnectivityManager check = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(check==null){
            return false;
        }else {
            return true;
        }
    }
}
