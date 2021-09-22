package com.exampledeveloper.me.testprojectforclass

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import java.io.*
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.experimental.and

//Number 1
//Check nullability
fun Any?.isNull() = this == null
//--Ex:
//if (something.isNull()){}
//--end



//Number2
//formatting a Double to decimal format
fun Double.toPriceAmount(): String {
    val dec = DecimalFormat("###,###,###.00")
    return dec.format(this)
}
//--Ex:
//println(12.0.toPriceAmount())   =>    12.00
//--end



//Number3
//formatting a string to decimal format
fun String.toPriceAmount(): String {
    val dec = DecimalFormat("###,###,###.00")
    return dec.format(this.toDouble())
}
//--Ex:
//println("05".toPriceAmount())  =>    5.00
//--end



//Number4
//Date Format
private val TIME_STAMP_FORMAT = "EEEE, MMMM d, yyyy - hh:mm:ss a"
private val DATE_FORMAT = "yyyy-MM-dd"

fun Long.getTimeStamp(): String {
    val date = Date(this)
    val simpleDateFormat = SimpleDateFormat(TIME_STAMP_FORMAT, Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getDefault()
    return simpleDateFormat.format(date)
}

fun Long.getYearMonthDay(): String {
    val date = Date(this)
    val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getDefault()
    return simpleDateFormat.format(date)
}

@Throws(ParseException::class)
fun String.getDateUnixTime(): Long {
    try {
        val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        simpleDateFormat.timeZone = TimeZone.getDefault()
        return simpleDateFormat.parse(this)!!.time
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    throw ParseException("Please Enter a valid date", 0)
}

//--Ex:
//val currentTime = System.currentTimeMillis()
//println(currentTime.getTimeStamp())      => Sunday, September 20, 2020 - 10:48:26 AM
//println(currentTime.getYearMonthDay())   =>2020-09-20
//println("2020-09-20".getDateUnixTime())  =>1600549200000
//--end




//Number5
//ObjectSerializer
@Throws(IOException::class)
fun Serializable.serialize(): String {
    val serialObj = ByteArrayOutputStream()
    val objStream = ObjectOutputStream(serialObj)
    objStream.writeObject(this)
    objStream.close()
    return encodeBytes(serialObj.toByteArray())
}

@Throws(IOException::class, ClassNotFoundException::class)
fun String?.deserialize(): Any? {
    if (this.isNullOrEmpty()) return null
    val serialObj = ByteArrayInputStream(decodeBytes(this))
    val objStream = ObjectInputStream(serialObj)
    return objStream.readObject()
}

private fun encodeBytes(bytes: ByteArray): String {
    val strBuf = StringBuffer()
    for (i in bytes.indices) {
        strBuf.append(((bytes[i].toInt() shr 4 and 0xF) + 'a'.toInt()).toChar())
        strBuf.append(((bytes[i] and 0xF) + 'a'.toInt()).toChar())
    }
    return strBuf.toString()
}

private fun decodeBytes(str: String): ByteArray {
    val bytes = ByteArray(str.length / 2)
    var i = 0
    while (i < str.length) {
        var c = str[i]
        bytes[i / 2] = (c - 'a' shl 4).toByte()
        c = str[i + 1]
        bytes[i / 2] = bytes[i / 2].plus(((c - 'a'))).toByte()
        i += 2
    }
    return bytes
}
//--Ex:
//val kotlinExtention = "Kotlin Extensions ObjectSerializer"
//val serializedString = kotlinExtention.serialize()
//println(serializedString)
//println(serializedString.deserialize() as String)
// =>
//kmonaaafheaaccelgphegmgjgocaefhihegfgohdgjgpgohdcaepgcgkgfgdhefdgfhcgjgbgmgjhkgfhc
//Kotlin Extensions ObjectSerializer
//--end



//Number6
//Get Simple Permissions
fun Context.hasPermissions(vararg permissions: String) = permissions.all { permission ->
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}
//--Ex:
//hasPermissions(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION)
//--end




//Number7
//Copy to clipboard
fun Context.copyToClipboard(content: String) {
    val clipboardManager = ContextCompat.getSystemService(this, ClipboardManager::class.java)!!
    val clip = ClipData.newPlainText("clipboard", content)
    clipboardManager.setPrimaryClip(clip)
}
//--Ex:
//copyToClipboard("Test")
//--end