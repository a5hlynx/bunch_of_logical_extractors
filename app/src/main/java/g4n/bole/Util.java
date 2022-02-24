package g4n.bole;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class Util{
    @SuppressWarnings("deprecation")
    public static String getDirForEvidence(Context context){
        String path = "";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R ){
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
        }else{
            path = context.getExternalFilesDir(null).toString();
        }

        path = path + "/" + context.getPackageName();
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            LocalDateTime d = null;
            d = LocalDateTime.now();
            path = path + "/"
                    + String.format("%04d", Integer.valueOf(d.getYear()))
                    + String.format("%02d", Integer.valueOf(d.getMonthValue()))
                    + String.format("%02d", Integer.valueOf(d.getDayOfMonth()))
                    + String.format("%02d", Integer.valueOf(d.getHour()))
                    + String.format("%02d", Integer.valueOf(d.getMinute()))
                    + String.format("%02d", Integer.valueOf(d.getSecond()));
        }else{
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            Date d = new Date(System.currentTimeMillis());
            path = path + "/" + df.format(d);
        }
        return path;
    }
    public static void saveEvidence(String filename, String content){
        File f = new File(filename);
        f.delete();
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(filename, true);
            fileOutputStream.write(content.getBytes());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
