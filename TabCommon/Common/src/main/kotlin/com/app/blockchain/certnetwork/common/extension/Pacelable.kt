package com.app.blockchain.certnetwork.common.extension

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

/**
 * Created by「 The Khaeng 」on 01 Apr 2018 :)
 */

interface KParcelable : Parcelable {
    override fun describeContents() = 0
    override fun writeToParcel(dest: Parcel, flags: Int)
}

// Creator factory functions

fun <T : Parcelable> Bundle.putParcelableList(key: String, value: List<T>?) =
        this.putParcelableArrayList(key, ArrayList(value ?: kotlin.collections.listOf()))

inline fun <reified T> parcelableCreator(crossinline create: (Parcel) -> T) =
        object : Parcelable.Creator<T> {
            override fun createFromParcel(source: Parcel) = create(source)
            override fun newArray(size: Int) = arrayOfNulls<T>(size)
        }

inline fun <reified T> parcelableClassLoaderCreator(
        crossinline create: (Parcel, ClassLoader) -> T) =
        object : Parcelable.ClassLoaderCreator<T> {
            override fun createFromParcel(source: Parcel, loader: ClassLoader) =
                    create(source, loader)

            override fun createFromParcel(source: Parcel) =
                    createFromParcel(source, T::class.java.classLoader)

            override fun newArray(size: Int) = arrayOfNulls<T>(size)
        }

// Parcel extensions

fun Parcel.readBoolean() = readInt() != 0
fun Parcel.writeBoolean(value: Boolean) = writeInt(if (value) 1 else 0)
fun Parcel.readBooleanList(): List<Boolean> = readPrimitiveList { readBoolean() }
fun Parcel.writeBooleanList(value: List<Boolean>) {
    writeInt(value.size)
    value.forEach {
        writeBoolean(it)
    }
}

fun Parcel.readChar(): Char = readString().single()
fun Parcel.writeChar(value: Char) = writeString(value.toString())
fun Parcel.readCharList(): List<Char> = readPrimitiveList { readChar() }
fun Parcel.writeCharList(value: List<Char>) {
    writeInt(value.size)
    value.forEach {
        writeChar(it)
    }
}

fun Parcel.readByteArrayV2(): ByteArray = readPrimitiveByteArray { readByte() }
fun Parcel.writeByteArrayV2(value: ByteArray) {
    writeInt(value.size)
    value.forEach {
        writeByte(it)
    }
}

fun Parcel.readByteList(): List<Byte> = readPrimitiveList { readByte() }
fun Parcel.writeByteList(value: List<Byte>) {
    writeInt(value.size)
    value.forEach {
        writeByte(it)
    }
}


fun Parcel.readShort() = readInt().toShort()
fun Parcel.writeShort(value: Short) = writeInt(value.toInt())
fun Parcel.readShortList(): List<Short> = readPrimitiveList { readShort() }
fun Parcel.writeShortList(value: List<Short>) {
    writeInt(value.size)
    value.forEach {
        writeShort(it)
    }
}

fun Parcel.readIntList(): List<Int> = readPrimitiveList { readInt() }
fun Parcel.writeIntList(value: List<Int>) {
    writeInt(value.size)
    value.forEach {
        writeInt(it)
    }
}

fun Parcel.readFloatList(): List<Float> = readPrimitiveList { readFloat() }
fun Parcel.writeFloatList(value: List<Float>) {
    writeInt(value.size)
    value.forEach {
        writeFloat(it)
    }
}

fun Parcel.readDoubleList(): List<Double> = readPrimitiveList { readDouble() }
fun Parcel.writeDoubleList(value: List<Double>) {
    writeInt(value.size)
    value.forEach {
        writeDouble(it)
    }
}

fun Parcel.readLongList(): List<Long> = readPrimitiveList { readLong() }
fun Parcel.writeLongList(value: List<Long>) {
    writeInt(value.size)
    value.forEach {
        writeLong(it)
    }
}

fun Parcel.readStringList(): List<String> = readPrimitiveList { readString() }

inline fun <reified T : Enum<T>> Parcel.readEnum() =
        readInt().let { if (it >= 0) enumValues<T>()[it] else null }

fun <T : Enum<T>> Parcel.writeEnum(value: T?) =
        writeInt(value?.ordinal ?: -1)

inline fun <T> Parcel.readPrimitiveList(reader: () -> T): List<T> {
    val size = readInt()
    val shortList = mutableListOf<T>()
    for (i in 0 until size) {
        shortList.add(reader())
    }
    return shortList
}

inline fun Parcel.readPrimitiveByteArray(reader: () -> Byte): ByteArray {
    val size = readInt()
    val byteArray = ByteArray(size)
    for (i in 0 until size) {
        byteArray[i] = reader()
    }
    return byteArray
}


inline fun <T> Parcel.readNullable(reader: () -> T) =
        if (readInt() != 0) reader() else null

inline fun <T> Parcel.writeNullable(value: T?, writer: (T) -> Unit) {
    if (value != null) {
        writeInt(1)
        writer(value)
    } else {
        writeInt(0)
    }
}

fun Parcel.readDate() =
        readNullable { Date(readLong()) }

fun Parcel.writeDate(value: Date?) =
        writeNullable(value) { writeLong(it.time) }

fun Parcel.readBigInteger() =
        readNullable { BigInteger(createByteArray()) } ?: BigInteger.ZERO

fun Parcel.writeBigInteger(value: BigInteger?) =
        writeNullable(value) { writeByteArray(it.toByteArray()) }

fun Parcel.readBigDecimal() =
        readNullable { BigDecimal(BigInteger(createByteArray()), readInt()) } ?: BigDecimal.ZERO

fun Parcel.writeBigDecimal(value: BigDecimal?) = writeNullable(value) {
    writeByteArray(it.unscaledValue().toByteArray())
    writeInt(it.scale())
}


fun Parcel.readMapString() =
        readNullable {
            val mapStringSize = this.readInt()
            val mapString = HashMap<String, String>(mapStringSize)
            for (i in 0 until mapStringSize) {
                val key = this.readString()
                val value = this.readString()
                mapString[key] = value
            }
            mapString
        }

fun Parcel.writeMapString(mapString: Map<String, String>) =
        writeNullable(mapString) {
            writeInt(mapString.size)
            for ((key, value) in mapString) {
                writeString(key)
                writeString(value)
            }
        }


fun Parcel.readListMapString() =
        readNullable {
            val listSize = this.readInt()
            val listObject = arrayListOf<Map<String, String>>()
            for (i in 0 until listSize) {
                listObject.add(readMapString() ?: mapOf())
            }
            listObject
        }

fun Parcel.writeListMapString(value: List<Map<String, String>>) =
        writeNullable(value) {
            writeInt(value.size)
            value.forEach {
                writeMapString(it)
            }

        }

fun <T : Parcelable> Parcel.readTypedObjectCompat(c: Parcelable.Creator<T>) =
        readNullable { c.createFromParcel(this) }

fun <T : Parcelable> Parcel.writeTypedObjectCompat(value: T?, parcelableFlags: Int) =
        writeNullable(value) { it.writeToParcel(this, parcelableFlags) }
