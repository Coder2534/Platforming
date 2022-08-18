package com.android.platforming.clazz;

import android.util.Log;

import com.android.platforming.interfaze.ListenerInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;
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
                    Map<String, Object> datas = documentSnapshot.getData();
                    User.setUser(new User(firebaseAuth.getCurrentUser().getUid(), datas));
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
    public void readCommunityMsgCount(ListenerInterface interfaze ,String workName ,String count){
        DocumentReference documentReference = firestore.collection("community").document(workName).collection("chat").document(count);

        documentReference.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot documentSnapshot = task.getResult();

                if(documentSnapshot.exists()){
                    Log.w("MsgCountData", "Document exist",task.getException());
                    Map<String, Object> datas = documentSnapshot.getData();
                    User.setUser(new User(firebaseAuth.getCurrentUser().getUid(), datas));
                }
                else{
                    Log.w("MsgCountData", "Document doesn't exist");
                }

                interfaze.onSuccess();
            }else{
                Log.w("MsgCountData", "Failed with: ",task.getException());
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

    public void writePostData(String workName, Map<String, Object> data, ListenerInterface interfaze){
        DocumentReference documentReference = firestore.collection("noticeboard").document(workName).collection("post").document();

        documentReference.set(data).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                interfaze.onSuccess();
            }
            else{
                interfaze.onFail();
            }
        });
    }


    public void writeMagData(String workName, Map<String, Object> data, ListenerInterface interfaze){
        DocumentReference documentReference = firestore.collection("community").document(workName).collection("chat").document();

        documentReference.set(data).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                interfaze.onSuccess();
            }
            else{
                interfaze.onFail();
            }
        });
    }}
