package fr.oworld.vigiTorrent.data.tools

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import fr.oworld.vigiTorrent.MyApp
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception


class FirebaseTools {
    companion object {
        var kErrorInt = -999
        var kSignalementPath = "/Signalements/"

        fun removeOnStorage(name: String) {
            FirebaseTools.removeLocalFile(name)

            val storageRef = FirebaseStorage.getInstance()
            val fileRef = storageRef.getReference(name)

            // Delete the file
            fileRef.delete().addOnSuccessListener {
                // File deleted successfully
            }.addOnFailureListener {
                // Uh-oh, an error occurred!
            }
        }

        fun removeLocalFile(name :String) {
            //get the documents directory:
            if(MyApp.context == null){
                return
            }

            val path = MyApp.context!!.getFilesDir().path
            val file = File(path + name)

            file.delete()
        }

        fun saveOnStorage(data: ByteArray?, name: String, completion: ((url: String?) -> Unit)? = null) {
            if (data == null || data.isEmpty()) return

            writeFileOnLocalStorage(name, data)
            val storageRef = FirebaseStorage.getInstance()
            val fileRef = storageRef.getReference(name)

            // Create file metadata including the content type
            var builder = StorageMetadata.Builder()

            if (name.endsWith("jpeg")){
                builder.contentType = "image/jpg"
            } else {
                builder.contentType = "text/html"
            }
            var metadata = builder.build()

            val uploadTask = fileRef.putBytes(data, metadata)
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
                if(completion != null) {
                    completion(null)
                }
            }.addOnSuccessListener { taskSnapshot ->
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                val downloadUrl = taskSnapshot.getMetadata()?.path
                println("url :" + downloadUrl)
                if(completion != null) {
                    completion(downloadUrl)
                }
            }
        }

        fun getDatasFromStorage(path: String, completion: (bytes: ByteArray?) -> Unit) {
            if (path.equals("", ignoreCase = true)){
                completion(null)
                return
            }

            val storageRef = FirebaseStorage.getInstance()
            val fileRef = storageRef.getReference(path)

            fileRef.getBytes((3 * 1024 * 1024).toLong())
                .addOnSuccessListener { bytes -> completion(bytes) }
                .addOnFailureListener {
                    val err = it.toString()
                    if (err == "com.google.firebase.storage.StorageException: Object does not exist at location.") {
                        completion(ByteArray(0))
                    } else {
    //                    val pathLocal = (MyApp.context)!!.getFilesDir().path
    //                    val file = File(pathLocal + path)
    //                    val bytes = file.readBytes()
    //                    completion(bytes)
    //                    try {
    //
    //                    } catch (e : Exception){
    //                        completion(null)
    //                    }
                    }
                }
        }

        fun writeFileOnLocalStorage(fileName: String, data: ByteArray?): Uri? {
            if(MyApp.context == null){
                return null
            }

            val path = MyApp.context!!.getFilesDir().path
            val file = File(path + fileName)

            try {
                if (!file.exists()) {
                    val path = File(file.parent)
                    path.mkdirs()
                    file.createNewFile();
                }
                val fos = FileOutputStream(file)
                fos.write(data)
                fos.close()
                return Uri.fromFile(file)

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        fun getBitmap(data: ByteArray?): Bitmap? {
            if (data != null && data?.size != 0) {
                val options = BitmapFactory.Options()
                options.inMutable = true
                return BitmapFactory.decodeByteArray(data, 0, data.size, options)
            }
            return null
        }
    }


}
