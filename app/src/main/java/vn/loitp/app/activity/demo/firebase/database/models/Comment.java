package vn.loitp.app.activity.demo.firebase.database.models;

import androidx.annotation.Keep;

import com.core.base.BaseModel;
import com.google.firebase.database.IgnoreExtraProperties;

// [START comment_class]
@IgnoreExtraProperties
@Keep
public class Comment extends BaseModel {

    public String uid;
    public String author;
    public String text;

    public Comment() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public Comment(String uid, String author, String text) {
        this.uid = uid;
        this.author = author;
        this.text = text;
    }

}
// [END comment_class]
