package com.dcen.wallet.dellet.core.sign

import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.AC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ACC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ACT
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ADA
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ADF
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.AIB
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.AION
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ARA
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ARG
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ARK
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ASK
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ATN
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ATP
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.AUR
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.AXE
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BANANO
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BBC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BCA
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BCD
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BCH
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BCO
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BCS
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BCX
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BEET
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BELA
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BIFI
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BIGUP
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BIO
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BIQ
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BIS
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BITB
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BITG
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BLK
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BLOCK
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BOPO
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BOXY
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BPA
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BRK
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BSD
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BSQ
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BTA
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BTC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BTC2X
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BTCD
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BTCP
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BTCS
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BTCZ
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BTC_TEST_NET
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BTF
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BTG
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BTM
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BTN
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BTP
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BTQ
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BTV
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BTW
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BTX
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BURST
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.BUZZ
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.CCN
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.CDN
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.CDY
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.CLAM
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.CLC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.CLO
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.CLUB
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.CMP
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.CMT
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.CNMC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.CRAVE
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.CRW
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.CRX
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.DBIC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.DCR
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.DEO
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.DFC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.DGB
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.DGC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.DLC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.DMD
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.DNR
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.DOGE
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.DOPE
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.DSH
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.EAST
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.EC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ECN
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.EDRC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.EFL
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.EGEM
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ELLA
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.EMC2
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.EOS
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.EOSC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ERC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ESN
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ETF
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ETH
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ETP
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ETSC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.EVO
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.EVT
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.EXCL
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.EXP
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.FCT
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.FIC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.FJC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.FLASH
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.FLO
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.FRST
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.FTC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.GAME
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.GB
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.GBX
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.GCR
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.GNX
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.GO
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.GOD
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.GRC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.GRS
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.HNC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.HODL
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.HSR
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.HTML
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.HUSH
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.HYC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.IOTA
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.IXC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.JBS
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.KMD
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.KOBO
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.KOTO
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.LAX
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.LBC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.LBTC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.LCC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.LCH
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.LINX
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.LSK
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.LTC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.MARS
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.MBRS
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.MEM
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.MIX
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.MNX
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.MOIN
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.MONA
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.MTR
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.MUE
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.MUSIC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.MXT
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.MZC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.NANO
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.NAS
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.NAV
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.NBT
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.NEBL
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.NEET
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.NEO
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.NEOS
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.NIM
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.NLC2
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.NLG
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.NMC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.NRG
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.NRO
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.NSR
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.NVC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.NXT
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.NYC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ODN
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.OMNI
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ONE
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ONT
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ONX
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.PART
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.PHR
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.PIGGY
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.PINK
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.PIRL
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.PIVX
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.PKB
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.PND
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.POA
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.POLIS
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.POT
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.PPC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.PTC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.PUT
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.PWR
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.QRK
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.QTUM
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.QVT
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.RAP
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.RBY
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.RDD
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.RIC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.RICHX
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.RIN
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ROGER
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ROI
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.RPT
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.RSK
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.RVN
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.SAFE
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.SBC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.SBTC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.SDC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.SH
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.SHM
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.SHR
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.SLR
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.SMART
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.SMLY
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.SSC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.SSN
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.STAK
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.START
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.STEEM
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.STO
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.STRAT
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.SYS
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.TES
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.THC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.TOA
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.TPC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.TRC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.TRX
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.UBQ
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.UC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.UFO
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.UNIFY
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.UNO
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.USC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.VAR
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.VASH
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.VC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.VET
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.VIA
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.VIPS
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.VIVO
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.VOX
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.VPN
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.VTC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.WAN
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.WAVES
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.WBTC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.WC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.WHL
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.WICC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.XCH
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.XCP
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.XEM
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.XFE
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.XLM
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.XMR
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.XMX
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.XMY
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.XPM
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.XRB
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.XRD
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.XRP
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.XSEL
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.XST
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.XTZ
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.XUEZ
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.XVG
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.XWC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.XZC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.YAP
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ZCL
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ZEC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ZEN
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ZEST
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ZNY
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ZOOM
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ZRC
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.ZYD
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.kUSD
import com.dcen.wallet.dellet.core.sign.core.ECKeyPair
import com.dcen.wallet.dellet.core.sign.ethereum.EthereumRawTransaction
import com.dcen.wallet.dellet.core.sign.ethereum.EthereumSigner
import java.math.BigInteger

/**
 * Created by「 The Khaeng 」on 06 Aug 2018 :)
 */
class SignerManager private constructor() {

    companion object {
        val INSTANCE: SignerManager by lazy { SignerManager() }
    }

    fun sign(coinType: Int,
             privateKey: ByteArray,
            /* Eth signer */
             nonce: BigInteger = BigInteger.ZERO,
             gasPrice: BigInteger = BigInteger.ZERO,
             gasLimit: BigInteger = BigInteger.ZERO,
             to: String = "",
             value: BigInteger = BigInteger.ZERO,
             data: String = "",
             chainId: Byte? = null
    ): ByteArray {

        // TODO: contribute mapping coin signer
        when (coinType) {
            BTC -> {
            }
            BTC_TEST_NET -> {
            }
            LTC -> {
            }
            DOGE -> {
            }
            RDD -> {
            }
            DSH -> {
            }
            NMC -> {
            }
            PPC -> {
            }
            FTC -> {
            }
            XCP -> {
            }
            BLK -> {
            }
            NSR -> {
            }
            NBT -> {
            }
            MZC -> {
            }
            VIA -> {
            }
            XCH -> {
            }
            RBY -> {
            }
            GRS -> {
            }
            DGC -> {
            }
            CCN -> {
            }
            DGB -> {
            }
            MONA -> {
            }
            CLAM -> {
            }
            XPM -> {
            }
            NEOS -> {
            }
            JBS -> {
            }
            ZRC -> {
            }
            VTC -> {
            }
            NXT -> {
            }
            BURST -> {
            }
            MUE -> {
            }
            ZOOM -> {
            }
            VPN -> {
            }
            CDN -> {
            }
            SDC -> {
            }
            PKB -> {
            }
            PND -> {
            }
            START -> {
            }
            MOIN -> {
            }
            EXP -> {
            }
            EMC2 -> {
            }
            DCR -> {
            }
            XEM -> {
            }
            PART -> {
            }
            ARG -> {
            }
            SHR -> {
            }
            GCR -> {
            }
            NVC -> {
            }
            AC -> {
            }
            BTCD -> {
            }
            DOPE -> {
            }
            TPC -> {
            }
            AIB -> {
            }
            EDRC -> {
            }
            SYS -> {
            }
            SLR -> {
            }
            SMLY -> {
            }
            ETH ->
                return EthereumSigner().generateTransactionHash(
                        EthereumRawTransaction(nonce, gasPrice, gasLimit, to, value, data),
                        ECKeyPair.create(privateKey),
                        chainId)

            CMP -> {
            }
            CRW -> {
            }
            BELA -> {
            }
            VASH -> {
            }
            FJC -> {
            }
            MIX -> {
            }
            XVG -> {
            }
            EFL -> {
            }
            CLUB -> {
            }
            RICHX -> {
            }
            POT -> {
            }
            QRK -> {
            }
            TRC -> {
            }
            GRC -> {
            }
            AUR -> {
            }
            IXC -> {
            }
            NLG -> {
            }
            BITB -> {
            }
            BTA -> {
            }
            XMY -> {
            }
            BSD -> {
            }
            UNO -> {
            }
            MTR -> {
            }
            GB -> {
            }
            SHM -> {
            }
            CRX -> {
            }
            BIQ -> {
            }
            EVO -> {
            }
            STO -> {
            }
            BIGUP -> {
            }
            GAME -> {
            }
            DLC -> {
            }
            ZYD -> {
            }
            DBIC -> {
            }
            STRAT -> {
            }
            SH -> {
            }
            MARS -> {
            }
            UBQ -> {
            }
            PTC -> {
            }
            NRO -> {
            }
            ARK -> {
            }
            USC -> {
            }
            THC -> {
            }
            LINX -> {
            }
            ECN -> {
            }
            DNR -> {
            }
            PINK -> {
            }
            PIGGY -> {
            }
            PIVX -> {
            }
            FLASH -> {
            }
            ZEN -> {
            }
            PUT -> {
            }
            ZNY -> {
            }
            UNIFY -> {
            }
            XST -> {
            }
            BRK -> {
            }
            VC -> {
            }
            XMR -> {
            }
            VOX -> {
            }
            NAV -> {
            }
            FCT -> {
            }
            EC -> {
            }
            ZEC -> {
            }
            LSK -> {
            }
            STEEM -> {
            }
            XZC -> {
            }
            RSK -> {
            }
            RPT -> {
            }
            LBC -> {
            }
            KMD -> {
            }
            BSQ -> {
            }
            RIC -> {
            }
            XRP -> {
            }
            BCH -> {
            }
            NEBL -> {
            }
            ZCL -> {
            }
            XLM -> {
            }
            NLC2 -> {
            }
            WHL -> {
            }
            ERC -> {
            }
            DMD -> {
            }
            BTM -> {
            }
            BIO -> {
            }
            XWC -> {
            }
            BTG -> {
            }
            BTC2X -> {
            }
            SSN -> {
            }
            TOA -> {
            }
            BTX -> {
            }
            ACC -> {
            }
            ELLA -> {
            }
            PIRL -> {
            }
            XRB -> {
            }
            VIVO -> {
            }
            FRST -> {
            }
            HNC -> {
            }
            BUZZ -> {
            }
            MBRS -> {
            }
            HSR -> {
            }
            HTML -> {
            }
            ODN -> {
            }
            ONX -> {
            }
            RVN -> {
            }
            GBX -> {
            }
            BTCZ -> {
            }
            POA -> {
            }
            NYC -> {
            }
            MXT -> {
            }
            WC -> {
            }
            MNX -> {
            }
            BTCP -> {
            }
            MUSIC -> {
            }
            BCA -> {
            }
            CRAVE -> {
            }
            STAK -> {
            }
            WBTC -> {
            }
            LCH -> {
            }
            EXCL -> {
            }
            LCC -> {
            }
            XFE -> {
            }
            EOS -> {
            }
            TRX -> {
            }
            KOBO -> {
            }
            HUSH -> {
            }
            BANANO -> {
            }
            ETF -> {
            }
            OMNI -> {
            }
            BIFI -> {
            }
            UFO -> {
            }
            CNMC -> {
            }
            NRG -> {
            }
            RIN -> {
            }
            ATP -> {
            }
            EVT -> {
            }
            ATN -> {
            }
            BIS -> {
            }
            NEET -> {
            }
            BOPO -> {
            }
            BOXY -> {
            }
            FLO -> {
            }
            BITG -> {
            }
            ASK -> {
            }
            SMART -> {
            }
            XUEZ -> {
            }
            VAR -> {
            }
            NIM -> {
            }
            UC -> {
            }
            NANO -> {
            }
            ZEST -> {
            }
            ONE -> {
            }
            SBC -> {
            }
            GNX -> {
            }
            ARA -> {
            }
            RAP -> {
            }
            BLOCK -> {
            }
            MEM -> {
            }
            AION -> {
            }
            PHR -> {
            }
            KOTO -> {
            }
            XRD -> {
            }
            YAP -> {
            }
            BCS -> {
            }
            EAST -> {
            }
            ACT -> {
            }
            SSC -> {
            }
            BTW -> {
            }
            BEET -> {
            }
            QVT -> {
            }
            VET -> {
            }
            CLO -> {
            }
            ADF -> {
            }
            NEO -> {
            }
            XSEL -> {
            }
            LBTC -> {
            }
            BCD -> {
            }
            BTN -> {
            }
            ONT -> {
            }
            BBC -> {
            }
            CMT -> {
            }
            ETSC -> {
            }
            CDY -> {
            }
            DFC -> {
            }
            HYC -> {
            }
            BCX -> {
            }
            XTZ -> {
            }
            ADA -> {
            }
            TES -> {
            }
            CLC -> {
            }
            VIPS -> {
            }
            XMX -> {
            }
            EGEM -> {
            }
            HODL -> {
            }
            POLIS -> {
            }
            EOSC -> {
            }
            QTUM -> {
            }
            ETP -> {
            }
            DEO -> {
            }
            NAS -> {
            }
            ROI -> {
            }
            IOTA -> {
            }
            AXE -> {
            }
            FIC -> {
            }
            GO -> {
            }
            BPA -> {
            }
            SAFE -> {
            }
            ROGER -> {
            }
            BTV -> {
            }
            BTQ -> {
            }
            SBTC -> {
            }
            BTP -> {
            }
            BTF -> {
            }
            GOD -> {
            }
            PWR -> {
            }
            ESN -> {
            }
            BTCS -> {
            }
            WICC -> {
            }
            LAX -> {
            }
            BCO -> {
            }
            WAN -> {
            }
            WAVES -> {
            }
            kUSD -> {
            }
        }

        return ByteArray(0)
    }
}