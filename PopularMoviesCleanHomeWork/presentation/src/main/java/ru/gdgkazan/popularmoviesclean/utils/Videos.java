package ru.gdgkazan.popularmoviesclean.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import ru.gdgkazan.popularmoviesclean.domain.model.Video;

/**
 * @author Artur Vasilov
 */
public final class Videos {

    private static final String YOUTUBE = "https://www.youtube.com/watch?v=";

    private Videos() {
    }

    public static void browseVideo(@NonNull Context context, @NonNull Video video) {
        String videoUrl = YOUTUBE + video.getKey();

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        }
        catch (Exception e)
        {
            e.getMessage();
        }

    }

}
