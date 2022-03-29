package com.example.firebasedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebasedemo.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("myData")

        if (auth.currentUser != null){
            binding.tvResult.text = auth.currentUser!!.email
        }else{
            binding.tvResult.text = "Logout"
        }

        binding.btnLogin.setOnClickListener(){
                login("bait2073.202201@gmail.com","123456")
        }

        binding.btnRegister.setOnClickListener(){
                register("bait2073.202201@gmail.com","123456")
        }
        binding.btnSignOut.setOnClickListener(){
            logout()
        }
        binding.btnInsert.setOnClickListener(){
            val student = Student("A123","Tan Ah Kao")
            insertNewStudent(student)
        }
        binding.btnRead.setOnClickListener(){
            getData("B123")
        }
    }
    private fun getData(id:String){
        database.child("Student").child(id).get()
            .addOnSuccessListener { student->
                binding.tvResult.text = student.child("name").value.toString()
            }
            .addOnFailureListener{ e->
                binding.tvResult.text = e.message

            }
    }

    private fun insertNewStudent(student:Student){
        database.child("Student")
            .child(student.id).setValue(student)
            .addOnSuccessListener {
                binding.tvResult.text = "New Student Added"
            }
            .addOnFailureListener{ e->
                binding.tvResult.text = e.message
            }
    }

    private fun register(email:String, psw:String){
        auth.createUserWithEmailAndPassword(email,psw)
            .addOnSuccessListener {
                binding.tvResult.text = "Successfully register  new user"
            }
            .addOnFailureListener{ e->
                binding.tvResult.text = e.message
            }
    }

    private fun login(email:String, psw:String){
        auth.signInWithEmailAndPassword(email,psw)
            .addOnSuccessListener {
                binding.tvResult.text = "Sign in successfully"
            }
            .addOnFailureListener{  e->
                binding.tvResult.text = e.message
            }
    }

    private fun logout(){
        auth.signOut()
        binding.tvResult.text = "Sign out"
    }
}