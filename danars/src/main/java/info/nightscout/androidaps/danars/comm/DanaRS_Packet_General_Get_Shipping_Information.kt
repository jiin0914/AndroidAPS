package info.nightscout.androidaps.danars.comm

import dagger.android.HasAndroidInjector
import info.nightscout.androidaps.logging.LTag
import info.nightscout.androidaps.dana.DanaRPump
import info.nightscout.androidaps.danars.encryption.BleEncryption
import javax.inject.Inject

class DanaRS_Packet_General_Get_Shipping_Information(
    injector: HasAndroidInjector
) : DanaRS_Packet(injector) {

    @Inject lateinit var danaRPump: DanaRPump

    init {
        opCode = BleEncryption.DANAR_PACKET__OPCODE_REVIEW__GET_SHIPPING_INFORMATION
        aapsLogger.debug(LTag.PUMPCOMM, "New message")
    }

    override fun handleMessage(data: ByteArray) {
        if (data.size < 18) {
            failed = true
            return
        } else failed = false
        var dataIndex = DATA_START
        var dataSize = 10
        danaRPump.serialNumber = stringFromBuff(data, dataIndex, dataSize)
        dataIndex += dataSize
        dataSize = 3
        danaRPump.shippingDate = dateFromBuff(data, dataIndex)
        dataIndex += dataSize
        dataSize = 3
        danaRPump.shippingCountry = asciiStringFromBuff(data, dataIndex, dataSize)
        aapsLogger.debug(LTag.PUMPCOMM, "Serial number: " + danaRPump.serialNumber)
        aapsLogger.debug(LTag.PUMPCOMM, "Shipping date: " + dateUtil.dateAndTimeString(danaRPump.shippingDate))
        aapsLogger.debug(LTag.PUMPCOMM, "Shipping country: " + danaRPump.shippingCountry)
    }

    override fun getFriendlyName(): String {
        return "REVIEW__GET_SHIPPING_INFORMATION"
    }
}