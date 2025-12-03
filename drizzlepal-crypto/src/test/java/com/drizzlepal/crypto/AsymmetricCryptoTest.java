package com.drizzlepal.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;

import org.junit.jupiter.api.Test;

import com.drizzlepal.crypto.asymmetric.AsymmetricAlgorithmName;
import com.drizzlepal.crypto.asymmetric.AsymmetricAlgorithms;
import com.drizzlepal.crypto.asymmetric.KeyPairFactory;
import com.drizzlepal.crypto.exception.CryptoErrorException;
import com.drizzlepal.crypto.exception.InvalidKeyAlgorithmException;

public class AsymmetricCryptoTest {

    private static final String PLAIN_TEXT = "This is a testasssssssssssdfakdsjfnalkdnf;lakjnsdlkansdfjanpdsfn"
            + "a;dkca2fa1s3d5f16a5sd1fas5d1f3a5s1df35a1sd3f5a1s3d5f13a5sd1f3a51wef"
            + "qfwfjqkmdlaknlfmkdaokofnkfmaosf12kemo232tjni34nvsldkmfaokdmsofpasdkj"
            + "nfam,dsfooapdsnfasdfasdfvwgbgfcdsgbtefasf  rgs 324v wgfq   w fafdd g"
            + "qfdssklhopio4jvmpfiniapjo啊看到；罚款带你飞阿九独食难肥麦娜丝电极法阿萨法哪怕是对"
            + "方可能阿婆萨弗拉斯带你飞拍摄放屁文件福临万家额放屁解放脾气甲方奇葩国家屁封号地方盆倾瓮"
            + "瀽福娃放假奇葩微积分文凭isdncaksdm;ansdfpansdf;lkasodpfnkal;sdknfasjdnfakdsml;"
            + "kanmsdkfnasjidnfiqwnpoadskmkjansdpjncapsodnpunfe[poqm[weofm'wekdmpiqen29378r"
            + "h3823ui4nrkef90r8hwub8ih3njk44oirgtibe9fu8h8uybuihwkjnrmlglrgfmvsnjhbiudyah9"
            + "43q0jpgktmlnjbhifsdfajnkwrl3m4opg90bhj9uifshgk mklvfpojia0hfnuijkdsjhbifuhoq"
            + "拉克丝迪菲娜看到你阿里斯顿会计法哪怕是打卡机不怕is的技能爬架打牌觉得v爬架擦啤酒肚擦大家夹啊圣诞快乐cals"
            + "asodnfaopdsj pajdn oajpijd vajpd vpidj PONDOEWJC [ONEW    [COIencojcsd aijcvn[SDOVKL CPOC N[Pijv kncaoppdjkopvjik ]]]]"
            + "aspdionvo aadf inoadkaooa sdojadfo ii哦啊阿九哦啊的哦啊甲方奥到付件傲娇傲娇发阿萨德阿达啊"
            + " 阿帆阿帆阿道夫发阿道夫阿道夫阿帆阿道夫不过我哥呢日本 in哦婆娘【】奥卡福哦啊甲方大佛假发票剿匪记阿帆"
            + " 阿帆阿道夫奥法泡芙买房哦啊附加费鳌峰路； 傲娇安抚巾奥法奥发动机奥减肥的【】打个卡v奥日女诶绕开发哦阿道夫"
            + " 阿道夫懒狗家发哦放假哦啊辅导猫皮带女啊颗粒剂嗲傲娇发奖阿皮讲课费奥法啊佛教啊佛教啊【of安抚巾安抚巾阿帆阿帆"
            + " 阿帆阿道夫大佛服了你【 哦佛你今儿【日文版IE破减肥呢饿哦弄佛心【企鹅哦批发价夹排风机奥迪及拉断阀破解屁艰苦奋斗答复】"
            + " 爱番番哦哪怕付娜佛那皮肤阿帆派件费阿福缴费阿帆阿帆啊服务日 我突然文件调和油手高手低发货价提款机一天也染头发深V的放哪天涯敏锐冷酷又"
            + "jp4309gt8ghubihj 3h4frgevbuygsu9hawo3nt4lkgrnjvfoihubsijknfljaodsbifhudviuohiqlknvjb]]";

    @Test
    public void test() throws CryptoErrorException, InvalidKeyAlgorithmException {
        KeyPair keyPair = KeyPairFactory.generateKeyPair(AsymmetricAlgorithms.RSA4096);
        byte[] decryptBytes = AsymmetricCrypto.encryptBytes(
                PLAIN_TEXT.getBytes(StandardCharsets.UTF_8), keyPair.getPublic(), AsymmetricAlgorithmName.RSA);
        byte[] decryptBytes2 = AsymmetricCrypto.decryptBytes(decryptBytes, keyPair.getPrivate(),
                AsymmetricAlgorithmName.RSA);
        String string = new String(decryptBytes2, StandardCharsets.UTF_8);
        assertEquals(PLAIN_TEXT, string);
    }

}
