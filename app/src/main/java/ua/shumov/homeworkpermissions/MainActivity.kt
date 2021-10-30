package ua.shumov.homeworkpermissions

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import ua.shumov.homeworkpermissions.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    private val requestBackgroundLocationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                binding.tvStatusAccessBackgroundLocation.setTextColor(getColor(R.color.done))
                binding.tvStatusAccessBackgroundLocation.text = getText(R.string.granted)
            }
        }

    @RequiresApi(Build.VERSION_CODES.M)
    private val requestActivityRecognitionPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                binding.tvStatusActivityRecognition.setTextColor(getColor(R.color.done))
                binding.tvStatusActivityRecognition.text = getText(R.string.granted)
            }
        }

    @RequiresApi(Build.VERSION_CODES.M)
    private val requestReadCalendarPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                binding.tvStatusReadCalendarPermission.setTextColor(getColor(R.color.done))
                binding.tvStatusReadCalendarPermission.text = getText(R.string.granted)
            }
        }

    @RequiresApi(Build.VERSION_CODES.M)
    private val requestAllPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { it ->
            it.entries.forEach() {
                val isGranted = it.value
                if (isGranted) {
                    when (it.key) {
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION -> {
                            binding.tvStatusAccessBackgroundLocation.setTextColor(getColor(R.color.done))
                            binding.tvStatusAccessBackgroundLocation.text = getText(R.string.granted)
                        }
                        Manifest.permission.ACTIVITY_RECOGNITION -> {
                            with(binding) { tvStatusActivityRecognition.setTextColor(getColor(R.color.done)) }
                            binding.tvStatusActivityRecognition.text = getText(R.string.granted)
                        }
                        Manifest.permission.READ_CALENDAR -> {
                            binding.tvStatusReadCalendarPermission.setTextColor(getColor(R.color.done))
                            binding.tvStatusReadCalendarPermission.text = getText(R.string.granted)
                        }
                    }
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            val view = binding.root
            setContentView(view)

            val permissions = arrayOf(
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                Manifest.permission.ACTIVITY_RECOGNITION,
                Manifest.permission.READ_CALENDAR
        )

        binding.apply {
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                tvStatusAccessBackgroundLocation.setTextColor(getColor(R.color.done))
                tvStatusAccessBackgroundLocation.text = getText(R.string.granted)
            }

            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.ACTIVITY_RECOGNITION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                tvStatusActivityRecognition.setTextColor(getColor(R.color.done))
                tvStatusActivityRecognition.text = getText(R.string.granted)
            }

            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.READ_CALENDAR
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                tvStatusReadCalendarPermission.setTextColor(getColor(R.color.done))
                tvStatusReadCalendarPermission.text = getText(R.string.granted)
            }

            btnAccessBackgroundLocation.setOnClickListener {
                requestBackgroundLocationPermission(
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                    tvStatusAccessBackgroundLocation
                )
            }
            btnActivityRecognition.setOnClickListener {
                requestAnswerActivityRecognitionPermission(
                    Manifest.permission.ACTIVITY_RECOGNITION,
                    tvStatusActivityRecognition
                )
            }
            btnReadCalendar.setOnClickListener {
                requestReadCalendarPermission(
                    Manifest.permission.READ_CALENDAR,
                    tvStatusReadCalendarPermission
                )
            }
            btnGrantAll.setOnClickListener {
                requestAllPermissions(permissions)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestAllPermissions(permissions: Array<String>) {
        permissions.forEach{
            // if should show rationale
            if (shouldShowRequestPermissionRationale(it)) {
                Snackbar.make(
                    binding.root,
                    "We need you to grant all the permissions for app to work correctly.",
                    Snackbar.LENGTH_LONG
                ).setAction("GRANT") {
                    requestAllPermissions.launch(permissions)
                }.show()
            } else {
                requestAllPermissions.launch(permissions)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestBackgroundLocationPermission(
        permission: String,
        tvStatus: TextView
    ) {
        if (ContextCompat.checkSelfPermission(
                this, permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            tvStatus.setTextColor(getColor(R.color.not_provided))
            tvStatus.text = getText(R.string.not_granted)

            if (shouldShowRequestPermissionRationale(permission)) {
                Snackbar.make(
                    binding.root,
                    "We need you to grant a permission for app to work correctly.",
                    Snackbar.LENGTH_LONG
                ).setAction("GRANT") {
                    requestBackgroundLocationPermissionLauncher.launch(permission)
                }.show()
            } else { // if shouldn't, then just request
                requestBackgroundLocationPermissionLauncher.launch(permission)
            }
        } else { // if permission is granted
            tvStatus.setTextColor(getColor(R.color.done))
            tvStatus.text = getText(R.string.granted)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestAnswerActivityRecognitionPermission(
        permission: String,
        tvStatus: TextView
    ) {
        if (ContextCompat.checkSelfPermission(
                this, permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            tvStatus.setTextColor(getColor(R.color.not_provided))
            tvStatus.text = getText(R.string.not_granted)

            if (shouldShowRequestPermissionRationale(permission)) {
                Snackbar.make(
                    binding.root,
                    "We need you to grant a permission for app to work correctly.",
                    Snackbar.LENGTH_LONG
                ).setAction("GRANT") {
                    requestActivityRecognitionPermissionLauncher.launch(permission)
                }.show()
            } else {
                requestActivityRecognitionPermissionLauncher.launch(permission)
            }
        } else {
            tvStatus.setTextColor(getColor(R.color.done))
            tvStatus.text = getText(R.string.granted)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestReadCalendarPermission(
        permission: String,
        tvStatus: TextView
    ) {
        if (ContextCompat.checkSelfPermission(
                this, permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            tvStatus.setTextColor(getColor(R.color.not_provided))
            tvStatus.text = getText(R.string.not_granted)

            if (shouldShowRequestPermissionRationale(permission)) {
                Snackbar.make(
                    binding.root,
                    "We need you to grant a permission for app to work correctly.",
                    Snackbar.LENGTH_LONG
                ).setAction("GRANT") {
                    requestReadCalendarPermissionLauncher.launch(permission)
                }.show()
            } else {
                requestReadCalendarPermissionLauncher.launch(permission)
            }
        } else {
            tvStatus.setTextColor(getColor(R.color.done))
            tvStatus.text = getText(R.string.granted)
        }
    }
}