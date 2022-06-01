package com.android.platforming.object;

import android.util.Log;

import com.android.platforming.interfaze.ListenerInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
                    User.setUser(new User(datas));
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
}
