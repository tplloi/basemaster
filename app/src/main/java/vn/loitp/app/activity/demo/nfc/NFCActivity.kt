package vn.loitp.app.activity.demo.nfc

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.*
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.core.base.BaseFontActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_demo_nfc.*
import vn.loitp.app.R
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.*
import kotlin.experimental.and

class NFCActivity : BaseFontActivity() {
    private val tags: ArrayList<TagWrapper> = ArrayList()

    private var adapter: NfcAdapter? = null
    private var pendingIntent: PendingIntent? = null

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_demo_nfc
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentTagView.text = "Loading..."

        adapter = NfcAdapter.getDefaultAdapter(this)
    }

    override fun onResume() {
        super.onResume()
        if (adapter?.isEnabled == false) {
            Utils.showNfcSettingsDialog(this)
            return
        }
        if (pendingIntent == null) {
            pendingIntent = PendingIntent.getActivity(this, 0,
                    Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
            currentTagView.text = "Scan a tag"
        }
        adapter?.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        adapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("loitpp onNewIntent", "Discovered tag with intent $intent")
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        val tagId: String = Utils.bytesToHex(tag.id)
        val tagWrapper = TagWrapper(tagId)
        val misc = ArrayList<String>()
        misc.add("scanned at: " + Utils.now())
        val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        var tagData = ""
        if (rawMsgs != null) {
            val msg = rawMsgs[0] as NdefMessage
            val cardRecord = msg.records[0]
            tagData = try {
                readRecord(cardRecord.payload) ?: ""
            } catch (e: UnsupportedEncodingException) {
                Log.e("TagScan", e.message)
                return
            }
        }
        misc.add("tag data: $tagData")
        tagWrapper.techList.put("Misc", misc)
        for (tech in tag.techList) {
            val tech = tech.replace("android.nfc.tech.", "")
            val info = getTagInfo(tag, tech)
            tagWrapper.techList.put("Technology: $tech", info)
        }
        if (tags.size == 1) {
            Toast.makeText(this, "Swipe right to see previous tags", Toast.LENGTH_LONG).show()
        }
        tags.add(tagWrapper)
        Log.d("loitpp", "--> tags")
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val gson = Gson()
        tvResult.text = gson.toJson(tags)
    }

    @Throws(UnsupportedEncodingException::class)
    fun readRecord(payload: ByteArray): String? {
        //val textEncoding = if (payload[0] and 128 == 0) "UTF-8" else "UTF-16"
        val textEncoding = if (payload[0] and 0x80.toByte() == 0.toByte()) "UTF-8" else "UTF-16"

        val languageCodeLength: Int = (payload[0] and 63).toInt()
        return String(payload, languageCodeLength + 1, payload.size - languageCodeLength - 1, Charset.forName(textEncoding))
    }

    private fun getTagInfo(tag: Tag, tech: String): List<String> {
        val info: MutableList<String> = ArrayList()
        when (tech) {
            "NfcA" -> {
                info.add("aka ISO 14443-3A")
                val nfcATag = NfcA.get(tag)
                info.add("atqa: " + Utils.bytesToHexAndString(nfcATag.atqa))
                info.add("sak: " + nfcATag.sak)
                info.add("maxTransceiveLength: " + nfcATag.maxTransceiveLength)
            }
            "NfcF" -> {
                info.add("aka JIS 6319-4")
                val nfcFTag = NfcF.get(tag)
                info.add("manufacturer: " + Utils.bytesToHex(nfcFTag.manufacturer))
                info.add("systemCode: " + Utils.bytesToHex(nfcFTag.systemCode))
                info.add("maxTransceiveLength: " + nfcFTag.maxTransceiveLength)
            }
            "NfcV" -> {
                info.add("aka ISO 15693")
                val nfcVTag = NfcV.get(tag)
                info.add("dsfId: " + nfcVTag.dsfId)
                info.add("responseFlags: " + nfcVTag.responseFlags)
                info.add("maxTransceiveLength: " + nfcVTag.maxTransceiveLength)
            }
            "Ndef" -> {
                val ndefTag = Ndef.get(tag)
                var ndefMessage: NdefMessage? = null
                try {
                    ndefTag.connect()
                    ndefMessage = ndefTag.ndefMessage
                    ndefTag.close()
                    for (record in ndefMessage.records) {
                        val id = if (record.id.size == 0) "null" else Utils.bytesToHex(record.id)
                        info.add("record[" + id + "].tnf: " + record.tnf)
                        info.add("record[" + id + "].type: " + Utils.bytesToHexAndString(record.type))
                        info.add("record[" + id + "].payload: " + Utils.bytesToHexAndString(record.payload))
                    }
                    info.add("messageSize: " + ndefMessage.byteArrayLength)
                } catch (e: Exception) {
                    e.printStackTrace()
                    info.add("error reading message: $e")
                }
                val typeMap = HashMap<String, String>()
                typeMap[Ndef.NFC_FORUM_TYPE_1] = "typically Innovision Topaz"
                typeMap[Ndef.NFC_FORUM_TYPE_2] = "typically NXP MIFARE Ultralight"
                typeMap[Ndef.NFC_FORUM_TYPE_3] = "typically Sony Felica"
                typeMap[Ndef.NFC_FORUM_TYPE_4] = "typically NXP MIFARE Desfire"
                var type = ndefTag.type
                if (typeMap[type] != null) {
                    type += " (" + typeMap[type] + ")"
                }
                info.add("type: $type")
                info.add("canMakeReadOnly: " + ndefTag.canMakeReadOnly())
                info.add("isWritable: " + ndefTag.isWritable)
                info.add("maxSize: " + ndefTag.maxSize)
            }
            "NdefFormatable" -> info.add("nothing to read")
            "MifareUltralight" -> {
                val mifareUltralightTag = MifareUltralight.get(tag)
                info.add("type: " + mifareUltralightTag.type)
                info.add("tiemout: " + mifareUltralightTag.timeout)
                info.add("maxTransceiveLength: " + mifareUltralightTag.maxTransceiveLength)
            }
            "IsoDep" -> {
                info.add("aka ISO 14443-4")
                val isoDepTag = IsoDep.get(tag)
                info.add("historicalBytes: " + Utils.bytesToHexAndString(isoDepTag.historicalBytes))
                info.add("hiLayerResponse: " + Utils.bytesToHexAndString(isoDepTag.hiLayerResponse))
                info.add("timeout: " + isoDepTag.timeout)
                info.add("extendedLengthApduSupported: " + isoDepTag.isExtendedLengthApduSupported)
                info.add("maxTransceiveLength: " + isoDepTag.maxTransceiveLength)
            }
            else -> info.add("unknown tech!")
        }
        return info
    }
}