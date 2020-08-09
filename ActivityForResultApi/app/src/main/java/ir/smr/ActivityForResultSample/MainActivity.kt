package ir.smr.ActivityForResultSample

import android.Manifest.permission
import android.app.Activity
import android.app.usage.StorageStats
import android.app.usage.StorageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.audiofx.EnvironmentalReverb
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.storage.StorageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider.getUriForFile
import androidx.core.net.toUri
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // request Location Permission
        btn_requestPermission.setOnClickListener {
            requestPermissionLauncher.launch(permission.ACCESS_FINE_LOCATION)
        }

        // request for camera, Read Sms, Read Contacts
        btn_requestMultiplePermission.setOnClickListener {
            requestMultiplePermissionLauncher.launch(
                arrayOf(
                    permission.CAMERA,
                    permission.READ_SMS,
                    permission.READ_CONTACTS
                )
            )
        }

        // taking picture from camera
        btn_capturePicture.setOnClickListener {

            if (isAllPermissionGranted(
                    arrayOf(
                        permission.CAMERA,
                        permission.READ_EXTERNAL_STORAGE,
                        permission.WRITE_EXTERNAL_STORAGE
                    )
                )
            ) {
                takePictureLauncher.launch(getFileUri())
            } else
                requestMultiplePermissionLauncher.launch(
                    arrayOf(
                        permission.CAMERA,
                        permission.WRITE_EXTERNAL_STORAGE,
                        permission.READ_EXTERNAL_STORAGE
                    )
                )
        }

        // change Activity
        btn_startActivity.setOnClickListener {

            changeActivityLauncher.launch(
                Intent(this, SecondActivity::class.java)
            )
        }

        // pick image file
        btn_customContract_pickFile.setOnClickListener {

            customPickContentContractLauncher.launch("audio/*")
        }

    }

    private fun isAllPermissionGranted(permissions: Array<String>): Boolean {

        permissions.forEach {
            if (
                ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
            ) return false
        }

        return true
    }

    private fun getFileUri(): Uri {

        var image: File? = null
        val dir = getExternalFilesDir(null)

        dir?.apply {
            if (exists() && isDirectory) {
                image = File("$dir/imageTest.jpg")
                image!!.createNewFile()
            } else {
                dir.mkdir()
                getFileUri()
            }
        }

        return getUriForFile(this, "ir.smr.ActivityForResultSample.fileprovider", image!!)

    }

    private val requestPermissionLauncher =
        registerForActivityResult(RequestPermission()) { isGranted: Boolean ->

            if (isGranted) toast("Permission Granted")
            else toast("Permission Denied")

        }

    private val requestMultiplePermissionLauncher =
        registerForActivityResult(RequestMultiplePermissions()) { result: Map<String, Boolean> ->

            result.forEach {
                if (it.value == false)
                    toast("permission ${it.key} Denied")
                else
                    toast("permission ${it.key} Granted")

            }

        }

    private val takePictureLauncher =
        registerForActivityResult(TakePicture()) { result: Boolean ->
            if (result) toast("successful")
            else toast("Unsuccessful")
        }

    private val changeActivityLauncher =
        registerForActivityResult(StartActivityForResult()) { activity: ActivityResult ->

            if (activity.resultCode.isOk()) {
                val data = activity.data?.extras?.getString("ex_key")
                toast(data!!)
            } else
                toast("failed")
        }

    private val customPickContentContractLauncher =
        registerForActivityResult(CustomPickContentContract()) { uri: Uri ->

            if (uri.path?.isNotEmpty()!!)
                toast("file path : ${uri.path}")
        }


    private fun Activity.toast(message: String) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }

    private fun Any.log(message: String) = Log.d("TAG", message)
    private fun String.toLog() = Log.d("TAG", this)


    private fun Int.isOk() = this == Activity.RESULT_OK

}

class CustomPickContentContract : ActivityResultContract<String, Uri>() {

    override fun createIntent(context: Context, fileType: String?) =
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = fileType
        }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri {

        if (resultCode != Activity.RESULT_OK)
            return Uri.EMPTY

        return intent?.data!!
    }

}
