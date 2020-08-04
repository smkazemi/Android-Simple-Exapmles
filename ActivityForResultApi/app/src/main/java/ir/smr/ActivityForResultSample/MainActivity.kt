package ir.smr.ActivityForResultSample

import android.Manifest.permission
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

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

            if (ContextCompat.checkSelfPermission(
                    this,
                    permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            )
            // TODO : create the file and put the uri below
                takePictureLauncher.launch(Uri.EMPTY)
            else
                requestPermissionLauncher.launch(permission.CAMERA)
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
                toast("image path : ${uri.path}")
        }


    private fun Activity.toast(message: String) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }

    private fun Int.isOk() = this == Activity.RESULT_OK

}

class CustomPickContentContract : ActivityResultContract<String, Uri>() {

    override fun createIntent(context: Context, imageType: String?) =
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = imageType
        }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri {

        if (resultCode != Activity.RESULT_OK)
            return Uri.EMPTY

        return intent?.data!!
    }

}
