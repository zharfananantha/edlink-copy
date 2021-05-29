package com.example.edlink

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.edlink.model.Posting
import com.example.edlink.model.Users
import com.example.edlink.utilities.ModelPreferencesManager
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_add_posting.*
import kotlinx.android.synthetic.main.fragment_home.toolbar
import java.util.*


class AddPostingActivity : AppCompatActivity() {
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var database: DatabaseReference
    private val pickImage = 100
    private var imageUri: Uri? = null
    private lateinit var userPosting: Users

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_posting)
        toolbar.title = "Add Posting"
        database = Firebase.database.reference
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()
        ModelPreferencesManager.with(application)
        val userData = ModelPreferencesManager.get<Users>("user")
        if (userData != null) {
            userPosting = userData
        }

        pickImgBtn.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        saveBtn.setOnClickListener {
            val text = contentTextInput.text
//            Log.d("CEK", "Content Text $text, " +
//                    "Content Image URI $imageUri, Date now ${Calendar.getInstance().time}" +
//                    ", Date now in Millis ${Calendar.getInstance().timeInMillis}")
//            Log.d("CEK", "USERDATA ${userPosting.email}")
            savedata()
        }
    }

    private fun savedata() {
        val id = database.push().key.toString()
        val userId = userPosting.id
        val userFullName = userPosting.fullname
        val dateNow = Calendar.getInstance().timeInMillis.toString()
        val text = contentTextInput.text.toString()
        var downloadUri: Uri? = null

        if (text.isEmpty()) {
            return Toast.makeText(this, "Silahkan isi Teks Posting terlebih dahulu",Toast.LENGTH_LONG).show()
        } else {
            if (imageUri !== null) {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                val ref = storageReference?.child("images/" + UUID.randomUUID().toString())
                val uploadTask = ref?.putFile(imageUri!!)

                val urlTask = uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation ref.downloadUrl
                })?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        downloadUri = task.result
                        progressDialog.dismiss()
                        doPosting(id, userId, userFullName, dateNow, text, downloadUri.toString())
                    } else {
                        // Handle failures
                    }
                }?.addOnFailureListener{

                }
            } else {
                doPosting(id, userId, userFullName, dateNow, text, "")
            }
        }


    }

    private fun doPosting(id: String, userId: String, userFullName: String, dateNow: String, text: String, imageString: String) {
        val postPosting = Posting(id, userId, userFullName, dateNow, text, imageString)
        database.child("postings").child(id).setValue(postPosting).addOnCompleteListener {
            Toast.makeText(this, "Successs Input Data", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            contentImageInput.setImageURI(imageUri)
        }
    }
}