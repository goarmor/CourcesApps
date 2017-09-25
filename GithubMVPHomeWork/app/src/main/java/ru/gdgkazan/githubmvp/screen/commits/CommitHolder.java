package ru.gdgkazan.githubmvp.screen.commits;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.content.Commit;

/**
 * Created by DIMON on 08.09.2017.
 */

public class CommitHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.commitAuthor)
    TextView mAuthor;

    @BindView(R.id.commitMessage)
    TextView mMessage;

    public CommitHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull Commit commit) {
        mAuthor.setText(commit.getAuthor().getAuthorName());
        mMessage.setText(commit.getMessage());
    }
}
