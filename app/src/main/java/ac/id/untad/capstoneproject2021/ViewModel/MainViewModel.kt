package ac.id.untad.capstoneproject2021.ViewModel

import ac.id.untad.capstoneproject2021.Model.User
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class MainViewModel : ViewModel(){

    private lateinit var user: FirebaseUser
    private val report = MutableLiveData<Int>()
    var userProfile = User()

    fun loadData(){
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseDatabase = FirebaseDatabase.getInstance()

        if (firebaseAuth.currentUser != null){
            user = FirebaseAuth.getInstance().currentUser!!
        } else {
            report.postValue(0)
        }

        val databaseReference = firebaseDatabase.getReference().child("Akun").child(user.uid)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userProfile = snapshot.getValue(
                    User::class.java
                )!!
            }

            override fun onCancelled(error: DatabaseError) {
                report.postValue(0)
            }
        })
    }

    fun getData(): User = userProfile

}