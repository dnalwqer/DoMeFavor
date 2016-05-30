package com.cs165.domefavor.domefavor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Alex on 16/5/30.
 */
public class Mail {
    public static void send(Context context){
        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto:gaixuetong@gmail.com"));
        data.putExtra(Intent.EXTRA_SUBJECT, "这是标题");
        data.putExtra(Intent.EXTRA_TEXT, "这是内容");
        context.startActivity(data);
    }
}
