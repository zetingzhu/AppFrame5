
key: zztappkey.jks
key store password:android
key alias:android
key password:android


D:\ZZTAndroid\Android_Work\AppFrame5>keytool -list -v -keystore D:\ZZTAndroid\Android_Work\AppFrame5\dev\zztappkey.jks
输入密钥库口令:
密钥库类型: PKCS12
密钥库提供方: SUN

您的密钥库包含 1 个条目

别名: android
创建日期: 2021-7-15
条目类型: PrivateKeyEntry
证书链长度: 1
证书[1]:
所有者: CN=zzt
发布者: CN=zzt
序列号: 515807bb
有效期为 Thu Jul 15 15:01:29 CST 2021 至 Sat Jun 21 15:01:29 CST 2121
证书指纹:
         MD5:  A1:E6:74:33:26:70:22:9E:65:6B:34:4A:24:92:95:53
         SHA1: BB:B2:88:5D:F5:A7:90:CC:AC:7A:FA:F7:86:66:1F:74:B9:A9:4A:2A
         SHA256: D2:A3:C7:5A:72:AA:C7:7D:E2:8A:05:AC:A6:77:99:F3:5E:96:08:6C:66:1F:14:75:3C:D2:E0:A5:6B:34:84:42
签名算法名称: SHA256withRSA
主体公共密钥算法: 2048 位 RSA 密钥
版本: 3

扩展:

#1: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: 89 65 8B E1 5F 16 2D 2B   02 16 EA AA E1 EA 95 F3  .e.._.-+........
0010: BE 9C 26 EF                                        ..&.
]
]



*******************************************
*******************************************



D:\ZZTAndroid\Android_Work\AppFrame5>
