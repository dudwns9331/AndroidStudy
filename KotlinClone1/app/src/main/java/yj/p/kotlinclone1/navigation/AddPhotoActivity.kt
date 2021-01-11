package yj.p.kotlinclone1.navigation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add_photo.*
import yj.p.kotlinclone1.R
import yj.p.kotlinclone1.navigation.model.ContentDTO
import java.text.SimpleDateFormat
import java.util.*

class AddPhotoActivity : AppCompatActivity() {

    var PICK_IMAGE_FROM_ALBUM = 0
    var storage: FirebaseStorage? = null
    var photoUrl: Uri? = null

    var auth: FirebaseAuth? = null
    var firestore: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)

        // Initiate storage, auth, firestore form Firebase
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Open the album
        var photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)

        addphoto_btn_upload.setOnClickListener {
            contentUpload()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_FROM_ALBUM) {
            if (resultCode == Activity.RESULT_OK) {
                // This is path to the selected image

                photoUrl = data?.data
                addphoto_image.setImageURI(photoUrl)

            } else {
                // Exit the addPhotoActivity if you leave the album without selecting it
                finish()
            }
        }
    }

    fun contentUpload() {
        // Make file name

        var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imageFileName = "IMAGE_" + timestamp + "_.png"

        var storageRef = storage?.reference?.child("images")?.child(imageFileName)

        // Promise method

        storageRef?.putFile(photoUrl!!)?.continueWithTask {
            return@continueWithTask storageRef.downloadUrl
        }?.addOnSuccessListener { uri ->
            var contentDTO = ContentDTO()

            // Insert downloadUrl of image
            contentDTO.imageUri = uri.toString()
            // Insert uid of user
            contentDTO.uid = auth?.currentUser?.uid
            // Insert userId
            contentDTO.userId = auth?.currentUser?.email
            // Insert explain of content
            contentDTO.explain = addphoto_edit_explain.text.toString()
            // Insert timestamp
            contentDTO.timestamp = System.currentTimeMillis().toString()

            firestore?.collection("images")?.document()?.set(contentDTO)

            setResult(Activity.RESULT_OK)
            finish()

        }


        /*        // Callback method
        storageRef?.putFile(photoUrl!!)?.addOnSuccessListener { uri ->
            var contentDTO = ContentDTO()

            // Insert downloadUrl of image
            contentDTO.imageUri = uri.toString()
            // Insert uid of user
            contentDTO.uid = auth?.currentUser?.uid
            // Insert userId
            contentDTO.uid = auth?.currentUser?.email
            // Insert explain of content
            contentDTO.explain = addphoto_edit_explain.text.toString()
            // Insert timestamp
            contentDTO.timestamp = System.currentTimeMillis().toString()

            firestore?.collection("images")?.document()?.set(contentDTO)

            setResult(Activity.RESULT_OK)
            finish()

        } */
    }
}