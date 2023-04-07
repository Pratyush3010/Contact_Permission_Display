package com.example.contact_permission_display

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private var handler = Handler(Looper.getMainLooper())
    @SuppressLint("MissingInflatedId", "Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var currentprogress : Int = 0
        var RcvContact = findViewById<RecyclerView>(R.id.RcvContact)
        var progres = findViewById<ProgressBar>(R.id.progress)
        var BtnRead = findViewById<Button>(R.id.btnread)
        enableRuntimePermission()
        RcvContact.layoutManager = LinearLayoutManager(this)
       // var draw : Drawable = Drawable(R.drawable.custom_progress_bar)


        BtnRead.setOnClickListener {
    // for runtime permission


            val contactList : ArrayList<Contact_Modal> = arrayListOf()

            val contacts = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)


            progres.max = 100

            currentprogress = currentprogress + 10
            progres.setProgress(currentprogress)
            if (contacts != null) {
                while (contacts.moveToNext()){



                    val name = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val number = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    var photouri = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
                    val obj = Contact_Modal()
                    obj.name = name
                    obj.number = number
                    if (photouri != null){
                        obj.image = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(photouri))
                    }
                    contactList.add(obj)



                }
                contacts.close()
            }
            RcvContact.adapter = ContactAdapter(contactList,this)
        }
    }

    private fun enableRuntimePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this@MainActivity,
                android.Manifest.permission.READ_CONTACTS
        )){

            Toast.makeText(this@MainActivity, "Please Enable contact Permission fromSetting", Toast.LENGTH_SHORT).show()

             val intent : Intent  = Intent().setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            var uri : Uri = Uri.fromParts("package",packageName,null)
            intent.setData(uri)
            startActivity(intent)
        }

        else{
            ActivityCompat.requestPermissions(this@MainActivity,
                arrayOf(android.Manifest.permission.READ_CONTACTS), RequestPermissionCode
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RequestPermissionCode -> if(grantResults.size>0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this@MainActivity, "Permission Denied", Toast.LENGTH_SHORT).show()

                enableRuntimePermission()
            }
        }
    }



    companion object{
        const val RequestPermissionCode = 111
    }
}