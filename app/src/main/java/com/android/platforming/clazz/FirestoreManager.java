package com.android.platforming.clazz;

import android.util.Log;

import androidx.annotation.NonNull;

import com.android.platforming.interfaze.ListenerInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreManager {
    private static FirebaseFirestore firestore = null;
    private static FirebaseAuth firebaseAuth = null;

    public static void setFirestore(FirebaseFirestore firestore) {
        FirestoreManager.firestore = firestore;
    }

    public static FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public static void setFirebaseAuth(FirebaseAuth firebaseAuth) {
        FirestoreManager.firebaseAuth = firebaseAuth;
    }

    public void readUserData(ListenerInterface interfaze){
        DocumentReference documentReference = firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid());

        documentReference.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot documentSnapshot = task.getResult();

                if(documentSnapshot.exists()){
                    Log.w("setUserData", "Document exist",task.getException());
                    Map<String, Object> data = documentSnapshot.getData();
                    User.setUser(new User(firebaseAuth.getCurrentUser().getUid(), firebaseAuth.getCurrentUser().getEmail(), data));
                }
                else{
                    Log.w("setUserData", "Document doesn't exist");
                }

                interfaze.onSuccess();
            }else{
                Log.w("setUserData", "Failed with: ",task.getException());
                interfaze.onFail();
            }
        });
    }

    public void writeUserData(Map<String, Object> data, ListenerInterface interfaze){
        DocumentReference documentReference = firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid());

        documentReference.set(data).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                interfaze.onSuccess();
            }
            else{
                interfaze.onFail();
            }
        });
    }

    public void updateUserData(Map<String, Object> data, ListenerInterface interfaze){
        DocumentReference documentReference = firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid());
        documentReference.update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    interfaze.onSuccess();
                }
            }
        });
    }

    public void readPostData(String workName, ListenerInterface interfaze){
        FirebaseFirestore.getInstance().collection(workName).orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<Post> posts = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        posts.add(new Post(documentSnapshot.getId(), documentSnapshot.getData()));
                    }
                    Post.getPosts().clear();
                    Post.getPosts().addAll(posts);
                    interfaze.onSuccess();
                }
            }
        });
    }

    public void writePostData(String workName, Map<String, Object> data, ListenerInterface interfaze){
        firestore.collection(workName).document().set(data).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                interfaze.onSuccess();
            }
            else{
                interfaze.onFail();
            }
        });
    }

    public void readCommentData(String workName, Post post, ListenerInterface listenerInterface){
        firestore.collection(workName).document(post.getId()).collection("comments").orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<Comment> comments = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        comments.add(new Comment(documentSnapshot.getData()));
                    }
                    post.getComments().clear();
                    post.getComments().addAll(comments);
                    listenerInterface.onSuccess();
                }
            }
        });
    }

    public void writeCommentData(String workName, String id, Map<String, Object> data, ListenerInterface listenerInterface){
        Log.w("collection", "workName:" + workName + " | id:"+id);
        firestore.collection(workName).document(id).collection("comments").document().set(data).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                listenerInterface.onSuccess();
            }
            else{
                listenerInterface.onFail();
            }
        });
    }
}
